package Players;

import Card.Card;

import java.util.List;

public class DealerPlayer extends Player {

    public DealerPlayer() {
        super("Dealer");
    }

    @Override
    public Action makeDecision(Card dealerUpCard) {
        // Dealer doesn't need to look at dealerUpCard since it IS the dealer
        if (getHandValue() < 17) {
            return Action.HIT;
        }
        return Action.STAND;
    }

    @Override
    public void bet(int betAmount){
        //do nothing dealer does not bet
    }


}
