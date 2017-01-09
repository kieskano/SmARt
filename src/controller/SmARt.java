package controller;

import java.awt.Dimension;

import model.Game;
import view.GameView;

public class SmARt {

  public static final int CAM_NR = 0;
  public static final Dimension CAM_DIMENSION = new Dimension(1280, 720);
  public static final int NUMBER_SQUARE_SIZE = 42;
  public static final int NUMBERS_PER_SIDE = 5;
  
  public static void main(String[] args) {
    Game game = new Game();
    GameView view = new GameView();
    CameraReader camReader = new CameraReader(CAM_NR, CAM_DIMENSION);
    ImageProcessor imgProcessor = new ImageProcessor();
    Clock clock = new Clock(game, view, camReader, imgProcessor);
    clock.start();
  }
}
