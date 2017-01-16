import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Deck extends StackOfCards {

  protected List<Card> originalDeck;
  /**
    * Standard 52 card deck, no jokers
    */
  public Deck() {
    cards = new ArrayList<Card>();
    gen = new Random(System.currentTimeMillis());
    fillDeck();
    originalDeck = new ArrayList<Card>();
    originalDeck.addAll(cards);
    shuffle();
  }

  /**
   * For games that use multiple decks
   */
  public Deck(int numDecks) {

    cards = new ArrayList<Card>();

    gen = new Random(System.currentTimeMillis());

    for (int i = 0; i < numDecks; ++i) {
      fillDeck();
    }

    originalDeck = new ArrayList<Card>();
    originalDeck.addAll(cards);
    
    shuffle();
  }

  private void fillDeck() {
    Card c;
    for (int i = 1; i < 14; ++i) {
      for (Suit s : Suit.values()) {
        c = new Card(i, s);
        cards.add(c);
      }
    }
  }

  public Card draw() {
    return cards.remove(0);
  }

  public Card drawWithFillWhenEmpty() {
    if (cards.isEmpty()) {
      shuffleIn(originalDeck);
    }
    return draw();
  }

  public static void main(String[] args) {
        Deck d = new Deck();

        System.out.println("Shuffled");
        d.shuffle();
        for (int i = 0; i < 52; ++i) {
          System.out.println(d.cards.get(i));
        }

        d.sort();
        System.out.println("Sorted");

        for (int i = 0; i < 52; ++i) {
          System.out.println(d.cards.get(i));
        }

        System.out.println(d.cards.get(0).lessThan(d.cards.get(1)));
  }
}