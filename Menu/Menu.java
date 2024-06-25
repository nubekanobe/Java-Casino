package Menu;

import Player.Player;

import java.util.Scanner;


/*
This class holds the menu function, the withdrawal function.
the addFunds function, and the displayRainbowString function.
These function are mainly general function that are needed
throughout the program.
*/
public class Menu
{
//============================================================
// Description: This function takes a String and displays it
//              in rainbow colors.
// Input: String text
// Output: rainbow text displayed
//============================================================
    public static void displayRainbowString(String text) {
        String[] colors = {
                "\u001B[31m", // Red
                "\u001B[33m", // Yellow
                "\u001B[32m", // Green
                "\u001B[36m", // Cyan
                "\u001B[34m", // Blue
                "\u001B[35m"  // Magenta
        };

        int colorIndex = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c != ' ') {
                System.out.print(colors[colorIndex % colors.length] + c);
                colorIndex++;
            } else {
                System.out.print(c);
            }
        }
        System.out.print("\u001B[0m"); // Reset color to default
    }                 //end of displayRainbowString(String)
//============================================================



//============================================================
// Description: This function displays the menu then gets and
//              verifies the user's input
// Input: menu choice
// Output: menu choice verified and returned
//============================================================
    public static int menu(Scanner input, Player currentPlayer) {
        int choice;

        do
        {
        displayRainbowString("\n--------------------------");
        System.out.println(" Balance: " + "$" + currentPlayer.getBalance());
        System.out.println("Select an Option (number only):\n\n1.Add Funds\n2.Withdraw Winnings\n3.Play BlackJack\n4.Play Roulette\n5.Play Slots\n6.Exit");
        displayRainbowString("--------------------------\n");
        choice = input.nextInt();

        if (choice <= 0 || choice >= 7)
        {
            System.out.println("Error, that is not valid menu option...");
        }
    }while (choice <= 0 || choice >= 7);

        return choice;
    }                 //end of menu(Scanner, Player)
//============================================================



//============================================================
// Description: This function allows the user to add money to
//              their balance.
// Input: money to add
// Output: updated balance
//============================================================
    public static boolean addFunds(Player player, Scanner input)
    {
        System.out.print("Please insert the amount of funds you wish to add (or 0 if nothing): ");
        long funds = input.nextLong();
        while (funds < 0 || funds >= 10_000_001)
        {
            if (funds < 0)
            {
                System.out.println("Error, you cannot add less than 0");

            }
            else
            {
                System.out.println("Error, you cannot add more than ten million at a time to your funds");
            }
            System.out.print("Please insert the amount of funds you wish to add: ");
            funds = input.nextLong();
        }
        if (funds == 0)
        {
            return false;
        }
        char choice;
        System.out.print("Are you sure you want to add " + funds + " dollars to your account? Y/N: ");
        choice = input.next().charAt(0);
        choice = Character.toUpperCase(choice);

        while (choice != 'Y' && choice != 'N')
        {
            System.out.println("Error, that is not a valid input, please try again");
            System.out.print("Are you sure you want to add " + funds + " dollars to your account? Y/N: ");
            choice = input.next().charAt(0);
            choice = Character.toUpperCase(choice);
        }

        if(choice == 'N')
        {
            return false;
        }
        return player.addFunds(funds);
    }                 //end of addFunds(Player, Scanner)
//============================================================



//============================================================
// Description: This function allows the user to withdraw
//              money from their balance.
// Input: money to withdraw
// Output: updated balance
//============================================================
    public static boolean withdrawFunds(Player player, Scanner input) {

        boolean canWithdraw;
        do {
            System.out.print("Please insert the amount of funds you wish to withdraw (0 if nothing): ");
            long withdraw = input.nextLong();
            while (withdraw < 0 || withdraw >= 10_000_001) {
                if (withdraw < 0)
                {
                    System.out.println("Error, you cannot withdraw less than 0");

                }
                else
                {
                    System.out.println("Error, you cannot withdraw more than ten million at a time to your funds");
                }
                System.out.print("Please insert the amount of funds you wish to withdraw: ");
                withdraw = input.nextLong();
            }
            if (withdraw == 0)
            {
                return false;
            }
            char choice;
            System.out.print("Are you sure you want to withdraw " + withdraw + " dollars from your account? Y/N: ");
            choice = input.next().charAt(0);
            choice = Character.toUpperCase(choice);

            while (choice != 'Y' && choice != 'N') {
                System.out.println("Error, that is not a valid input, please try again");
                System.out.print("Are you sure you want to withdraw " + withdraw + " dollars from your account? Y/N: ");
                choice = input.next().charAt(0);
                choice = Character.toUpperCase(choice);
            }

            if (choice == 'N') {
                return false;
            }
            canWithdraw = player.withdraw(withdraw);

            if (!canWithdraw)
            {
                System.out.println("Error, you do not have enough funds to the that withdrawal, please try again");
            }
        }while (!canWithdraw);

        return canWithdraw;
    }                 //end of withdrawFunds(Player, Scanner)
//============================================================
}
