package menu;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javafx.scene.control.TextArea;
import model.Player;

public class HighScores {

	private static final String FILEPATH = "resources/textfiles/scores.txt";

	private static HashMap<String, Integer> scores = new HashMap<>();

	private HighScores() {	}

	/**
	 * Adds test values to the scores list .
	 */
	public static void main(String[] args) {
		System.out.println("[HighScores.java] This is the start of the HighScores test!");
		clearScores();
		writeScore("Henk", 20);
		writeScore("Jan", 10);
		writeScore("Angelina", 25);
		writeScore("Mister", 20);
		
		Player.setPlayername("Jan");
		Player.addPoints(420);
		writeScore();
	}


	public static void writeScore() {
		List<String> lines = null;
		HashMap<String, Integer> temp = new HashMap<>();

		try {
			lines = Files.readAllLines(new File(FILEPATH).toPath());

			for (String line : lines) {
				String[] words = line.split(" ");
				temp.put(words[0], Integer.parseInt(words[1]));
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (temp.containsKey(Player.getPlayername()) && temp.get(Player.getPlayername()) < Player.getScore()) {
			temp.replace(Player.getPlayername(), Player.getScore());
		} else if(!temp.containsKey(Player.getPlayername())) {
			temp.put(Player.getPlayername(), Player.getScore());
		}

		clearScores();

		for (String name : temp.keySet()) {
			int score = temp.get(name);
			writeScore(name, score);
		}

	}

	public static void clearScores() {
		PrintWriter writer;
		try {
			writer = new PrintWriter(FILEPATH);
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void writeScore(String name, int score) {
		BufferedWriter writer = null;
		try {
			String data = name + " " + score;

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
		HashMap<Integer, ArrayList<String>> scores = new HashMap<Integer, ArrayList<String>>();	//SCORE -> LIST(NAMES)

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
