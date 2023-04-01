package hexlet.code.schemas;

import java.util.function.Predicate;

public final class StringSchema extends BaseSchema {

    public StringSchema minLength(int length) {
        Predicate<String> minLength = x -> x.length() > length;
        addValidator("minLength", minLength);
        return this;
    }

    public StringSchema contains(String string) {
        Predicate<String> contains = x -> x.contains(string);
        addValidator("contains" + string, contains);
        return this;
    }

    @Override
    public StringSchema required() {
        return (StringSchema) super.required();
    }
}
