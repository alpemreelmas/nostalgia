package org.nostalgia.common.model.response;

import lombok.Builder;
import lombok.Getter;
import org.nostalgia.common.model.NostalgiaFilter;
import org.nostalgia.common.model.NostalgiaPage;
import org.nostalgia.common.model.NostalgiaSort;

import java.util.List;

/**
 * A generic response class representing a paginated result.
 *
 * @param <R> The type of content in the response.
 */
@Getter
@Builder
public class NostalgiaPageResponse<R> {

    /**
     * The list of content elements in the response.
     */
    private List<R> content;

    /**
     * The current page number.
     */
    private Integer pageNumber;

    /**
     * The number of elements per page.
     */
    private Integer pageSize;

    /**
     * The total number of pages.
     */
    private Integer totalPageCount;

    /**
     * The total number of elements across all pages.
     */
    private Long totalElementCount;

    /**
     * The list of sorting criteria applied to the results.
     */
    private List<NostalgiaSort.NostalgiaOrder> orderedBy;

    /**
     * The filtering criteria applied to the results.
     */
    private NostalgiaFilter filteredBy;


    /**
     * Builder class for constructing instances of {@link NostalgiaPageResponse}.
     *
     * @param <R> The type of content in the response.
     */
    @SuppressWarnings("This method is unused by the application directly but Spring is using it in the background.")
    public static class NostalgiaPageResponseBuilder<R> {

        /**
         * Creates an instance of {@link NostalgiaPageResponseBuilder} from an existing {@link NostalgiaPage} object.
         *
         * @param page The source page object.
         * @return An instance of {@link NostalgiaPageResponseBuilder} with the page attributes set.
         */
        public <M> NostalgiaPageResponseBuilder<R> of(final NostalgiaPage<M> page) {
            return NostalgiaPageResponse.<R>builder()
                    .pageNumber(page.getPageNumber())
                    .pageSize(page.getPageSize())
                    .totalPageCount(page.getTotalPageCount())
                    .totalElementCount(page.getTotalElementCount())
                    .orderedBy(page.getOrderedBy())
                    .filteredBy(page.getFilteredBy());
        }
    }
}
