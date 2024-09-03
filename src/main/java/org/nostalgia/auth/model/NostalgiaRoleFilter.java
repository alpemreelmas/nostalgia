package org.nostalgia.auth.model;

import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.collections4.CollectionUtils;
import org.nostalgia.auth.model.entity.NostalgiaRoleEntity;
import org.nostalgia.auth.model.enums.NostalgiaRoleStatus;
import org.nostalgia.common.model.NostalgiaFilter;
import org.nostalgia.common.util.validation.Name;
import org.springframework.data.jpa.domain.Specification;

import java.util.Set;

/**
 * Filter criteria for querying roles in the application.
 * <p>
 * The {@link NostalgiaRoleFilter} class allows the construction of dynamic query specifications
 * for roles, based on the provided filter parameters. It implements the {@link AysFilter} interface,
 * which requires the implementation of the {@link NostalgiaRoleFilter#toSpecification()} method.
 * </p>
 * <p>
 * This filter supports filtering by role name and statuses. When converting to a
 * {@link Specification}, it combines the criteria for name and statuses using logical AND operations.
 * </p>
 *
 * @see NostalgiaRoleEntity
 * @see NostalgiaFilter
 * @see Specification
 */
@Getter
@Setter
@Builder
public class NostalgiaRoleFilter implements NostalgiaFilter {

    @Name
    @Size(min = 2, max = 255)
    private String name;
    private Set<NostalgiaRoleStatus> statuses;
    private String institutionId;

    /**
     * Converts the current filter criteria into a {@link Specification} for querying roles.
     * <p>
     * This method builds a {@link Specification} based on the filter properties. It uses
     * the role name for partial matching (case-insensitive) and filters by the provided
     * role statuses. If no statuses are specified, this criterion is not included in the
     * final specification.
     * </p>
     *
     * @return a {@link Specification} object representing the query criteria based on the current filter.
     */
    @Override
    public Specification<NostalgiaRoleEntity> toSpecification() {

        Specification<NostalgiaRoleEntity> specification = Specification.where(null);

        specification = specification.and((root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("institutionId"), this.institutionId));

        if (this.name != null) {
            specification = specification.and((root, query, criteriaBuilder) ->
                    criteriaBuilder.like(criteriaBuilder.upper(root.get("name")), "%" + this.name.toUpperCase() + "%"));
        }

        if (!CollectionUtils.isEmpty(this.statuses)) {

            Specification<NostalgiaRoleEntity> statusSpecification = this.statuses.stream()
                    .map(status -> (Specification<NostalgiaRoleEntity>) (root, query, criteriaBuilder) ->
                            criteriaBuilder.equal(root.get("status"), status))
                    .reduce(Specification::or)
                    .orElse(null);

            specification = specification.and(statusSpecification);
        }

        return specification;
    }

}
