package Menu;

import Casino.Casino;
import CasinoFileManager.CasinoFileManagerCasino;

import java.io.IOException;
import java.util.Scanner;


/*
This class holds the manager menu function and all the
extra functions needed for manager use including but not limited to:
adding and withdrawing from casino's balance, changing balance multipliers,
and changing the standard multiplier
*/
public class ManagerMenu {

//============================================================
// Description: This function displays the menu, verifies the
//              input, and then calls the needed function
// Input: menu choice
// Output:
//============================================================
    public static void managerMenu(Scanner input, Casino casino, CasinoFileManagerCasino filesCasino) throws IOException {
        int choice = 0;
        do {
            System.out.println(casino.toString());
            System.out.println("Select an option:\n\n1.Add to casino balance\n2.Withdraw from casino balance\n3.Change multiplier\n4.Change thresholds for casino balance multiplier\n5.Exit");
            choice = input.nextInt();

            while (choice <= 0 || choice >= 6) {
                System.out.println("Error, that is not valid menu option...");
                System.out.println("Select an option:\n\n1.Add to casino balance\n2.Withdraw from casino balance\n3.Change multiplier\n4.Change thresholds for casino balance multiplier\n5.Exit");
                choice = input.nextInt();
            }

            boolean succesful;

            switch (choice) {
                case 1:
                    succesful = addFunds(input, casino);
                    if (succesful)
                    {
                        System.out.println("Money successfully added");
                        filesCasino.rewriteData(casino);
                    }
                    else
                    {
                        System.out.println("Money was not successfully added");
                    }
                    break;
                case 2:
                    succesful = withdrawFunds(input, casino);
                    if (succesful)
                    {
                        System.out.println("Money successfully withdrawn");
                        filesCasino.rewriteData(casino);
                    }
                    else
                    {
                        System.out.println("Money was not successfully withdrawn");
                    }
                    break;
                case 3:
                    succesful = changeMultiplier(input, casino);
                    if (succesful)
                    {
                        System.out.println("Multiplier successfully changed");
                        filesCasino.rewriteData(casino);
                    }
                    else
                    {
                        System.out.println("Multiplier was not successfully changed");
                    }
                    break;
                case 4:
                    succesful = changeBMs(input, casino);
                    if (succesful)
                    {
                        System.out.println("Balance multiplier thresholds successfully changed");
                        filesCasino.rewriteData(casino);
                    }
                    else
                    {
                        System.out.println("Balance multiplier thresholds not successfully changed");
                    }
                    break;
                case 5:
                        filesCasino.closeFile(casino);
                    break;

            }
        } while (choice != 5);
        System.exit(0);
    }                 //end of managerMenu(Scanner, Casino, CasinoFileManagerCasino)
//============================================================



//============================================================
// Description: This function allows the for adding of money to
//              the casino balance.
// Input: money to add
// Output: updated casino balance
//============================================================
    private static boolean addFunds(Scanner input, Casino casino)
    {
        System.out.print("Please insert the amount of funds you wish to add (or 0 if nothing): ");
        long funds = input.nextLong();
        while (funds < 0)
        {
                System.out.println("Error, you cannot add less than 0");

            System.out.print("Please insert the amount of funds you wish to add: ");
            funds = input.nextLong();
        }
        if (funds == 0)
        {
            return false;
        }
        char choice;
        System.out.print("Are you sure you want to add " + funds + " dollars to the casino? Y/N: ");
        choice = input.next().charAt(0);
        choice = Character.toUpperCase(choice);

        while (choice != 'Y' && choice != 'N')
        {
            System.out.println("Error, that is not a valid input, please try again");
            System.out.print("Are you sure you want to add " + funds + " dollars to the casino? Y/N: ");
            choice = input.next().charAt(0);
            choice = Character.toUpperCase(choice);
        }

        if(choice == 'N')
        {
            return false;
        }
        casino.addToBalance(funds);
        return true;
    }                 //end of addFunds(Scanner, Casino)
//============================================================



//============================================================
// Description: This function allows for the withdrawing of
//              money from the casino balance.
// Input: money to withdraw
// Output: updated balance
//============================================================
    private static boolean withdrawFunds(Scanner input, Casino casino)
    {

        boolean canWithdraw;
        System.out.print("Please insert the amount of funds you wish to withdraw from the casino (0 if nothing): ");
        long withdraw = input.nextLong();
        canWithdraw = !casino.negative(withdraw);
        while (withdraw < 0)
        {
            System.out.println("Error, you cannot withdraw less than 0");

            System.out.print("Please insert the amount of funds you wish to withdraw from the casino: ");
            withdraw = input.nextLong();
            canWithdraw = !casino.negative(withdraw);
        }
        if (withdraw == 0) {
            return false;
        }
        char choice;
        if (canWithdraw) {
            System.out.print("Are you sure you want to withdraw " + withdraw + " dollars from the casino? Y/N: ");
        } else {
            System.out.print("Are you sure you want to withdraw " + withdraw + " dollars from the casino? (please note that this will put the casino negative in funds) Y/N: ");
        }
        choice = input.next().charAt(0);
        choice = Character.toUpperCase(choice);

        while (choice != 'Y' && choice != 'N') {
            System.out.println("Error, that is not a valid input, please try again");
            if (canWithdraw) {
                System.out.print("Are you sure you want to withdraw " + withdraw + " dollars from the casino? Y/N: ");
            } else {
                System.out.print("Are you sure you want to withdraw " + withdraw + " dollars from the casino? (please note that this will put the casino negative in funds) Y/N: ");
            }
            choice = input.next().charAt(0);
            choice = Character.toUpperCase(choice);
        }

        if (choice == 'N') {
            return false;
        }
        else
        {
            casino.takeFromBalance(withdraw);
            return true;
        }
    }                 //end of withdrawFunds(Scanner, Casino)
//============================================================



//============================================================
// Description: This function allows for the changing of the
//              standard multiplier.
// Input: new multiplier
// Output: multiplier updated
//============================================================
    private static boolean changeMultiplier (Scanner input, Casino casino)
    {
        float multiplier;
        System.out.print("Please input the casino's new default winnings multiplier or type 0 to cancel (NOTE the suggested lowest is 0.9 and the suggested highest is 1.5 (2 for special events)): ");
        multiplier = input.nextFloat();

        while (multiplier < 0.75 && multiplier != 0|| multiplier > 2.5)
        {
            System.out.println("Error, that is not a valid multiplier.\nPlease try again, the multiplier must be less than 0.75 and cannot be greater than 2.5 (or 0 to cancel)");
            System.out.print("Please input the casino's new default winnings multiplier or type 0 to cancel (NOTE the suggested lowest is 0.9 and the suggested highest is 1.5 (2 for special events)): ");
            multiplier = input.nextFloat();
        }
        if (multiplier == 0)
        {
            return false;
        }
        else
        {
            casino.changeMultiplier(multiplier);
            return true;
        }
    }                 //end of changeMultiplier (Scanner, Casino)
//============================================================



//============================================================
// Description: This function allows for the changing of the
//              balance multipliers.
// Input: new balance multipliers
// Output: balance multipliers updated
//============================================================
    private static boolean changeBMs(Scanner input, Casino casino)
    {
        long balanceMultipliers[] = new long[4];
        double[] multiplierList = {0.85, 0.95, 1, 1.1};
        String[] stringListForMultipliers = {"(the lowest multiplier)", "(the second lowest multiplier)", "(the default multiplier)", "(the highest multiplier)"};

        for (int i = 0; i < multiplierList.length; i++) {
            System.out.print("Please input the casino's balance to trigger a " + multiplierList[i] + " multiplier " +
                    stringListForMultipliers[i] + " or type 0 to cancel: ");
            balanceMultipliers[i] = input.nextLong();

            if (balanceMultipliers[i] == 0)
            {
                return false;
            }

            if (i > 0)
            {
                while (balanceMultipliers[i] <= balanceMultipliers[i-1])
                {
                    System.out.println("Error, that is not a valid balance multiplier.\nPlease try again, this balance multiplier must be greater than the previous multiplier balance threshold");
                    System.out.print("Please input the casino's balance to trigger a " + multiplierList[i] + " multiplier " +
                            stringListForMultipliers[i] + " or type 0 to cancel: ");
                    balanceMultipliers[i] = input.nextLong();
                    if (balanceMultipliers[i] == 0)
                    {
                        return false;
                    }
                }
            }
        }
        casino.changeBMs(balanceMultipliers[0], balanceMultipliers[1], balanceMultipliers[2], balanceMultipliers[3]);
        return true;
    }
}                 //end of changeBMs(Scanner, Casino)
//============================================================
