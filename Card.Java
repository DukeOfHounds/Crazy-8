
package crazy8;

import java.awt.Image;

/**
 *
 * @author DukeOfHounds
 */

/**
 * ***************************************************************
 * Suit enum allows for the comparison of card suits, as well as the Card back
 * note that this will not run properly if the card image names are changed.
 * placed in a file named Card.java
 * ****************************************************************
 */
enum Suit {// allows Suits to be compared.
    SPADES(3), HEARTS(2), DIAMONDS(1), CLUBS(0), CARDBACK(4);
    private final int value;

    private Suit(int value) {
        this.value = value;
    }
};

/**
 * ***************************************************************
 * Class Card, the derived class each card is one object of type Card May be
 * placed in a file named Card.java
 * ****************************************************************
 */
class Card extends Link {

    private Image cardimage;
    private int rank;// holds card's value
    private Suit suit;// holds the card's suit
    private Card next;
    private int cardnum;

    public Card(int cardnum) {
        cardimage = Crazy8.load_picture("images/gbCard" + cardnum + ".gif");
        // code ASSUMES there is an images sub-dir in your project folder
        if (cardimage == null) {
            System.out.println("Error - image failed to load: images/gbCard" + cardnum + ".gif");
            System.exit(-1);
        }
        switch (cardnum / 13) {
            case 0:
                this.suit = Suit.CLUBS;
                break;
            case 1:
                this.suit = Suit.DIAMONDS;
                break;
            case 2:
                this.suit = Suit.HEARTS;
                break;
            case 3:
                this.suit = Suit.SPADES;
                break;
            case 4:
                this.suit = Suit.CARDBACK;
        }
        rank = cardnum % 13 + 1;
        next = null;
    }

    public Card getNextCard() {
        return (Card) next;
    }
    public void setNext(Card next){
        this.next = next;
    }

    public Image getCardImage() {
        return cardimage;
    }

    public int getCardRank() {
        if( rank >10){
            return 10;
        }  else return rank;
    }

    public Suit getSuit() {
        return suit;
    }
    public int getCardnum(){
        return cardnum;
    }
    
}  //end class Card
