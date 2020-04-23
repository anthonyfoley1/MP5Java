// Anthony Foley
// 2313898
// afoley@chapman.edu
// CPSC-231-01
// Assignment MP5
// Deck class

/*
This class is used to store LinkedList with Cards and assign
their value and suit, representing a deck of cards
*/

import java.util.LinkedList;
import java.util.Random;

public class Deck{

  //Private Member variables
  private LinkedList <Card> m_deck;

  //constructor
  public Deck(){
    this.m_deck = new LinkedList<Card>();

    //Populates deck with 52 cards, treats Kings/Queens etc. as normal values (11-14)
    for (int i=2;i<15;++i) {
      Card card1 = new Card(i,"Hearts");
      Card card2 = new Card(i,"Spades");
      Card card3 = new Card(i,"Clubs");
      Card card4 = new Card(i,"Diamonds");
      m_deck.add(card1);
      m_deck.add(card2);
      m_deck.add(card3);
      m_deck.add(card4);
    }
  }

  //Getters
  //getCard returns card at specified index
  public Card getCard(int x){
    return m_deck.get(x);
  }
  //getSize returns how many cards are in the deck
  private int getSize(){
    return this.m_deck.size();
  }

  //Setters
  //Replace card at certain index
  public void setCard(int i,Card x){
    this.m_deck.set(i,x);
  }
  //Add card at certain index
  public void addCard(Card x){
    this.m_deck.add(x);
  }
  public void addCard(int i,Card x){
    this.m_deck.add(i,x);
  }

  //toString
  //Returns String of the entire deck
  public String toString(){
    String answer = "";
    answer += "\nHere is the deck of 52 cards: ";
    for (int i = 0;i<this.m_deck.size();++i) {
      answer += System.lineSeparator() + this.m_deck.get(i).toString();
    }
    return answer;
  }

  //Equals()
  //returns boolean seeing if two decks are same length
  public boolean equals(Deck x){
    return (this.m_deck.size() == x.getSize());
  }

  //Deal method
  //Deal returns a random card and removes it from deck
  public Card Deal(){
    //Assign Random Numbers
    Random random = new Random();
    int randNumber = random.nextInt(this.m_deck.size());

    //Return deleted index
    return this.m_deck.remove(randNumber);
  }
}
