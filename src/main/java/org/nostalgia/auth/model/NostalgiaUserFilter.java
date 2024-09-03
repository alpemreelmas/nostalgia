package org.nostalgia.auth.model;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.nostalgia.auth.model.entity.NostalgiaUserEntity;
import org.nostalgia.auth.model.enums.NostalgiaUserStatus;
import org.nostalgia.common.model.NostalgiaFilter;
import org.nostalgia.common.model.NostalgiaPhoneNumber;
import org.nostalgia.common.util.validation.Name;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Set;

/**
 * Filter criteria for querying users in the application.
 * <p>
 * The {@link NostalgiaUserFilter} class allows the construction of dynamic query specifications
 * for users, based on the provided filter parameters. It implements the {@link NostalgiaFilter} interface,
 * which requires the implementation of the {@link NostalgiaUserFilter#toSpecification()} method.
 * </p>
 * <p>
 * When converting to a {@link Specification}, it combines the criteria using logical AND operations.
 * </p>
 *
 * @see NostalgiaUserEntity
 * @see NostalgiaFilter
 * @see Specification
 */
@Getter
@Setter
@Builder
public class NostalgiaUserFilter implements NostalgiaFilter {

    @Name
    @Size(min = 2, max = 100)
    private String firstName;

    @Name
    @Size(min = 2, max = 100)
    private String lastName;

    @Size(min = 2, max = 255)
    private String emailAddress;

    private NostalgiaPhoneNumber phoneNumber;

    private Set<NostalgiaUserStatus> statuses;

    @Name
    @Size(min = 2, max = 100)
    private String city;

    private String institutionId;


    /**
     * Converts the current filter criteria into a {@link Specification} for querying users.
     * <p>
     * This method builds a {@link Specification} based on the filter properties. It uses
     * the first name and last name for partial matching (case-insensitive), filters by the provided
     * user statuses, phone number, city, and institution ID. If no specific filter properties are specified,
     * those criteria are not included in the final specification.
     * </p>
     *
     * @return a {@link Specification} object representing the query criteria based on the current filter.
     */
    @Override
    public Specification<NostalgiaUserEntity> toSpecification() {

        Specification<NostalgiaUserEntity> specification = Specification.where(null);

        specification = specification.and((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("institutionId"), this.institutionId));

        if (!CollectionUtils.isEmpty(this.statuses)) {

            Specification<NostalgiaUserEntity> statusSpecification = this.statuses.stream()
                    .map(status -> (Specification<NostalgiaUserEntity>) (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("status"), status))
                    .reduce(Specification::or)
                    .orElse(null);

            specification = specification.and(statusSpecification);
        }

        if (this.firstName != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.upper(root.get("firstName")), "%" + this.firstName.toUpperCase() + "%"));
        }

        if (this.lastName != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.upper(root.get("lastName")), "%" + this.lastName.toUpperCase() + "%"));
        }

        if (this.emailAddress != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.upper(root.get("emailAddress")), "%" + this.emailAddress.toUpperCase() + "%"));
        }

        if (this.phoneNumber != null && StringUtils.hasText(this.phoneNumber.getCountryCode())) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("countryCode"), "%" + this.phoneNumber.getCountryCode() + "%"));
        }

        if (this.phoneNumber != null && StringUtils.hasText(this.phoneNumber.getLineNumber())) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(root.get("lineNumber"), "%" + this.phoneNumber.getLineNumber() + "%"));
        }

        if (this.city != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.upper(root.get("city")), "%" + this.city.toUpperCase() + "%"));
        }

        return specification;

    }

}

