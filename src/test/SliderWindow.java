package test;

import controller.SmARt;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import menu.MainMenu;

public class SliderWindow extends Application{

  public static void main(String[] args) {
    launch();
  }

  private Slider hueMin;
  private Slider hueMax;
  private Slider satMin;
  private Slider satMax;
  private Slider valMin;
  private Slider valMax;
  private Text hueMinText;
  private Text hueMaxText;
  private Text satMinText;
  private Text satMaxText;
  private Text valMinText;
  private Text valMaxText;

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("Sliders");
    primaryStage.setWidth(240);
    primaryStage.setHeight(140);
    hueMin = new Slider(0, 180, 0);
    hueMax = new Slider(0, 180, 180);
    satMin = new Slider(0, 255, 0);
    satMax = new Slider(0, 255, 255);
    valMin = new Slider(0, 255, 0);
    valMax = new Slider(0, 255, 255);
    hueMin.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> ov,
          Number old_val, Number new_val) {
        updateTextFields();
      }
    });
    hueMax.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> ov,
          Number old_val, Number new_val) {
        updateTextFields();
      }
    });
    satMin.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> ov,
          Number old_val, Number new_val) {
        updateTextFields();
      }
    });
    satMax.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> ov,
          Number old_val, Number new_val) {
        updateTextFields();
      }
    });
    valMin.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> ov,
          Number old_val, Number new_val) {
        updateTextFields();
      }
    });
    valMax.valueProperty().addListener(new ChangeListener<Number>() {
      public void changed(ObservableValue<? extends Number> ov,
          Number old_val, Number new_val) {
        updateTextFields();
      }
    });
    
    hueMinText = new Text("hueMin = 0");
    hueMaxText = new Text("hueMax = 180");
    satMinText = new Text("satMin = 0");
    satMaxText = new Text("satMax = 255");
    valMinText = new Text("valMin = 0");
    valMaxText = new Text("valMax = 255");

    FlowPane root = new FlowPane();

    root.getChildren().add(hueMin);
    root.getChildren().add(hueMinText);
    root.getChildren().add(hueMax);
    root.getChildren().add(hueMaxText);
    root.getChildren().add(satMin);
    root.getChildren().add(satMinText);
    root.getChildren().add(satMax);
    root.getChildren().add(satMaxText);
    root.getChildren().add(valMin);
    root.getChildren().add(valMinText);
    root.getChildren().add(valMax);
    root.getChildren().add(valMaxText);

    Scene scene = new Scene(root);
    primaryStage.setScene(scene);
    primaryStage.show();

  }

  private void updateTextFields() {
    hueMinText.setText("hueMin = " + (int) hueMin.getValue());
    hueMaxText.setText("hueMax = " + (int) hueMax.getValue());
    satMinText.setText("satMin = " + (int) satMin.getValue());
    satMaxText.setText("satMax = " + (int) satMax.getValue());
    valMinText.setText("valMin = " + (int) valMin.getValue());
    valMaxText.setText("valMax = " + (int) valMax.getValue());
  }

  public int getHueMin() {
    return (int) hueMin.getValue();
  }
  
  public int getHueMax() {
    return (int) hueMax.getValue();
  }
  
  public int getSatMin() {
    return (int) hueMin.getValue();
  }
  
  public int getSatMax() {
    return (int) hueMax.getValue();
  }
  
  public int getValMin() {
    return (int) hueMin.getValue();
  }
  
  public int getValMax() {
    return (int) hueMax.getValue();
  }
}
