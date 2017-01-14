package menu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javafx.scene.control.TextArea;
import model.Player;

public class HighScores {
	
	private static final String FILEPATH = "resources/textfiles/scores.txt";
	
	private HighScores() {	}

	/**
	 * Adds test values to the scores list .
	 */
	public static void main(String[] args) {
		System.out.println("[HighScores.java] This is the start of the HighScores test!");
		writeScore(new Player("Henk", 20));
		writeScore(new Player("Jan", 10));
		writeScore(new Player("Angelina", 25));
		writeScore(new Player("Mister", 20));
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
	 * Returns all the player names linked to their score.
	 * Supports multiple people on the score-list having the same name.
	 * MAP: SCORE -> LIST(NAMES)
	 * @return HashMap with all the player names and their respective scores.
	 */
	private static HashMap<Integer, ArrayList<String>> getScores() {
		String line;
        BufferedReader in;
        HashMap<Integer, ArrayList<String>> scores = new HashMap<Integer, ArrayList<String>>();
        
        try {
            in = new BufferedReader(new FileReader(FILEPATH));
            while((line = in.readLine()) != null) {
            	String[] temp = line.split("\\s+");
            	String name = temp[0];
            	int score = Integer.parseInt(temp[1]);
            	
            	if (!scores.containsKey(score)) {
            		ArrayList<String> list = new ArrayList<String>();
            		list.add(name);
            		scores.put(score, list);
            	} else {
            		scores.get(score).add(name);
            	}
            }
        } catch (IOException e) {
        	e.printStackTrace();
        }
        return scores;
	}
	
	/**
	 * Inserts the highscores in ordered fashion into the given TexArea object.
	 * In the case of multiple players having the same score value, the most recent update gets inserted first (at the top).
	 * @param area TextArea type where the scores need to be inserted in
	 */
	public static void insertScoresSorted(TextArea area) {
		HashMap<Integer, ArrayList<String>> scores = getScores();
		ArrayList<Integer> list = new ArrayList<Integer>(scores.keySet());
		Collections.sort(list, Collections.reverseOrder());
		for (Integer score : list) {
			for (int i = scores.get(score).size() - 1; i >= 0; i--) {
				area.appendText(scores.get(score).get(i) + ": " + score + "\n");
			}
		}
	}

}
