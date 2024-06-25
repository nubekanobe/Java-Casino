package Games.Slots;

import Player.Player;
import java.util.Random;
import Menu.Menu;
import Casino.Casino;


public class SlotMachine {
    private static String wheel1;
    private static String wheel2;
    private static String wheel3;
    private static Random randIndex;

    // Payout multipliers for different symbols
    private static final int BAR_MULTIPLIER = 5;
    private static final int CHERRY_MULTIPLIER = 40;
    private static final int LEMON_MULTIPLIER = 10;
    private static final int SEVEN_MULTIPLIER = 7;
    private static final int BELL_MULTIPLIER = 50;

    private static String[] icons = { "BAR", "CHERRY", "LEMON", "7", "BELL" };

    /*
	The slot machine class allows for the user to play slots.
	A player, betAmount, and the casino is passed into the function
	called play. Each wheel is spun and displays the result and whether
	the user won or lost money.
	*/

//============================================================
// Description: This is the play function which acts as the
//              main of SlotMachine. This function deals with
//              all the data and calls the needed functions to
//              play slots.
// Input: a player, a bet amount, and a casino
// Output: slots played
//============================================================
    public static void play(Player p, int betAmount, Casino casino) throws InterruptedException {
        randIndex = new Random();
        validateInput(betAmount);
        Menu.displayRainbowString("\nWELCOME TO SLOTS\n");

        wheel1 = "";
        wheel2 = "";
        wheel3 = "";

        //Spin the slot wheels
        wheel1 = spinSlots();
        wheel2 = spinSlots();
        wheel3 = spinSlots();

        printOutcome(wheel1, wheel2, wheel3);

        //Check if each icon on the wheel matches
        if (wheel1.equals(wheel2) && wheel1.equals(wheel3) && wheel2.equals(wheel3)) {
            int payout = findPayout(betAmount, wheel1, casino);
            casino.takeFromBalance(payout);
            p.addFunds(payout);
        } else {
            System.out.println("Sorry, no match, you lose.");
            p.withdraw(betAmount); //casino gains the user's amount of money bet, added to casino balance
            casino.addToBalance(betAmount);
            System.out.println("Sorry, you lost $" + betAmount);
        }
    }                 //end of play(Player, int, Casino)
//============================================================



//============================================================
// Description: This function validates the user's input.
// Input: a bet amount
// Output: out verified
//============================================================
    private static void validateInput(int betAmount) {
        if (betAmount < 0) {
            throw new IllegalArgumentException("Invalid bet amount");
        }
    }                 //end of validateInput(int)
//============================================================



//============================================================
// Description: This function simulates spinning the slot
//              machine wheels. The special symbols (cherry and
//              bell) and rarer than the regular symbols(7, lemon,
//              and bar).
// Input:
// Output: special or regular spin
//============================================================
    private static String spinSlots() {
        int temp = randIndex.nextInt(100) + 1;

        if (temp % 3 == 0) {
            return spinSpecialSymbols(temp);
        } else {
            return spinRegularSymbols(temp);
        }
    }                 //end of spinSlots()
//============================================================



//============================================================
// Description: This function simulates spinning the slot
//              machine wheels for a regular spin(7, lemon,
//              and bar)
// Input:
// Output: 7, lemon, or bar
//============================================================
    private static String spinRegularSymbols(int temp) {
        temp = randIndex.nextInt(100) + 1;
        if (temp % 3 == 0) {
            return icons[3]; // "7"
        } else if (temp % 2 == 0) {
            return icons[2]; // "LEMON"
        } else {
            return icons[0]; // "BAR"
        }
    }                 //end of spinRegularSymbols(int)
//============================================================



//============================================================
// Description: This function simulates spinning the slot
//              machine wheels for a special spin(cherry and
//              bell)
// Input:
// Output: cherry or bell
//============================================================
    private static String spinSpecialSymbols(int temp) {
        temp = randIndex.nextInt(100) + 1;
        if (temp % 4 == 0) {
            return icons[1]; // "CHERRY"
        } else {
            return icons[4]; // "BELL"
        }
    }                 //end of spinSpecialSymbols(int)
//============================================================



//============================================================
// Description: This function determines the payout based on
//              the winning symbol. The corresponding multiplier
//              is calculated based on the winning symbol.
// Input:  bet, the symbol, and the casino
// Output: payment output
//============================================================
    private static int findPayout(int bet, String w1, Casino casino) {
        int pay = 0;
        int multiplier = 0;
        switch (w1) {
            case "BAR":
                multiplier = BAR_MULTIPLIER;
                break;
            case "CHERRY":
                multiplier = CHERRY_MULTIPLIER;
                break;
            case "LEMON":
                multiplier = LEMON_MULTIPLIER;
                break;
            case "7":
                multiplier = SEVEN_MULTIPLIER;
                break;
            case "BELL":
                multiplier = BELL_MULTIPLIER;
                break;
        }

        pay = (int)(bet * multiplier *casino.getBalanceMultiplierRate() * casino.getMultiplier());
        System.out.printf("Congrats! You won %.2fx your bet! $%d\n", (multiplier*casino.getBalanceMultiplierRate()* casino.getMultiplier()), pay);
        return pay;
    }                 //end of findPayout(int, String, Casino)
//============================================================



//============================================================
// Description: This function prints the outcome of the slot machine spin.
// Input: three symbols
// Output: outcome printed
//============================================================
    private static void printOutcome(String w1, String w2, String w3) throws InterruptedException {
        Menu.displayRainbowString("------------------------------------------------------------\n");
        System.out.print("\n");

        for (int i = 0; i < 15 - w2.length(); i++) {
            w1 = w1 + " ";
            w3 = " " + w3;
        }

        for (int i = 0; i < (60 - w1.length() - w2.length() - w3.length()) / 2; i++) {
            System.out.print(" ");
        }

        Thread.sleep(1000);
        System.out.print(w1);

        Thread.sleep(1000);
        System.out.print(w2);

        Thread.sleep(1000);
        System.out.print(w3);

        System.out.print("\n");
        Menu.displayRainbowString("------------------------------------------------------------\n");

        Thread.sleep(1000); // Add a delay after printing the entire outcome
    }                 //end of printOutcome(String, String, String)
//============================================================
}