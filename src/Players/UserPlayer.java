package Players;

import java.util.Scanner;
import Card.*;

public class UserPlayer extends Player {
    private Scanner scanner;
    public int bankRoll;

    public UserPlayer(String name, Scanner scanner) {
        super(name);
        this.scanner = scanner;
        bankRoll=100;
    }

    @Override
    public Action makeDecision(Card dealerUpCard) {
        System.out.println(getName() + "'s current hand: " + getHand() + " (Total: " + getHandValue() + ")");
        System.out.println("Dealer's up card: " + dealerUpCard);
        System.out.print("Choose action: [H]it or [S]tand: ");

        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.startsWith("H")) return Action.HIT;
            if (input.startsWith("S")) return Action.STAND;
            System.out.print("Invalid choice. Type 'H' for Hit or 'S' for Stand: ");
        }
    }

    @Override
    public void bet(int betAmount){
        totalBet = betAmount;
        if(betAmount > bankRoll){
            System.out.println("Player: " + getName() + " has bet too much");
            return;
        }
        bankRoll-= betAmount;
        System.out.println("User: " + getName()+" has bet "+ betAmount + " bankroll is now: " + bankRoll);

    }
}
