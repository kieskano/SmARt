package controller;

import java.awt.image.BufferedImage;
import java.util.Date;

import model.Game;
import view.GameView;

public class Clock extends Thread {

  public static long TICK_LENGTH = 50;

  private boolean running = true;
  private Game game;
  private GameView view;
  private CameraReader camReader;
  private ImageProcessor imgProcessor;

  public Clock(Game game, GameView view, CameraReader camReader, ImageProcessor imgProcessor) {
    this.game = game;
    this.view = view;
    this.camReader = camReader;
    this.imgProcessor = imgProcessor;
  }

  public void run() {
    camReader.open();
    try {
      while (running) {
        long startTime = new Date().getTime();
        tick();
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
      System.out.println("Can't keep up! Time too late: " + timeToWait * -1 + " ms");
    }
  }

  private void tick() {
    BufferedImage image = camReader.takePhoto();
    imgProcessor.checkIfTouched(image, game.getScreen().getAllNumbers());
    game.update();
    view.update(game);
  }
}
