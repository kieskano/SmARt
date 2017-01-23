package model;

import java.util.ArrayList;
import java.util.Date;

import controller.SmARt;

public class Game {

  public static final long TIME_PER_GAME = 2*60*1000 + 5*1000;

  private Screen screen;
  private boolean answerIsCorrect;
  private long startTime;
  private long curTime;

  public Game() {
    answerIsCorrect = false;
    screen = Screen.generateScreen(SmARt.SCREEN_DIMENSION);
    startTime = new Date().getTime();
    curTime = new Date().getTime();
  }

  public Screen getScreen() {
    return screen;
  }

  public void update() {
    if (!isGameOver()) {
      if (answerIsCorrect) {
        screen = Screen.generateScreen(SmARt.SCREEN_DIMENSION);
        answerIsCorrect = false;
      }
      ArrayList<Number> touchedNrs = new ArrayList<>();
      for (Number number : screen.getAllNumbers()) {
        if (number.isTouched()) {
          touchedNrs.add(number);
        }
      }
      if (screen.getObjective().isCorrect(touchedNrs)) {
        System.out.println("The answer is correct!");
        Player.addPoints(10);
        answerIsCorrect = true;
      }
      curTime = new Date().getTime();
    }
  }

  public boolean answerIsCorrect() {
    return answerIsCorrect;
  }

  public String getTimeLeft() {
    String result = "";
    long timeLeft = TIME_PER_GAME - (curTime - startTime);
    if (timeLeft < 0) {
      result = "0:00";
    } else {
      long min = timeLeft/(60000);
      long sec;
      if (min > 0) {
        sec = timeLeft % (timeLeft/(60000)*60000)/1000;
      } else {
        sec = timeLeft / 1000;
      }
      if (sec < 10) {
        result = min + ":0" + sec;
      } else {
        result = min + ":" + sec;
      }
    }
    return result;
  }

  public boolean isGameOver() {
    return TIME_PER_GAME - (curTime - startTime) < 0;
  }
}
