/**
 * Simple card class, implements rank and suit
 */
public class Card implements Comparable<Card> {

  private int rank;
  private Suit suit;

  public Card() {
  	rank = 1;
  	suit = Suit.DIAMONDS;
  }

  public Card(int rank, Suit suit) {

  	if (rank < 1 || rank > 13) {
  		this.rank = 1;
  	} else {
  		this.rank = rank;
  	}

    if (suit == null) {
      suit = Suit.DIAMONDS;
    } else {
      this.suit = suit;
    }
  }

  public Card clone() {
    return new Card(this.rank, this.suit);
  }

  @Override
  public String toString() {
    return getRankByName() + " of " + getSuit() + ".";
  }

  public String getRankByName() {
    switch (rank) {
      case 1:
        return "Ace";
      case 2:
        return "Two";
      case 3:
        return "Three";
      case 4:
        return "Four";
      case 5:
        return "Five";
      case 6:
        return "Six";
      case 7:
        return "Seven";
      case 8:
        return "Eight";
      case 9:
        return "Nine";
      case 10:
        return "Ten";
      case 11:
        return "Jack";
      case 12:
        return "Queen";
      case 13:
        return "King";
      default:
        return "ERROR";
    }
  }

  public String getSuit() {
    return suit.toString();
  }

  public void setSuit(String suit) {
    switch (suit) {
      case "Diamonds":
      this.suit = Suit.DIAMONDS;
      break;
      case "Clubs":
      this.suit = Suit.CLUBS;
      break;
      case "Hearts":
      this.suit = Suit.HEARTS;
      break;
      case "Spades":
      this.suit = Suit.SPADES;
      break;
    }
  }

  public int getRank() {
    return rank;
  }

  // Numerical value, useful for sorting if necessary
  public int getCombinedValue() {
    return rank + (suit.getNumericValue() * 13) - 1;
  }

  public int compareTo(Card other) {
    if (this.getCombinedValue() == other.getCombinedValue()) {
      return 0;
    }
    if (this.getCombinedValue() < other.getCombinedValue()) {
      return -1;
    }
    return 1;
  }

  public boolean lessThan(Card other) {
    return compareTo(other) < 0;
  }

  public boolean greaterThan(Card other) {
    return compareTo(other) > 0;
  }

  public boolean equalTo(Card other) {
    return compareTo(other) == 0;
  }

}