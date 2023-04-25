package hexlet.code.schemas;

import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

public abstract class BaseSchema {
    private Map<String, Predicate> checkForData = new TreeMap<>();
    private boolean isNullValid = false;

    public final <T> void addValidator(String nameCheck, Predicate<T> caseCheck) {
        checkForData.put(nameCheck, caseCheck);
    }

    protected BaseSchema() {
        checkForData.put("a", x -> {
            isNullValid = x == null;
            return true;
        });
    }

    public abstract BaseSchema required();

    public final boolean isValid(Object value) {
        boolean result = true;
        for (Map.Entry<String, Predicate> check : checkForData.entrySet()) {
            isNullValid = false;
            result = check.getValue().test(value);
            if (isNullValid) {
                return true;
            }
            if (!result) {
                return false;
            }
        }
        return true;
    }
}
