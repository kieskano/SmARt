package view;

import model.Game;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

import com.sun.javafx.tk.FontLoader;
import com.sun.javafx.tk.Toolkit;

import controller.Clock;
import controller.SmARt;
import model.Number;
import model.Player;
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
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import menu.MainMenu;

public class GameView extends Application {

  public static final String FONT_NAME = "Veranda";

  public static final Color NUMBER_STROKE_COLOR = Color.DARKBLUE;
  public static final Color NUMBER_FILL_COLOR = Color.LIGHTBLUE;
  public static final Color NUMBER_SELECTED_STROKE_COLOR = Color.DARKGOLDENROD;
  public static final Color NUMBER_SELECTED_FILL_COLOR = Color.LIGHTGOLDENRODYELLOW;
  public static final double NUMBER_STROKE_WIDTH = 5;
  public static final double NUMBER_TEXT_SIZE = 60;
  public static final Font NUMBER_TEXT_FONT = Font.font(FONT_NAME, NUMBER_TEXT_SIZE);

  public static final Color TEXT_COLOR = Color.BLACK;
  public static final Font DEBUG_FONT = Font.font(FONT_NAME, 15);

  public static final Color BORDER_COLOR = Color.BLACK;
  public static final int BORDER_WIDTH = 10;

  public static final int TIMER_HEIGHT = 120;
  public static final int TIMER_WIDTH = 240;
  public static final Font TIMER_FONT = Font.font(FONT_NAME, 100);

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
  private Image checkMark;
  private Scene scene;
  private Stage primaryStage;

  public GameView(int width, int height) {
    this.width = width;
    this.height = height;
    try {
      checkMark = new Image(Files.newInputStream(Paths.get("resources/images/check_mark.png")));
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("COULD NOT LOAD IMAGE");
      System.exit(1);
    }
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    this.primaryStage = primaryStage;
    fl = Toolkit.getToolkit().getFontLoader();

    primaryStage.setTitle("SmARt");
    canvas = new Canvas(SmARt.SCREEN_DIMENSION.getWidth(), SmARt.SCREEN_DIMENSION.getHeight());
    GraphicsContext gc = canvas.getGraphicsContext2D();
    gc.setFill(Color.BLACK);
    gc.setFont(Font.font("Veranda", 150));
    gc.fillText("Loading...", SmARt.SCREEN_DIMENSION.getWidth()/2 - fl.computeStringWidth("Loading...", Font.font("Veranda", 150))/2, SmARt.SCREEN_DIMENSION.getHeight()/2);
    primaryStage.initStyle(StageStyle.UNDECORATED);
    primaryStage.setX(0);
    primaryStage.setY(0);


    StackPane root = new StackPane();
    root.getChildren().add(canvas);

    scene = new Scene(root);
    scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent event) {
        if (event.getCode() == KeyCode.ESCAPE && clock != null) {
          primaryStage.close();
          clock.pleaseStop();
          Platform.runLater(new Runnable() {
            public void run() {
              new MainMenu().start(new Stage());
            }
          });
        }
      }
    });
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public void update(Game game, BufferedImage bImage, boolean answerCorrect, boolean gameOver) {
    if (canvas != null) {
      GraphicsContext gc = canvas.getGraphicsContext2D();

      //Draw background
      Image image = SwingFXUtils.toFXImage(bImage, null);
      gc.drawImage(image, 0, 0, width, height);

      if (game.isGameOver()) {
        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
          @Override
          public void handle(KeyEvent event) {
            primaryStage.close();
            clock.pleaseStop();
            Platform.runLater(new Runnable() {
              public void run() {
                new MainMenu().start(new Stage());
              }
            });

          }
        });

        //Draw "Time is up"
        Font font = Font.font("Veranda", FontWeight.BOLD, 250);
        gc.setFont(font);
        gc.setFill(Color.WHITE);
        gc.setStroke(Color.BLACK);
        gc.fillText("Time's up!", width/2 -fl.computeStringWidth("Time's up!", font)/2, height/4);
        gc.strokeText("Time's up!", width/2 -fl.computeStringWidth("Time's up!", font)/2, height/4);

        //Draw "Your score is : <score>"
        font = Font.font("Veranda", FontWeight.BOLD, 200);
        gc.setFont(font);
        gc.fillText("Your score is: " + Player.getScore(), width/2 -fl.computeStringWidth("Your score is: " + Player.getScore(), font)/2, height/4*2);
        gc.strokeText("Your score is: " + Player.getScore(), width/2 -fl.computeStringWidth("Your score is: " + Player.getScore(), font)/2, height/4*2);

        //Draw "Press any key to go to the main menu"
        font = Font.font("Veranda", FontWeight.BOLD, 95);
        gc.setFont(font);
        gc.fillText("Press any key to go to the main menu", width/2 -fl.computeStringWidth("Press any key to go to the main menu", font)/2, height/4*3);
        gc.strokeText("Press any key to go to the main menu", width/2 -fl.computeStringWidth("Press any key to go to the main menu", font)/2, height/4*3);

      } else {
        //Draw numbers
        gc.setLineWidth(NUMBER_STROKE_WIDTH);
        for (Number number : game.getScreen().getAllNumbers()) {
          //Draw square
          if (number.isTouched()) {
            gc.setStroke(NUMBER_SELECTED_STROKE_COLOR);
            gc.setFill(NUMBER_SELECTED_FILL_COLOR);
          } else {
            gc.setStroke(NUMBER_STROKE_COLOR);
            gc.setFill(NUMBER_FILL_COLOR);
          }
          gc.fillRect(number.getX(), number.getY(), number.getSize(), number.getSize());
          gc.strokeRect(number.getX(), number.getY(), number.getSize(), number.getSize());
          
          //Draw number
          gc.setFont(NUMBER_TEXT_FONT);
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

        //Draw timer
        gc.setFill(OBJECTIVE_FILL_COLOR);
        gc.setStroke(OBJECTIVE_STROKE_COLOR);
        gc.fillRoundRect(width/2 - TIMER_WIDTH/2, 20, TIMER_WIDTH, TIMER_HEIGHT, 40, 40);
        gc.strokeRoundRect(width/2 - TIMER_WIDTH/2, 20, TIMER_WIDTH, TIMER_HEIGHT, 40, 40);
        String time = game.getTimeLeft();
        gc.setFill(TEXT_COLOR);
        gc.setFont(TIMER_FONT);
        gc.fillText(time, width/2 - fl.computeStringWidth(time, TIMER_FONT)/2, 20 + TIMER_HEIGHT/2 + TIMER_FONT.getSize()/3);


        //Draw check mark
        if (answerCorrect) {
          gc.setGlobalAlpha(0.6);
          gc.drawImage(checkMark, width/2 - checkMark.getWidth()/2, height/2 - checkMark.getHeight()/2);
          gc.setGlobalAlpha(1);
        }
      }



    }
  }

  public void setClock(Clock clock) {
    this.clock = clock;
  }

}
