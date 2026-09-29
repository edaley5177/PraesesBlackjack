package Card;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Shoe {
    private final List<Card> cards;

    public Shoe(int numberOfDecks) {
        this.cards = new ArrayList<>();

        // Build the shoe by combining N decks
        for (int i = 0; i < numberOfDecks; i++) {
            Deck deck = new Deck();
            this.cards.addAll(deck.getCards());
        }

        //shuffle();
    }

    // Shuffles all remaining cards in the shoe
    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    // Draws the top card from the shoe
    public Card drawCard() {
        if (cards.isEmpty()) {
            throw new IllegalStateException("The shoe is empty!");
        }
        return cards.remove(0); // Removes and returns the first card
    }

    // Returns the number of cards left in the shoe
    public int cardsRemaining() {
        return cards.size();
    }
}
