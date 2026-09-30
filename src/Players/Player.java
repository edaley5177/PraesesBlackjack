package Players;

import java.util.ArrayList;
import java.util.List;
import Card.*;

public abstract class Player {
    private String name;
    private List<Card> hand;
    private int chips;
    protected int totalBet;

    public static String WIN = "Win!!!";
    public static String LOOSE = "Lost:(";
    public static String PUSH = "Push :O";

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
        chips=100;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void clearHand() {
        hand.clear();
    }

    public List<Card> getHand() {
        return hand;
    }
    //only used in testing
    public void setHand(List<Card> customHand){
        hand=customHand;
    }

    public List<Card> hit(Shoe shoe){
        addCard(shoe.drawCard());
        return getHand();
    }

    public void doubleDown(Shoe shoe){
        totalBet+= totalBet;
        hit(shoe);
    }

    public String getName() {
        return name;
    }

    // Hand valuation logic (handling Aces as 1 or 11)
    public int getHandValue() {
        int value = 0;
        int aces = 0;
        for (Card card : hand) {
            value += card.getNumericValue();
            if (card.isAce()) aces++;
        }
        while (value > 21 && aces > 0) {
            value -= 10;
            aces--;
        }
        return value;
    }

    public int getChips(){
        return chips;
    }
    public int getBetAmount(){
        return totalBet;
    }
    public void win(int betAmount){
        chips += betAmount;

        System.out.println("Player: " + name +" "+ WIN + " bankroll is now: " + chips );

    }

    public void loose(int betAmount){
        chips-= betAmount;
        System.out.println("Player: " + name +" "+ LOOSE +  " bankroll is now: " + chips );

    }

    public void push(){
        System.out.println("Player: " + name + " " + PUSH +  " bankroll is now: " + chips);
    }

    public void winBJ(int betAmount){

        int winAmount = (int) Math.ceil(betAmount*1.5);
        chips+= winAmount;
        System.out.println("Player " + name + "has blackjack " + " bankroll is now: " +chips);

    }

    public boolean isBust() {
        return getHandValue() > 21;
    }

    public boolean hasBJ(){
        if(hand.size()==2 && getHandValue()==21){
            return true;
        }
        return false;
    }


    public abstract Action makeDecision(Card dealerUpCard);

    public abstract void bet(int betAmount);
}
