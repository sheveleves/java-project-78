package hexlet.code.schemas;

public abstract class BaseSchema {
    private boolean required = false;
    public final BaseSchema required() {
        required = true;
        return this;
    }
    public final boolean getRequired() {
        return required;
    }
    public abstract boolean isRequired(Object value);
    public abstract boolean isValid(Object value);
}
