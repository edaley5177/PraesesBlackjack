import Card.Deck;
import Card.Shoe;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome to blackjack! How many decks do you want to play with? ");

        int numberOfDecks = scanner.nextInt();

        Deck deck1 = new Deck();


        Shoe mainShoe = new Shoe(numberOfDecks);
        for (int i = 0; i <= numberOfDecks*52; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("current card = " + mainShoe.drawCard().toString());
        }

        scanner.close();
    }
}