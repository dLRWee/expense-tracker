package io.dlrwee.expensetracker.domain.entity;

import java.util.Objects;
import java.util.UUID;

public final class Transaction {
    public enum Type {EXPENSE, INCOME}

    private final UUID id;
    private Type type;
    private double amount;
    private String description;

    private Transaction(UUID id, Type type, double amount, String description) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }

    public static Transaction create(Type type, double amount, String description) {
        validateType(type);
        validateAmount(amount);
        validateDescription(description);

        return new Transaction(UUID.randomUUID(), type, amount, description);
    }

    private static void validateType(Type type) {
        Objects.requireNonNull(type, "Type must not be null");
    }

    private static void validateAmount(double amount) {
        if (amount <= 0) {
            String message = String.format("Amount (%.2f) must be positive", amount);
            throw new IllegalArgumentException(message);
        }
    }

    private static void validateDescription(String description) {
        Objects.requireNonNull(description, "Description must not be null");

        if (description.isBlank()) {
            String message = String.format("Description (%s) must not be blank", description);
            throw new IllegalArgumentException(message);
        }
    }

    public static Transaction copy(Transaction other) {
        validateOther(other);

        return new Transaction(
                other.id,
                other.type,
                other.amount,
                other.description
        );
    }

    private static void validateOther(Transaction other) {
        Objects.requireNonNull(other, "Other must not be null");
    }

    public UUID getId() {
        return id;
    }

    public Type getType() {
        return  type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public void setType(Type type) {
        validateType(type);
        this.type = type;
    }

    public void setAmount(double amount) {
        validateAmount(amount);
        this.amount = amount;
    }

    public void setDescription(String description) {
        validateDescription(description);
        this.description = description;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Transaction t)) {
            return false;
        }

        return id.equals(t.id) && type.equals(t.type) &&
                Double.compare(amount, t.amount) == 0 &&
                description.equals(t.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, type, amount, description);
    }

    @Override
    public String toString() {
        return String.format("{type: %s, amount: %.2f, description: %S}",
                type, amount, description);
    }
}
