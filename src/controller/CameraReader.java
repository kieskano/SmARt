package controller;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamDriver;
import com.github.sarxos.webcam.WebcamResolution;
import com.sun.javafx.iio.ImageStorage.ImageType;

public class CameraReader {

  private Webcam webcam;
  
  
  
  public CameraReader(int cameraNumber, Dimension dimensions) {
    webcam = Webcam.getWebcams().get(cameraNumber);
    Dimension[] nonStandardResolutions = new Dimension[] {
        WebcamResolution.HD720.getSize(),
    };
    webcam.setCustomViewSizes(nonStandardResolutions);
    webcam.setViewSize(WebcamResolution.HD720.getSize());
  }
  
  public void open() {
    webcam.open();
  }
  
  public void close() {
    webcam.close();
  }
  
  public BufferedImage takePhoto() {
    return mirrorImage(webcam.getImage());
  }
  
  private BufferedImage mirrorImage(BufferedImage image) {
    BufferedImage result = new BufferedImage(SmARt.CAM_DIMENSION.width, SmARt.CAM_DIMENSION.height, BufferedImage.TYPE_3BYTE_BGR);
    for (int x = 0; x < image.getWidth(); x++) {
      for (int y = 0; y < image.getHeight(); y++) {
        result.setRGB(image.getWidth() - x - 1, y, image.getRGB(x, y));
      }
    }
    return result;
  }
  
}
