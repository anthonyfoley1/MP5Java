// Anthony Foley
// 2313898
// afoley@chapman.edu
// CPSC-231-01
// Assignment MP5
// Player class

/*
This class is used to create a player and deal him half of the deck,
depending if he is player 1 or player 2
Also used to simulate players removing (flipping) and adding
cards to their own deck (collecting)
*/

import java.util.LinkedList;

public class Player{

  //Private member variables
  private LinkedList <Card> m_playerDeck;
  private int m_name;

  //These cards are used to represent the 3 cards that are drawn for each battle
  private Card card1;
  private Card card2;
  private Card card3;
  private boolean m_winByDefault;

  //Overloaded constructor
  public Player(int name, Deck deck1){

    //Player will either be 1 or 2
    this.m_name = name;
    this.m_winByDefault = false;
    this.m_playerDeck = new LinkedList<Card>();
    Card addMe;

    //If he is player 1, deal him half of the deck
    if (name == 1) {
      for(int i = 0;i<52; i += 2){ //add every other card (i+=2) to simulate actual dealer
        addMe = deck1.Deal();
        this.m_playerDeck.addFirst(addMe);
      }
    }
    //If he is player 2, deal him other half of the deck
    else if (name == 2) {
      for (int q = 0;q<26;++q) {
        addMe = deck1.Deal();
        this.m_playerDeck.addFirst(addMe);
      }
    }
  }

  //Getters
  public String getStats(){
    return ("\nPlayer is player #" +this.m_name+" and they have " +this.getSize()+ " cards");
  }
  public int getName(){
    return this.m_name;
  }
  public int getSize(){
    return this.m_playerDeck.size();
  }
  public Card getCard(int z){
    return this.m_playerDeck.get(z);
  }
  public Card getCard1(){
    return this.card1;
  }
  public Card getCard2(){
    return this.card2;
  }
  public Card getCard3(){
    return this.card3;
  }
  public boolean getWinByDefault(){
    return this.m_winByDefault;
  }

  //Setters
  public void setWinByDefault(boolean x){
    this.m_winByDefault = x;
  }
  public void addCard(Card x){
    this.m_playerDeck.add(x);
  }

  //collect()
  //Method to collect pile of 6 cards if player has won battle
  //We place an if statement to ensure that no place holders or unwanted cards get added
  public void collect(Card q, Card w, Card e, Card r, Card t, Card y){
    if (q.getCardNumber()!=999) {
      this.m_playerDeck.add(q);
    }
    if (y.getCardNumber()!=999) {
      this.m_playerDeck.add(y);
    }
    if (t.getCardNumber()!=999) {
      this.m_playerDeck.add(t);
    }
    if (w.getCardNumber()!=999) {
      this.m_playerDeck.add(w);
    }
    if (e.getCardNumber()!=999) {
      this.m_playerDeck.add(e);
    }
    if (r.getCardNumber()!=999) {
      this.m_playerDeck.add(r);
    }
  }

  //collect()
  //Method to collect 2 cards if player was won war
  public void collect(Card p, Card o){
    if (p.getCardNumber()!=999) {
      this.m_playerDeck.add(p);
    }
    if (o.getCardNumber()!=999) {
      this.m_playerDeck.add(o);
    }
  }

  //hasWon()
  //Returns boolean depending if player has entire deck
  public boolean hasWon(){
    return this.getSize() >= 52;
  }

  //warFlip()
  //Returns top card of deck to use in war
  public Card warFlip(){
    return this.m_playerDeck.remove(0);
  }

  /*
  cleanDeck()
  Not even needed, just an extra step of precaution
  deletes the (999,deleteMe) cards
  */
  public void cleanDeck(){
    for (int a = 0;a<m_playerDeck.size();++a) {
      if (m_playerDeck.get(a).getCardNumber() == 999) {
        m_playerDeck.remove(a);
        continue;
      }
    }
  }


  /*
  flip()
  returns median value out of 3 cards
  Method to flip out 3 cards for player, then return the median
  value; or max value if player has less than 3 cards
  */
  public Card flip(){

    //variables
    int value1;
    int value2;
    int value3;

    //If player has more than 3 cards, we can proceed as normal
    if (m_playerDeck.size() >= 3) {
      this.cleanDeck();

      card1 = this.m_playerDeck.remove(0);
      card2 = this.m_playerDeck.remove(0);
      card3 = this.m_playerDeck.remove(0);

      value1 = card1.getCardNumber();
      value2 = card2.getCardNumber();
      value3 = card3.getCardNumber();

      //System.out.println("\nFirst card:  "+value1);
      //System.out.println("\nSecond card:  "+value2);
      //System.out.println("\nThird card:  "+value3);

      //Branch to determine the middle (median) card value
      if (value1 >= value2 && value1 <= value3) {
        return card1;
      }
      else if (value1 <= value2 && value1 >= value3){
        return card1;
      }
      else if (value2 <= value1 && value2 >= value3) {
        return card2;
      }
      else if (value2 >= value1 && value2 <= value3){
        return card2;
      }
      else{
        return card3;
      }
    }

    //Finds max value when player has 2 cards left
    else if (m_playerDeck.size()==2) {
      card1 = this.m_playerDeck.remove(0);
      card2 = this.m_playerDeck.remove(0);

      /*Assign card 3 with specific values so that we know not to add
      Do this because card3 from previous loop will get added
      otherwise, then duplicate cards will arise etc.*/
      card3 = new Card(999,"deleteMe1");

      value1 = card1.getCardNumber();
      value2 = card2.getCardNumber();

      //Branch to determine the max value out of the 2
      if (value1 > value2) {
        return card1;
      }
      else{
        return card2;
      }
    }

    //If player has 1 card left, you just play the last card
    else{
      card1 = this.m_playerDeck.remove(0);
      //Again, assigns additional cards to act as place holders which aren't added
      card2 = new Card(999,"deleteMe2");
      card3 = new Card(999,"deleteMe3");
      //Return only card available
      return card1;
    }
  }


  //toString()
  //returns String of Player's deck
  public String toString(){
    String answer = "";
    answer += "\n ---- Player "+this.getName()+" and this is their deck:  ----";
    for (int v=0;v<this.m_playerDeck.size();++v) {
      answer += System.lineSeparator() + this.m_playerDeck.get(v).toString();
    }
    return answer;
  }

  //equals()
  //Returns boolean if two players have same deck sizes
  public boolean equals(Player x){
    return (this.m_playerDeck.size() == x.getSize());
  }
}
