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

    public String getValue(){
        return value;
    }

    public int getNumericValue(){
        switch(value){
            case "2":
                return 2;
            case "3":
                return 3;
            case "4":
                return 4;
            case "5":
                return 5;
            case "6":
                return 6;
            case "7":
                return 7;
            case "8":
                return 8;
            case "9":
                return 9;
            case "10", "Jack", "Queen", "King":
                return 10;
            case "Ace":


        }
    }

    public boolean isAce(){
        return value.equals("Ace");
    }
}
