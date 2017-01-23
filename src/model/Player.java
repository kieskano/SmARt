package model;

public class Player {

	private static String playername = "Anonymous";
	private static int score = 0;
		
	public Player(String _playername, int _score) {
		playername = _playername;
		score = _score;
	}
	
	public static void addPoints(int points) {
		score += points;
	}
	
	public static int getScore() {
		return score;
	}
	
	public static void setPlayername(String name) {
		playername = name;
	}
	
	public static String getPlayername() {
		return playername;
	}
	
	public static void resetScore() {
	  score = 0;
	}
	
}
