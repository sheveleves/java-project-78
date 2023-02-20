package hexlet.code;

public abstract class BaseSchema {
    private boolean required = false;

    public final BaseSchema required() {
        required = true;
        return this;
    }

    protected final boolean isNotRequired() {
        return !required;
    }
    public abstract boolean isValid(Object value);
}
