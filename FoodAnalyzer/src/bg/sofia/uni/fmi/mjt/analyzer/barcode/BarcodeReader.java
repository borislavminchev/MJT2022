package bg.sofia.uni.fmi.mjt.analyzer.barcode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class BarcodeReader {

    public static String read(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            throw new RuntimeException("File path cannot be null or empty string");
        }
        Result result = null;

        try (InputStream barCodeInputStream = new FileInputStream(filePath)) {

            BufferedImage barCodeBufferedImage = ImageIO.read(barCodeInputStream);
            LuminanceSource source = new BufferedImageLuminanceSource(barCodeBufferedImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            Reader reader = new MultiFormatReader();
            result = reader.decode(bitmap);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }

        return result.getText();
    }
}
