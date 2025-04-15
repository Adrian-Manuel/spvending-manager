package com.SmartPadel.spvendingManagerApi.shared.Utils;

import com.SmartPadel.spvendingManagerApi.tenant.infrastructure.persistence.entity.Filtrable;
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
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get(field.getName())),
                            like
                    ));
                }
            }

            return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
        };
    }
}
