package Players;
import Card.*;

public class BotPlayer extends Player {

    int bankRoll;
    public BotPlayer(String name) {
        super(name);
        bankRoll=100;
    }

    @Override
    public Action makeDecision(Card dealerUpCard) {
        int playerTotal = super.getHands().get(0).getHandValue();
        int dealerValue = dealerUpCard.getNumericValue();

        // Example Basic Strategy logic:
        if (playerTotal >= 17) {
            return Action.STAND;
        }
        if (playerTotal <= 11) {
            return Action.HIT;
        }
        // Total is 12–16: Stand if dealer shows 2–6, otherwise Hit
        if (dealerValue >= 2 && dealerValue <= 6) {
            return Action.STAND;
        }
        return Action.HIT;
    }

    @Override
    public void bet(int betAmount){
        totalBet = betAmount;
        if(betAmount > bankRoll){
            System.out.println("Player: " + getName() + " has bet too much");
            return;
        }

        bankRoll-= betAmount;
        System.out.println(getName() + " has bet " + betAmount + " remaining bankroll is: " + bankRoll);

    }
}
