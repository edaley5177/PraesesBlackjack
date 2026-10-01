package Players;

import java.util.ArrayList;
import java.util.List;
import Card.*;

public abstract class Player {
    private String name;
    private List<Hand> hands;
    private int chips;
    protected int totalBet;

    public static String WIN = "Win!!!";
    public static String LOOSE = "Lost:(";
    public static String PUSH = "Push :O";

    public Player(String name) {
        this.name = name;
        this.hands = new ArrayList<>();
        chips=100;
    }



    public void clearHands() {
        hands.clear();
    }

    public List<Hand> getHands() {
        return hands;
    }
    //only used in testing
    public void setHands(List<Hand> customHand){
        hands=customHand;
   }


    public String getName() {
        return name;
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
        System.out.println("Player " + name + " has blackjack bankroll is now: " +chips);

    }

    public abstract Action makeDecision(Card dealerUpCard);

    public abstract void bet(int betAmount);
}
