import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * Simple implementation of blackjack
 * using Poker library
 */
public class Blackjack {

  public Deck d;
  public Hand dealerHand;
  public List<Player> players;
  /**
   * This implementation of blackjack
   * relies on making use of one constant
   * scanner, which may be passed in via
   * one of the constructors
   */
  private Scanner sc;

  public Blackjack() {
    d = new Deck();
    players = new ArrayList<>();
    dealerHand = new Hand();
    players.add(new Player(20));
    sc = new Scanner(System.in);
  }

  public Blackjack(int numPlayers, int startingAmount, int numDecks) {
    d = new Deck(numDecks);
    players = new ArrayList<>(numPlayers);
    dealerHand = new Hand();
    Utils.repeat.accept(numPlayers,
      () -> players.add(new Player(startingAmount))
    );
    sc = new Scanner(System.in);
  }

  public Blackjack(int numPlayers,
                        int startingAmount,
                        int numDecks,
                        Scanner sc) {
    d = new Deck(numDecks);
    players = new ArrayList<>(numPlayers);
    dealerHand = new Hand();
    Utils.repeat.accept(numPlayers,
      () -> players.add(new Player(startingAmount))
    );
    this.sc = sc;
  }

  public void run() {
    do {
      setupRound();
      if (!arePlayersPlaying()) {
        return;
      }
      players.forEach((p) -> playRound(p));
      if (checkDealerBlackjack()) {
        players.forEach((p) -> compareNaturalBlackjack(p));
      } else {
        boolean canPlay = dealerPlayRound();
        if (canPlay) {
          players.forEach((p) -> resolveHands(p));
        }
      }
    } while(arePlayersPlaying());
  }

  private void setupRound() {
    Utils.repeat.accept(2, 
        () -> dealerHand.addToHand(d.drawWithFillWhenEmpty())
        );
    players.forEach((p) -> takeBets(p));
    players.forEach((p) -> dealCards(p));
  }

  private void takeBets(Player p) {
    if (p.isPlaying()) {
      System.out.print("Player " + p.getPlayerID());
      System.out.println(", how much money do you wish to wager?");
      System.out.println("Wager 0 to stop playing.");
      p.setCurrentAnte(Utils.parseNonNegativeIntegerValue(sc));
      if (p.getCurrentAnte() == 0) {
        p.stopPlaying();
      }
    }
  }

  private void dealCards(Player p) {
    if (!p.isPlaying()) {
      return;
    }
    Utils.repeat.accept(2,
        () -> p.addToHand(d.drawWithFillWhenEmpty())
    );
  }

  private boolean checkDealerBlackjack() {
    String rankName = dealerHand.getNthCard(0).getRankByName();
    if (rankName.equals("Ace")) {
      return evaluateHand(dealerHand) == 21;
    }
    return false;
  }

  //TODO: Implement insurance
  private void askInsurance(Player p) {
    if (p.isPlaying() && p.getCurrentAnte() > 0) {
      System.out.println("Do you wish to pay for blackjack insurance?");
    }
  }

  private int evaluateHand(Hand h) {
    int total = 0,
        numAces = 0;
    for (Card card : h.getCards()) {
      switch (card.getRankByName()) {
        case "Jack":
        case "Queen":
        case "King":
          total += 10;
          break;
        case "Ace":
          total += 11;
          numAces++;
          break;
        case "Error":
          System.out.println("Encountered erroneous card: " + card);
          break;
        default:
          total += card.getRank();
          break;
      }
    }

    while (total > 21 && numAces > 0) {
      total -= 10;
      numAces--;
    }

    return total;
  }

  private void compareNaturalBlackjack(Player p) {
    if (!p.isPlaying()) {
      return;
    }
    if (evaluateHand(p.getHand()) < 21 ||
        p.getHand().getCards().size() != 2) {
      System.out.print("Player " + p.getPlayerID());
      System.out.println(" has lost against a dealer blackjack.");
      playerLoss(p);
    } else {
      System.out.println("Player " + p.getPlayerID());
      System.out.println("matched dealer's blackjack.");
      resetPlayer(p);
    }
  }

  private void playerWin(Player p) {
    if (!p.isPlaying()) {
      return;
    }
    p.winCurrentAnte();
    resetPlayer(p);
  }

  private void playerLoss(Player p) {
    if (!p.isPlaying()) {
      return;
    }
    p.loseCurrentAnte();
    resetPlayer(p);
  }

  private void resetPlayer(Player p) {
    p.clearHand();
    p.resetCurrentAnte();
  }

  private void printChoices() {
    System.out.println("Please enter a number corresponding");
    System.out.println(" to one of the following actions:");
    System.out.println("1 - Hit");
    System.out.println("2 - Stand");
    System.out.println("3 - List all cards in play");
  }

