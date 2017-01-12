package model;

import java.awt.Dimension;
import java.util.ArrayList;

import controller.SmARt;
import model.Objective.Type;

public class Screen {

  private int width;
  private int height;
  private ArrayList<Number> leftNumbers;
  private ArrayList<Number> rightNumbers;
  private ArrayList<Number> allNumbers;
  private Objective objective;
  
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
  
  public Objective getObjective() {
    return objective;
  }

  public static Screen generateScreen(Dimension dim) {
    Screen screen = new Screen((int) dim.getWidth(), (int) dim.getHeight());
    
    //Generate the numbers
    ArrayList<Integer> leftIntegers = new ArrayList<>();
    ArrayList<Integer> rightIntegers = new ArrayList<>();
    for (int i = 0; i < SmARt.NUMBERS_PER_SIDE; i++) {
      int newLeftNr = (int) (Math.random() * SmARt.BIGGEST_NUMBER + 1.0);
      leftIntegers.add(newLeftNr);
      int newRightNr = (int) (Math.random() * SmARt.BIGGEST_NUMBER + 1.0);
      rightIntegers.add(newRightNr);
    }
    
    ArrayList<Number> leftNumbers = new ArrayList<>();
    //Place the numbers on the left screen
    for (int integer : leftIntegers) {
      boolean emptySpaceFound = false;
      int x = 0;
      int y = 0;
      while (!emptySpaceFound) {
        x = (int) (Math.random() * (SmARt.SCREEN_DIMENSION.getWidth() / 2 - SmARt.NUMBER_SQUARE_SIZE));
        y = (int) (Math.random() * (SmARt.SCREEN_DIMENSION.getHeight() - 2*SmARt.NUMBER_SQUARE_SIZE));
        emptySpaceFound = true;
        for (Number number : leftNumbers) {
          //Check if generated square collides with already placed squares
          if ((number.getX() < x && number.getX() + SmARt.NUMBER_SQUARE_SIZE > x) && (number.getY() < y && number.getY() + SmARt.NUMBER_SQUARE_SIZE > y) || 
              (number.getX() < x && number.getX() + SmARt.NUMBER_SQUARE_SIZE > x) && (number.getY() < y + SmARt.NUMBER_SQUARE_SIZE && number.getY() + SmARt.NUMBER_SQUARE_SIZE > y + SmARt.NUMBER_SQUARE_SIZE) || 
              (number.getX() < x + SmARt.NUMBER_SQUARE_SIZE && number.getX() + SmARt.NUMBER_SQUARE_SIZE > x + SmARt.NUMBER_SQUARE_SIZE) && (number.getY() < y && number.getY() + SmARt.NUMBER_SQUARE_SIZE > y) || 
              (number.getX() < x + SmARt.NUMBER_SQUARE_SIZE && number.getX() + SmARt.NUMBER_SQUARE_SIZE > x + SmARt.NUMBER_SQUARE_SIZE) && (number.getY() < y + SmARt.NUMBER_SQUARE_SIZE && number.getY() + SmARt.NUMBER_SQUARE_SIZE > y + SmARt.NUMBER_SQUARE_SIZE)) {
            emptySpaceFound = false;
            break;
          }
        }
      }
      leftNumbers.add(new Number(integer, x, y, SmARt.NUMBER_SQUARE_SIZE));
    }
    
    ArrayList<Number> rightNumbers = new ArrayList<>();
    //Place the numbers on the right screen
    for (int integer : rightIntegers) {
      boolean emptySpaceFound = false;
      int x = 0;
      int y = 0;
      while (!emptySpaceFound) {
        x = (int) (Math.random() * (SmARt.SCREEN_DIMENSION.getWidth() / 2 - SmARt.NUMBER_SQUARE_SIZE) + (SmARt.SCREEN_DIMENSION.getWidth() / 2));
        y = (int) (Math.random() * SmARt.SCREEN_DIMENSION.getHeight() - SmARt.NUMBER_SQUARE_SIZE);
        emptySpaceFound = true;
        for (Number number : rightNumbers) {
          //Check if generated square collides with already placed squares
          if ((number.getX() < x && number.getX() + SmARt.NUMBER_SQUARE_SIZE > x) && (number.getY() < y && number.getY() + SmARt.NUMBER_SQUARE_SIZE > y) || 
              (number.getX() < x && number.getX() + SmARt.NUMBER_SQUARE_SIZE > x) && (number.getY() < y + SmARt.NUMBER_SQUARE_SIZE && number.getY() + SmARt.NUMBER_SQUARE_SIZE > y + SmARt.NUMBER_SQUARE_SIZE) || 
              (number.getX() < x + SmARt.NUMBER_SQUARE_SIZE && number.getX() + SmARt.NUMBER_SQUARE_SIZE > x + SmARt.NUMBER_SQUARE_SIZE) && (number.getY() < y && number.getY() + SmARt.NUMBER_SQUARE_SIZE > y) || 
              (number.getX() < x + SmARt.NUMBER_SQUARE_SIZE && number.getX() + SmARt.NUMBER_SQUARE_SIZE > x + SmARt.NUMBER_SQUARE_SIZE) && (number.getY() < y + SmARt.NUMBER_SQUARE_SIZE && number.getY() + SmARt.NUMBER_SQUARE_SIZE > y + SmARt.NUMBER_SQUARE_SIZE)) {
            emptySpaceFound = false;
            break;
          }
        }
      }
      rightNumbers.add(new Number(integer, x, y, SmARt.NUMBER_SQUARE_SIZE));
    }
    
    screen.setNumbers(leftNumbers, rightNumbers);
    screen.refreshObjective();
    return screen;
  }

  private void refreshObjective() {
    int operation = (int) Math.round(Math.random() * (SmARt.ARITHMATIC_INSTRUCTIONS.size() - 1));
    int leftNr = leftNumbers.get((int) Math.round(Math.random() * (SmARt.NUMBERS_PER_SIDE - 1))).getValue();
    int rightNr = rightNumbers.get((int) Math.round(Math.random() * (SmARt.NUMBERS_PER_SIDE - 1))).getValue();
    int answer = 0;
    switch (SmARt.ARITHMATIC_INSTRUCTIONS.get(operation)) {
    case ADD:
      answer = leftNr + rightNr;
      break;
    case SUBTRACT:
      answer = leftNr - rightNr;
      break;
    case DEVIDE:
      answer = leftNr / rightNr;
      break;
    case MULTIPLY:
      answer = leftNr * rightNr;
      break;
    default:
      answer = -999;
      break;
    }
    objective = new Objective(SmARt.ARITHMATIC_INSTRUCTIONS.get(operation), answer);
  }
}
