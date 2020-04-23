// Anthony Foley
// 2313898
// afoley@chapman.edu
// CPSC-231-01
// Assignment MP5
// Game class

/*
This class is used to simulate 2 players in a game of war, where the class
will have players deal out the cards, and if a war occurs, they deal out
extra card
WarLogger is just used solely for grading purposes
*/

public class Game{

  //Private member variables

  //variables used for statistics
  private int m_numBattles;
  private int m_numWars;
  private int m_numDoubleWars;

  //variables used for actual class functionality
  private int gameWinner;
  private Player player1;
  private Player player2;
  private Deck deck1 = new Deck();

  //Default Constructor
  public Game(){
    this.player1 = new Player(1,deck1);
    this.player2 = new Player(2,deck1);

    this.m_numBattles = 0;
    this.m_numWars = 0;
    this.m_numDoubleWars = 0;
  }

  //Getters
  public int getNumBattles(){
    return this.m_numBattles;
  }
  public int getNumWars(){
    return this.m_numWars;
  }
  public int getNumDoubleWars(){
    return this.m_numDoubleWars;
  }
  public int getGameWinner(){
    return this.gameWinner;
  }

  //setGameWinner()
  public void setGameWinner(int x){
    this.gameWinner = x;
  }

  //play()
  //Method to simulate game from beginning to end
  public String play(){

    //Cleans deck of unwanted/duplicate cards
    player1.cleanDeck();
    player2.cleanDeck();
    //Declare 2 variables representing the median values
    Card median1;
    Card median2;

    //System.out.println(player1.toString());
    //System.out.println(player2.toString());

    while (true) {
      //If player 1 or player 2 has won, break to bottom of loop to assign Winner
      if (player1.hasWon() || player2.hasWon() || player1.getSize()<1 || player2.getSize()<1) {
        break;
      }
      player1.cleanDeck();
      player2.cleanDeck();
      //System.out.println(player1.getStats());
      //System.out.println(player2.getStats());

      Card[] cardArray1 = new Card[player1.getSize()];
      Card[] cardArray2 = new Card[player2.getSize()];

      for (int t=0;t<cardArray1.length;++t) {
        cardArray1[t] = player1.getCard(t);
      }
      for (int y=0;y<cardArray2.length;++y) {
        cardArray2[y] = player2.getCard(y);
      }

      //Will use flip method in Player class to assign median value
      median1 = player1.flip();
      median2 = player2.flip();
      ++m_numBattles;

      WarLogger.getInstance().logBattle(m_numBattles,WarLogger.P1,cardArray1);
      WarLogger.getInstance().logBattle(m_numBattles,WarLogger.P2,cardArray2);

      //System.out.println("\nPlayer 1's deck: "+median1.toString());
      //System.out.println("\nPlayer 2's deck: "+median2.toString());

      //If player 1 wins, collect all 6 cards to his deck
      if (median1.getCardNumber() > median2.getCardNumber()) {
        player1.collect(player1.getCard1(),player1.getCard2(),player1.getCard3(),
                        player2.getCard1(),player2.getCard2(),player2.getCard3());

        WarLogger.getInstance().logBattleOutcome(m_numBattles,WarLogger.P1);
      }
      //If player 2 wins, collect all 6
      else if (median1.getCardNumber() < median2.getCardNumber()) {
        player2.collect(player1.getCard1(),player1.getCard2(),player1.getCard3(),
                        player2.getCard1(),player2.getCard2(),player2.getCard3());

        WarLogger.getInstance().logBattleOutcome(m_numBattles,WarLogger.P2);
      }
      //If war happens (or tie)
      //If both players have enough cards to do War, then proceed
      else{
        if (player1.getSize()>=1 && player2.getSize()>=1) {
          //System.out.println("\n --- war has been declared --- \n");
          WarLogger.getInstance().logBattleOutcome(m_numBattles,WarLogger.WAR);
          //Use war method
          war();
        }

        //This branch declares player winner by default b/c opponent has run out of cards
        else{
          if (player1.getSize()>player2.getSize()) {
            //System.out.println("War has been declared but player 2 has no other cards left, therefore he defaults..");
            //Player 1 has now won, so break
            player1.setWinByDefault(true);
            break;
          }
          else{
            //System.out.println("War has been declared, but player 1 has no other cards left, therefore he defaults..");
            //Player 2 has won, so break
            player2.setWinByDefault(true);
            break;
          }
        }
      }
    }

    //Here we decide who winner is after play() has finished
    //If player 1 wins
    if ( player1.getSize()>player2.getSize() || player1.getWinByDefault()==true) {
      //System.out.println("\nPlayer 1 has won the game!!!");
      setGameWinner(1);
      return "Game over";
    }
    //If player 2 wins
    else{
      //System.out.println("\nPlayer 2 has won the game!!!");
      setGameWinner(2);
      return "Game over";
    }
  }

