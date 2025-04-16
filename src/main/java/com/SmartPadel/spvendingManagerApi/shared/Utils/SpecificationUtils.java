package com.SmartPadel.spvendingManagerApi.shared.Utils;

import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.Filtrable;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SpecificationUtils {
    public static <T> Specification<T> buildFilterSpec(Class<T> clazz, String filter) {
        return (root, query, criteriaBuilder) -> {
            String like = "%" + filter.toLowerCase() + "%";
            List<Predicate> predicates = new ArrayList<>();

            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Filtrable.class)) {
                    Filtrable annotation = field.getAnnotation(Filtrable.class);
                    String path = annotation.name().isEmpty() ? field.getName() : annotation.name();

                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(getPath(root, path).as(String.class)),
                            like
                    ));
                }
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }

    private static Path<?> getPath(From<?, ?> root, String path) {
        String[] parts = path.split("\\.");
        Path<?> result = root;
        for (String part : parts) {
            result = result.get(part); // esto permite llegar a tenant.name
        }
        return result;
    }
}
