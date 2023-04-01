package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class AppTest {
    private static final int NUMBER_5 = 5;
    private static final int NUMBER_6 = 6;
    private static final int NUMBER_7 = 7;


    private static final int NUMBER_MINUS_10 = -10;
    private static final int NUMBER_10 = 10;
    private static final int NUMBER_4 = 4;
    private static final int NUMBER_11 = 11;
    private static final int NUMBER_100 = 100;

    @Test
    public void testStringSchemaRequired() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertThat(schema.isValid("")).isTrue();
        assertThat(schema.isValid(null)).isTrue();

        schema.required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("")).isFalse();
    }

    @Test
    public void testStringSchemaMinLength() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertThat(schema.isValid("hexlet")).isTrue();

        schema.minLength(NUMBER_6);

        assertThat(schema.isValid("hexlet")).isTrue();

        schema.minLength(NUMBER_7);

        assertThat(schema.isValid("hexlet")).isFalse();
    }

    @Test
    public void testStringSchemaContains() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertThat(schema.contains("one").isValid("one two three four five")).isTrue();
        assertThat(schema.contains("tw").isValid("one two three four five")).isTrue();
        assertThat(schema.contains("three").isValid("one two three four five")).isTrue();
        assertThat(schema.contains("four").isValid("two three four five")).isFalse();
        assertThat(schema.isValid("one eleven three four five")).isFalse();
        assertThat(schema.isValid("one eleven two three four five")).isTrue();
    }

    @Test
    public void testStringSchemaWhenNotString() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertThat(schema.isValid(NUMBER_5)).isFalse();
        HashMap<String, String> hashMap = new HashMap<>();
        assertThat(schema.isValid(hashMap)).isFalse();
    }

    @Test
    public void testNumberSchemaRequired() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid(NUMBER_5)).isTrue();

        assertThat(schema.required().isValid(NUMBER_5)).isTrue();

        assertThat(schema.isValid(null)).isFalse();
    }

    @Test
    public void testNumberSchemaPositive() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertThat(schema.isValid(0)).isTrue();
        assertThat(schema.isValid(NUMBER_10)).isTrue();
        assertThat(schema.isValid(NUMBER_MINUS_10)).isTrue();

        assertThat(schema.positive().isValid(NUMBER_5)).isTrue();

        assertThat(schema.isValid(0)).isFalse();
        assertThat(schema.isValid(NUMBER_10)).isTrue();
        assertThat(schema.isValid(NUMBER_MINUS_10)).isFalse();
    }

    @Test
    public void testNumberSchemaRange() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertThat(schema.isValid(NUMBER_5)).isTrue();
        assertThat(schema.isValid(NUMBER_10)).isTrue();
        assertThat(schema.isValid(NUMBER_4)).isTrue();
        assertThat(schema.isValid(NUMBER_11)).isTrue();

        schema.range(NUMBER_5, NUMBER_10);

        assertThat(schema.isValid(NUMBER_5)).isTrue();
        assertThat(schema.isValid(NUMBER_10)).isTrue();
        assertThat(schema.isValid(NUMBER_4)).isFalse();
        assertThat(schema.isValid(NUMBER_11)).isFalse();
    }

    @Test
    public void testNumberSchemaWhenNotNumber() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertThat(schema.isValid("5")).isFalse();
        HashMap<String, String> hashMap = new HashMap<>();
        assertThat(schema.isValid(hashMap)).isFalse();
    }

    @Test
    public void testMapSchemaRequired() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.isValid(new HashMap<>())).isTrue();
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(schema.isValid(data)).isTrue();

        schema.required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(new HashMap<>())).isTrue();
        assertThat(schema.isValid(data)).isTrue();
    }

    @Test
    public void testMapSchemaSizeof() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(schema.isValid(data)).isTrue();

        schema.sizeof(2);

        assertThat(schema.isValid(data)).isFalse();
        data.put("key2", "value2");
        assertThat(schema.isValid(data)).isTrue();

        data.put("key3", "value3");

        assertThat(schema.isValid(data)).isFalse();
    }

    @Test
    public void testMapShape1() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", v.string().required());
        schemas.put("age", v.number().positive());
        schema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Kolya");
        human1.put("age", NUMBER_100);
        assertThat(schema.isValid(human1)).isTrue();

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Maya");
        human2.put("age", null);
        assertThat(schema.isValid(human2)).isTrue();

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "");
        human3.put("age", null);
        assertThat(schema.isValid(human3)).isFalse();

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Valya");
        human4.put("age", NUMBER_MINUS_10);
        assertThat(schema.isValid(human4)).isFalse();
    }
    @Test
    public void testMapShape2() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", v.string().required().contains("ava"));
        schemas.put("age", v.number().required().positive());
        schema.shape(schemas);

        Map<String, Object> human1 = new HashMap<>();
        human1.put("name", "Slava");
        human1.put("age", NUMBER_100);
        assertThat(schema.isValid(human1)).isTrue();

        Map<String, Object> human2 = new HashMap<>();
        human2.put("name", "Pavel");
        human2.put("age", NUMBER_100);
        assertThat(schema.isValid(human2)).isFalse();

        Map<String, Object> human3 = new HashMap<>();
        human3.put("name", "Slava");
        human3.put("age", null);
        assertThat(schema.isValid(human3)).isFalse();

        Map<String, Object> human4 = new HashMap<>();
        human4.put("name", "Slava");
        human4.put("age", NUMBER_MINUS_10);
        assertThat(schema.isValid(human4)).isFalse();
    }
}
