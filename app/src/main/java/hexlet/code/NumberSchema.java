package hexlet.code;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema {
    private boolean positive = false;
    private ArrayList<Integer> range = new ArrayList<>();

    public NumberSchema positive() {
        positive = true;
        return this;
    }

    public NumberSchema range(int number1, int number2) {
        range.clear();
        range.addAll(List.of(number1, number2));
        return this;
    }

    private Predicate<Object> isRequired = x -> x instanceof Integer;
    private Predicate<Integer> isPositive = x -> x > 0;
    private Predicate<Integer> isRange = x -> range.isEmpty() || x >= range.get(0) && x <= range.get(1);

    public boolean isValid(Object value) {

        return isNotRequired()
                || isRequired.test(value) && isPositive.test((Integer) value) && isRange.test((Integer) value);

    }
}
