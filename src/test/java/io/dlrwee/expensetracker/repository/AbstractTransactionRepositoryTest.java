package io.dlrwee.expensetracker.repository;

import io.dlrwee.expensetracker.domain.entity.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

public abstract class AbstractTransactionRepositoryTest<R extends TransactionRepository> {
    protected abstract R createRepository();

    R repository;
    Transaction first;
    Transaction second;

    @BeforeEach
    void setUp() {
        repository = createRepository();
        repository.deleteAll();
        first = Transaction.create(Transaction.Type.EXPENSE, 1, "first");
        second = Transaction.create(Transaction.Type.INCOME, 2, "second");
    }

    @Nested
    class Save {
        @Test
        @DisplayName("Should save single transaction")
        void shouldSaveSingleTransaction() {
            repository.save(first);

            assertThat(repository.findAll()).containsExactly(first);
        }

        @Test
        @DisplayName("Should update transaction if it already exists")
        void shouldUpdateTransactionIfItAlreadyExists() {
            repository.save(first);
            Transaction copy = Transaction.copy(first);
            copy.setDescription("updated");

            repository.save(copy);

            assertThat(repository.findAll()).containsExactly(copy);
        }

        @Test
        @DisplayName("Should save multiple transactions")
        void shouldSaveMultipleTransactions() {
            repository.save(first);
            repository.save(second);

            assertThat(repository.findAll()).containsExactlyInAnyOrder(first, second);
        }

        @Nested
        class Null {
            @Test
            @DisplayName("Should throw exception if transaction is null")
            void shouldThrowExceptionIfTransactionIsNull() {
                assertThatThrownBy(() -> repository.save(null))
                        .isInstanceOf(NullPointerException.class)
                        .hasMessage("Transaction must not be null");
            }
        }
    }

    @Nested
    class FindById {
        @Test
        @DisplayName("Should return empty optional if transaction is not present")
        void shouldReturnEmptyOptionalIfTransactionIsNotPresent() {
            Optional<Transaction> got = repository.findById(first.getId());

            assertThat(got).isEmpty();
        }

        @Test
        @DisplayName("Should return transaction if it is present")
        void shouldReturnTransactionIfItIsPresent() {
            repository.save(first);

            Optional<Transaction> got = repository.findById(first.getId());

            assertThat(got).contains(first);
        }

        @Test
        @DisplayName("Should return correct transaction if there is multiple transactions")
        void shouldReturnCorrectTransactionIfThereIsMultipleTransactions() {
            repository.save(first);
            repository.save(second);

            Optional<Transaction> got = repository.findById(second.getId());

            assertThat(got).contains(second);
        }

        @Nested
        class Null {
            @Test
            @DisplayName("Should throw exception if id is null")
            void shouldThrowExceptionIfIdIsNull() {
                assertThatThrownBy(() -> repository.findById(null))
                        .isInstanceOf(NullPointerException.class)
                        .hasMessage("Id must not be null");
            }
        }
    }

    @Nested
    class DeleteById {
        @Test
        @DisplayName("Should execute without exceptions if there is no such transaction")
        void shouldExecuteWithoutExceptionsIfThereIsNoSuchTransaction() {
            assertThatNoException().isThrownBy(() -> repository.deleteById(first.getId()));
        }

        @Test
        @DisplayName("Should delete single transaction")
        void shouldDeleteSingleTransaction() {
            repository.save(first);

            repository.deleteById(first.getId());

            assertThat(repository.findAll()).isEmpty();
        }

        @Test
        @DisplayName("Should delete correct transaction if there is multiple transactions")
        void shouldDeleteCorrectTransactionIfThereIsMultipleTransactions() {
            repository.save(first);
            repository.save(second);

            repository.deleteById(second.getId());

            assertThat(repository.findAll()).containsExactly(first);
        }

        @Nested
        class Null {
            @Test
            @DisplayName("Should throw exception if id is null")
            void shouldThrowExceptionIfIdIsNull() {
                assertThatThrownBy(() -> repository.deleteById(null))
                        .isInstanceOf(NullPointerException.class)
                        .hasMessage("Id must not be null");
            }
        }
    }

    @Nested
    class DeleteAll {
        @Test
        @DisplayName("Should execute without exceptions if there is no transactions")
        void shouldExecuteWithoutExceptionsIfThereIsNoTransactions() {
            assertThatNoException().isThrownBy(() -> repository.deleteAll());
        }

        @Test
        @DisplayName("Should delete all transactions")
        void shouldDeleteAllTransactions() {
            repository.save(first);
            repository.save(second);

            repository.deleteAll();

            assertThat(repository.findAll()).isEmpty();
        }
    }

    @Nested
    class Count {
        @Test
        @DisplayName("Should return 0 if empty")
        void shouldReturnZeroIfEmpty() {
            assertThat(repository.count()).isZero();
        }

        @Test
        @DisplayName("Should return correct count if there is multiple transactions")
        void shouldReturnCorrectCountIfThereIsMultipleTransactions() {
            repository.save(first);
            repository.save(second);

            assertThat(repository.count()).isEqualTo(2);
        }
    }
}
