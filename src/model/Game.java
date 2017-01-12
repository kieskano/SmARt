package model;

import java.util.ArrayList;

import controller.SmARt;

public class Game {

  private Screen screen;
  
  public Game() {
    screen = Screen.generateScreen(SmARt.SCREEN_DIMENSION);
  }
  
  public Screen getScreen() {
    return screen;
  }

  public void update() {
    ArrayList<Integer> touchedNrs = new ArrayList<>();
    for (Number number : screen.getAllNumbers()) {
      if (number.isTouched()) {
        touchedNrs.add(number.getValue());
      }
    }
    if (screen.getObjective().isCorrect(touchedNrs)) {
      System.out.println("The answer is correct!");
      screen = Screen.generateScreen(SmARt.SCREEN_DIMENSION);
    }
  }

}
