package hexlet.code.schemas;

import java.util.Map;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema {
    @Override
    public MapSchema required() {
        Predicate<Object> required = x -> x instanceof Map;
        addValidator("a", required);
        return this;
    }

    public MapSchema sizeof(int number) {
        Predicate<Map> sizeof = x -> x.size() == number;
        addValidator("sizeof", sizeof);
        return this;
    }

    public MapSchema shape(Map<?, BaseSchema> checkData) {
        Predicate<Map<?, Object>> shape = x -> {
            for (Map.Entry<?, Object> entry : x.entrySet()) {
                boolean result = true;
                if (checkData.containsKey(entry.getKey())) {
                    result = checkData.get(entry.getKey()).isValid(entry.getValue());
                }
                if (!result) {
                    return false;
                }
            }
            return true;
        };
        addValidator("shape", shape);
        return this;
    }
}
