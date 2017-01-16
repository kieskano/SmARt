package test;

import java.awt.Dimension;

import controller.CameraReader;

public class HSVTester {

  public static void main(String[] args) {
    ImageWindow imgWin = new ImageWindow();
    SliderWindow slWin = new SliderWindow();
    CameraReader camReader = new CameraReader(0, new Dimension(1280, 720));
    Clock clock = new Clock(slWin, camReader, slWin, null);
    
    clock.start();
  }
  
}
