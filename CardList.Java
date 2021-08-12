/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crazy8;


/**
 * ***************************************************************
 * Class CardList, A Linked list of playing cards May be placed in a file named
 * CardList.java
 *
 * Note : This class can be used to create a 'hand' of cards Just Create another
 * CardList object, and delete cards from 'theDeck' and insert the cards into
 * the new CardList object
 *
 *****************************************************************
 */
class CardList {

    private Card firstcard = null;
    private int numcards = 0;

    public CardList(int num) {
        numcards = num;   //set numcards in the deck
        for (int i = 0; i < num; i++) {  // load the cards
            Card temp = new Card(i);
            if (firstcard != null) {
                temp.setNext(firstcard);
            }
            firstcard = temp;
        }
    }

    public Card getFirstCard() {
        return firstcard;
    }
    public Card getCardAt(int position){
        Card curr = firstcard;
        for(int i = 1;i <= numcards; i++){
            if (i == position){
                break;
            }
            curr= curr.getNextCard();
        }
        return curr;
    }

    public Card deleteCard(int cardnum) {
        Card target, targetprevious;

        if (cardnum > numcards) {
            return null;   // not enough cards to delete that one
        } else {
            numcards--;
        }

        target = firstcard;
        targetprevious = null;
        while (cardnum-- > 0) {
            targetprevious = target;
            target = target.getNextCard();
            if (target == null) {
                return null;  // error, card not found
            }
        }
        if (targetprevious != null) {
            targetprevious.setNext(target.getNextCard());
        } else {
            firstcard = target.getNextCard();
        }
        return target;
    }

    public void insertCard(Card target) {
        numcards++;
        if (firstcard != null) {
            target.setNext(firstcard);
        } else {
            target.setNext(null);
        }
        firstcard = target;
    }

    public void shuffle() {
        for (int i = 0; i < 300; i++) {
            int rand = (int) (Math.random() * 100) % numcards;
            Card temp = deleteCard(rand);
            if (temp != null) {
                insertCard(temp);
            }
        }  // end for loop
    }   // end shuffle
    
    public int getValue(){ // will return the total evaluation of Cardlist
        int sum=0;
        boolean hasAce = false;
        Card current = firstcard;
        if(current == null){
            return 0;
        }
        while(current != null){
            if(current.getCardRank() == 1){
                hasAce = true;
            }
            sum += current.getCardRank();
            current = current.getNextCard();
        }
        if(hasAce && sum <=11){
            sum+=10;
        }
        return sum;
    }
    public Card deal(){
        return this.deleteCard(0);
    }
    public int getNumCards(){
        return numcards;
    }
}    // end class CardList

