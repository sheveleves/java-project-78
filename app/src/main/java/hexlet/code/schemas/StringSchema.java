package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class StringSchema extends BaseSchema {
    private List<String> list = new ArrayList<>();

    public StringSchema minLength(int length) {
        Predicate<String> minLength = x -> x.length() > length;
        addValidator("minLength", minLength);
        return this;
    }

    public StringSchema contains(String string) {
        list.add(string);
        Predicate<String> contains = x -> {
            if (list.isEmpty()) {
                return true;
            }

            for (String string1 : list) {
                if (!x.contains(string1)) {
                    return false;
                }
            }
            return true;
        };
        addValidator("contains", contains);
        return this;
    }

//    public StringSchema required() {
//        super.required();
//        return this;
//    }
}
