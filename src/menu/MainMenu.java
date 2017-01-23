package menu;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import controller.SmARt;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Player;

public class MainMenu extends Application {
	
	private static final int WIDTH = 1280;
	private static final int HEIGHT = 800;
	private static final int BUTTONWIDTH = 500;
	private static final int BUTTONHEIGHT = 60;
	private static final int DISTANCEBETWEENBUTTONS = 10;

	private Menu menu;
	private Stage stage;
	private ImageView background_menu = getBackgroundView("background_menu.jpg");
	private ImageView background_instruction = getBackgroundView("background_instruction.jpg");
	
	
	
	/**
	 * Run method to launch the menu on the stage.
	 */
	@Override
	public void start(Stage primaryStage) {
		this.stage = primaryStage;
		Pane root = new Pane();
		root.setPrefSize(WIDTH, HEIGHT);
		
		menu = new Menu();
		root.getChildren().addAll(background_menu, menu);
		root.setFocusTraversable(true);
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Returns an ImageView of the given filename located in the images directory.
	 * @param resource The filename of the image
	 * @return ImageView of the image
	 */
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
			menu1.setTranslateY(HEIGHT/2 - BUTTONHEIGHT/2 + 4*DISTANCEBETWEENBUTTONS + 4*BUTTONHEIGHT);
			
			//Highscores menu
			VBox menu2 = new VBox(DISTANCEBETWEENBUTTONS);
			menu2.setTranslateX(WIDTH/2 - BUTTONWIDTH/2);
			menu2.setTranslateY(HEIGHT/4 - BUTTONHEIGHT/4 + 2*DISTANCEBETWEENBUTTONS + 2*BUTTONHEIGHT);
			
			//Options menu
			VBox menu3 = new VBox(DISTANCEBETWEENBUTTONS);
			menu3.setTranslateX(WIDTH/2 - BUTTONWIDTH/2);
			menu3.setTranslateY(HEIGHT/2 - BUTTONHEIGHT/2 - 1*DISTANCEBETWEENBUTTONS + 2*BUTTONHEIGHT);
			
			//Play Game menu
			VBox menu4 = new VBox(DISTANCEBETWEENBUTTONS);
			menu4.setTranslateX(WIDTH/2 - BUTTONWIDTH/2);
			menu4.setTranslateY(HEIGHT/2 - BUTTONHEIGHT/2);

			
			//Contains all the scores for the highscores menu.
			TextArea scoresArea = new TextArea();
			scoresArea.setEditable(false);
			scoresArea.setScrollTop(0);
						
			//Space for playername input field
			Label label1 = new Label("Name:");
			label1.setFont(new Font("Verdana", 20));
			label1.setBackground(null);
			TextField playernameField = new TextField(Player.getPlayername());
			HBox hbPlayername = new HBox();
			hbPlayername.getChildren().addAll(label1, playernameField);
			hbPlayername.setSpacing(10);
			
			//The menu buttons are implemented below here
			MenuButton playButton = new MenuButton("Play Game");
			playButton.setOnMouseClicked(event -> {
				getChildren().add(menu4);
				getChildren().remove(menu0);
			});
			
			MenuButton instructionButton = new MenuButton("Instructions");
			instructionButton.setOnMouseClicked(event -> {
				getChildren().add(background_instruction);
				getChildren().add(menu1);
				getChildren().remove(menu0);
			});
			
			MenuButton highscoreButton = new MenuButton("Highscores");
			highscoreButton.setOnMouseClicked(event -> {
				scoresArea.clear();
				HighScores.insertScoresSorted(scoresArea);
				getChildren().add(menu2);
				getChildren().remove(menu0);
			});
			
			MenuButton optionsButton = new MenuButton("Options");
			optionsButton.setOnMouseClicked(event -> {
				playernameField.setText(Player.getPlayername());
				getChildren().add(menu3);
				getChildren().remove(menu0);
			});
			
			MenuButton exitButton = new MenuButton("Exit");
			exitButton.setOnMouseClicked(event -> {
				System.exit(0);
			});
			
			MenuButton mpButton = new MenuButton("Multiplayer");
			mpButton.setOnMouseClicked(event -> {
				stage.close();
				SmARt.gameLoop();
//				SmARt.gameLoop(true);
			});
			
			MenuButton spButton = new MenuButton("Singleplayer");
			spButton.setOnMouseClicked(event -> {
//				SmARt.gameLoop(false);
			});
			
			MenuButton backButton1 = new MenuButton("Back");
			backButton1.setOnMouseClicked(event -> {
				getChildren().remove(background_instruction);
				getChildren().add(menu0);
				getChildren().remove(menu1);
			});
			
			MenuButton backButton2 = new MenuButton("Back");
			backButton2.setOnMouseClicked(event -> {
				getChildren().add(menu0);
				getChildren().remove(menu2);
			});
			
			
			MenuButton backButton3 = new MenuButton("Save and return");
			backButton3.setOnMouseClicked(event -> {
				Player.setPlayername(playernameField.getText());
				getChildren().add(menu0);
				getChildren().remove(menu3);
			});
			
			MenuButton backButton4 = new MenuButton("Return without saving");
			backButton4.setOnMouseClicked(event -> {
				getChildren().add(menu0);
				getChildren().remove(menu3);
			});
			
			MenuButton backButton5 = new MenuButton("Back");
			backButton5.setOnMouseClicked(event -> {
				getChildren().add(menu0);
				getChildren().remove(menu4);
			});
			
			setOnKeyReleased(event -> {
				if (event.getCode() == KeyCode.ESCAPE) {
					System.out.println("Escape character recognized, going back to mainscreen!");
					getChildren().setAll(menu0);
				}
			});
			
			
			//Add the buttons to the corresponding menus
			menu0.getChildren().addAll(playButton, instructionButton, highscoreButton, optionsButton, exitButton);
			menu1.getChildren().addAll(backButton1);
			menu2.getChildren().addAll(scoresArea, backButton2);
			menu3.getChildren().addAll(hbPlayername, backButton3, backButton4);
			menu4.getChildren().addAll(mpButton, spButton, backButton5);
			
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
