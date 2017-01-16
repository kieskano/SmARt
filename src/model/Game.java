package model;

import java.util.ArrayList;

import controller.SmARt;

public class Game {

  private Screen screen;
  private boolean answerIsCorrect;
  private int frameCounter;

  public Game() {
    screen = Screen.generateScreen(SmARt.SCREEN_DIMENSION);
    answerIsCorrect = false;
    frameCounter = SmARt.NR_OF_FRAMES_CHECKMARK_SHOWED;
  }

  public Screen getScreen() {
    return screen;
  }

  public void update() {
    if (answerIsCorrect) {
      frameCounter--;
      if (frameCounter == 0) {
        screen = Screen.generateScreen(SmARt.SCREEN_DIMENSION);
        answerIsCorrect = false;
        frameCounter = SmARt.NR_OF_FRAMES_CHECKMARK_SHOWED;
      }
    } else {
      ArrayList<Integer> touchedNrs = new ArrayList<>();
      for (Number number : screen.getAllNumbers()) {
        if (number.isTouched()) {
          touchedNrs.add(number.getValue());
        }
      }
      if (screen.getObjective().isCorrect(touchedNrs)) {
        System.out.println("The answer is correct!");
        answerIsCorrect = true;
      }
    }
  }

  public boolean answerIsCorrect() {
    return answerIsCorrect;
  }

}
