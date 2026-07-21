package io.dlrwee.expensetracker.repository;

public record Sort(String property, Direction direction) {
    public enum Direction {ASC, DESC}
}
