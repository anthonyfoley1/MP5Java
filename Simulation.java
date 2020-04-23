// Anthony Foley
// 2313898
// afoley@chapman.edu
// CPSC-231-01
// Assignment MP5
// Simulation class

/*
This class is used to act as a main class that uses all
of the card classes to simulate the card game of war X amount
of times, specified in command line
*/

import java.util.ArrayList;

public class Simulation{

  //variables
  private double m_avgBattles = 0;
  private double m_avgWars = 0;
  private double m_avgDoubleWars;
  private int m_minBattles = 999;
  private int m_maxBattles = 0;
  private int m_minWars = 999;
  private int m_maxWars = 0;
  private int counter = 0;

  //Create LL's to store each game's stats
  private ArrayList <Double> avgBattlesLL = new ArrayList<Double>();
  private ArrayList <Double> avgWarsLL = new ArrayList<Double>();
  private ArrayList <Double> avgDoubleWarsLL = new ArrayList<Double>();

  //Default Constructor to automatically play 5 games
  public Simulation(){
    simulate(5);
  }

  //Overloaded Constructor to play x games
  public Simulation(int x){
    simulate(x);
  }

  //calculate()
  //computes aggregate statistics from all games
  public void calculate(Game t, int x){
    //Variables to compute averages, minimums, and maximums
    double battles;
    double Wars;
    double DoubleWars;

    ++counter;
    battles = t.getNumBattles();
    Wars = t.getNumWars();
    DoubleWars = t.getNumDoubleWars();

    avgBattlesLL.add(battles);
    avgWarsLL.add(Wars);
    avgDoubleWarsLL.add(DoubleWars);

    //Battles
    for (int i=0;i<avgBattlesLL.size();++i) {
      //Finds min # battles
      if (avgBattlesLL.get(i)<m_minBattles) {
        m_minBattles = avgBattlesLL.get(i).intValue();
      }
      //Finds max # battles
      if (avgBattlesLL.get(i)>m_maxBattles) {
        m_maxBattles = avgBattlesLL.get(i).intValue();
      }
    }
    //Wars
    for (int z=0;z<avgWarsLL.size();++z) {
      //Finds min # wars
      if (avgWarsLL.get(z)<m_minWars) {
        m_minWars = avgWarsLL.get(z).intValue();
      }
      //Finds max # Wars
      if (avgWarsLL.get(z)>m_maxWars) {
        m_maxWars = avgWarsLL.get(z).intValue();
      }
    }

    //If we have simulated all games, now we can divide and find average
    if (counter == x) {
      //Add up all Battle counts
      for (int i = 0;i<avgBattlesLL.size();++i) {
        m_avgBattles += avgBattlesLL.get(i);
      }
      //Add up all War counts
      for (int q = 0;q<avgWarsLL.size();++q) {
        m_avgWars += avgWarsLL.get(q);
      }
      //Add up all double war counts
      for (int c = 0;c<avgDoubleWarsLL.size();++c) {
        m_avgDoubleWars += avgDoubleWarsLL.get(c);
      }

      //Divide by the number of games/rounds that were played
      m_avgBattles = m_avgBattles / x;
      m_avgWars = m_avgWars / x;
      m_avgDoubleWars = m_avgDoubleWars / x;
    }
  }

  //report()
  //prints statistics from calculate() in nice format
  public void report(int x){
    System.out.println("\n                -------------- \n");
    System.out.println("The average number of Battles across "+x+" game(s) was: "+m_avgBattles);
    System.out.println("The average number of Wars across "+x+" game(s) was: "+m_avgWars);
    System.out.println("The average number of Double Wars across "+x+" game(s) was: "+m_avgDoubleWars);
    System.out.println("The maximum number of Battles across "+x+" game(s) was: "+m_maxBattles);
    System.out.println("The minimum number of Battles across "+x+" game(s) was: "+m_minBattles);
    System.out.println("The maximum number of Wars across "+x+" game(s) was: "+m_maxWars);
    System.out.println("The minimum number of Wars across "+x+" game(s) was: "+m_minWars);
    System.out.println("\n                -------------- \n");
  }

  //simulate()
  //plays specified number of games
  public void simulate(int y){
    for (int i=0;i<y;++i) {
      //Create new game & play it
      Game game = new Game();
      game.play();

      //This branch just writes winner to WarLogger
      if (game.getGameWinner() == 1) {
        WarLogger.getInstance().logGameOutcome((i+1),WarLogger.P1);
      }
      else {
        WarLogger.getInstance().logGameOutcome((i+1),WarLogger.P2);
      }
      //Computes stats from game
      calculate(game,y);
    }
  }


  //Main method
  public static void main(String[] args) {
    try {
      int numGames = Integer.parseInt(args[0]);

      Simulation warTime = new Simulation(numGames);

      System.out.println("\n --- Here are the stats for all of the Games ---");
      warTime.report(numGames);
    }
    catch(Exception e) {
      System.err.println("Please enter Command Line Arguement of type int");
    }
    WarLogger.getInstance().release();
  }
}
