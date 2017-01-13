package model;

public class Number {

  private int value;
  private int x;		//X and Y coordinates represent the upper left corner of the square
  private int y;
  private int size;
  private boolean isTouched;
  
  public Number(int value, int x, int y, int size) {
    this.value = value;
    this.x = x;
    this.y = y;
    this.size = size;
    
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
  
}
