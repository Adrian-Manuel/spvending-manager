package com.smart_padel.spvending_management_api.shared.utils;
import jakarta.persistence.criteria.*;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class SpecificationUtilsTest {
    static class Dummy {
        @Filtrable String name;
        String notFiltrable;
        @Filtrable(name = "customField") String custom;
    }



    @Test
    void buildFilterSpec_ClassWithFiltrableFieldsButNoMatch_ReturnsNullPredicate() {
        var specification = SpecificationUtils.buildFilterSpec(Dummy.class, "abc");
        var root = mock(Root.class);
        var cb = mock(CriteriaBuilder.class);

        when(root.get(anyString())).thenReturn(mock(Path.class));
        specification.toPredicate(root, null, cb);

        verify(cb).or(any(Predicate[].class));
    }

    @Test
    void buildFilterSpec_NullFilter_ReturnsNullSpecification() {
        assertThat(SpecificationUtils.buildFilterSpec(Object.class, null)).isNull();
    }

    @Test
    void buildFilterSpec_EmptyFilter_ReturnsNullSpecification() {
        assertThat(SpecificationUtils.buildFilterSpec(Object.class, "   ")).isNull();
    }

    @Test
    void buildFilterSpec_ValidFilter_ReturnsNonNullSpecification() {
        assertThat(SpecificationUtils.buildFilterSpec(Object.class, "test")).isNotNull();
    }

    @Test
    void buildFilterSpec_ClassWithNoFiltrableFields_ReturnsEmptyPredicate() {
        Specification<Object> specification = SpecificationUtils.buildFilterSpec(Object.class, "test");
        assertThat(specification.toPredicate(null, null, mock(CriteriaBuilder.class))).isNull();
    }

    @Test
    void privateConstructor_ThrowsIllegalStateException() throws NoSuchMethodException {
        var constructor = SpecificationUtils.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        Exception exception = assertThrows(Exception.class, constructor::newInstance);
        AssertionsForClassTypes.assertThat(exception.getCause()).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void getPath_SingleLevelPath_ReturnsCorrectPath() {
        var root = mock(From.class);
        var path = mock(Path.class);

        when(root.get("field")).thenReturn(path);
        assertThat(SpecificationUtils.getPath(root, "field")).isEqualTo(path);
    }

    @Test
    void getPath_MultiLevelPath_ReturnsCorrectPath() {
        var root = mock(From.class);
        var join = mock(Join.class);
        var path = mock(Path.class);

        when(root.join("parent", JoinType.LEFT)).thenReturn(join);
        when(join.get("child")).thenReturn(path);

        assertThat(SpecificationUtils.getPath(root, "parent.child")).isEqualTo(path);
    }

    @Test
    void getPath_NullPath_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> SpecificationUtils.getPath(mock(From.class), null));
    }
}