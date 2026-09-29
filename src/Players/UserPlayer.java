package Players;

import java.util.Scanner;
import Card.*;

public class UserPlayer extends Player {
    private Scanner scanner;

    public UserPlayer(String name, Scanner scanner) {
        super(name);
        this.scanner = scanner;
    }

    @Override
    public Action makeDecision(Card dealerUpCard) {
        System.out.println(getName() + "'s current hand: " + getHand() + " (Total: " + getHandValue() + ")");
        System.out.print("Choose action: [H]it or [S]tand: ");

        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.startsWith("H")) return Action.HIT;
            if (input.startsWith("S")) return Action.STAND;
            System.out.print("Invalid choice. Type 'H' for Hit or 'S' for Stand: ");
        }
    }
}
