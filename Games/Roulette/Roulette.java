package Games.Roulette;

import java.util.Random;

import Player.Player;
import Casino.Casino;


/*

The class handles all the game logic for Roulette.
There is no user-input, the values used to play the
game and calculate winnings are passed to this class.

There are three separate play() functions, each of
which correspond to the users betType, and are
called polymorphically based on the parameters passed
to play().

Likewise, there are three separate calculatePayout()
functions, each of which correspond the users betType.
The play() functions call their respective calculatePayout()
function to determine the user's winnings and update
their balance.

 */

public class Roulette {

    private static int winningNumber;
    private static String winningColor;
    private static char parity;


//====play by Number==========================================
// This function is called if the user selects to play by Even/Odd
// and calls the spinWheel() to determine the winning number.
// The corresponding calculatePayout class is called to determine
// whether the user has won or lost money, and updates the balance
// accordingly.
//============================================================
    public static void play(Player player, int betAmount, int selectedNumber, Casino casino) {

        winningNumber = spinWheel();
        winningColor = getColor(winningNumber);

        // Pay the player accordingly based on both number and color
        int payout = calculatePayout(betAmount, selectedNumber, casino);

        payoutAndOutput(player, payout, betAmount, casino);

        // Display additional information
        outputWinInfo();
    }                 //end of play(Player, int, int, Casino)
//============================================================



//==== play() by Color========================================
// This function is called if the user selects to play by color,
// and calls the spinWheel() and getColor() functions to determine
// the winning color. The corresponding calculatePayout class is
// called to determine whether the user has won or lost money,
// and updates the balance accordingly.
//============================================================
    public static void play(Player player, int betAmount, String color, Casino casino) {


        winningNumber = spinWheel();
        winningColor = getColor(winningNumber);

        // Pay the player accordingly based on color
        int payout = calculatePayout(betAmount, color, casino);

        payoutAndOutput(player, payout, betAmount, casino);

        // Display additional information
        outputWinInfo();
    }                 //end of play(Player, int, String, Casino)
//============================================================



//==== play() by Parity ======================================
// This function is called if the user selects to play by Parity
// and calls the spinWheel() to determine if the winning number is
// Even or Odd. The corresponding calculatePayout class is
// called to determine whether the user has won or lost money,
// and updates the balance accordingly.
//============================================================
    public static void play(Player player, int betAmount, char evenOdd, Casino casino) {


        winningNumber = spinWheel();
        winningColor = getColor(winningNumber);

        parity = 'O';

        if(winningNumber % 2 == 0)
            parity = 'E';

        // Pay the player accordingly based on number
        int payout = calculatePayout(betAmount, evenOdd, casino);

        payoutAndOutput(player, payout, betAmount, casino);

        // Display additional information
        outputWinInfo();

    }                 //end of play(Player, int, char, Casino)
//============================================================



//==== spinWheel() ===========================================
// This function generates a random number from 0 - 36, and
// returns that number to the caller.
// Each of the play functions call spinWheel().
//============================================================
    private static int spinWheel()
    {

        Random randomNumberObject = new Random();
        return randomNumberObject.nextInt(37); // Assuming a standard roulette wheel with numbers 0 to 36
    }                 //end of spinWheel()
//============================================================



//==== getColor() ============================================
// This function determines the color of the winning numbers,
// and returns it to the calling play() function.
//============================================================
    private static String getColor(int number) {

        switch (number) {
            case 1, 3, 5, 7, 9, 12, 14, 16, 18, 19, 21, 23, 25, 27, 30, 32, 34, 36:
                return "Red";
            case 0, 2, 4, 6, 8, 10, 11, 13, 15, 17, 20, 22, 24, 26, 28, 29, 31, 33, 35:
                return "Black";
        }

        return "";

    }                 //end of getColor(int)
//============================================================



//==== calculatePayout() by number ===========================
// This function calculates the payout based on the number
// entered by user.
// Payout is 35 to 1
//============================================================
    private static int calculatePayout(int betAmount, int selectedNumber, Casino casino) {


        if (selectedNumber == winningNumber) {
            // Payout is 35 to 1
            return (int)(betAmount * 35 * casino.getBalanceMultiplierRate() * casino.getMultiplier());
        }

        // No match, return 0
        else {
            return 0;
        }
    }                 //end of calculatePayout(int, int, Casino)
//============================================================



// ==== calculatePayout() by color ===========================
// This function calculates the payout based on the color
// selected by the user.
// Payout is 2 to 1
//============================================================
    private static int calculatePayout(int betAmount, String selectedColor, Casino casino)
    {
        // check if color matches
        if (selectedColor.equalsIgnoreCase(winningColor)) {
            // Payout is 2 to 1
            return (int)(betAmount * 2 * casino.getBalanceMultiplierRate() * casino.getMultiplier());
        }
        // No match, return 0
        else {
            return 0;
        }
    }                 //end of calculatePayout(int, String, Casino)
//============================================================



// ==== calculatePayout() by Parity ==========================
// This function calculates the payout based on the parity
// selected by the user.
// Payout is 2 to 1
//============================================================
    private static int calculatePayout(int betAmount, char evenOdd, Casino casino)
    {
        // check if number matches even/odd
        if (parity == evenOdd) {
            // Payout is 2 to 1
            return (int)(betAmount * 2 * casino.getBalanceMultiplierRate() * casino.getMultiplier());
        }
        // No match, return 0
        else {
            return 0;
        }
    }                 //end of calculatePayout(int, char, Casino)
//============================================================



// ==== payoutAndOutput ======================================
// Outputs payout information and updates balances
//============================================================
    private static void payoutAndOutput(Player player, int payout, int betAmount, Casino casino)
    {

        if (payout > 0) {
            player.addFunds(payout);
            casino.takeFromBalance(payout);
            System.out.println("Congratulations! You won $" + payout);
        } else {
            player.withdraw(betAmount);
            casino.addToBalance(betAmount);
            System.out.println("Sorry, you lost $" + betAmount);
        }
    }                 //end of payoutAndOutput(Player, int, int, Casino)
//============================================================



// ==== outputWinInfo ========================================
// Outputs winning number and color
//============================================================
    private static void outputWinInfo()
    {
        System.out.println("Winning Number: " + winningNumber);
        System.out.println("Winning Color: " + winningColor);
    }                 //end of outputWinInfo()
//============================================================
}