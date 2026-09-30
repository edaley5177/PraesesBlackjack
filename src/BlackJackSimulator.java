import Card.*;

import Players.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class BlackJackSimulator {
    public static Shoe mainShoe;
    public static List<Player> allPlayers;
    public static DealerPlayer dealer;
    public static String DEALERBJ = "Dealer has black jack everyone looses :(";
    public static String NOBJ = "Dealer does not have blackjack!!!";
    public static String WIN = "Win!!!";
    public static String LOOSE = "Lost:(";
    public static String PUSH = "Push :O";

    public static void main(String[] args) {

        int numberOfPlayers;
        Scanner scanner = new Scanner(System.in);

        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome to blackjack! How many decks do you want to play with? (Max:50) ");

        int numberOfDecks = scanner.nextInt();
        if(numberOfDecks<=0 || numberOfDecks>=51){
            System.out.println("You must enter an integer between 1 and 50");
            return;
        }

        System.out.println("How many bot players do you want?(Max: 30)");
        int numberOfBots = scanner.nextInt();
        if(numberOfBots<0 || numberOfBots>30){
            System.out.println("You must enter an integer between 1 and 30");
            return;
        }

        int numberOfUsers=0;
        System.out.println("Would you like to add 1 user player?");
        String user = scanner.next();
        if(user.equals("yes") || user.equals("Yes"))
             numberOfUsers =1;


        numberOfPlayers = 1+numberOfBots+numberOfUsers;

        Deck deck1 = new Deck();


        mainShoe = new Shoe(numberOfDecks);
        mainShoe.shuffle();
        mainShoe.shuffle();

        allPlayers = makeAllPlayers(numberOfBots, numberOfUsers);

        dealFirstCards(allPlayers);
        printAllHands();

        //if dealer has BJ print message

        if(allPlayers.getLast().getHandValue() ==21){
            System.out.println(DEALERBJ);
            return;
        }
        System.out.println(NOBJ);
        Card dealerUpCard = dealer.getHand().get(1);

        //dealer does not have BJ so loop through all players, let each play hand
        //just player actions in this loop, dealer will be separate
        playBotHands(dealerUpCard);

        //play users
        if(numberOfUsers==1)
            playUserHand(dealerUpCard, (UserPlayer) allPlayers.get(allPlayers.size()-2));

        //play dealers hand
        playDealerHand(dealerUpCard);
        printAllHands();

        //after all players including dealer are done, loop all nondealer players and print win loose or push
        printPlayersResults();


        scanner.close();
    }

    private static void playUserHand(Card dealerUpCard, UserPlayer user){


        Action nextAction = user.makeDecision(dealerUpCard);
        while(!nextAction.equals(Action.STAND) && !user.isBust()){
            nextAction = user.makeDecision(dealerUpCard);
        }

    }

    private static void playDealerHand(Card dealerUpCard) {
        while(!dealer.isBust() && dealer.getHandValue()<17){
            Action nextAction = dealer.makeDecision(dealerUpCard);
            System.out.println("currdealervalue: " + dealer.getHandValue());
            if(nextAction == Action.HIT){
                dealer.hit(mainShoe);
            }
            if (nextAction == Action.STAND){
                continue;
            }

        }
    }

    private static void playBotHands(Card dealerUpCard) {
        for (int i = 0; i < allPlayers.size()-1; i++) {
            Player currPlayer = allPlayers.get(i);
            Action nextAction= currPlayer.makeDecision(dealerUpCard);
            if(nextAction == Action.HIT){
                currPlayer.hit(mainShoe);
            }
            if (nextAction == Action.STAND){
                continue;
            }
        }
    }

    private static void printPlayersResults() {
        for (int i = 0; i < allPlayers.size()-1; i++) {
            Player currPlayer = allPlayers.get(i);
            if(dealer.isBust()){
                if(currPlayer.isBust()){
                    System.out.println("Player: " + currPlayer.getName() + LOOSE);
                    continue;
                }
                else {
                    System.out.println("Player: " + currPlayer.getName() + WIN);
                    continue;
                }

            }
            if(currPlayer.isBust() || currPlayer.getHandValue()<dealer.getHandValue()){
                System.out.println("Player: " + currPlayer.getName() + LOOSE);
                continue;
            }
            if (currPlayer.getHandValue()== dealer.getHandValue()){
                System.out.println("Player: " + currPlayer.getName() + PUSH);
                continue;
            }
            if(currPlayer.getHandValue() > dealer.getHandValue()){
                System.out.println("Player: " + currPlayer.getName() + WIN);
                continue;
            }
        }
    }

    public static List<Player> makeAllPlayers(int bots, int users){

       allPlayers = new ArrayList<Player>();

        //check for user errors first
        if(bots <=0 || users<0){

            return allPlayers;
        }
        for (int i = 0; i < bots; i++) {
            allPlayers.add(new BotPlayer("bot" + i));
        }

        if(users ==1){
            Scanner scan = new Scanner(System.in);
            System.out.println("What would you like the human players name to be?");
            String humanName = scan.nextLine();
            allPlayers.add(new UserPlayer(humanName, scan));
        }

        allPlayers.add(new DealerPlayer());


        return allPlayers;
    }

    public static void dealFirstCards(List<Player> allPlayers){
        dealer = (DealerPlayer) allPlayers.getLast();
        int currPlayerIndex = 0;
        while(dealer.getHand().size()<2){
            //deal to dealer player
            if(currPlayerIndex==allPlayers.size()-1){
                dealer.addCard(mainShoe.drawCard());
            }
            else {
                Player currPlayer = allPlayers.get(currPlayerIndex);
                currPlayer.addCard(mainShoe.drawCard());
            }

            if(currPlayerIndex >=allPlayers.size()-1){
                currPlayerIndex=0;
            }
            else {
                currPlayerIndex++;
            }
        }

    }

    public static void printAllHands(){
        for (int i = 0; i < allPlayers.size(); i++) {
            System.out.println("Player: " + allPlayers.get(i).getName()+" hand: " + allPlayers.get(i).getHand() + " value: " + allPlayers.get(i).getHandValue());
        }
    }
}