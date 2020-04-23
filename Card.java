// Anthony Foley
// 2313898
// afoley@chapman.edu
// CPSC-231-01
// Assignment MP5
// Card class

/*
This class is used to create a Card object that contains
a suit and a number
*/

public class Card{

  //private member variables
  private int m_cardNumber;
  private String m_suit;

  //Overloaded constructor
  public Card(int card, String suit){
    this.m_cardNumber = card;
    this.m_suit = suit;
  }

  //Setters
  public void setCardNumber(int number){
    this.m_cardNumber = number;
  }
  public void setSuit(String suit){
    this.m_suit = suit;
  }

  //Getters
  public int getCardNumber(){
    return this.m_cardNumber;
  }
  public String getSuit(){
    return this.m_suit;
  }

  //ToString
  //returns String of the card
  public String toString(){

    if (m_cardNumber<12 && m_cardNumber>10) {
      return ("Joker of " + this.m_suit);
    }
    else if (m_cardNumber<13 && m_cardNumber>11) {
      return ("Queen of " + this.m_suit);
    }
    else if (m_cardNumber<14 && m_cardNumber>12) {
      return ("King of " + this.m_suit);
    }
    else if (m_cardNumber<15 && m_cardNumber>13) {
      return ("Ace of " + this.m_suit);
    }
    return (this.m_cardNumber + " of " + this.m_suit);
  }

  //Equals
  //Checks to see if two cards are equal
  public boolean equals(Card card2){
    return ( this.m_cardNumber == card2.getCardNumber() && this.m_suit == card2.getSuit() );
  }
}