  //toString
  //returns current standings of game based on whichever player has more cards
  public String toString(){
    return (player1.getStats()+player2.getStats());
  }

  /*
  war()
  Returns the winner of war(s) battle(s)
  Method to deal out one extra card per player to engage in war
  Only to be used inside of play() method
  */
  public Player war(){
    ++m_numWars;
    //Decare 4 cards representing single war cards
    Card warCard1;
    Card warCard2;
    Card warCard3;
    Card warCard4;

    //Assign top card as the war card
    warCard1 = player1.warFlip();
    warCard2 = player2.warFlip();
    //System.out.println("Player 1's war card:  "+warCard1.toString());
    //System.out.println("Player 2's war card:  "+warCard2.toString());

    //if player 1 has higher value war card, they win and collect original 6 + 2
    if (warCard1.getCardNumber() > warCard2.getCardNumber()) {
      player1.collect(warCard1, warCard2);
      player1.collect(player1.getCard1(),player1.getCard2(),player1.getCard3(),
                      player2.getCard1(),player2.getCard2(),player2.getCard3());
      //System.out.println("Player 1 has won the war!");

      WarLogger.getInstance().logWarOutcome(m_numWars,WarLogger.P1);

      return player1;
    }

    //otherwise player 2 collects all 8 cards
    else if (warCard1.getCardNumber() < warCard2.getCardNumber()){
      player2.collect(warCard1, warCard2);
      player2.collect(player1.getCard1(),player1.getCard2(),player1.getCard3(),
                      player2.getCard1(),player2.getCard2(),player2.getCard3());
      //System.out.println("Player 2 has won the war!");

      WarLogger.getInstance().logWarOutcome(m_numWars,WarLogger.P2);

      return player2;
    }

    //if 2 war cards are equal, we do a second war
    else {
      if (player1.getSize()>0 && player2.getSize()>0) {
        ++m_numDoubleWars;
        //System.out.println("\n --- A SECOND WAR HAS BEEN DECLARED!!! --- \n");

        WarLogger.getInstance().logWarOutcome(m_numWars,WarLogger.WAR);

        //Flip a 2nd war card
        warCard3 = player1.warFlip();
        warCard4 = player2.warFlip();

        //System.out.println("First player drew a:  "+warCard3.toString());
        //System.out.println("Second player drew a:  "+warCard4.toString());

        //Player 1 collects all 4 cards as well os original 6
        if (warCard3.getCardNumber() > warCard4.getCardNumber()) {
          player1.collect(warCard1,warCard2);
          player1.collect(warCard3,warCard4);
          player1.collect(player1.getCard1(),player1.getCard2(),player1.getCard3(),
                          player2.getCard1(),player2.getCard2(),player2.getCard3());

          WarLogger.getInstance().logWarOutcome(m_numWars,WarLogger.P1);
          //System.out.println("\nPlayer 1 has won the second war!!!\n");
          return player1;
        }
        //Player 2 wins & collects 4 + 6
        else{
          player2.collect(warCard1,warCard2);
          player2.collect(warCard3,warCard4);
          player2.collect(player1.getCard1(),player1.getCard2(),player1.getCard3(),
                          player2.getCard1(),player2.getCard2(),player2.getCard3());

          WarLogger.getInstance().logWarOutcome(m_numWars,WarLogger.P2);
          //System.out.println("\nPlayer 2 has won the second war!!!\n");
          return player2;
        }
      }
      //Go here if player doesn't have enough cards for second war.
      else {
        //default win for player 1..
        if ( player1.getSize()>player2.getSize() ){
          player1.setWinByDefault(true);
          return player1;
        }
        else {
          player2.setWinByDefault(true);
          return player2;
        }
      }
    }
  }
}
