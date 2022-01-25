package de.perdian.divelog.web.support.converters;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

import de.perdian.divelog.web.support.types.image.Image;

public class MultipartFileToImageConverter implements Converter<MultipartFile, Image> {

    @Override
    public Image convert(MultipartFile source) {
        try {
            return new Image(this.convertToJpegBytes(source.getBytes()));
        } catch (Exception e) {
            throw new IllegalArgumentException("Cannot convert image to JPEG", e);
        }
    }

    private byte[] convertToJpegBytes(byte[] sourceBytes) throws IOException {
        if (sourceBytes == null || sourceBytes.length <= 0) {
            return null;
        } else {
            try (InputStream sourceStream = new ByteArrayInputStream(sourceBytes)) {
                BufferedImage image = ImageIO.read(sourceStream);
                try (ByteArrayOutputStream resultStream = new ByteArrayOutputStream()) {
                    ImageIO.write(image, "jpeg", resultStream);
                    return resultStream.toByteArray();
                }
            }
        }
    }

}
