package hexlet.code.schemas;

public abstract class BaseSchema {
    private boolean required = false;

    public final void setRequired() {
        this.required = true;
    }
    public final boolean getRequired() {
        return required;
    }

    public abstract BaseSchema required();
    public abstract boolean isRequired(Object value);
    public abstract boolean isValid(Object value);
}
