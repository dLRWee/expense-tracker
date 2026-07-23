package io.dlrwee.expensetracker.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class SortTest {
    @Nested
    class Exception {
        @ParameterizedTest(name = "property: ")
        @ValueSource(strings = {"", " ", "  "})
        @DisplayName("Should throw exception if property is blank")
        void shouldThrowExceptionIfPropertyIsBlank(String property) {
            assertThatThrownBy(() -> new Sort(property, Sort.Direction.ASC))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("Property must not be blank");
        }
    }

    @Nested
    class Null {
        @Test
        @DisplayName("Should throw exception if property is null")
        void shouldThrowExceptionIfPropertyIsNull() {
            assertThatThrownBy(() -> new Sort(null, Sort.Direction.ASC))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessage("Property must not be null");
        }

        @Test
        @DisplayName("Should throw exception if direction is null")
        void shouldThrowExceptionIfDirectionIsNull() {
            assertThatThrownBy(() -> new Sort("test", null))
                    .isInstanceOf(NullPointerException.class)
                    .hasMessage("Direction must not be null");
        }
    }
}
