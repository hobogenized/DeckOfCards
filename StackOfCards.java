import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Template for classes that revolve around
 * stacks of cards. Has common methods
 * to stacks/collections of cards
 */

public abstract class StackOfCards {
    protected List<Card> cards;

    protected Random gen;

    // Shuffles cards in deck
    public void shuffle() {
      List<Card> newCards = new ArrayList<Card>();
      int index;

      while (!cards.isEmpty()) {
         index = gen.nextInt(cards.size());
        newCards.add(cards.remove(index));
      }
      cards = newCards;
    }

    //Sort the cards according to getCombinedValue(), least to greatest
    public void sort() {
      Collections.sort(cards);
    }

    public void addIn(Card c) {
      cards.add(c);
    }

    public void addIn(List<Card> cardList) {
      cards.addAll(cardList);
    }

    public void shuffleIn(Card c) {
      addIn(c);
      shuffle();
    }

    public void shuffleIn(List<Card> cardList) {
      addIn(cardList);
      shuffle();
    }

    public int getNumberOfCards() {
      return cards.size();
    }

    public void emptyCards() {
      cards.clear();
    }

    public boolean isEmpty() {
      return cards.isEmpty();
    }
}