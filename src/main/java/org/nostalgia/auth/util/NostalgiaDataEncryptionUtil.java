package org.nostalgia.auth.util;

import lombok.experimental.UtilityClass;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * Utility class for data encryption and decryption operations.
 * This class provides methods to encrypt and decrypt data using a salt-based approach.
 */
@UtilityClass
public class NostalgiaDataEncryptionUtil {

    /**
     * The prefix of the salt used for encryption and decryption operations.
     */
    private static final String PREFIX_OF_SALT = "VGs3VkUzNml0TEVNVjJWbmw3UXA3SUg4Um5vbjc1RlY=";

    /**
     * The postfix of the salt used for encryption and decryption operations.
     */
    private static final String POSTFIX_OF_SALT = "Nm5Pa1ZIaEJOVmQzamFuYW5EZ05qdW5UUzJJbERDME4K";

    /**
     * Encrypts the given raw data using the default salt.
     *
     * @param rawData the raw data to be encrypted
     * @return the encrypted data
     */
    public static String encrypt(final String rawData) {
        return encrypt(rawData, PREFIX_OF_SALT, POSTFIX_OF_SALT);
    }

    /**
     * Encrypts the given raw data using the provided prefix and postfix of the salt.
     *
     * @param rawData       the raw data to be encrypted
     * @param prefixOfSalt  the prefix of the salt to be used
     * @param postfixOfSalt the postfix of the salt to be used
     * @return the encrypted data
     */
    public static String encrypt(final String rawData,
                                 final String prefixOfSalt,
                                 final String postfixOfSalt) {

        byte[] encodedBytes = Base64.getEncoder().encode(rawData.getBytes(StandardCharsets.UTF_8));
        String encodedData = new String(encodedBytes, StandardCharsets.UTF_8);
        return prefixOfSalt.concat(encodedData).concat(postfixOfSalt);
    }

    /**
     * Decrypts the given encrypted data using the default salt.
     *
     * @param encryptedData the encrypted data to be decrypted
     * @return the decrypted data
     */
    public static String decrypt(final String encryptedData) {
        return decrypt(encryptedData, PREFIX_OF_SALT, POSTFIX_OF_SALT);
    }

    /**
     * Decrypts the given encrypted data using the provided prefix and postfix of the salt.
     *
     * @param encryptedData the encrypted data to be decrypted
     * @param prefixOfSalt  the prefix of the salt used during encryption
     * @param postfixOfSalt the postfix of the salt used during encryption
     * @return the decrypted data
     */
    public static String decrypt(final String encryptedData,
                                 final String prefixOfSalt,
                                 final String postfixOfSalt) {

        String decodedData = encryptedData
                .replace(prefixOfSalt, "")
                .replace(postfixOfSalt, "");
        byte[] decodedBytes = Base64.getDecoder().decode(decodedData.getBytes(StandardCharsets.UTF_8));
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

}