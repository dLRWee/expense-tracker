package io.dlrwee.expensetracker.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.*;

class TransactionTest {
    private static final Transaction.Type TYPE = Transaction.Type.EXPENSE;
    private static final double AMOUNT = 1.5;
    private static final String DESCRIPTION = "test";

    @Nested
    class Create {
        @Test
        @DisplayName("Should initialize with valid arguments")
        void shouldInitializeWithValidArguments() {
            Transaction transaction = Transaction.create(TYPE, AMOUNT, DESCRIPTION);

            assertThat(transaction.getId()).isNotNull();
            assertThat(transaction.getType()).isEqualTo(TYPE);
            assertThat(transaction.getAmount()).isEqualTo(AMOUNT);
            assertThat(transaction.getDescription()).isEqualTo(DESCRIPTION);
        }

        @Nested
        class Exception {
            @ParameterizedTest(name = "amount: {0}")
            @ValueSource(doubles = {0, -1})
            @DisplayName("Should throw exception if amount is not positive")
            void shouldThrowExceptionIfAmountIsNotPositive(double amount) {
                String expectedMessage = String.format("Amount (%.2f) must be positive", amount);

                assertThatThrownBy(() -> Transaction.create(TYPE, amount, DESCRIPTION))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage(expectedMessage);
            }

            @ParameterizedTest(name = "description: {0}")
            @ValueSource(strings = {"", " ", "  "})
            @DisplayName("Should throw exception if description if blank")
            void shouldThrowExceptionIfDescriptionIsBlank(String description) {
                String expectedMessage = String.format("Description (%s) must not be blank", description);

                assertThatThrownBy(() -> Transaction.create(TYPE, AMOUNT, description))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage(expectedMessage);
            }
        }

        @Nested
        class Null {
            @Test
            @DisplayName("Should throw exception if type is null")
            void shouldThrowExceptionIfTypeIsNull() {
                String expectedMessage = "Type must not be null";

                assertThatThrownBy(() -> Transaction.create(null, AMOUNT, DESCRIPTION))
                        .isInstanceOf(NullPointerException.class)
                        .hasMessage(expectedMessage);
            }

            @Test
            @DisplayName("Should throw exception if description is null")
            void shouldThrowExceptionIfDescriptionIsNull() {
                String expectedMessage = "Description must not be null";

                assertThatThrownBy(() -> Transaction.create(TYPE, AMOUNT, null))
                        .isInstanceOf(NullPointerException.class)
                        .hasMessage(expectedMessage);
            }
        }
    }
}
