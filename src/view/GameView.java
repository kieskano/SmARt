package view;

import model.Game;

import java.awt.image.BufferedImage;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;

import controller.Clock;
import controller.SmARt;
import model.Number;
import javafx.application.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import menu.MainMenu;

public class GameView extends Application {

  public static final String FONT_NAME = "Veranda";

  public static final Color NUMBER_STROKE_COLOR = Color.DARKBLUE;
  public static final Color NUMBER_FILL_COLOR = Color.LIGHTBLUE;
  public static final double NUMBER_STROKE_WIDTH = 5;
  public static final double NUMBER_TEXT_SIZE = 60;
  public static final Font NUMBER_TEXT_FONT = Font.font(FONT_NAME, NUMBER_TEXT_SIZE);

  public static final Color TEXT_COLOR = Color.BLACK;

  public static final Color BORDER_COLOR = Color.BLACK;
  public static final int BORDER_WIDTH = 10;

  public static final Color OBJECTIVE_STROKE_COLOR = Color.DARKVIOLET;
  public static final Color OBJECTIVE_FILL_COLOR = Color.VIOLET;
  public static final double OBJECTIVE_TEXT_SIZE = 80;
  public static final Font OBJECTIVE_TEXT_FONT = Font.font(FONT_NAME, OBJECTIVE_TEXT_SIZE);
  public static final double OBJECTIVE_SQUARE_SIZE = 130;
  public static final double OBJECTIVE_CIRCLE_SIZE = 120;

  private int width;
  private int height;
  private Canvas canvas;
  private FontLoader fl;
  private Clock clock;

  public GameView(int width, int height) {
    this.width = width;
    this.height = height;
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("SmARt");
    canvas = new Canvas(SmARt.SCREEN_DIMENSION.getWidth(), SmARt.SCREEN_DIMENSION.getHeight());
    primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.setX(0);
    primaryStage.setY(0);

    fl = Toolkit.getToolkit().getFontLoader();

    StackPane root = new StackPane();
    root.getChildren().add(canvas);

    Scene scene = new Scene(root);
    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE && clock != null) {
          primaryStage.close();
          clock.pleaseStop();
          new Thread(){
            public void run() {
              MainMenu.main(new String[0]);
            }
          }.start();
        }
      }
    });
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public void update(Game game, BufferedImage bImage) {
    if (canvas != null) {
      GraphicsContext gc = canvas.getGraphicsContext2D();

      //Draw background
      Image image = SwingFXUtils.toFXImage(bImage, null);
      gc.drawImage(image, 0, 0, width, height);

      //Draw numbers
      gc.setLineWidth(NUMBER_STROKE_WIDTH);
      gc.setStroke(NUMBER_STROKE_COLOR);
      gc.setFill(NUMBER_FILL_COLOR);
      gc.setFont(NUMBER_TEXT_FONT);
      for (Number number : game.getScreen().getAllNumbers()) {
        //Draw square
        gc.setFill(NUMBER_FILL_COLOR);
        gc.fillRect(number.getX(), number.getY(), number.getSize(), number.getSize());
        gc.strokeRect(number.getX(), number.getY(), number.getSize(), number.getSize());

        //Draw number
        gc.setFill(TEXT_COLOR);
        String text = "" + number.getValue();
        gc.fillText(text, number.getX() + number.getSize()/2 - fl.computeStringWidth(text, NUMBER_TEXT_FONT)/2, number.getY() + number.getSize()/2 + NUMBER_TEXT_SIZE/3);
      }

      //Draw border
      gc.setFill(BORDER_COLOR);
      gc.fillRect(width / 2 - BORDER_WIDTH / 2, 0, BORDER_WIDTH, height);

      //Draw objective circle and square
      gc.setFill(OBJECTIVE_FILL_COLOR);
      gc.setStroke(OBJECTIVE_STROKE_COLOR);
      gc.fillOval(width/2 - OBJECTIVE_CIRCLE_SIZE/2, height/4 - OBJECTIVE_CIRCLE_SIZE/2, OBJECTIVE_CIRCLE_SIZE, OBJECTIVE_CIRCLE_SIZE);
      gc.strokeOval(width/2 - OBJECTIVE_CIRCLE_SIZE/2, height/4 - OBJECTIVE_CIRCLE_SIZE/2, OBJECTIVE_CIRCLE_SIZE, OBJECTIVE_CIRCLE_SIZE);
      gc.fillRect(width/2 - OBJECTIVE_SQUARE_SIZE/2, height/2 - OBJECTIVE_SQUARE_SIZE/2, OBJECTIVE_SQUARE_SIZE, OBJECTIVE_SQUARE_SIZE);
      gc.strokeRect(width/2 - OBJECTIVE_SQUARE_SIZE/2, height/2 - OBJECTIVE_SQUARE_SIZE/2, OBJECTIVE_SQUARE_SIZE, OBJECTIVE_SQUARE_SIZE);

      //Draw objective text
      String symbol = game.getScreen().getObjective().getType().toString();
      String answer = "" + game.getScreen().getObjective().getAnswer();
      gc.setFill(TEXT_COLOR);
      gc.setFont(OBJECTIVE_TEXT_FONT);
      gc.fillText(symbol, width/2 - fl.computeStringWidth(symbol, OBJECTIVE_TEXT_FONT)/2, height/4 + OBJECTIVE_TEXT_FONT.getSize()/3);
      gc.fillText(answer, width/2 - fl.computeStringWidth(answer, OBJECTIVE_TEXT_FONT)/2, height/2 + OBJECTIVE_TEXT_FONT.getSize()/3);

    }
  }

  public void setClock(Clock clock) {
    this.clock = clock;
  }

}
