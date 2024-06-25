package Games.Blackjack;
import java.util.ArrayList;
import java.util.Random;

class Deck {
    private ArrayList<Card> deck;//represents a deck of cards


//============================================================
// Description: This is the constructor for Deck. It properly
//              initializes all the cards.
// Input:
// Output: Deck created
//============================================================
    Deck()
    {
        deck = new ArrayList<Card>();
        for(int i=0; i<4; i++)
        {
            for(int j=1; j<=13; j++)
            {
                deck.add(new Card(i,j));
            }
        }
    }                 //end of Deck()
//============================================================



//============================================================
// Description: This function shuffles the deck by changing the
//              indexes of pairs of cards in the deck
// Input:
// Output: Deck shuffled
//============================================================
    public void shuffle()
    {
        Random random = new Random();
        Card temp;
        for(int i=0; i<100; i++)
        {
            int index1 = random.nextInt(deck.size()-1);
            int index2 = random.nextInt(deck.size()-1);
            temp = deck.get(index2);
            deck.set(index2, deck.get(index1));
            deck.set(index1, temp);
        }
    }                 //end of shuffle()
//============================================================



//============================================================
// Description: This function Draws one card from the deck
// Input:
// Output: top card
//============================================================
    public Card drawCard()
    {
        return deck.remove(0);
    }
}                 //end of drawCard()
//============================================================