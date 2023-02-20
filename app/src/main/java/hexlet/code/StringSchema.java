package hexlet.code;

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

    private Predicate<Object> isRequired = x -> ((x instanceof String) && !Objects.equals(x, ""));
    private Predicate<String> isMinLength = x -> x.length() >= minLength;
    private Predicate<String> isContains = x -> str == null || x.contains(str);


    public boolean isValid(Object value) {
        return isNotRequired()
                || isRequired.test(value) && isMinLength.test(value.toString()) && isContains.test(value.toString());
    }
}
