package Players;

import java.util.ArrayList;
import java.util.List;

import Card.*;

public class Hand {
    private List<Card> cards = new ArrayList<>();
    private int bet;
    private boolean isSplitHand = false;
    private boolean isCompleted = false;

    public Hand(int bet){
        this.bet=bet;
    }

    public boolean canSplit() {
        // Must have exactly two cards of matching rank (or matching value, depending on rules)
        return cards.size() == 2 &&
                cards.get(0).getValue() == cards.get(1).getValue();
    }

    /*public Hand split(Shoe shoe) {
        Card splitCard = cards.remove(1); // Remove the second card
        Hand newHand = new Hand();
        newHand.setBet(this.bet);
        newHand.addCard(splitCard);
        //newHand.setSplitHand(true);
        this.isSplitHand = true;

        // Deal one card to each hand to make them two-card hands again
        this.addCard(shoe.drawCard());
        //newHand.addCard(deck.draw());

        return newHand;
    }*/

    public int getBet(){
        return bet;
    }
    public void setBet(int bet){
        this.bet = bet;
    }

    public void hit(Shoe shoe){
        this.addCard(shoe.drawCard());

    }

    public void doubleDown(Shoe shoe, Player player){
        player.bet(bet);
        bet+=bet;
        hit(shoe);
    }

    public void addCard(Card card) {
        this.cards.add(card);
    }

    public void removeTopCard(){
        this.cards.remove(1);
    }

    public List<Card> getCards(){
        return this.cards;
    }

    public void setCards(List<Card> cards){
        this.cards=cards;
    }

    public boolean isBust() {
        return getHandValue() > 21;
    }

    public boolean hasBJ(){
        if(this.getCards().size()==2 && getHandValue()==21){
            return true;
        }
        return false;
    }

    public int getHandValue() {
        int value = 0;
        int aces = 0;
        for (Card card : this.getCards()) {
            value += card.getNumericValue();
            if (card.isAce()) aces++;
        }
        while (value > 21 && aces > 0) {
            value -= 10;
            aces--;
        }
        return value;
    }
}
