package mainMenu;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenu extends Application {
	
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 800;
	private static final int BUTTONWIDTH = 500;
	private static final int BUTTONHEIGHT = 60;

	private Menu menu;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Pane root = new Pane();
		root.setPrefSize(WIDTH, HEIGHT);
		
		InputStream is = Files.newInputStream(Paths.get("resources/images/background_menu.jpg"));
		Image bg = new Image(is);
		is.close();
		
		ImageView imgView = new ImageView(bg);
		imgView.setFitWidth(WIDTH);
		imgView.setFitHeight(HEIGHT);
		
		menu = new Menu();
		//menu.setVisible(true);
		
		menu = new Menu();
		root.getChildren().addAll(imgView, menu);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	private class Menu extends Parent {
		public Menu() {
			VBox menu0 = new VBox(10);
			VBox menu1 = new VBox(10);
			menu0.setTranslateX(WIDTH/2 - BUTTONWIDTH/2);
			menu0.setTranslateY(HEIGHT/2 - BUTTONHEIGHT/2);
			menu1.setTranslateX(200);
			menu1.setTranslateY(200);
			
			final int offset = 400;
			
			MenuButton playButton = new MenuButton("Play Game");
			playButton.setOnMouseClicked(event -> {
				// Nothing yet
				System.out.println("Play button clicked!");
			});
			
			MenuButton instructionButton = new MenuButton("Instructions");
			instructionButton.setOnMouseClicked(event -> {
				/*TranslateTransition tt = new TranslateTransition(Duration.seconds(0.25), menu0);
				tt.setToX(menu0.getTranslateX() - offset);
				
				TranslateTransition tt2 = new TranslateTransition(Duration.seconds(0.5), menu1);
				tt.setToX(menu0.getTranslateX() - offset);*/
				
				System.out.println("Instructions button clicked!");
			});
			
			MenuButton exitButton = new MenuButton("Exit");
			exitButton.setOnMouseClicked(event -> {
				System.exit(0);
			});
			
			menu0.getChildren().addAll(playButton, instructionButton, exitButton);
			
			Rectangle bg = new Rectangle(WIDTH, HEIGHT);
			bg.setFill(Color.GRAY);
			bg.setOpacity(0.4);
			
			getChildren().addAll(bg, menu0);
		}
	}
	
	
	private static class MenuButton extends StackPane {
		private Text text;
		
		public MenuButton(String buttonName) {
			text = new Text(buttonName);
			text.setFont(text.getFont().font(20));
			
			Rectangle box = new Rectangle(BUTTONWIDTH, BUTTONHEIGHT);
			box.setOpacity(0.6);
			box.setFill(Color.WHITE);
			box.setEffect(new GaussianBlur(3.5));
			
			setAlignment(Pos.CENTER);
			//setRotate(-0.5);
			getChildren().addAll(box, text);
			
			setOnMouseEntered(event -> {
				box.setTranslateX(10);
				text.setTranslateX(10);
				box.setFill(Color.BLACK);
				text.setFill(Color.WHITE);
			});
			
			setOnMouseExited(event -> {
				box.setTranslateX(0);
				text.setTranslateX(0);
				box.setFill(Color.WHITE);
				text.setFill(Color.BLACK);
			});
			//Potential effects
			
			DropShadow effect = new DropShadow(50, Color.WHITE);
			effect.setInput(new Glow());
			setOnMousePressed(event -> setEffect(effect));
			setOnMouseReleased(event -> setEffect(null));
		}
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	
}
