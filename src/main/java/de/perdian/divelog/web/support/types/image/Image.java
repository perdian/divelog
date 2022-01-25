package de.perdian.divelog.web.support.types.image;

import java.io.Serializable;

public class Image implements Serializable {

    static final long serialVersionUID = 1L;

    private byte[] jpegBytes = null;

    public Image(byte[] jpegBytes) {
        this.setJpegBytes(jpegBytes);
    }

    public boolean isAvailable() {
        return this.getJpegBytes() != null && this.getJpegBytes().length > 0;
    }

    public byte[] getJpegBytes() {
        return this.jpegBytes;
    }
    private void setJpegBytes(byte[] jpegBytes) {
        this.jpegBytes = jpegBytes;
    }

}
