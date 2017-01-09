package model;

public class Game {

  private int difficulty;
  private Screen screen;
  
  public Game(int difficulty) {
    this.difficulty = difficulty;
    screen = Screen.generateScreen(difficulty);
  }
  
  public Screen getScreen() {
    return screen;
  }

  public void update() {
    // TODO Auto-generated method stub
    
  }

}
