package hexlet.code.schemas;

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

    @Override
    public boolean isRequired(Object value) {
        if (getRequired()) {
            return value instanceof Double || value instanceof Integer;
        } else {
            return true;
        }
    }

    private Predicate<Double> isPositive = x -> x == null || !positive || x > 0;
    private Predicate<Double> isRange = x -> range.isEmpty() || x >= range.get(0) && x <= range.get(1);

    public boolean isValid(Object value) {
        if (!isRequired(value)) {
            return false;
        }

        double doubleValue;

        if (value == null) {
            return true;
        } else {
            doubleValue = Double.parseDouble(value.toString());
        }

        if (!isPositive.test(doubleValue)) {
            return false;
        }

        return isRange.test(doubleValue);
    }

    @Override
    public NumberSchema required() {
        super.setRequired();
        return this;
    }
}
