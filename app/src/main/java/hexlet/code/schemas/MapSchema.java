package hexlet.code.schemas;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema {
    private Map<?, BaseSchema> checkData;
//    private boolean required = false;

    public MapSchema sizeof(int number) {
        Predicate<Map> sizeof = x -> x.size() == number;
        addValidator("sizeof", sizeof);
        return this;
    }

    public void shape(Map<?, BaseSchema> setup) {
        checkData = setup;

        Predicate<Map<?, Object>> shape = x -> {
            if (checkData == null) {
                return true;
            }

            Set<?> key = checkData.keySet();
            for (Map.Entry<?, Object> entry : x.entrySet()) {
                if (key.contains(entry.getKey())) {
                    BaseSchema check = checkData.get(entry.getKey());
                    if (!check.isValid(entry.getValue())) {
                        return false;
                    }
                }
            }
            return true;
        };
        addValidator("shape", shape);
    }

    public void required() {
        this.setRequired();
    }
}
