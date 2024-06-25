package Games.Blackjack;
import java.util.ArrayList;

class Dealer {
    ArrayList<Card> hand;//represents the dealer's hand
    private int handvalue=0;//value of the dealer's hand (starts at 0)
    private Card[] aHand;//used to convert the dealer's hand to an array
    private int AceCounter;//counts the aces in the dealer's hand



//============================================================
// Description: This is the constructor for Dealer. It properly
//              creates and sets up the dealer.
// Input: a deck
// Output:
//============================================================
    Dealer(Deck deck)
    {
        hand = new ArrayList<>();
        aHand = new Card[]{};
        int AceCounter=0;
        for(int i=0; i<2; i++)
        {
            hand.add(deck.drawCard());
        }
        aHand = hand.toArray(aHand);
        for(int i=0; i<aHand.length; i++)
        {
            handvalue += aHand[i].getValue();
            if(aHand[i].getValue()==11)
            {
                AceCounter++;
            }
            while(AceCounter>0 && handvalue>21)
            {
                handvalue-=10;
                AceCounter--;
            }
        }
    }                 //end of Dealer()
//============================================================



//============================================================
// Description: This function prints the dealer's first card.
// Input:
// Output: dealer's first card printed.
//============================================================
    public void showFirstCard()
    {
        Card[] firstCard = new Card[]{};
        firstCard = hand.toArray(firstCard);
        System.out.println("["+firstCard[0]+"]");
    }                 //end of showFirstCard()
//============================================================



//============================================================
// Description: This function gives the dealer another card
//              and updates the value of his hand. Takes into
//              account the value of aces.
// Input:
// Output:
//============================================================
    public void Hit(Deck deck)
    {
        hand.add(deck.drawCard());
        aHand = hand.toArray(aHand);
        handvalue = 0;
        for(int i=0; i<aHand.length; i++)
        {
            handvalue += aHand[i].getValue();
            if(aHand[i].getValue()==11)
            {
                AceCounter++;
            }
            while(AceCounter>0 && handvalue>21)
            {
                handvalue-=10;
                AceCounter--;
            }
        }
    }                 //end of Hit(Deck)
//============================================================



//============================================================
// Description: This function determines if the dealer wants
//              to hit according to classic Games.Blackjack
//              rules.
// Input:
// Output:
//============================================================
    public boolean wantsToHit()
    {
        if(handvalue<17)
        {
            return true;
        }
        return false;
    }                 //end of wantsToHit()
//============================================================



//============================================================
// Description: This function returns true if the dealer
//              has blackjack.
// Input:
// Output: true or false depending on if the dealer had a
//          blackjack.
//============================================================
    public boolean hasBlackJack()
    {
        if(hand.size()==2 && handvalue==21)
        {
            System.out.println("The dealer has blackjack!");
            return true;
        }
        return false;
    }                 //end of hasBlackJack()
//============================================================



//============================================================
// Description: This function prints the dealer's hand
// Input:
// Output: dealer's hand printed
//============================================================
    public void showHand()
    {
        System.out.println(hand);
    }                 //end of showHand()
//============================================================



//============================================================
// Description: This function returns the value of the dealer's
//              hand.
// Input:
// Output: dealer's hand's value
//============================================================
    public int getHandValue()
    {
        return handvalue;
    }                 //end of getHandValue()
//============================================================



//============================================================
// Description: This function determines if a dealer has busted.
// Input: int handValue
// Output: true of false depending if the dealer has busted
//============================================================
    public boolean busted(int handValue)
    {
        if(handValue>21)
        {
            System.out.println("The dealer busted!");
            return true;
        }
        return false;
    }                 //end of busted(int)
//============================================================



//============================================================
// Description: This function takes the turn for the dealer
//              and returns the value of his hand.
// Input: Deck deck
// Output: takes turn for the dealer and returns the value of
//         his hand.
//============================================================
    public int takeTurn(Deck deck)
    {
        while(wantsToHit())
        {
            System.out.println("The dealer hits");
            Hit(deck);
            if(busted(handvalue))
            {
                break;
            }
        }
        if(handvalue<=21)
        {
            System.out.print("The dealer stands.");
        }
        return handvalue;
    }
}                 //end of takeTurn(Deck)
//============================================================
