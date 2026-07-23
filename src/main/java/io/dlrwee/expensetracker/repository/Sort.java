package io.dlrwee.expensetracker.repository;

import java.util.Objects;

public record Sort(String property, Direction direction) {
    public enum Direction {ASC, DESC}

    public Sort {
        validateProperty(property);
        validateDirection(direction);
    }

    private static void validateProperty(String property) {
        Objects.requireNonNull(property, "Property must not be null");
        if (property.isBlank()) {
            throw new IllegalArgumentException("Property must not be blank");
        }
    }

    private static void validateDirection(Direction direction) {
        Objects.requireNonNull(direction, "Direction must not be null");
    }
}
