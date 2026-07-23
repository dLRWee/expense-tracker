package io.dlrwee.expensetracker.exception;

public class NoSuchPropertyException extends RuntimeException {
    public NoSuchPropertyException() {
        super();
    }

    public NoSuchPropertyException(String property) {
        super(createMessage(property));
    }

    public NoSuchPropertyException(Throwable cause) {
        super(cause);
    }

    public NoSuchPropertyException(String property, Throwable cause) {
        super(createMessage(property), cause);
    }

    private static String createMessage(String property) {
        return String.format("No such property: %s", property);
    }
}
