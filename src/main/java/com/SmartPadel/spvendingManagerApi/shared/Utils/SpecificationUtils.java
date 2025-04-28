package com.SmartPadel.spvendingManagerApi.shared.Utils;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
public class SpecificationUtils {
    public static <T> Specification<T> buildFilterSpec(Class<T> clazz, String filter) {
        if (filter == null || filter.trim().isEmpty()) {
            return null;
        }
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
        From<?, ?> from = root;
        Path<?> result = root;
        for (int i = 0; i < parts.length; i++) {
            if (i < parts.length - 1) {
                from = from.join(parts[i], JoinType.LEFT); // JOIN seguro
                result = from;
            } else {
                result = result.get(parts[i]); // Último campo
            }
        }
        return result;
    }
}
