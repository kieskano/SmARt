package model;

import java.util.ArrayList;

import controller.SmARt;

public class Screen {

  private int width;
  private int height;
  private ArrayList<Number> leftNumbers;
  private ArrayList<Number> rightNumbers;
  private ArrayList<Number> allNumbers;
  
  public Screen(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public void setNumbers(ArrayList<Number> leftNumbers, ArrayList<Number> rightNumbers) {
    this.leftNumbers = leftNumbers;
    this.rightNumbers = rightNumbers;
    allNumbers = new ArrayList<>();
    allNumbers.addAll(leftNumbers);
    allNumbers.addAll(rightNumbers);
  }
  
  public ArrayList<Number> getLeftNumbers() {
    return leftNumbers;
  }
  
  public ArrayList<Number> getRightNumbers() {
    return rightNumbers;
  }
  
  public ArrayList<Number> getAllNumbers() {
    return allNumbers;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public static Screen generateScreen(int width, int height) {
    Screen screen = new Screen(width, height);
    ArrayList<Integer> leftIntegers = new ArrayList<>();
    ArrayList<Integer> rightIntegers = new ArrayList<>();
    for (int i = 0; i < SmARt.NUMBERS_PER_SIDE; i++) {
      
    }
    return null;
  }
}
