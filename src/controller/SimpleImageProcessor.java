package controller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Number;

public class SimpleImageProcessor implements ImgProcessor {

  private int checkRed;
  private int checkGreen;
  private int checkBlue;
  private int margin;
  private BufferedImage firstImage;
  private Map<Integer, Map<Integer, ArrayList<Integer>>> savedColorAvg;

  public SimpleImageProcessor(int red, int green, int blue, int margin) {
    checkRed = red;
    checkGreen = green;
    checkBlue = blue;
    this.margin = margin;
    savedColorAvg = new HashMap<>();
  }

  public void checkIfTouched(BufferedImage image, ArrayList<Number> numbers) {
    if (firstImage == null) {
      firstImage = image;
      for (Number number : numbers) {
        addFirstAvgColorOfNumber(number);
      }
    }
    for (Number number : numbers) {
      int xLoc = (int) (number.getX() * SmARt.IMG_SCALING);
      int yLoc = (int) (number.getY() * SmARt.IMG_SCALING);
      int size = (int) (number.getSize() * SmARt.IMG_SCALING);
      int r = 0;
      int g = 0;
      int b = 0;

      for (int x = xLoc; x < (xLoc + size); x++) {
        for (int y = yLoc; y < (yLoc + size); y++) {
          int color = image.getRGB(x, y);
          b = b + (int) (color & 0xff);
          g = g + (int) ((color & 0xff00) >> 8);
          r = r + (int) ((color & 0xff0000) >> 16);
        }
      }
      r = r / (size * size);
      g = g / (size * size);
      b = b / (size * size);

      //Check for specific color
      //======================================================================
//      number.setIsTouched(r > checkRed - margin && r < checkRed + margin &&
//          g > checkGreen - margin && g < checkGreen + margin &&
//          b > checkBlue - margin && b < checkBlue + margin);
      //======================================================================
      
      //Check for difference
      //======================================================================
      if (!savedColorAvg.containsKey(number.getX()) || !savedColorAvg.get(number.getX()).containsKey(number.getY())) {
        addFirstAvgColorOfNumber(number);
      }
      ArrayList<Integer> rgb = savedColorAvg.get(number.getX()).get(number.getY());
      number.setIsTouched(r < rgb.get(0) - margin || r > rgb.get(0) + margin ||
          g < rgb.get(1) - margin || g > rgb.get(1) + margin ||
          b < rgb.get(2) - margin || b > rgb.get(2) + margin);
      //======================================================================
    }
  }

  private void addFirstAvgColorOfNumber(Number number) {
    int xLoc = (int) (number.getX() * SmARt.IMG_SCALING);
    int yLoc = (int) (number.getY() * SmARt.IMG_SCALING);
    int size = (int) (number.getSize() * SmARt.IMG_SCALING);
    int r = 0;
    int g = 0;
    int b = 0;

    for (int x = xLoc; x < (xLoc + size); x++) {
      for (int y = yLoc; y < (yLoc + size); y++) {
        int color = firstImage.getRGB(x, y);
        b = b + (int) (color & 0xff);
        g = g + (int) ((color & 0xff00) >> 8);
        r = r + (int) ((color & 0xff0000) >> 16);
      }
    }
    r = r / (size * size);
    g = g / (size * size);
    b = b / (size * size);
    ArrayList<Integer> rgb = new ArrayList<>();
    rgb.add(r);
    rgb.add(g);
    rgb.add(b);
    if (savedColorAvg.containsKey(number.getX())) {
      if (savedColorAvg.get(number.getX()).containsKey(number.getY())) {
        savedColorAvg.get(number.getX()).replace(number.getY(), rgb);
      } else {
        savedColorAvg.get(number.getX()).put(number.getY(), rgb);
      }
    } else {
      savedColorAvg.put(number.getX(), new HashMap<>());
      savedColorAvg.get(number.getX()).put(number.getY(), rgb);
    }
  }
}
