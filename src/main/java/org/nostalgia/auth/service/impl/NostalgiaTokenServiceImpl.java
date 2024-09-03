package org.nostalgia.auth.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.RequiredTypeException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.nostalgia.auth.config.NostalgiaTokenConfigurationParameter;
import org.nostalgia.auth.model.NostalgiaToken;
import org.nostalgia.auth.model.enums.NostalgiaTokenClaims;
import org.nostalgia.auth.service.NostalgiaTokenService;
import org.nostalgia.auth.util.exception.NostalgiaTokenNotValidException;
import org.nostalgia.common.util.NostalgiaListUtil;
import org.nostalgia.common.util.NostalgiaRandomUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * AYS Token service to handle with JWT processes
 */
@Slf4j
@Service
@RequiredArgsConstructor
class NostalgiaTokenServiceImpl implements NostalgiaTokenService {

    private final NostalgiaTokenConfigurationParameter tokenConfiguration;

    /**
     * Generates an access token and a refresh token based on the provided claims.
     *
     * @param claims The claims to be included in the tokens.
     * @return AysToken object containing the access token and refresh token.
     */
    @Override
    public NostalgiaToken generate(final Claims claims) {

        final long currentTimeMillis = System.currentTimeMillis();

        final JwtBuilder tokenBuilder = this.initializeTokenBuilder(currentTimeMillis);

        final Date accessTokenExpiresAt = DateUtils.addMinutes(
                new Date(currentTimeMillis), tokenConfiguration.getAccessTokenExpireMinute()
        );
        final String accessToken = tokenBuilder
                .id(NostalgiaRandomUtil.generateUUID())
                .expiration(accessTokenExpiresAt)
                .claims(claims)
                .compact();

        final Date refreshTokenExpiresAt = DateUtils.addDays(
                new Date(currentTimeMillis), tokenConfiguration.getRefreshTokenExpireDay()
        );
        final String refreshToken = tokenBuilder
                .id(NostalgiaRandomUtil.generateUUID())
                .expiration(refreshTokenExpiresAt)
                .claim(NostalgiaTokenClaims.USER_ID.getValue(), claims.get(NostalgiaTokenClaims.USER_ID.getValue()))
                .compact();

        return NostalgiaToken.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .refreshToken(refreshToken)
                .build();
    }


    /**
     * Generates an access token based on the provided claims and refresh token.
     *
     * @param claims       The claims to be included in the access token.
     * @param refreshToken The refresh token.
     * @return AysToken object containing the generated access token and refresh token.
     */
    @Override
    public NostalgiaToken generate(final Claims claims, final String refreshToken) {

        final long currentTimeMillis = System.currentTimeMillis();

        final Date accessTokenExpiresAt = DateUtils.addMinutes(
                new Date(currentTimeMillis), tokenConfiguration.getAccessTokenExpireMinute()
        );
        final String accessToken = this.initializeTokenBuilder(currentTimeMillis)
                .id(NostalgiaRandomUtil.generateUUID())
                .expiration(accessTokenExpiresAt)
                .claims(claims)
                .compact();

        return NostalgiaToken.builder()
                .accessToken(accessToken)
                .accessTokenExpiresAt(accessTokenExpiresAt.toInstant().getEpochSecond())
                .refreshToken(refreshToken)
                .build();
    }

    /**
     * Initializes a JwtBuilder for creating a JSON Web Token (JWT) with the specified current time.
     *
     * @param currentTimeMillis The current time in milliseconds to be used as the "issued at" claim.
     * @return JwtBuilder instance configured with default and provided settings.
     * <p>
     * The JWT will have the following claims set:
     * - Header with the token type set to Bearer.
     * - Issuer claim set to the configured issuer from the token configuration.
     * - Issued At (iat) claim set to the specified current time.
     * - Signature configured with the private key from the token configuration.
     */
    private JwtBuilder initializeTokenBuilder(long currentTimeMillis) {
        return Jwts.builder()
                .header()
                .type(OAuth2AccessToken.TokenType.BEARER.getValue())
                .and()
                .issuer(tokenConfiguration.getIssuer())
                .issuedAt(new Date(currentTimeMillis))
                .signWith(tokenConfiguration.getPrivateKey());
    }


    /**
     * Verifies and validates the given JWT (JSON Web Token).
     * This method parses the token using the public key from the {@link NostalgiaTokenConfigurationParameter},
     * and throws a {@link NostalgiaTokenNotValidException} if the token is not valid due to being malformed, expired or having an invalid signature.
     *
     * @param token The JWT (JSON Web Token) to be verified and validated.
     * @throws NostalgiaTokenNotValidException If the token is not valid due to being malformed, expired or having an invalid signature.
     */
    @Override
    public void verifyAndValidate(String token) {
        try {
            final Jws<Claims> claims = Jwts.parser()
                    .verifyWith(tokenConfiguration.getPublicKey())
                    .build()
                    .parseSignedClaims(token);

            final JwsHeader header = claims.getHeader();
            if (!OAuth2AccessToken.TokenType.BEARER.getValue().equals(header.getType())) {
                throw new RequiredTypeException(token);
            }

            if (!Jwts.SIG.RS256.getId().equals(header.getAlgorithm())) {
                throw new SignatureException(token);
            }

        } catch (MalformedJwtException | ExpiredJwtException | SignatureException | RequiredTypeException exception) {
            throw new NostalgiaTokenNotValidException(token, exception);
        }
    }

    /**
     * Parses the given JWT and returns its claims as a {@link Claims} object.
     *
     * @param token the JWT string to parse
     * @return the parsed JWT claims as a {@link Claims} object
     */
    @Override
    public Claims getPayload(String token) {
        return Jwts.parser()
                .verifyWith(tokenConfiguration.getPublicKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    /**
     * Retrieves the authentication object {@link UsernamePasswordAuthenticationToken} based on the provided token.
     * This method parses the token using the public key from the {@link NostalgiaTokenConfigurationParameter} and extracts the necessary information from the token claims,
     * such as user type and roles, to construct the authentication object.
     *
     * @param token The token string used for authentication.
     * @return The constructed {@link UsernamePasswordAuthenticationToken} object.
     */
    @Override
    public UsernamePasswordAuthenticationToken getAuthentication(String token) {

        Jws<Claims> claims = Jwts.parser()
                .verifyWith(tokenConfiguration.getPublicKey())
                .build()
                .parseSignedClaims(token);

        JwsHeader header = claims.getHeader();
        Claims payload = claims.getPayload();

        final Jwt jwt = new Jwt(
                token,
                payload.getIssuedAt().toInstant(),
                payload.getExpiration().toInstant(),
                Map.of(
                        NostalgiaTokenClaims.TYPE.getValue(), header.getType(),
                        NostalgiaTokenClaims.ALGORITHM.getValue(), header.getAlgorithm()
                ),
                payload
        );

        final List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        final List<String> permissions = NostalgiaListUtil.to(payload.get(NostalgiaTokenClaims.USER_PERMISSIONS.getValue()), String.class);
        permissions.forEach(permission -> authorities.add(new SimpleGrantedAuthority(permission)));

        return UsernamePasswordAuthenticationToken.authenticated(jwt, null, authorities);
    }
}
