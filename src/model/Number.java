package model;

public class Number {

  private int value;
  private int x;		//X and Y coordinates represent the upper left corner of the square
  private int y;
  private int size;
  private boolean isTouched;
  private boolean isLeftNumber;
  
  public Number(int value, int x, int y, int size, boolean isLeftNumber) {
    this.value = value;
    this.x = x;
    this.y = y;
    this.size = size;
    this.isLeftNumber = isLeftNumber;
    
    isTouched = false;
  }

  public int getValue() {
    return value;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public int getSize() {
    return size;
  }

  public boolean isTouched() {
    return isTouched;
  }
  
  public void setIsTouched(boolean isTouched) {
    this.isTouched = isTouched;
  }
  
  /**
   * Returns whether the number is located in the left or right area of the screen
   * @return - true if number is on the left side, false if the number is on the right side
   */
  public boolean getIsLeftNumber() {
	  return isLeftNumber;
  }
  
  /**
   * Prints info about the number.
   */
  public void printInfo() {
	  System.out.printf("Number (%4d,%4d) size:%3d value:%2d isTouched: %b\n", x, y, size, value, isTouched);
  }
  
}
