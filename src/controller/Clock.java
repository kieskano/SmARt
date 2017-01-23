package controller;

import java.awt.image.BufferedImage;
import java.util.Date;

import javafx.application.Platform;
import menu.HighScores;
import model.Game;
import view.GameView;

public class Clock extends Thread {

  public static long TICK_LENGTH = 0;

  private boolean running = true;
  private Game game;
  private GameView view;
  private CameraReader camReader;
  private ImgProcessor imgProcessor;

  public Clock(Game game, GameView view, CameraReader camReader, ImgProcessor imgProcessor) {
    this.game = game;
    this.view = view;
    this.camReader = camReader;
    this.imgProcessor = imgProcessor;
  }

  public void run() {
    camReader.open();
    try {
      view.setClock(this);
      while (running) {
        long startTime = new Date().getTime();
        tick();
        if (game.isGameOver()) {
          HighScores.writeScore();
          break;
        }
        waitForNextTick(startTime);
      }
    } finally {
      camReader.close();
    }
  }

  private void waitForNextTick(long startTime) {
    long ammountOfTimeWeTook = new Date().getTime() - startTime;
    long timeToWait = TICK_LENGTH - ammountOfTimeWeTook;
    if (timeToWait > 0) {
      try {
        Thread.sleep(timeToWait);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    } else {
      //System.out.println("Can't keep up! Time too late: " + timeToWait * -1 + " ms");
    }
  }

  private void tick() {
    long startTime = new Date().getTime();
    
    BufferedImage image = camReader.takePhoto();
    System.out.print("Photo: " + (new Date().getTime() - startTime) + " ms, ");
    startTime = new Date().getTime();
    
    imgProcessor.checkIfTouched(image, game.getScreen().getAllNumbers()); //TODO UNCOMMENT DIS SHIT
    System.out.print("ImgProc: " + (new Date().getTime() - startTime) + " ms, ");
    startTime = new Date().getTime();
    
    game.update();
    System.out.print("GameUpdate: " + (new Date().getTime() - startTime) + " ms\n");
    startTime = new Date().getTime();
    
    Platform.runLater(new Runnable() {
      @Override
      public void run() {
        view.update(game, image, game.answerIsCorrect(), game.isGameOver());
      }
    });
    if (game.answerIsCorrect()) {
      try {
        Thread.sleep(SmARt.TIME_CHECKMARK_SHOWED);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
    }
  }
  
  public void pleaseStop() {
    running = false;
  }
}
