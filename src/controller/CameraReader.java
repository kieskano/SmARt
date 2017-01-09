package controller;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import com.github.sarxos.webcam.Webcam;

public class CameraReader {

  private Webcam webcam;
  
  public CameraReader(int cameraNumber, Dimension dimensions) {
    webcam = Webcam.getWebcams().get(cameraNumber);
    webcam.setViewSize(dimensions);
  }
  
  public void open() {
    webcam.open();
  }
  
  public void close() {
    webcam.close();
  }
  
  public BufferedImage takePhoto() {
    return webcam.getImage();
  }
}
