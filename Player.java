/**
 * Standard player class; may consider extending
 * in the future so that we can have
 * more specialized players for certain
 * card games
 */
public class Player {
  private Hand hand;
  private int money;
  private boolean isPlaying;
  private int currentAnte;
  private int playerID;

  public Player() {
    hand = new Hand();
    isPlaying = true;
  }

  public Player(int startingMoney) {
    hand = new Hand();
    money = startingMoney;
    isPlaying = true;
    playerID = Utils.getFreeID();
  }

  public Player(int startingMoney, int id) {
    hand = new Hand();
    money = startingMoney;
    isPlaying = true;
    playerID = id;
  }

  public void subtractAnte(int ante) {
    money -= ante;
    if (money <= 0) {
      stopPlaying();
    }
  }

  public void loseCurrentAnte() {
    subtractAnte(currentAnte);
    currentAnte = 0;
  }

  public void addWinnings(int winnings) {
    money += winnings;
  }

  public void winCurrentAnte() {
    addWinnings(currentAnte);
  }

  public int getCurrentMoney() {
    return money;
  }

  public void addToHand(Card c) {
    hand.addToHand(c);
  }

  public Hand getHand() {
    return hand;
  }

  public int getPlayerID() {
    return playerID;
  }

  public boolean isPlaying() {
    return isPlaying;
  }

  public void stopPlaying() {
    isPlaying = false;
  }

  public int getCurrentAnte() {
    return currentAnte;
  }

  public void setCurrentAnte(int amount) {
    currentAnte = amount;
  }

  public void resetCurrentAnte() {
    currentAnte = 0;
  }

  public void clearHand() {
    hand.emptyCards();
  }
}