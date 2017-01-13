package menu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import model.Player;

public class HighScores {
	
	private static final String FILEPATH = "resources/textfiles/scores.txt";
	
	private HighScores() {	}

	public static void main(String[] args) {
		System.out.println("[HighScores.java] This is the start of the HighScores test!");
		writeScore(new Player("Henk", 20));
		writeScore(new Player("Jan", 10));
		writeScore(new Player("Angelina", 25));
		writeScore(new Player("Joley", 30));
	}
	
	/**
	 * Writes the score of the given player in the scores text file.
	 * @param player The player who's score needs to be registered
	 */
	public static void writeScore(Player player) {
        BufferedWriter writer = null;
        try {
        	String data = player.getPlayername() + " " + player.getScore();
        	
            writer = new BufferedWriter(new FileWriter(FILEPATH, true));
            writer.write(data);
            writer.newLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (Exception e) {
            }
        }
    }

	/**
	 * Returns all the player names linked to their score
	 * @return
	 */
	public HashMap<String, Integer> getScores() {
		String line;
        BufferedReader in;
        HashMap<String, Integer> scores = new HashMap<String, Integer>();
        
        try {
            in = new BufferedReader(new FileReader(FILEPATH));
            while((line = in.readLine()) != null) {
            	String[] temp = line.split("\\s+");
                scores.put(temp[0], Integer.parseInt(temp[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
        
	}

}
