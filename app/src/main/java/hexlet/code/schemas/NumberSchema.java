package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema {

    public NumberSchema positive() {
        Predicate<Integer> positive = x -> x > 0;
        addValidator("positive", positive);
        return this;
    }

    public NumberSchema range(int number1, int number2) {
        ArrayList<Integer> rangeList = new ArrayList<>(List.of(number1, number2));
        Predicate<Integer> range = x -> x >= rangeList.get(0) && x <= rangeList.get(1);
        addValidator("range", range);
        return this;
    }

    @Override
    public NumberSchema required() {
        Predicate<Object> required = x -> x instanceof Integer;
        addValidator("a", required);
        return this;
    }
}
