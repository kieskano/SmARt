package mainMenu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javafx.scene.input.TouchPoint.State;

public class GuiButton {

	private Rectangle clickArea;
	private State state = State.RELEASED;
	private ArrayList<ActionListener> actionListeners;
	private String text;
	
	private Color released;
	private Color hover;
	private Color pressed;
	private Font buttonFont;
	
	private int y;
	
	public GuiButton(int x, int y, int width, int height) {
		clickArea = new Rectangle(x, y, width, height);
		released = new Color(0,255,34);
		hover = new Color(0,255,102);
		pressed = new Color(0,255,179);
		this.y = y;
	}
	
	public int getY() {
		return y;
	}
	
	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void addActionListener(ActionListener listener) {
		actionListeners.add(listener);
	}
	
	public void mousePressed(MouseEvent e) {
		if (clickArea.contains(e.getPoint())) {
			state = State.PRESSED;
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if (clickArea.contains(e.getPoint())) {
			for (ActionListener al : actionListeners) {
				al.actionPerformed(null);
			}
		}
		state = State.RELEASED;
	}
	
	public void mouseMoved(MouseEvent e) {
		if (clickArea.contains(e.getPoint())) {
			state = State.PRESSED;
		} else {
			state = state.RELEASED;
		}
	}
	
	
	
}
