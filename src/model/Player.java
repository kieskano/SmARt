package model;

public class Player {

	private String playername = "";
	private int score = 0;
	
	public Player() {
		
	}
	
	public Player(String playername, int score) {
		this.playername = playername;
		this.score = score;
	}
	
	public void addPoints(int points) {
		score += points;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setPlayername(String name) {
		playername = name;
	}
	
	public String getPlayername() {
		return playername;
	}
	
}
