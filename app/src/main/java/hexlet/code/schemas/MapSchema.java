package hexlet.code.schemas;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema {
    private int requiredSize = -1;
    private Map<?, BaseSchema> checkData;

    public MapSchema sizeof(int number) {
        requiredSize = number;
        return this;
    }

    @Override
    public MapSchema required() {
        super.setRequired();
        return this;
    }

    @Override
    public boolean isRequired(Object value) {
        if (getRequired()) {
            return value instanceof Map<?, ?>;
        } else {
            return true;
        }
    }
    private Predicate<Map<?, ?>> isSizeof = x -> requiredSize == -1 || x.size() == requiredSize;

    public boolean isValid(Object value) {
        if (!isRequired(value)) {
            return false;
        }

        if (value instanceof Map<?, ?> && !isSizeof.test((Map<?, ?>) value)) {
            return false;
        }

        Map<?, Object> map = (Map<?, Object>) value;

        if (checkData == null) {
            return true;
        }

        Set<?> key = checkData.keySet();
        for (Map.Entry<?, Object> entry: map.entrySet()) {
            if (key.contains(entry.getKey())) {
                BaseSchema setup = checkData.get(entry.getKey());
                if (!setup.isValid(entry.getValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    public void shape(Map<?, BaseSchema> setup) {
        checkData = setup;
    }
}
