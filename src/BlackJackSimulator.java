import Card.*;
import Players.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BlackJackSimulator {
    public static Shoe mainShoe;
    public static List<Player> allPlayers;
    public static DealerPlayer dealer;
    public static String NOBJ = "Dealer does not have blackjack!!!";
    public static UserPlayer human;
    public static Card dealerUpCard;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Hello and welcome to blackjack! How many decks do you want to play with? (Max:50) ");
        int numberOfDecks = scanner.nextInt();
        if (numberOfDecks <= 0 || numberOfDecks >= 51) {
            System.out.println("You must enter an integer between 1 and 50");
            return;
        }

        System.out.println("How many bot players do you want?(Max: 30)");
        int numberOfBots = scanner.nextInt();
        if (numberOfBots < 0 || numberOfBots > 30) {
            System.out.println("You must enter an integer between 1 and 30");
            return;
        }

        int numberOfUsers = 0;
        System.out.println("Would you like to add 1 user player?");
        String user = scanner.next().toUpperCase();
        if (user.startsWith("Y")) {
            numberOfUsers = 1;
        }

        mainShoe = new Shoe(numberOfDecks);
        mainShoe.shuffle();
        mainShoe.shuffle();

        allPlayers = makeAllPlayers(numberOfBots, numberOfUsers);

        dealFirstCards(allPlayers);
        printAllHands();

        dealer = (DealerPlayer) allPlayers.getLast();

        // Check if dealer has natural Blackjack
        Hand dealerHand = dealer.getHands().get(0);
        if (dealerHand.hasBJ()) {
            printPlayersResults(true);
            return;
        }

        System.out.println(NOBJ);
        dealerUpCard = dealerHand.getCards().get(1);

        // Play bot turns
        playBotHands(dealerUpCard);

        // Play human turn
        if (numberOfUsers == 1) {

            playUserTurn(human, dealerUpCard, scanner);
        }

        // Play dealer turn
        playDealerHand(dealerUpCard);
        printAllHands();

        // Settle all hands
        printPlayersResults(false);

        scanner.close();
    }

    private static void playUserTurn(UserPlayer user, Card dealerUpCard, Scanner scanner) {
        Hand initialHand = user.getHands().get(0);

        // Prompt for Split if initial 2-card pair and player has enough chips
        if (initialHand.canSplit() && user.getChips() >= initialHand.getBet()) {
            printDealerHand();
            System.out.println("Hand: " + initialHand.getCards() + " | Value: " + initialHand.getHandValue());
            System.out.print("You have a pair! Would you like to Split? (Y/N): ");
            String choice = scanner.next().toUpperCase();
            if (choice.startsWith("Y")) {
                executeSplit(user, initialHand);
            }
        }

        // Play each hand (1 hand if not split, 2 hands if split)
        for (int i = 0; i < user.getHands().size(); i++) {
            Hand currentHand = user.getHands().get(i);

            if (user.getHands().size() == 2) {
                System.out.println("\n--- Playing Hand " + (i + 1) + " of 2 ---");
            }

            playSingleHand(user, currentHand, dealerUpCard, scanner);
        }
    }

    private static void executeSplit(UserPlayer user, Hand initialHand) {
        user.bet(initialHand.getBet());

        Hand secondHand = new Hand(initialHand.getBet());
        secondHand.setBet(initialHand.getBet());

        // Remove 2nd card from hand 1 and add to hand 2
        Card splitCard = initialHand.getCards().remove(1);
        secondHand.addCard(splitCard);

        // Deal 1 new card from Shoe to each hand
        initialHand.addCard(mainShoe.drawCard());
        secondHand.addCard(mainShoe.drawCard());

        // Register second hand to player
        user.getHands().add(secondHand);
        System.out.println("Hand split successfully into 2 active hands!");
    }

    private static void playSingleHand(UserPlayer user, Hand hand, Card dealerUpCard, Scanner scanner) {
        boolean firstAction = true;
        while (true) {
            printDealerHand();
            System.out.println("Hand: " + hand.getCards() + " | Value: " + hand.getHandValue());

            if (hand.isBust()) {
                System.out.println("Hand busted!");
                break;
            }
            if (hand.getHandValue() == 21) {
                System.out.println("Hand hit 21!");
                break;
            }

            // Delegate decision input to the UserPlayer object
            Action action = user.makeDecision(dealerUpCard);

            if (action == Action.DOUBLE) {
                if (firstAction && user.getChips() >= hand.getBet()) {
                    user.bet(hand.getBet());
                    hand.setBet(hand.getBet() * 2);
                    hand.addCard(mainShoe.drawCard());
                    System.out.println("Doubled Down! Hand is now: " + hand.getCards() + " | Value: " + hand.getHandValue());
                    if (hand.isBust()) {
                        System.out.println("Hand busted!");
                    }
                    break; // Double down gets exactly 1 card then hand ends
                } else {
                    System.out.println("Cannot double down right now.");
                }
            } else if (action == Action.HIT) {
                hand.addCard(mainShoe.drawCard());
                firstAction = false;
            } else if (action == Action.STAND) {
                break; // Stand
            } else if (action == Action.SPLIT) {
                if (hand.canSplit() && user.getChips() >= hand.getBet()) {
                    executeSplit(user, hand);
                    break; // After splitting current hand state changes
                } else {
                    System.out.println("Cannot split this hand.");
                }
            } else {
                System.out.println("Invalid option. Try again.");
            }
        }
    }

    private static void playDealerHand(Card dealerUpCard) {
        Hand dealerHand = dealer.getHands().get(0);
        while (!dealerHand.isBust() && dealerHand.getHandValue() < 17) {
            System.out.println("Current dealer value: " + dealerHand.getHandValue());
            dealerHand.addCard(mainShoe.drawCard());
        }
    }

    private static void playBotHands(Card dealerUpCard) {
        for (int i = 0; i < allPlayers.size() - 2; i++) {
            BotPlayer currBot = (BotPlayer) allPlayers.get(i);
            Hand botHand = currBot.getHands().get(0);

            Action nextAction = currBot.makeDecision(dealerUpCard);
            if (nextAction == Action.HIT) {
                botHand.addCard(mainShoe.drawCard());
            }
        }
    }

    private static void printPlayersResults(boolean dealerBJ) {
        Hand dealerHand = dealer.getHands().get(0);
        int dealerValue = dealerHand.getHandValue();

        // Loop all players except Dealer
        for (int i = 0; i < allPlayers.size() - 1; i++) {
            Player player = allPlayers.get(i);

            // Iterate over every active hand (1 or 2 hands if split)
            for (int h = 0; h < player.getHands().size(); h++) {
                Hand hand = player.getHands().get(h);
                int handValue = hand.getHandValue();
                int bet = hand.getBet();

                System.out.print(player.getName() + " (Hand " + (h + 1) + "): ");

                if (dealerBJ) {
                    if (hand.hasBJ()) {
                        player.push();
                    } else {
                        player.loose(bet);

                    }
                    continue;
                }

                if (hand.isBust()) {
                    player.loose(bet);
                } else if (dealerHand.isBust()) {
                    if (hand.hasBJ()) {
                        player.winBJ(bet);
                    } else {
                        player.win(bet);
                    }
                } else if (handValue < dealerValue) {

                    player.loose(bet);
                } else if (handValue == dealerValue) {
                    player.push();
                } else { // handValue > dealerValue
                    if (hand.hasBJ()) {
                        player.winBJ(bet);
                    } else {
                        player.win(bet);
                    }
                }
            }
        }
    }

    public static List<Player> makeAllPlayers(int bots, int users) {
        allPlayers = new ArrayList<>();

        for (int i = 0; i < bots; i++) {
            BotPlayer bot = new BotPlayer("bot" + i);
            bot.getHands().add(new Hand(5)); // Hand initialized with bet 5
            allPlayers.add(bot);
        }

        if (users == 1) {
            Scanner scan = new Scanner(System.in);
            System.out.println("What would you like the human player's name to be?");
            String humanName = scan.nextLine();
            human = new UserPlayer(humanName, scan);

            System.out.println("How much would " + humanName + " like to bet? (min: 5, max: 100)");
            int userBet = scan.nextInt();
            while (userBet < 5 || userBet > 100) {
                System.out.println("Invalid bet, enter an integer from 5 to 100.");
                userBet = scan.nextInt();
            }

            human.getHands().add(new Hand(userBet));
            human.bet(userBet);
            allPlayers.add(human);
        }

        DealerPlayer dealerPlayer = new DealerPlayer();
        dealerPlayer.getHands().add(new Hand(0));
        allPlayers.add(dealerPlayer);

        return allPlayers;
    }

    public static void dealFirstCards(List<Player> allPlayers) {
        dealer = (DealerPlayer) allPlayers.getLast();

        // Deal 2 cards to each player's initial hand
        for (int round = 0; round < 2; round++) {
            for (Player p : allPlayers) {
                p.getHands().get(0).addCard(mainShoe.drawCard());
            }
        }
    }

    public static void printAllHands() {
        for (Player p : allPlayers) {
            for (int i = 0; i < p.getHands().size(); i++) {
                Hand h = p.getHands().get(i);
                System.out.println("Player: " + p.getName() + " [Hand " + (i + 1) + "]: "
                        + h.getCards() + " (Value: " + h.getHandValue() + ")");
            }
        }
    }

    public static void printDealerHand(){
        System.out.println("Dealers Hand: " + dealerUpCard);
    }
}