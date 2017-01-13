package controller;

import java.awt.Dimension;
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
  public static final int NUMBER_SQUARE_SIZE = 100;
  public static final int NUMBERS_PER_SIDE = 5;
  public static final Dimension SCREEN_DIMENSION = new Dimension(1920, 1080);
  public static final int BIGGEST_NUMBER = 9;
  public static final List<Type> ARITHMATIC_INSTRUCTIONS = Arrays.asList(new Type[]{Type.ADD, Type.SUBTRACT});
  
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
	
	Game game = new Game();
	CameraReader camReader = new CameraReader(CAM_NR, CAM_DIMENSION);
	ImageProcessor imgProcessor = new ImageProcessor();
	Clock clock = new Clock(game, view, camReader, imgProcessor);
	clock.start();
  }
  
  
  
}
