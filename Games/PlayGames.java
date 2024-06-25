package Games;

import CasinoFileManager.*;
import Menu.Menu;
import Player.Player;
import Games.Roulette.Roulette;
import Games.Slots.SlotMachine;
import Games.Blackjack.Blackjack;

import java.io.IOException;
import java.util.Scanner;
import Casino.Casino;

/*
This class facilitates the user playing any of the
games. The primary purpose of this class is to
prompt the user to enter bet amounts, and collect user
input for actually playing the games. The values entered
by the user are stored in local variables which are
passed to their respective games, in which
all the game logic is being handled.

The game functions all take a player, input scanner, and casino.
After this, data such as a bet type and amount is retrieved and
then passed to the play function within the corresponding class.
The user plays the game and finally within the respective play
function any needed data such as user balance is modified
appropriately.

After each play the file balance is updated to prevent cheating.
*/
public class PlayGames {

    //final strings for getBetAmount function
    private static final String[] betString = {"Select a bet amount (or enter 0 to cancel): ", "You don't have enough money! Try again.",
            "You can't bet 0 or less, try again", "You can't bet more that 10 million at a time, try again"};
    private static final String[] tableString = { "How much money would you like to put on the table (money taken from account to be used during this game's session) or type 0 to cancel?",
            "Error, you cannot put more money on the table than you currently have in your account", "Error, you cannot put 0 or less on the table...",
            "Error,  you cannot put more than ten million on the table..."};



//====playBlackJack===========================================
//
//============================================================
    public static void playBlackJack(Player player, Scanner input, Casino casino, CasinoFileManagerCasino filesCasino, CasinoFileManagerUsers filesUser)
    {
        int cash;
        cash = getBetAmount(input, player, tableString);
        if (cash != 0)
        {
            Blackjack.play(player, casino, cash, input);
        }
        else
        {
            System.out.println("Canceling....");
        }
        try
        {
            filesUser.rewriteData(player);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        try
        {
            filesCasino.rewriteData(casino);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }                 //end of playBlackJack(Player, Scanner, Casino)
//============================================================



//====playRoulette============================================
//
//============================================================
    public static void playRoulette(Player player, Scanner input, Casino casino, CasinoFileManagerCasino filesCasino, CasinoFileManagerUsers filesUser) {
        int numberSelected = 0;
        int betTypeSelect;
        int betAmount;
        char contPlay;
        char evenOdd = ' ';
        String color = "";

        Menu.displayRainbowString("\nWELCOME TO ROULETTE\n");

        do {

            do{
                System.out.println("""
                    Choose type of Bet
                    1. Number [Highest Payout]
                    2. Color
                    3. Even/Odd""");

                betTypeSelect = input.nextInt();

                if(betTypeSelect != 1 && betTypeSelect !=2 && betTypeSelect != 3)
                    System.out.print("Please enter a valid bet type (1 for Number, 2 for Color, 3 for Even/Odd)");

            } while (betTypeSelect != 1 && betTypeSelect !=2 && betTypeSelect !=3);


            switch(betTypeSelect){

                case 1:
                    System.out.println("Select a number: (0 - 36)");
                    numberSelected = input.nextInt();

                    while (numberSelected < 0 || numberSelected > 36)
                    {
                        System.out.println("Error, that is not a valid number for this game\nPlease try again");
                        System.out.println("Select a number: (0 - 36)");
                        numberSelected = input.nextInt();
                    }

                    break;
                case 2:

                    do {
                        System.out.println("Select a color (Red or Black)");
                        color = input.next();

                        if(!color.equalsIgnoreCase("red") && !color.equalsIgnoreCase("black"))
                            System.out.println("Please choose a valid color (Red or Black)");

                    }
                    while(!color.equalsIgnoreCase("red") && !color.equalsIgnoreCase("black"));


                    break;

                case 3:

                    do {
                        System.out.println("Type E for Even, O for Odd");
                        evenOdd = input.next().charAt(0);
                        evenOdd = Character.toUpperCase(evenOdd);

                        if(evenOdd != 'E' && evenOdd != 'O')
                            System.out.println("Invalid Input");


                    } while(evenOdd != 'E' && evenOdd != 'O');

                    break;
            }

            betAmount = getBetAmount(input, player, betString);

            if (betAmount != 0)
            {
                if(betTypeSelect == 1)
                    Roulette.play(player, betAmount, numberSelected, casino);

                else if(betTypeSelect == 2)
                    Roulette.play(player, betAmount, color, casino);

                else // betType == 3
                    Roulette.play(player, betAmount, evenOdd, casino);

            }
            else
            {
                System.out.println("Canceling....");
            }

            if(player.getBalance() == 0){
                System.out.println("You're out of money. See ya!");
                break;
            }

            try
            {
                filesUser.rewriteData(player);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
            try
            {
                filesCasino.rewriteData(casino);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }

            System.out.println("Continue playing? (Y/N): ");

            contPlay = input.next().charAt(0);

            input.nextLine();

        } while(Character.toUpperCase(contPlay) == 'Y');
    }                 //end of playRoulette(Player, Scanner, Casino)
//============================================================



//====playSlots===============================================
//
//============================================================
    public static void playSlots(Player player, Scanner input, Casino casino, CasinoFileManagerCasino filesCasino, CasinoFileManagerUsers filesUser) throws InterruptedException {
        int betAmount;
        char contPlay;

        do {
            betAmount = getBetAmount(input, player, betString);

            if (betAmount != 0)
            {
                SlotMachine.play(player, betAmount, casino);
            }
            else
            {
                System.out.println("Canceling....");
            }
            if (player.getBalance() == 0)
            {
                System.out.println("You're out of money. See ya!");
                break;
            }

            try
            {
                filesUser.rewriteData(player);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
            try
            {
                filesCasino.rewriteData(casino);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
            System.out.println("Continue playing? (Y/N): ");

            contPlay = input.next().charAt(0);

            input.nextLine();
        }
        while (Character.toUpperCase(contPlay) == 'Y');
    }                 //end of playSlots(Player, Scanner, Casino)
//============================================================



//====getBetAmount============================================
// This function collects user input for the amount they want
// to bet, and handles all input validation to ensure they
// don't exceed their balance. All games call this function,
// and pass the bet amount to the corresponding game's class.
//============================================================
    private static int getBetAmount (Scanner input, Player player, String[] betStrings)
    {
        int betAmount = 0;

        do
        {
            System.out.println(betStrings[0]);
            betAmount = input.nextInt();

            if ((betAmount > player.getBalance() || betAmount < 0 || betAmount >= 10000001))
            {
                if (betAmount > player.getBalance())
                {
                    System.out.println(betStrings[1]);
                }
                else if (betAmount < 0)
                {
                    System.out.println(betStrings[2]);
                }
                else
                {
                    System.out.println(betStrings[3]);
                }
            }
        } while (betAmount > player.getBalance() || betAmount < 0 || betAmount >= 10000001);
        return betAmount;
    }                 //end of getBetAmount(Player, Scanner, Casino)
//============================================================

}
