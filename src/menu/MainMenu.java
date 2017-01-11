package menu;

import java.io.IOException;
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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenu extends Application {
	
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 800;
	private static final int BUTTONWIDTH = 500;
	private static final int BUTTONHEIGHT = 60;
	private static final int DISTANCEBETWEENBUTTONS = 10;

	private Menu menu;
	
	
	/**
	 * Run method to launch the menu on the stage
	 */
	@Override
	public void start(Stage primaryStage) {
		Pane root = new Pane();
		root.setPrefSize(WIDTH, HEIGHT);
		
		menu = new Menu();
		root.getChildren().addAll(getBackgroundView("background_menu.jpg"), menu);
		
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public ImageView getBackgroundView(String resource) {
		InputStream is;
		Image image = null;
		try {
			is = Files.newInputStream(Paths.get("resources/images/" + resource));
			image = new Image(is);
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		ImageView imgView = new ImageView(image);
		imgView.setFitWidth(WIDTH);
		imgView.setFitHeight(HEIGHT);
		
		return imgView;
	}
	
	
	/**
	 *	Menu class containing all the menu with their buttons, and the button functionality.
	 */
	private class Menu extends Parent {
		public Menu() {
			//Main menu
			VBox menu0 = new VBox(DISTANCEBETWEENBUTTONS);	//Distance between the menu buttons of menu 0
			menu0.setTranslateX(WIDTH/2 - BUTTONWIDTH/2);	//Move the menu buttons to the middle of the screen (x)
			menu0.setTranslateY(HEIGHT/2 - BUTTONHEIGHT/2);	//Move the menu buttons to the middle of the screen (y)
			
			//Instructions menu
			VBox menu1 = new VBox(DISTANCEBETWEENBUTTONS);
			menu1.setTranslateX(WIDTH/2 - BUTTONWIDTH/2);
			menu1.setTranslateY(HEIGHT/2 - BUTTONHEIGHT/2 + 2*DISTANCEBETWEENBUTTONS + 2*BUTTONHEIGHT);
			
			
			//The menu buttons are implemented below here
			MenuButton playButton = new MenuButton("Play Game");
			playButton.setOnMouseClicked(event -> {
				System.out.println("Play button clicked!");
			});
			
			MenuButton instructionButton = new MenuButton("Instructions");
			instructionButton.setOnMouseClicked(event -> {
				System.out.println("Instructions button clicked!");
				getChildren().add(getBackgroundView("background_instruction.jpg"));
				getChildren().add(menu1);
				getChildren().remove(menu0);
				
			});
			
			MenuButton exitButton = new MenuButton("Exit");
			exitButton.setOnMouseClicked(event -> {
				System.exit(0);
			});
			
			MenuButton backButton = new MenuButton("Back");
			backButton.setOnMouseClicked(event -> {
				System.out.println("Back button clicked!");
				getChildren().add(menu0);
				getChildren().remove(getBackgroundView("background_instruction.jpg"));
				getChildren().remove(menu1);
			});
			
			//Add the buttons to the corresponding menus
			menu0.getChildren().addAll(playButton, instructionButton, exitButton);
			menu1.getChildren().addAll(backButton);
			
			Rectangle bg = new Rectangle(WIDTH, HEIGHT);
			bg.setOpacity(0.1);
			
			getChildren().addAll(bg, menu0);
		}
	}
	
	
	/**
	 *	Blueprint for the menu buttons
	 */
	private static class MenuButton extends StackPane {
		private Text text;
		
		public MenuButton(String buttonName) {
			text = new Text(buttonName);
			text.getFont();
			text.setFont(Font.font(20));
			
			Rectangle box = new Rectangle(BUTTONWIDTH, BUTTONHEIGHT);
			box.setOpacity(0.6);
			box.setFill(Color.WHITE);
			box.setEffect(new GaussianBlur(0.5));
			getChildren().addAll(box, text);
			
			setOnMouseEntered(event -> {	// Mouse hovering over text box
				box.setFill(Color.BLACK);
				text.setFill(Color.WHITE);
			});
			
			setOnMouseExited(event -> {		// Mouse not hovering over text box anymore
				box.setFill(Color.WHITE);
				text.setFill(Color.BLACK);
			});
			
			//Glow effect when clicking on a menu button
			DropShadow effect = new DropShadow(50, Color.WHITE);
			effect.setInput(new Glow());
			setOnMousePressed(event -> setEffect(effect));
			setOnMouseReleased(event -> setEffect(null));
		}
	}
	
	/**
	 * Start the main menu (start of the application)
	 */
	public static void main(String[] args) {
		launch(args);
	}


}
