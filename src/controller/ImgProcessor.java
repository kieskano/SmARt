package controller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import model.Number;

public interface ImgProcessor {

  public void checkIfTouched(BufferedImage image, ArrayList<Number> numbers);
  
}
