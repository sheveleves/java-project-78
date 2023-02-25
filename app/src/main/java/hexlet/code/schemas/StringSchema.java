package hexlet.code.schemas;

import java.util.Objects;
import java.util.function.Predicate;

public final class StringSchema extends BaseSchema {
    private String str = null;
    private int minLength = -1;
    private String contains = null;

    public StringSchema minLength(int length) {
        length = length;
        return this;
    }

    public StringSchema contains(String string) {
        str = string;
        return this;
    }

    @Override
    public boolean isRequired(Object value) {
        if (getRequired()) {
            return value instanceof String && !Objects.equals(value, "");
        } else {
            return true;
        }
    }
    private Predicate<String> isMinLength = x -> minLength == -1 || x.length() >= minLength;
    private Predicate<String> isContains = x -> str == null || x.contains(str);


    public boolean isValid(Object value) {
        if (!isRequired(value)) {
            return false;
        }

        if (value != null && !isMinLength.test(value.toString())) {
            return false;
        }
        return value == null || isContains.test(value.toString());
    }

    @Override
    public StringSchema required() {
        super.setRequired();
        return this;
    }
}
