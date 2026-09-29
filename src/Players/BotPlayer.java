package Players;
import Card.*;

public class BotPlayer extends Player {

    public BotPlayer(String name) {
        super(name);
    }

    @Override
    public Action makeDecision(Card dealerUpCard) {
        int playerTotal = getHandValue();
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
}
