package bg.sofia.uni.fmi.mjt.analyzer.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class NutrientTest {

    @Test
    void testConstructing() {
        Nutrient nutrient = new Nutrient("Protein", 1, "G");
        assertNotNull(nutrient, "Object was not constructed correctly");
    }

    @Test
    void testGetName() {
        Nutrient nutrient = new Nutrient("Protein", 1, "G");
        assertEquals("Protein", nutrient.getName(), "Getter method is not working correctly");
    }

    @Test
    void getAmount() {
        Nutrient nutrient = new Nutrient("Protein", 1, "G");
        assertEquals(1, nutrient.getAmount(), "Getter method is not working correctly");
    }

    @Test
    void getUnitName() {
        Nutrient nutrient = new Nutrient("Protein", 1, "G");
        assertEquals("G", nutrient.getUnitName(), "Getter method is not working correctly");
    }

    @Test
    void testToString() {
        Nutrient nutrient = new Nutrient("Protein", 1, "G");
        assertFalse(nutrient.toString().isEmpty(), "Getter method is not working correctly");
    }
}