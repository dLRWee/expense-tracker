package io.dlrwee.expensetracker.repository.impl;

import io.dlrwee.expensetracker.domain.entity.Transaction;
import io.dlrwee.expensetracker.repository.TransactionRepository;

import java.util.*;

public final class InMemoryTransactionRepository implements TransactionRepository {
    private final Map<UUID, Transaction> storage;

    public InMemoryTransactionRepository() {
        storage = new LinkedHashMap<>();
    }

    @Override
    public void save(Transaction transaction) {
        validateTransaction(transaction);
        storage.put(transaction.getId(), transaction);
    }

    @Override
    public Optional<Transaction> findById(UUID id) {
        validateId(id);
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public List<Transaction> findAll() {
        return storage.values().stream()
                .map(Transaction::copy)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        validateId(id);
        storage.remove(id);
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public long count() {
        return storage.size();
    }

    private static void validateTransaction(Transaction transaction) {
        Objects.requireNonNull(transaction, "Transaction must not be null");
    }

    private static void validateId(UUID id) {
        Objects.requireNonNull(id, "Id must not be null");
    }
}
