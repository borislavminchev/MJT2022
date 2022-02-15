package bg.sofia.uni.fmi.mjt.analyzer.entity;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class FoodTest {

    @Test
    void testCreation() {
        final long id = 12345;
        Food food = new Food(id, "Default description", "123456", null);
        assertNotNull(food, "Object was not constructed correctly");
    }

    @Test
    void testGetFdcId() {
        final long id = 12345;
        Food food = new Food(id, "Default description", "123456", null);

        assertEquals(id, food.getFdcId(), "Getter method is not working correctly");
    }

    @Test
    void testGetDescription() {
        final long id = 12345;
        Food food = new Food(id, "Default description", "123456", null);

        assertEquals("Default description", food.getDescription(), "Getter method is not working correctly");
    }

    @Test
    void testGetGtinUpc() {
        final long id = 12345;
        Food food = new Food(id, "Default description", "123456", null);

        assertEquals("123456", food.getGtinUpc(), "Getter method is not working correctly");
    }

    @Test
    void testGetFoodNutrients() {
        List<Nutrient> nutrients = List.of(
                new Nutrient("Protein", 1, "G"),
                new Nutrient("Total lipid (fat)", 2, "G"),
                new Nutrient("Carbohydrate, by difference", 1, "G"),
                new Nutrient("Energy", 2, "KCAL"),
                new Nutrient("Fiber, total dietary", 0, "G")
        );
        final long id = 12345;
        Food food = new Food(id, "Default description", "123456", nutrients);
        assertIterableEquals(nutrients, food.getFoodNutrients(), "Getter method is not working correctly");
    }

    @Test
    void testIsValid() {
        final long id = 12345;
        Food food1 = new Food(id, "Default description", "123456", null);
        Food food2 = new Food(1, "Default description", "123456", null);
        Food food3 = new Food(id, "Default description", "", null);
        Food food4 = new Food(id, "Default description", null, null);
        assertTrue(food1.isValid(),
                "Expected isValid() method to return true when working with foods with null foodNutrients");
        assertTrue(food2.isValid(),
                "Expected isValid() method to return true when working with foods with small number for fdcId");
        assertTrue(food3.isValid(),
                "Expected isValid() method to return true when working with foods with empty gtinUpc");
        assertTrue(food4.isValid(),
                "Expected isValid() method to return true when working with foods with null gtinUpc");
    }
    @Test
    void testIsInvalid() {
        final long id = 12345;
        Food food1 = new Food(id, "", "123456", null);
        Food food2 = new Food(0, "Default description", "123456", null);
        Food food3 = new Food(id, null, null, null);
        assertFalse(food1.isValid(),
                "Expected isValid() method to return false when working with foods with Empty description");
        assertFalse(food2.isValid(),
                "Expected isValid() method to return false when working with foods with non positive fdcId");
        assertFalse(food3.isValid(),
                "Expected isValid() method to return false when working with foods with null description");
    }

    @Test
    void testHasReportInfo() {
        List<Nutrient> nutrients = List.of(
                new Nutrient("Protein", 1, "G"),
                new Nutrient("Total lipid (fat)", 2, "G"),
                new Nutrient("Carbohydrate, by difference", 1, "G"),
                new Nutrient("Energy", 2, "KCAL"),
                new Nutrient("Fiber, total dietary", 0, "G")
        );
        final long id = 12345;
        Food food = new Food(id, "Default description", "123456", nutrients);
        Food noReport1 = new Food(id, "Default description", "123456", List.of());
        Food noReport2 = new Food(id, "Default description", "123456", List.of(
                new Nutrient("", 1, "G")
        ));
        assertTrue(food.hasReportInfo(),
                "Expected hasReportInfo() method to return true when working with foods with correct nutrients");
        assertFalse(noReport1.hasReportInfo(),
                "Expected hasReportInfo() method to return false when working with foods with empty nutrients");
        assertFalse(noReport2.hasReportInfo(),
                "Expected hasReportInfo() method to return false when working with foods with incorrect nutrients");
    }

    @Test
    void testToString() {
        List<Nutrient> nutrients = List.of(
                new Nutrient("Protein", 1, "G"),
                new Nutrient("Total lipid (fat)", 2, "G"),
                new Nutrient("Carbohydrate, by difference", 1, "G"),
                new Nutrient("Energy", 2, "KCAL"),
                new Nutrient("Fiber, total dietary", 0, "G")
        );
        final long id = 12345;
        Food food1 = new Food(id, "Default description", "123456", nutrients);
        Food food2 = new Food(id, "Default description", "123456", null);
        assertFalse(food1.toString().isEmpty(), "toString() is not working correctly");
        assertFalse(food2.toString().isEmpty(), "toString() is not working correctly");
        assertTrue(food1.toString().length() > food2.toString().length(),
                "Expected to display changes when nutrients field is valid");
    }

    @Test
    void testEquals() {
        List<Nutrient> nutrients = List.of(
                new Nutrient("Protein", 1, "G"),
                new Nutrient("Total lipid (fat)", 2, "G"),
                new Nutrient("Carbohydrate, by difference", 1, "G"),
                new Nutrient("Energy", 2, "KCAL"),
                new Nutrient("Fiber, total dietary", 0, "G")
        );
        final long id = 12345;
        Food food1 = new Food(id, "Default description", "123456", nutrients);
        Food food2 = new Food(id, "Default description", "123456", null);
        Food food3 = new Food(id, "Default description", "123456", null);
        assertNotEquals(food1, food2, "Equals method s not working as expected");
        assertEquals(food2, food3, "Equals method s not working as expected");
    }

    @Test
    void testHashCode() {
        List<Nutrient> nutrients = List.of(
                new Nutrient("Protein", 1, "G"),
                new Nutrient("Total lipid (fat)", 2, "G"),
                new Nutrient("Carbohydrate, by difference", 1, "G"),
                new Nutrient("Energy", 2, "KCAL"),
                new Nutrient("Fiber, total dietary", 0, "G")
        );
        final long id = 12345;
        Food food1 = new Food(id, "Default description", "123456", nutrients);
        Food food2 = new Food(id, "Default description", "123456", null);
        Food food3 = new Food(id, "Default description", "123456", null);
        Food food4 = new Food(id + 1, "Default description", "123456", null);
        assertEquals(food1.hashCode(), food2.hashCode(), "HashCode method s not working as expected");
        assertEquals(food2.hashCode(),  food3.hashCode(), "HashCode method s not working as expected");
        assertNotEquals(food3.hashCode(),  food4.hashCode(), "HashCode method s not working as expected");
    }
}