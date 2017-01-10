package mainMenu;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenuBar;

public class MenuPanel extends GuiPanel {
	
	private static final long serialVersionUID = 1L;
	private Font titleFont = new Font(Font.MONOSPACED, Font.PLAIN, 30);
	private Font buttonFont = new Font(Font.DIALOG, Font.PLAIN, 30);
	public String title = "SmARt";
	private int width = 1280;
	private int height = 800;
	private int buttonWidth = 220;
	private int buttonHeight = 60;
	private int spacing = 20;

	public MenuPanel() {
		GuiButton playButton = new GuiButton(width / 2 - buttonWidth / 2, 220, buttonWidth, buttonHeight);
		GuiButton instructionsButton = new GuiButton(width / 2 - buttonWidth / 2, playButton.getY() + spacing, buttonWidth, buttonHeight);
		GuiButton exitButton = new GuiButton(width / 2 - buttonWidth / 2, instructionsButton.getY() + spacing, buttonWidth, buttonHeight);
		
		playButton.setText("Play Game");
		instructionsButton.setText("Instructions");
		exitButton.setText("Exit");
		
		playButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				//GuiScreen
			}
		});
		
		exitButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(0);
			}
		});
		
	}

	
	
	
	public void main(String[] args) {
		//TESTING PURPOSES ONLY
	}
	
	
}
