package Card;

public class Card {

    private String value;
    private String suit;// for this game i don't need to know the suit this is just to make sure each value only has 4 instances per deck

    public Card(String value, String suit){
        this.value= value;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return value + " of " + suit;
    }
}
