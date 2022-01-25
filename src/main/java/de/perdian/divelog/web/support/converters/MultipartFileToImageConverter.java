package de.perdian.divelog.web.support.converters;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import org.springframework.core.convert.converter.Converter;
import org.springframework.web.multipart.MultipartFile;

import de.perdian.divelog.web.support.types.image.Image;

public class MultipartFileToImageConverter implements Converter<MultipartFile, Image> {

    private float jpegCompressionFactor = 0.75f;

    @Override
    public Image convert(MultipartFile source) {
        try {
            return new Image(this.convertToJpegBytes(source.getBytes(), 1000, 1000));
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Cannot convert image to JPEG", e);
        }
    }

    private byte[] convertToJpegBytes(byte[] sourceBytes, int maxWidth, int maxHeight) throws IOException {
        if (sourceBytes == null || sourceBytes.length <= 0) {
            return null;
        } else {
            Iterator<ImageReader> imageReaders = ImageIO.getImageReaders(new ByteArrayInputStream(sourceBytes));
            ImageReader imageReader = imageReaders.hasNext() ? imageReaders.next() : ImageIO.getImageReadersByFormatName("jpg").next();
            imageReader.setInput(ImageIO.createImageInputStream(new ByteArrayInputStream(sourceBytes)));
            IIOImage image = imageReader.readAll(null).next();

            RenderedImage scaledImage = this.createScaledImage(image.getRenderedImage(), maxWidth, maxHeight);
            image.setRenderedImage(scaledImage);

            try (ByteArrayOutputStream resultStream = new ByteArrayOutputStream()) {

                JPEGImageWriteParam jpegParams = new JPEGImageWriteParam(null);
                jpegParams.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                jpegParams.setCompressionQuality(this.getJpegCompressionFactor());

                ImageWriter jpegWriter = ImageIO.getImageWritersByFormatName("jpg").next();
                try {
                    jpegWriter.setOutput(new MemoryCacheImageOutputStream(resultStream));
                    jpegWriter.write(null, image, jpegParams);
                } finally {
                    jpegWriter.dispose();
                }

                return resultStream.toByteArray();
            }
        }
    }

    private RenderedImage createScaledImage(RenderedImage sourceImage, int maxWidth, int maxHeight) {
        if (sourceImage.getWidth() <= maxWidth && sourceImage.getHeight() <= maxHeight) {
            return sourceImage;
        } else {
            double factorX = (double)maxWidth / (double)sourceImage.getWidth();
            double factorY = (double)maxHeight / (double)sourceImage.getHeight();
            double factor = Math.min(factorX, factorY);
            AffineTransformOp scaleOp = new AffineTransformOp(AffineTransform.getScaleInstance(factor, factor), AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            BufferedImage resultImage = scaleOp.createCompatibleDestImage((BufferedImage)sourceImage, null);
            return scaleOp.filter((BufferedImage)sourceImage, resultImage);
        }
    }

    public float getJpegCompressionFactor() {
        return this.jpegCompressionFactor;
    }
    public void setJpegCompressionFactor(float jpegCompressionFactor) {
        this.jpegCompressionFactor = jpegCompressionFactor;
    }

}
