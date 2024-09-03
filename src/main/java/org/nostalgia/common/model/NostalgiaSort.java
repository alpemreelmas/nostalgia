package org.nostalgia.common.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.domain.Sort;

import java.util.List;

/**
 * Represents a custom sorting configuration that integrates with Spring Data's sorting functionality.
 * This class wraps a list of {@link NostalgiaOrder} instances, each specifying a property and direction for sorting.
 * <p>
 * It provides conversion methods to and from Spring Data's {@link Sort}.
 * </p>
 *
 * @see Sort
 */
@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class NostalgiaSort {

    /**
     * A list of sorting orders, each defining a property and direction.
     */
    @Valid
    protected List<NostalgiaOrder> orders;

    /**
     * Represents an individual sorting order consisting of a property and a direction.
     * <p>
     * Each instance defines a property to sort by and the direction of sorting (ascending or descending).
     * </p>
     */
    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class NostalgiaOrder {

        @NotBlank
        private String property;

        @NotNull
        private Direction direction;

    }

    /**
     * Enum representing the direction of sorting: ascending or descending.
     * <p>
     * Provides a method to convert to Spring Data's {@link Sort.Direction}.
     * </p>
     */
    public enum Direction {

        ASC,
        DESC;

        /**
         * Converts this enum to the corresponding {@link Sort.Direction}.
         *
         * @return the corresponding {@link Sort.Direction}
         */
        public Sort.Direction toDirection() {
            return Sort.Direction.valueOf(this.name());
        }
    }

    /**
     * Converts this {@link NostalgiaSort} instance to a {@link Sort} instance.
     * <p>
     * This method maps each {@link NostalgiaOrder} to a {@link Sort.Order} and returns a {@link Sort} object.
     * </p>
     *
     * @return a {@link Sort} representing the same sorting configuration
     */
    protected Sort toSort() {
        return Sort.by(
                this.orders.stream()
                        .map(order -> Sort.Order.by(order.getProperty()).with(order.getDirection().toDirection()))
                        .toList()
        );
    }


    /**
     * Creates an {@link NostalgiaSort} instance from a given {@link Sort} object.
     * <p>
     * This method converts each {@link Sort.Order} in the input into an {@link NostalgiaOrder} and constructs an {@link NostalgiaSort} object.
     * </p>
     *
     * @param sorts the {@link Sort} to convert
     * @return a new {@link NostalgiaSort} object representing the same sorting configuration
     */
    public static NostalgiaSort of(final Sort sorts) {

        List<NostalgiaOrder> orders = sorts.stream()
                .map(order -> NostalgiaOrder.builder()
                        .property(order.getProperty())
                        .direction(Direction.valueOf(order.getDirection().toString()))
                        .build())
                .toList();

        return NostalgiaSort.builder()
                .orders(orders)
                .build();
    }

}
