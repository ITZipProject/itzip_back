package darkoverload.itzip.feature.techinfo.application.generator;

import org.springframework.data.domain.Page;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.PagedModel.PageMetadata;

public class PagedModelGenerator {

    private PagedModelGenerator() {
    }

    public static <T> PagedModel<EntityModel<T>> generate(final Page<T> page) {
        final PageMetadata metadata = new PageMetadata(
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages());

        return PagedModel.of(
                page.stream()
                        .map(EntityModel::of)
                        .toList(),
                metadata
        );
    }

}
