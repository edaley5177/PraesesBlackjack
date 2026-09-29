package Players;

import java.util.ArrayList;
import java.util.List;
import Card.*;

public abstract class Player {
    private String name;
    private List<Card> hand;
    private int chips; // will use later when i add betting

    public Player(String name) {
        this.name = name;
        this.hand = new ArrayList<>();
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

    public List<Card> hit(Shoe shoe){
        addCard(shoe.drawCard());
        return getHand();
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

    public boolean isBust() {
        return getHandValue() > 21;
    }


    public abstract Action makeDecision(Card dealerUpCard);
}
