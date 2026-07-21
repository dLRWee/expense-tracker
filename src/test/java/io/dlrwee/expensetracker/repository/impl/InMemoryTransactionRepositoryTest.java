package io.dlrwee.expensetracker.repository.impl;

import io.dlrwee.expensetracker.repository.AbstractTransactionRepositoryTest;

class InMemoryTransactionRepositoryTest extends AbstractTransactionRepositoryTest<InMemoryTransactionRepository> {
    @Override
    protected InMemoryTransactionRepository createRepository() {
        return new InMemoryTransactionRepository();
    }
}
