public enum Suit {
  DIAMONDS,  CLUBS , HEARTS, SPADES;

  public int getNumericValue() {
    switch (this) {
      case DIAMONDS:
          return 0;
      case CLUBS:
          return 1;
      case HEARTS:
          return 2;
      case SPADES:
          return 3;
      default:
          return -1;
    }
  }

  @Override
  public String toString() {
     switch (this) {
      case DIAMONDS:
          return "Diamonds";
      case CLUBS:
          return "Clubs";
      case HEARTS:
          return "Hearts";
      case SPADES:
          return "Spades";
      default:
          return "ERROR";
    }
  }
}