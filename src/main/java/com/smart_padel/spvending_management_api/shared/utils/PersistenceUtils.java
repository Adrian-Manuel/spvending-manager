package com.smart_padel.spvending_management_api.shared.utils;
import com.smart_padel.spvending_management_api.shared.exceptions.NotResourcesFoundException;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.function.Function;
public class PersistenceUtils {
    public static <T, R> Page<R> mapPageOrThrow(Page<T> page, String errorMessage, Function<T, R> mapper) {
        if (page.isEmpty()) {
            throw new NotResourcesFoundException(errorMessage);
        }
        return page.map(mapper);
    }
    public static <E, D> List<D> mapListOrThrow(List<E> list, String errorMessage, Function<E, D> mapper) {
        if (list.isEmpty()) {
            throw new NotResourcesFoundException(errorMessage);
        }
        return list.stream().map(mapper).toList();
    }
}