  private void playRound(Player p) {
    System.out.println("Dealer's visible card: " + dealerHand.getNthCard(0));
    System.out.print("Player " + p.getPlayerID());
    System.out.println("'s hand: " + p.getHand());
    System.out.print("Value of player " + p.getPlayerID());
    System.out.println("'s hand is: " + evaluateHand(p.getHand()));

    int choice = 0;
    boolean continueParsing;

    do {
      printChoices();
      choice = Utils.parseNonNegativeIntegerValue(sc);
      continueParsing = true;

      try {
        switch (choice) {
          case 1:
            Card c = d.drawWithFillWhenEmpty();
            p.addToHand(c);
            System.out.println("Last card drawn is " + c);
            continueParsing = evaluateHand(p.getHand()) <= 21;
            if (continueParsing) {
              System.out.print("The current value of your hand is ");
              System.out.println(evaluateHand(p.getHand()));
              System.out.println(p.getHand());
            } else {
              System.out.print("You've busted with a value of ");
              System.out.println(evaluateHand(p.getHand()));
              System.out.println(p.getHand());
              playerLoss(p);
            }
            break;
          case 2:
            continueParsing = false;
            break;
          case 3:
            System.out.println("Dealer's hand: " + dealerHand);
            System.out.println("Value: " + evaluateHand(dealerHand));
            System.out.println("Press enter to continue");
            System.in.read();
            for (Player pl : players) {
              System.out.print("Player " + pl.getPlayerID() + "'s hand: ");
              System.out.println(pl.getHand());
              System.out.println("Value: " + evaluateHand(pl.getHand()));
              System.out.println("Press enter to continue");
              System.in.read();
            }
            break;
          case 4:
            printChoices();
            break;
          default:
            System.out.println("Please enter a number from 1 to 4");
            break;
        }
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    } while (continueParsing);
  }

  private boolean dealerPlayRound() {
    System.out.println("Dealer's turn:");
    System.out.println("Dealer's hand is: " + dealerHand);
    while (evaluateHand(dealerHand) < 17) {
      Card c = d.drawWithFillWhenEmpty();
      dealerHand.addToHand(c);
      System.out.println("Dealer has drawn a " + c);
      System.out.println("Dealer's hand is: " + dealerHand);
      System.out.println("Press enter to continue");
      try {
        System.in.read();
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }

    if (evaluateHand(dealerHand) > 21) {
      System.out.println("Dealer has busted.");
      for (Player p : players) {
        if (p.getCurrentAnte() > 0) {
          playerWin(p);
        }
      }
      return false;
    } else {
      System.out.println("Dealer's hand is: " + dealerHand);
      System.out.println("Dealer's hand is worth: " + evaluateHand(dealerHand));
    }
    return true;
  }

  private void resolveHands(Player p) {
    if (p.getCurrentAnte() <= 0) {
      return;
    }
    int dealerVal = evaluateHand(dealerHand);
    int playerVal = evaluateHand(p.getHand());
    if (dealerVal == playerVal) {
      System.out.println("It's a push for player " + p.getPlayerID());
      System.out.println("; no money will change hands.");
      resetPlayer(p);
    } else {
      if (dealerVal > playerVal) {
        System.out.print("Dealer's hand, of value " + dealerVal);
        System.out.print(" beats out player " + p.getPlayerID());
        System.out.println("'s hand, of value " + playerVal);
        playerLoss(p);
      } else {
        System.out.print("Player " + p.getPlayerID());
        System.out.print("'s hand, of value " + playerVal);
        System.out.println(", beats out dealer's hand, of value " + dealerVal);
        playerWin(p);
      }
    }
  }

  private boolean arePlayersPlaying() {
    return players.stream()
                  .map(Player::isPlaying)
                  .reduce(true, (a, b) -> a && b);
  }

  public static void main(String[] args) {
    try (Scanner sc = new Scanner(System.in)) {
      int numPlayers;
      int startingAmount;
      int numDecks;
      String read;

      System.out.println("How many players will be playing?");
      numPlayers = Utils.parseNonNegativeIntegerValue(sc);

      System.out.println("How much money will each player start out with?");
      startingAmount = Utils.parseNonNegativeIntegerValue(sc);

      System.out.println("How many decks of cards do you wish to play with?");
      numDecks = Utils.parseNonNegativeIntegerValue(sc);

      if (numPlayers == 0 ||
          startingAmount == 0 ||
          numDecks == 0) {
        System.out.println("Cannot continue with no players, money, or decks. Exiting.");
        return;
      }

      Blackjack game = new Blackjack(numPlayers, startingAmount, numDecks, sc);

      game.run();

      System.out.println("The game has ended, thanks for playing!");

    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}