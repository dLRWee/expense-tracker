package io.dlrwee.expensetracker.repository;

import io.dlrwee.expensetracker.domain.entity.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TransactionRepository {
    void save(Transaction transaction);
    Optional<Transaction> findById(UUID id);
    List<Transaction> findAll();
    void deleteById(UUID id);
    void deleteAll();
    long count();
}
