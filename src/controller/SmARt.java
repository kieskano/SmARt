package controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import model.Game;
import model.Objective;
import model.Objective.Type;
import view.GameView;

public class SmARt {

  public static final int CAM_NR = 0;
  public static final Dimension CAM_DIMENSION = new Dimension(1280, 720);
  //  public static final Dimension CAM_DIMENSION = new Dimension(1600, 900);
  public static final int NUMBER_SQUARE_SIZE = 100;
  public static final int NUMBERS_PER_SIDE = 5;
  //  public static final Dimension SCREEN_DIMENSION = new Dimension(1920, 1080);
  //  public static final Dimension SCREEN_DIMENSION = new Dimension(1600, 900);
  public static final Dimension SCREEN_DIMENSION = Toolkit.getDefaultToolkit().getScreenSize();
  public static final int BIGGEST_NUMBER = 9;
  public static final long TIME_CHECKMARK_SHOWED = 3000;
  public static double IMG_SCALING = CAM_DIMENSION.getWidth() / SCREEN_DIMENSION.getWidth();
  public static final List<Type> ARITHMETIC_INSTRUCTIONS = Arrays.asList(new Type[]{Type.ADD, Type.SUBTRACT, Type.MULTIPLY, Type.DIVIDE});

  private SmARt() {	}

  public static void main(String[] args) {
    gameLoop();
  }

  public static void gameLoop() {
    //INIT toolkit
    JFXPanel panel = new JFXPanel();

    GameView view = new GameView(SCREEN_DIMENSION.width, SCREEN_DIMENSION.height);
    Platform.runLater(new Runnable() {
      public void run() {
        try {
          view.start(new Stage());
        } catch (Exception e) {
          e.printStackTrace();
        };
      }
    });

    CameraReader camReader = new CameraReader(CAM_NR, CAM_DIMENSION);
    ImgProcessor imgProcessor = new ImageProcessor();
    Game game = new Game();
    Clock clock = new Clock(game, view, camReader, imgProcessor);
    clock.start();
  }
}
