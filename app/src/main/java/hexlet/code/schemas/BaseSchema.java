package hexlet.code.schemas;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public abstract class BaseSchema {
    private  Map<String, Predicate> checkForData = new HashMap<>();
    private boolean required = false;

    public final <T> void addValidator(String nameCheck, Predicate<T> caseCheck) {
        checkForData.put(nameCheck, caseCheck);
    }

    public BaseSchema required() {
        required = true;
        return this;
    }

    public final boolean isValid(Object value) {
        if (required && value == null) {
            return false;
        }

        if (!required && value == null) {
            return true;
        }

        if (this instanceof StringSchema) {
            if (required && value.toString().equals("")) {
                return false;
            }
        }

        if (this instanceof StringSchema) {
            return value instanceof String && check(value.toString());
        }

        if (this instanceof NumberSchema) {
            return (value instanceof Double || value instanceof Integer) && check(Double.parseDouble(value.toString()));
        }

        if (this instanceof MapSchema) {
            return value instanceof Map && check(value);
        }
        return true;
    }

    private boolean check(Object value) {
        boolean result = true;
        for (Map.Entry<String, Predicate> check: checkForData.entrySet()) {
            result = check.getValue().test(value);

            if (!result) {
                return false;
            }
        }
        return true;
    }
}
