package view;

import model.Game;

import java.awt.image.BufferedImage;

import controller.SmARt;
import model.Number;
import javafx.application.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GameView extends Application {

  public static final Color NUMBER_STROKE_COLOR = Color.AQUA;
  public static final Color NUMBER_FILL_COLOR = Color.AQUAMARINE;
  public static final Color BORDER_COLOR = Color.DARKORCHID;
  public static final int BORDER_WIDTH = 40;

  private int width;
  private int height;
  private Canvas canvas;

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

    StackPane root = new StackPane();
    root.getChildren().add(canvas);
    primaryStage.setScene(new Scene(root));
    primaryStage.show();
  }

  public void update(Game game, BufferedImage bImage) {
    if (canvas != null) {
      GraphicsContext gc = canvas.getGraphicsContext2D();

      //Draw background
      Image image = SwingFXUtils.toFXImage(bImage, null);
      gc.drawImage(image, 0, 0, width, height);

      //Draw numbers
      gc.setStroke(NUMBER_STROKE_COLOR);
      gc.setFill(NUMBER_FILL_COLOR);
      for (Number number : game.getScreen().getAllNumbers()) {
        gc.fillRect(number.getX(), number.getY(), number.getSize(), number.getSize());
        gc.strokeRect(number.getX(), number.getY(), number.getSize(), number.getSize());
      }

      //Draw border
      gc.setFill(BORDER_COLOR);
      gc.fillRect(width / 2 - BORDER_WIDTH / 2, 0, BORDER_WIDTH, height);
    }
  }

}
