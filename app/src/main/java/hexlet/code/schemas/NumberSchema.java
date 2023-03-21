package hexlet.code.schemas;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema {
    private ArrayList<Integer> rangeList = new ArrayList<>();

    public NumberSchema positive() {
        Predicate<Double> positive = x -> x > 0;
        addValidator("positive", positive);
        return this;
    }

    public NumberSchema range(int number1, int number2) {
        rangeList.clear();
        rangeList.addAll(List.of(number1, number2));
        Predicate<Double> range = x -> x >= rangeList.get(0) && x <= rangeList.get(1);
        addValidator("range", range);
        return this;
    }

    public NumberSchema required() {
        super.required();
        return this;
    }
}
