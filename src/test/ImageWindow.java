package test;

import java.awt.image.BufferedImage;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import menu.MainMenu;

public class ImageWindow extends Application {

  private ImageView imgView;

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setHeight(720);
    primaryStage.setWidth(1280);

    StackPane root = new StackPane();

    imgView = new ImageView();

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public void showImage(BufferedImage bImage) {
    if (imgView != null) {
      imgView.setImage(SwingFXUtils.toFXImage(bImage, null));
    }
  }
}
