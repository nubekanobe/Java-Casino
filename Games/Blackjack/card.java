package Games.Blackjack;

class Card {
    private int rank;//represents the rank of a card
    private int suit;//represents the suit of a card
    private int value;//represents the value of a card
    private static String[] ranks = {"Joker","Ace","Two","Three","Four","Five","Six","Seven","Eight","Nine","Ten","Jack","Queen","King"};
    private static String[] suits = {"Clubs","Diamonds","Hearts","Spades"};



//============================================================
// Description: This is the constructor for Card. Created with an
//              integer that represents a spot in the String array
//              ranks and the String array suits. This represents
//              the rank and suit of an individual card.
// Input:
// Output: Card created
//============================================================
    Card(int suit, int values)
    {
        this.rank=values;
        this.suit=suit;
    }                 //end of Card(int, int)
//============================================================



//============================================================
// Description: This function overrides the standard toString
//              function and instead returns a String of the
//              card's information
// Input:
// Output: returns String of card's information
//============================================================
    public String toString()
    {
        return ranks[rank]+" of "+suits[suit];
    }                 //end of toString()
//============================================================



//============================================================
// Description: getRank function
// Input:
// Output: returns rank
//============================================================
    public int getRank()
    {
        return rank;
    }                 //end of getRank()
//============================================================



//============================================================
// Description: getSuit function
// Input:
// Output: returns suit
//============================================================
    public int getSuit()
    {
        return suit;
    }                 //end of getSuit()
//============================================================



//============================================================
// Description: getValue function
// Input:
// Output: returns value of the card
//============================================================
    public int getValue()
    {
        if(rank>10)
        {
            value=10;
        }
        else if(rank==1)
        {
            value=11;
        }
        else
        {
            value=rank;
        }
        return value;
    }                 //end of getValue()
//============================================================



//============================================================
// Description: setValue function sets the value of a card
// Input: new value to set
// Output: value of the card updated
//============================================================
    public void setValue(int set)
    {
        value = set;
    }
}                 //end of setValue(int)
//============================================================