package hexlet.code;

import java.util.Map;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema {
    private int requiredSize = -1;

    public MapSchema sizeof(int number) {
        requiredSize = number;
        return this;
    }

    private Predicate<Object> isRequired = x -> x instanceof Map<?, ?>;
    private Predicate<Map<?, ?>> isEmpty = Map::isEmpty;

    private Predicate<Map<?, ?>> isSizeof = x -> x.size() >= requiredSize;

    public boolean isValid(Object value) {
        if (isNotRequired()) {
            return true;
        }
        if (requiredSize == -1) {
            return isRequired.test(value);
        }
        Map<Object, Object> map = (Map<Object, Object>) value;
        return isSizeof.test(map);
    }
}
