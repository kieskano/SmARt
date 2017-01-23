package model;

import java.util.ArrayList;
import java.util.Collections;

public class Objective {

	public enum Type {
		ADD, SUBTRACT, MULTIPLY, DIVIDE;

		public String toString() {
			switch (this) {
			case ADD:
				return "+";
			case DIVIDE:
				return ":";
			case MULTIPLY:
				return "X";
			case SUBTRACT:
				return "-";
			default:
				return "ERROR";

			}
		}
	}

	private Type type;
	private int answer;

	public Objective(Type type, int answer) {
		this.type = type;
		this.answer = answer;
	}

	/**
	 * Checks whether the answer is correct.
	 * For Multiplayer: should add the condition that left/right-numbers.size()>1
	 * @return true if answer is correct, else false
	 */
	public boolean isCorrect(ArrayList<Number> numbersList) {
		ArrayList<Integer> numbers = getNumberValues(numbersList);
		
		double testAnswer = 0;
		
		//Make sure that the game does accept input using less than 2 tiles
		if(numbers.size() < 2 || !hasBothTiles(numbersList)) {
			return false;
		}

		if (type.equals(Type.ADD)) {
			for (int number : numbers) {
				testAnswer += number;
			}
		} else if (type.equals(Type.SUBTRACT) && numbers.size() == 2) {
			testAnswer = numbers.get(0) - numbers.get(1);
		} else if (type.equals(Type.MULTIPLY)) {
			testAnswer = numbers.get(0);
			for (int i = 1; i < numbers.size(); i++) {
				testAnswer = testAnswer * numbers.get(i);
			}
		} else if (type.equals(Type.DIVIDE)) {
			if (numbers.size() == 2) {
				if (numbers.get(0) / numbers.get(1) > 0) {
					testAnswer = numbers.get(0) / (double) numbers.get(1);
				} else {
					testAnswer = numbers.get(1) / (double) numbers.get(0);
				}
			}
		}
		return testAnswer == answer;
	}
	
	/**
	 * Returns an ArrayList filled with the values of the given Numbers.
	 * @param numbers - Numbers who's values need to be retrieved
	 * @return ArrayList<Integer> with number values
	 */
	public ArrayList<Integer> getNumberValues(ArrayList<Number> numbers) {
		ArrayList<Integer> numberValues = new ArrayList<>();
		for (Number number : numbers) {
			numberValues.add(number.getValue());
		}
		return numberValues;
	}
	
	/**
	 * Checks whether the Numbers list contain a number with both left and right numbers.
	 * @param numbers Numbers placed on the screen
	 * @return - true if the Numbers list contains a number placed on the left and on the right side
	 */
	public boolean hasBothTiles(ArrayList<Number> numbers) {
		int leftNumbers = 0;
		int rightNumbers = 0;
		
		for (Number number : numbers) {
			if (number.getIsLeftNumber()) {
				leftNumbers++;
			} else {
				rightNumbers++;
			}
		}
		return leftNumbers > 0 && rightNumbers > 0;
	}

	public Type getType() {
		return type;
	}

	public int getAnswer() {
		return answer;
	}
}
