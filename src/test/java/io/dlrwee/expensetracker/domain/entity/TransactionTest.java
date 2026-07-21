package io.dlrwee.expensetracker.domain.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.dlrwee.expensetracker.domain.entity.Transaction.Type.EXPENSE;
import static io.dlrwee.expensetracker.domain.entity.Transaction.Type.INCOME;
import static org.assertj.core.api.Assertions.*;

class TransactionTest {
    private static final Transaction.Type TYPE = EXPENSE;
    private static final double AMOUNT = 1.5;
    private static final String DESCRIPTION = "test";

    @Nested
    class Create {
        @Test
        @DisplayName("Should initialize with valid arguments")
        void shouldInitializeWithValidArguments() {
            Transaction transaction = Transaction.create(TYPE, AMOUNT, DESCRIPTION);

            assertThat(transaction).isNotNull();
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

    @Nested
    class Copy {
        @Test
        @DisplayName("Should make copy")
        void shouldMakeCopy() {
            Transaction transaction = Transaction.create(TYPE, AMOUNT, DESCRIPTION);

            Transaction copy = Transaction.copy(transaction);

            assertThat(copy).isNotNull();
            assertThat(copy).isNotSameAs(transaction);
            assertThat(copy.getId()).isEqualTo(transaction.getId());
            assertThat(copy.getType()).isEqualTo(transaction.getType());
            assertThat(copy.getAmount()).isEqualTo(transaction.getAmount());
            assertThat(copy.getDescription()).isEqualTo(transaction.getDescription());
        }

        @Nested
        class Null {
            @Test
            @DisplayName("Should throw exception if other is null")
            void shouldThrowExceptionIfOtherIsNull() {
                assertThatThrownBy(() -> Transaction.copy(null))
                        .isInstanceOf(NullPointerException.class)
                        .hasMessage("Other must not be null");
            }
        }
    }

    @Nested
    class SetType {
        @Test
        @DisplayName("Should set new type")
        void shouldSetNewType() {
            Transaction.Type newType = INCOME;
            Transaction transaction = Transaction.create(TYPE, AMOUNT, DESCRIPTION);

            transaction.setType(newType);

            assertThat(transaction.getType()).isEqualTo(newType);
        }

        @Nested
        class Null {
            @Test
            @DisplayName("Should throw exception if type is null")
            void shouldThrowExceptionIfTypeIsNull() {
                Transaction transaction = Transaction.create(TYPE, AMOUNT, DESCRIPTION);

                assertThatThrownBy(() -> transaction.setType(null))
                        .isInstanceOf(NullPointerException.class)
                        .hasMessage("Type must not be null");
            }
        }
    }

    @Nested
    class SetAmount {
        @Test
        @DisplayName("Should set new amount")
        void shouldSetNewAmount() {
            double newAmount = 2.5;
            Transaction transaction = Transaction.create(TYPE, AMOUNT, DESCRIPTION);

            transaction.setAmount(newAmount);

            assertThat(transaction.getAmount()).isEqualTo(newAmount);
        }

        @Nested
        class Exception {
            @ParameterizedTest(name = "amount: {0}")
            @ValueSource(doubles = {0, -1})
            @DisplayName("Should throw exception if amount is not positive")
            void shouldThrowExceptionIfAmountIsNotPositive(double amount) {
                String expectedMessage = String.format("Amount (%.2f) must be positive", amount);
                Transaction transaction = Transaction.create(TYPE, AMOUNT, DESCRIPTION);

                assertThatThrownBy(() -> transaction.setAmount(amount))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage(expectedMessage);
            }
        }
    }

    @Nested
    class SetDescription {
        @Test
        @DisplayName("Should set new description")
        void shouldSetNewDescription() {
            String newDescription = "new description";
            Transaction transaction = Transaction.create(TYPE, AMOUNT, DESCRIPTION);

            transaction.setDescription(newDescription);

            assertThat(transaction.getDescription()).isEqualTo(newDescription);
        }

        @Nested
        class Exception {
            @ParameterizedTest(name = "description: {0}")
            @ValueSource(strings = {"", " ", "  "})
            @DisplayName("Should throw exception if description if blank")
            void shouldThrowExceptionIfDescriptionIsBlank(String description) {
                String expectedMessage = String.format("Description (%s) must not be blank", description);
                Transaction transaction = Transaction.create(TYPE, AMOUNT, DESCRIPTION);

                assertThatThrownBy(() -> transaction.setDescription(description))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage(expectedMessage);
            }
        }

        @Nested
        class Null {
            @Test
            @DisplayName("Should throw exception if description is null")
            void shouldThrowExceptionIfDescriptionIsNull() {
                String expectedMessage = "Description must not be null";
                Transaction transaction = Transaction.create(TYPE, AMOUNT, DESCRIPTION);

                assertThatThrownBy(() -> transaction.setDescription(null))
                        .isInstanceOf(NullPointerException.class)
                        .hasMessage(expectedMessage);
            }
        }
    }
}
