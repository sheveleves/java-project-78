package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class AppTest {
    private static final int NUMBER_5 = 5;
    private static final int NUMBER_MINUS_10 = -10;
    private static final int NUMBER_10 = 10;
    private static final int NUMBER_4 = 4;
    private static final int NUMBER_11 = 11;
    @Test
    public void testStringSchema() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertThat(schema.isValid("")).isTrue();
        assertThat(schema.isValid(null)).isTrue();

        schema.required();
        assertThat(schema.isValid("what does the fox say")).isTrue();
        assertThat(schema.isValid("hexlet")).isTrue();
        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(NUMBER_5)).isFalse();
        assertThat(schema.isValid("")).isFalse();
        assertThat(schema.contains("wh").isValid("what does the fox say")).isTrue();
        assertThat(schema.contains("what").isValid("what does the fox say")).isTrue();
        assertThat(schema.contains("whatthe").isValid("what does the fox say")).isFalse();
        assertThat(schema.isValid("what does the fox say")).isFalse();
    }

    @Test
    public void testNumberSchema() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.positive().isValid(null)).isTrue();

        schema.required();
        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(NUMBER_10)).isTrue();
        assertThat(schema.isValid("5")).isFalse();
        assertThat(schema.isValid(NUMBER_MINUS_10)).isFalse();
        assertThat(schema.isValid(0)).isFalse();

        schema.range(NUMBER_5, NUMBER_10);
        assertThat(schema.isValid(NUMBER_5)).isTrue();
        assertThat(schema.isValid(NUMBER_10)).isTrue();
        assertThat(schema.isValid(NUMBER_4)).isFalse();
        assertThat(schema.isValid(NUMBER_11)).isFalse();
    }

    @Test
    public void testMapSchema() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        assertThat(schema.isValid(null)).isTrue();

        schema.required();
        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(new HashMap<>())).isTrue();
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value");
        assertThat(schema.isValid(data)).isTrue();

        schema.sizeof(2);
        assertThat(schema.isValid(data)).isFalse();
        data.put("key2", "value");
        assertThat(schema.isValid(data)).isTrue();
    }
}
