package bg.sofia.uni.fmi.mjt.analyzer.barcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BarcodeReaderTest {

    @Test
    void testReadEmpty() {
        assertThrows(RuntimeException.class, () -> BarcodeReader.read(""),
                "Expected to throw RuntimeException when method is called with invalid args");
        assertThrows(RuntimeException.class, () -> BarcodeReader.read(null),
                "Expected to throw RuntimeException when method is called with invalid args");
    }

    @Test
    void testReadNotExisting() {
        assertThrows(Exception.class, () -> BarcodeReader.read("barcode123.gif"),
                "Expected to throw RuntimeException when method is called with not existing file");
    }

    @Test
    void testRead() {
        assertEquals("725272730706", BarcodeReader.read("barcode.gif"),
                "Barcode parser is not working correctly");
    }
}