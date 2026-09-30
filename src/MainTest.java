import Players.BotPlayer;
import Players.DealerPlayer;
import Players.Player;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    BlackJackSimulator bjGame;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        bjGame  = new BlackJackSimulator();
    }

    @org.junit.jupiter.api.Test
    void main() {
    }

    @org.junit.jupiter.api.Test
    void makeAllPlayersHappyPath() {
        //Arrange
       List<Player> result =bjGame.makeAllPlayers(2,0);
       List<Player> expected = new ArrayList<Player>();
       expected.add(new BotPlayer("bot0"));
       expected.add(new BotPlayer("bot1"));
       expected.add(new DealerPlayer());

       //Assert
       assertEquals(expected.size(), result.size() );

        for (int i = 0; i < expected.size(); i++) {
            assertEquals(expected.get(i).getName(), result.get(i).getName());
        }


    }

    @org.junit.jupiter.api.Test
    void makeAllPlayersSadPath() {
        List<Player> resultNegativeBots = bjGame.makeAllPlayers(-4,8);
        List<Player> resultNegativeUsers = bjGame.makeAllPlayers(3,-9);

        List<Player> expected = new ArrayList<>();
        assertEquals(expected,resultNegativeBots);
        assertEquals(expected, resultNegativeUsers);

    }
    @org.junit.jupiter.api.Test
    void dealFirstCards() {
    }

    @org.junit.jupiter.api.Test
    void printAllHands() {
    }
}