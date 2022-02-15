package bg.sofia.uni.fmi.mjt.analyzer.storage;

import bg.sofia.uni.fmi.mjt.analyzer.entity.Food;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNull;

class DefaultFoodStorageTest {

    private FoodStorage storage = new DefaultFoodStorage();

    @Test
    void testEmptyStorage() {
        assertTrue(storage.getFoods().isEmpty(), "Initially storage must be empty in id part");
        assertTrue(storage.getGtinUpcFoods().isEmpty(), "Initially storage must be empty in gtinUpc part");
    }

    @Test
    void testAddNullFood() {
        assertThrows(RuntimeException.class, () -> storage.addFood(null),
                "Expected to throw RuntimeException when method is called with null arg");
    }

    @Test
    void testAddInvalidFood() {
        final long id = 12345;
        Food invalid1 = new Food(0, "Default descr", null, null);
        Food invalid2 = new Food(id, "", null, null);
        assertThrows(RuntimeException.class, () -> storage.addFood(invalid1),
                "Expected to throw RuntimeException when method is called with invalid arg");
        assertThrows(RuntimeException.class, () -> storage.addFood(invalid2),
                "Expected to throw RuntimeException when method is called with invalid arg");
    }

    @Test
    void testAddFoodSame() {
        final long id1 = 12345;
        final long id2 = 12345;
        Food food1 = new Food(id1, "Default description", null, null);
        Food food2 = new Food(id2, "Default description", null, null);

        storage.addFood(food1);
        storage.addFood(food2);

        final int expectedSize = 1;
        assertEquals(expectedSize, storage.getFoods().size(),
                "Expected only once to be possible to insert food in storage");
        assertTrue(storage.getGtinUpcFoods().isEmpty(),
                "Expected not to insert food in gtinUpc part when barcode is missing");
    }

    @Test
    void testAddFoodWithNoBarcode() {
        final long id = 12345;
        Food noBarcode = new Food(id, "Default description", null, null);
        storage.addFood(noBarcode);

        final int expectedSize = 1;
        assertEquals(expectedSize, storage.getFoods().size(),
                "Expected to insert food in id path when food has no barcode");
        assertTrue(storage.getGtinUpcFoods().isEmpty(),
                "Expected not to insert food in gtinUpc path when food has no barcode");
    }

    @Test
    void testAddTwoFoodsWithNoBarcode() {
        final long id1 = 12345;
        final long id2 = 54321;
        Food noBarcode1 = new Food(id1, "Default description", null, null);
        Food noBarcode2 = new Food(id2, "Default description", "", null);
        storage.addFood(noBarcode1);
        storage.addFood(noBarcode2);

        final int expectedSize = 2;
        assertEquals(expectedSize, storage.getFoods().size(),
                "Adding multiple foods in id part is not correct");
        assertTrue(storage.getGtinUpcFoods().isEmpty(),
                "Adding multiple foods in gtinUpc part is not correct");
    }

    @Test
    void testAddFoodWithBarcode() {
        final long id = 12345;
        Food withBarcode = new Food(id, "Default description", "123456", null);
        storage.addFood(withBarcode);

        final int expectedSize = 1;
        assertEquals(expectedSize, storage.getFoods().size(),
                "Adding food in id part is not correct when having barcode");
        assertEquals(expectedSize, storage.getGtinUpcFoods().size(),
                "Adding food in gtinUpc part is not correct when having barcode");
    }

    @Test
    void testAddTwoFoodsWithBarcode() {
        final long id1 = 12345;
        final long id2 = 54321;
        Food withBarcode1 = new Food(id1, "Default description", "123456", null);
        Food withBarcode2 = new Food(id2, "Default description", "654321", null);

        storage.addFood(withBarcode1);
        storage.addFood(withBarcode2);

        final int expectedSize = 2;
        assertEquals(expectedSize, storage.getFoods().size(),
                "Adding multiple foods in id part is not correct when having no barcode");
        assertEquals(expectedSize, storage.getGtinUpcFoods().size(),
                "Adding multiple foods in gtinUpc part is not correct when having barcode");
    }

    @Test
    void testAddTwoFoodsMixed() {
        final long id1 = 12345;
        final long id2 = 54321;
        Food withBarcode = new Food(id1, "Default description", "123456", null);
        Food noBarcode = new Food(id2, "Default description", "", null);

        storage.addFood(withBarcode);
        storage.addFood(noBarcode);

        final int expectedFoodSize = 2;
        final int expectedGtinUpcSize = 1;
        assertEquals(expectedFoodSize, storage.getFoods().size(),
                "Adding multiple foods(with and without barcode) in id part is not correct");
        assertEquals(expectedGtinUpcSize, storage.getGtinUpcFoods().size(),
                "Adding multiple foods(with and without barcode) in gtinUpc part is not correct");
    }

    @Test
    void testGetFoodByIdOne() {
        final int id = 12345;
        Food food = new Food(id, "Default description", "123456", null);
        storage.addFood(food);

        assertEquals(food, storage.getFoodById(id), "Food is not stored in storage");
    }

    @Test
    void testGetFoodByIdNotExisting() {
        final int id = 12345;
        Food food = new Food(id, "Default description", "123456", null);
        storage.addFood(food);

        final int newId = 1234567;
        assertNull(storage.getFoodById(newId), "Expected food that is not added in storage not to be there");
    }

    @Test
    void testGetFoodsByGtinUpcInvalid() {
        final int id = 12345;
        Food food = new Food(id, "Default description", "123456", null);
        storage.addFood(food);
        assertThrows(RuntimeException.class, () -> storage.getFoodByGtinUpc(""),
                "Expected method to throw RuntimeException when called with invalid arg");
    }

    @Test
    void testGetFoodsByGtinUpcOne() {
        final int id = 12345;
        Food food = new Food(id, "Default description", "123456", null);
        storage.addFood(food);
        assertEquals(food, storage.getFoodByGtinUpc("123456"),
                "Food was not stored correctly in storage");
    }

    @Test
    void testGetFoodsByGtinUpcTwo() {
        final int id1 = 12345;
        final int id2 = 54321;
        Food food1 = new Food(id1, "Default description", "123456", null);
        Food food2 = new Food(id2, "Default description", "123456", null);
        storage.addFood(food1);
        storage.addFood(food2);
        assertEquals(food2, storage.getFoodByGtinUpc("123456"),
                "Food was not stored correctly in storage");
    }
}