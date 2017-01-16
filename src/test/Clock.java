package test;

import java.awt.image.BufferedImage;
import java.util.Date;

import controller.CameraReader;
import javafx.application.Platform;
import model.Game;
import view.GameView;

public class Clock extends Thread {

  public static final long TICK_LENGTH = 0;
  
  private boolean running = true;
  private SliderWindow sliderWindow;
  private CameraReader camReader;
  private ImageWindow imageWindow;
  private ImageWindow imgWin;
  private HSVimgFactory imgFactory;

  public Clock(SliderWindow sliderWindow, CameraReader cameraReader, ImageWindow imageWindow, ImageWindow imgWin, HSVimgFactory imgFactory) {
    this.sliderWindow = sliderWindow;
    this.camReader = cameraReader;
    this.imageWindow = imageWindow;
    this.imgFactory = imgFactory;
    this.imgWin = imgWin;
  }

  public void run() {
    camReader.open();
    try {
      while (running) {
        long startTime = new Date().getTime();
        tick();
        waitForNextTick(startTime);
      }
    } finally {
      camReader.close();
    }
  }

  private void waitForNextTick(long startTime) {
    long ammountOfTimeWeTook = new Date().getTime() - startTime;
    long timeToWait = TICK_LENGTH - ammountOfTimeWeTook;
    if (timeToWait > 0) {
      try {
        Thread.sleep(timeToWait);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } else {
      //System.out.println("Can't keep up! Time too late: " + timeToWait * -1 + " ms");
    }
  }

  private void tick() {
    updateHSVValues();
    BufferedImage image = camReader.takePhoto();
    BufferedImage hsvImage = imgFactory.getHSVImage(image);
    imageWindow.showImage(hsvImage);
    imgWin.showImage(image);
  }
  
  private void updateHSVValues() {
    imgFactory.setHUE_MIN(sliderWindow.getHueMin());
    imgFactory.setHUE_MAX(sliderWindow.getHueMax());
    imgFactory.setSAT_MIN(sliderWindow.getSatMin());
    imgFactory.setSAT_MAX(sliderWindow.getSatMax());
    imgFactory.setVAL_MIN(sliderWindow.getValMin());
    imgFactory.setVAL_MAX(sliderWindow.getValMax());
  }

  public void pleaseStop() {
    running = false;
  }
}
