package model;

import java.util.ArrayList;

public class Objective {

  public enum Type {
    ADD, SUBTRACT, MULTIPLY, DEVIDE;
  }

  private Type type;
  private int answer;

  public Objective(Type type, int answer) {
    this.type = type;
    this.answer = answer;
  }

  public boolean isCorrect(ArrayList<Integer> numbers) {
    int testAnswer = 0;

    if (type.equals(Type.ADD)) {
      for (int number : numbers) {
        testAnswer = testAnswer + number;
      }
    } else if (type.equals(Type.SUBTRACT)) {
      if (numbers.size() > 1) {
        testAnswer = numbers.get(0) - numbers.get(1);
      }
    } else if (type.equals(Type.MULTIPLY)) {
      if (numbers.size() > 0) {
        testAnswer = numbers.get(0);
        for (int i = 1; i < numbers.size(); i++) {
          testAnswer = testAnswer * numbers.get(i);
        }
      }
    } else if (type.equals(Type.DEVIDE)) {
      if (numbers.size() > 1) {
        testAnswer = numbers.get(0) / numbers.get(1);
      }
    }

    return testAnswer == answer;
  }
}
