package Games.Blackjack;
import java.util.*;
import Player.Player;
import Casino.Casino;

public class Blackjack {
    private static Scanner input;
    private static int cash;//cash the user bets with
    private static int startingCash;
    private static int bet;//how much the user wants to bet
    private static int AceCounter;//how many aces are in the user's hand
    private static int handvalue;//the value of the user's hand


//============================================================
// Description: This is the play function which acts as the
//              main of Blackjack. This function displays
//              blackjack rules, initializes and shuffles deck,
//              deals hands to player and dealers, calls games,
//              functions
// Input: a player, a casino, a bet amount, and a scanner
// Output: blackjack played
//============================================================
    public static void play(Player player, Casino casino, int money, Scanner scanner)
    {
        input = scanner;
        Menu.Menu.displayRainbowString("Welcome to BlackJack!");
        System.out.println(" RULES: ");
        System.out.println("  Each player is dealt 2 cards. The dealer is dealt 2 cards with one face-up and one face-down.");
        System.out.println("  Cards are equal to their value with face cards being 10 and an Ace being 1 or 11.");
        System.out.println("  Player types “hit” to gain another card from the deck. Player types “stay” to keep their current card total.");
        System.out.println("  To win the game, the player hand must have a higher card total than the dealer without going over 21.");
        System.out.println("  If the player total equals the dealer total, it is a “Push” and the hand ends in a draw.\n");

        cash = money;
        player.spend(cash);
        startingCash = cash;
        while(cash>0){
            Deck deck = new Deck();//initialize deck, dealer, hands, and set the bet.
            deck.shuffle();
            AceCounter=0;
            Dealer dealer = new Dealer(deck);
            List<Card> hand = new ArrayList<>();
            hand.add(deck.drawCard());
            hand.add(deck.drawCard());
            bet=Bet(cash, input);
            System.out.println("Cash:"+(cash-bet));
            System.out.println("Money on the table:"+bet);
            System.out.println("");
            System.out.println("Here is your hand: ");
            System.out.println(hand);
            System.out.println("");
            int handvalue = calcHandValue(hand);
            System.out.println("The dealer is showing: ");
            dealer.showFirstCard();
            if(hasBlackJack(handvalue) && dealer.hasBlackJack())//check if both the user and dealer have blackjack.
            {
                Push();
            }
            else if(hasBlackJack(handvalue))//check if the user has blackjack.
            {
                System.out.println("You have BlackJack!");
                System.out.printf("You win %.2fx your money back!", 2*casino.getBalanceMultiplierRate()*casino.getMultiplier());
                cash=cash+(int)(bet*casino.getBalanceMultiplierRate()* casino.getMultiplier());
                Win(casino);
            }
            else if(dealer.hasBlackJack())//check if the dealer has blackjack.
            {
                System.out.println("Here is the dealer's hand:");
                dealer.showHand();
                Lose();
            }
            else
            {
                if(2*bet<cash)//check if the user can double down.
                {
                    System.out.println("Would you like to double down (yes or no)?");//allows the user to double down.
                    String doubled = input.nextLine();
                    while(!isYesOrNo(doubled))
                    {
                        System.out.println("Please enter yes or no.");
                        doubled = input.nextLine();
                    }
                    if(doubled.equals("yes"))
                    {
                        System.out.println("You have opted to double down!");
                        bet=2*bet;
                        System.out.println("Cash:"+(cash-bet));
                        System.out.println("Money on the table:"+bet);
                    }
                }
                System.out.println("Would you like to hit or stand?");//ask if the user will hit or stand
                String hitter = input.nextLine();
                while(!isHitOrStand(hitter))
                {
                    System.out.println("Please enter 'hit' or 'stand'.");
                    hitter = input.nextLine();
                }
                while(hitter.equalsIgnoreCase("hit"))//hits the user as many times as he or she pleases.
                {
                    Hit(deck, hand);
                    System.out.println("Your hand is now:");
                    System.out.println(hand);
                    handvalue = calcHandValue(hand);
                    if(checkBust(handvalue))//checks if the user busted
                    {
                        Lose();
                        break;
                    }
                    System.out.println("Would you like to hit or stand?");
                    hitter = input.nextLine();
                }
                if(hitter.equalsIgnoreCase("stand"))//lets the user stand.
                {
                    int dealerhand = dealer.takeTurn(deck);//takes the turn for the dealer.
                    System.out.println("");
                    System.out.println("Here is the dealer's hand:");
                    dealer.showHand();
                    if(dealerhand>21)//if the dealer busted, user wins.
                    {
                        Win(casino);
                    }
                    else
                    {
                        int you = 21-handvalue;//check who is closer to 21 and determine winner
                        int deal = 21-dealerhand;
                        if(you==deal)
                        {
                            Push();
                        }
                        if(you<deal)
                        {
                            Win(casino);
                        }
                        if(deal<you)
                        {
                            Lose();
                        }
                    }
                }
            }
            System.out.println("Would you like to play again (yes or no)?");//ask if the user wants to keep going
            String answer = input.nextLine();
            while(!isYesOrNo(answer))
            {
                System.out.println("Please answer yes or no.");
                answer = input.nextLine();
            }
            if(answer.equalsIgnoreCase("no"))
            {
                break;
            }
        }
        System.out.println("Your cash is: $"+cash);//if user doesn't want to play or runs out of cash, either congratulates them on their winnings or lets them know
        if(cash==0)
        {
            System.out.println("You ran out of cash!");
            casino.addToBalance(startingCash);
        }
        else if (cash == startingCash)
        {
            System.out.println("You broke even");
            player.addFunds(startingCash);
        }
        else if (cash > startingCash)
        {
            System.out.println("Congratulations you won $" + (cash-startingCash));
            casino.takeFromBalance(cash-startingCash);
            player.addFunds(cash);
        }
        else
        {
            System.out.println("Sorry you lost $" + (startingCash-cash));
            casino.addToBalance(startingCash-cash);
            player.addFunds(cash);
        }
    }                 //end of play()
//============================================================



//============================================================
// Description: This function checks if the user has
//              blackjack.
// Input: hand value
// Output: true or false depending on if the user has a
//         blackjack
//============================================================
    private static boolean hasBlackJack(int handValue)
    {
        if(handValue==21)
        {
            return true;
        }
        return false;
    }                 //end of hasBlackJack(int)
//============================================================



//============================================================
// Description: This function calculates the value of a
//              player's hand.
// Input: card list
// Output: value of player's hand
//============================================================
    private static int calcHandValue(List<Card> hand)
    {
        Card[] aHand = new Card[]{};
        aHand = hand.toArray(aHand);
        int handvalue=0;
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
        return handvalue;
    }                 //end of calcHandValue(List<Card>)
//============================================================



//============================================================
// Description: This function asks the user how much he
//              or she would like to bet.
// Input: table cash and a scanner
// Output: bet amount
//============================================================
    private static int Bet(int cash, Scanner input)
    {
        int bet;
        do
        {
            System.out.println("How much would you like to bet?");
            bet=input.nextInt();
            if (bet > cash || bet <= 0)
            {
                if (bet > cash)
                {
                    System.out.println("You cannot bet more cash than you have!");
                }
                else
                {
                    System.out.println("You cannot bet 0 or less!");
                }
            }

        } while(bet > cash || bet <= 0);

        return bet;
    }                 //end of Bet(int, Scanner)
//============================================================



//============================================================
// Description: This function is called if the user wins.
// Input: the casino
// Output: amount added to table cash
//============================================================
    private static void Win(Casino casino)
    {
        System.out.println("Congratulations, you won $" + (int)((bet)*casino.getMultiplier()*casino.getBalanceMultiplierRate()) + "!");
        cash=cash+(int)((bet)*casino.getMultiplier()*casino.getBalanceMultiplierRate());
        System.out.println("Cash: $"+cash);
    }                 //end of Win(Casino)
//============================================================



//============================================================
// Description: This function is called if the user loses.
// Input:
// Output: amount taken from table cash
//============================================================
    private static void Lose()
    {
        System.out.println("Sorry, you lost $" + bet + "!");
        cash=cash-bet;
        System.out.println("Cash: $"+cash);
    }                 //end of Lose()
//============================================================



//============================================================
// Description: This function is called if the user pushes
// Input:
// Output: pushes
//============================================================
    private static void Push()
    {
        System.out.println("It's a push!");
        System.out.println("You get your money back.");
        System.out.println("Cash: "+cash);
    }                 //end of Push()
//============================================================



//============================================================
// Description: This function adds a card to user's hand and
//              calculates the value of that hand. Aces are
//              taken into account.
// Input: Deck, card list
// Output: hits
//============================================================
    private static void Hit(Deck deck, List<Card> hand)
    {
        hand.add(deck.drawCard());
        Card[] aHand = new Card[]{};
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
    }                 //end of Hit(Deck, List<Card>)
// ============================================================



//============================================================
// Description: This function determines if a user has
//              input hit or stand.
// Input: String
// Output: true or false (true if they typed hit or stand)
//============================================================
    private static boolean isHitOrStand(String hitter)
    {
        if(hitter.equalsIgnoreCase("hit") || hitter.equalsIgnoreCase("stand"))
        {
            return true;
        }
        return false;
    }                 //end of isHitOrStand(String)
//============================================================



//============================================================
// Description: This function determines if a user has busted.
// Input: hand value
// Output: true or false (true if they have busted)
//============================================================
    private static boolean checkBust(int handvalue)
    {
        if(handvalue>21)
        {
            System.out.println("You have busted!");
            return true;
        }
        return false;
    }                 //end of checkBust(int)
//============================================================



//============================================================
// Description: This function determines if a user has input
//              yes or no.
// Input: String
// Output: true or false (true if they have typed either "yes"
//         or "no")
//============================================================
    private static boolean isYesOrNo(String answer)
    {
        if(answer.equalsIgnoreCase("yes") || answer.equalsIgnoreCase("no"))
        {
            return true;
        }
        return false;
    }
}                 //end of isYesOrNo(String)
//============================================================