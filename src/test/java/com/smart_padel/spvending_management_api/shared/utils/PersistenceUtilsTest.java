package com.smart_padel.spvending_management_api.shared.utils;
import com.smart_padel.spvending_management_api.shared.exceptions.NotResourcesFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class PersistenceUtilsTest {
    @Test
    void shouldMapPageSuccessfully() {
        Page<String> inputPage = new PageImpl<>(List.of("one", "two", "three"));

        Page<Integer> result = PersistenceUtils.mapPageOrThrow(inputPage, "No data", String::length);

        assertEquals(3, result.getTotalElements());
        assertEquals(3, result.getContent().get(0));
        assertEquals(3, result.getContent().get(1));
        assertEquals(5, result.getContent().get(2)); // "three"
    }

    @Test
    void shouldThrowExceptionWhenPageIsEmpty() {
        Page<String> emptyPage = new PageImpl<>(Collections.emptyList());

        NotResourcesFoundException exception = assertThrows(NotResourcesFoundException.class, () ->
                PersistenceUtils.mapPageOrThrow(emptyPage, "No tenants found", String::length)
        );

        assertEquals("No tenants found", exception.getMessage());
    }

    @Test
    void shouldMapListSuccessfully() {
        List<String> inputList = List.of("a", "bb", "ccc");

        List<Integer> result = PersistenceUtils.mapListOrThrow(inputList, "Empty list", String::length);

        assertEquals(List.of(1, 2, 3), result);
    }

    @Test
    void shouldThrowExceptionWhenListIsEmpty() {
        List<String> emptyList = Collections.emptyList();

        NotResourcesFoundException exception = assertThrows(NotResourcesFoundException.class, () ->
                PersistenceUtils.mapListOrThrow(emptyList, "Empty list", String::length)
        );

        assertEquals("Empty list", exception.getMessage());
    }
    @Test
    void shouldThrowIllegalStateExceptionWhenUtilityClassIsInstantiated() {
        IllegalStateException exception = assertThrows(IllegalStateException.class, PersistenceUtils::new);
        assertEquals("Utility class", exception.getMessage());
    }
}
