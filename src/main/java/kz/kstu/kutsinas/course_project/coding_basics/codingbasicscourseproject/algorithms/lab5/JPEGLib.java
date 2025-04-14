package kz.kstu.kutsinas.course_project.coding_basics.codingbasicscourseproject.algorithms.lab5;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

public class JPEGLib {
    public static void convertWithQuality(String inputPath, String outputPath, float quality) throws IOException {
        quality /= 100;

        BufferedImage image = ImageIO.read(new File(inputPath));

        // Убираем альфа-канал, создавая новое RGB изображение
        BufferedImage rgbImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = rgbImage.createGraphics();
        g.drawImage(image, 0, 0, null);
        g.dispose();

        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpeg");
        if (!writers.hasNext()) {
            throw new IllegalStateException("No JPEG writers found!");
        }

        ImageWriter writer = writers.next();
        ImageWriteParam param = writer.getDefaultWriteParam();

        if (param.canWriteCompressed()) {
            param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
            param.setCompressionQuality(quality); // 0.0 - наименьшее качество, 1.0 - наилучшее
        }

        // Добавим .jpg к выходному пути, если его нет
        if (!outputPath.toLowerCase().endsWith(".jpg") && !outputPath.toLowerCase().endsWith(".jpeg")) {
            outputPath += ".jpg";
        }

        try (ImageOutputStream ios = ImageIO.createImageOutputStream(new File(outputPath))) {
            writer.setOutput(ios);
            writer.write(null, new IIOImage(rgbImage, null, null), param);
        }

        writer.dispose();
    }

    public static void restoreToPNG(String inputJpegPath, String outputPngPath) throws IOException {
        BufferedImage image = ImageIO.read(new File(inputJpegPath));

        if (!outputPngPath.toLowerCase().endsWith(".png")) {
            outputPngPath += ".png";
        }

        ImageIO.write(image, "png", new File(outputPngPath));
    }
}
