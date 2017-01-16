import java.util.ArrayList;
import java.util.List;

public class Hand extends StackOfCards {

  public Hand() {
    cards = new ArrayList<Card>();
  }

  public void addToHand(Card c) {
    cards.add(c);
  }

  public Card getNthCard(int n) {
    if (n > cards.size()) {
      System.out.println("Card index is greater than number of cards!");
      return null;
    }
    return cards.get(n);
  }

  public List<Card> getCards() {
    return cards;
  }

  public void printHand() {
    System.out.println(cards);
  }

  public String toString() {
    String s = "Hand: ";
    for (Card c : cards) {
      s += c.getRankByName() + " ";
    }
    return s;
  }
}