import java.util.Scanner;
import CasinoFileManager.*;
import Player.Player;
import Games.PlayGames;
import Menu.*;
import Casino.Casino;


public class Main
{
    public static void main(String[] args) throws Exception
    {
        //create scanner
        Scanner input = new Scanner(System.in);

        //create file managing variables with respective file names
        CasinoFileManagerUsers filesUser = new CasinoFileManagerUsers("UserData.txt", input);
        CasinoFileManagerCasino filesCasino = new CasinoFileManagerCasino("CasinoData.txt", input);

        //creating variables
        Player currentPlr = null;
        Casino casino = filesCasino.loadCasino();
        String accountActive;
        int menuChoice;


        Menu.displayRainbowString("WELCOME TO THE HOMI CASINO\n");

        //prompting to see if user has a preexisting account
        System.out.println("\nWould you like to log into an existing account or create a new one? (Please enter either \"Existing\" or \"Create\")");
        accountActive = input.next();

        //verifying
        while (!accountActive.equalsIgnoreCase("Existing") && !accountActive.equalsIgnoreCase("Create") && !accountActive.equalsIgnoreCase("manager"))
        {
            input.nextLine();
            System.out.println("Error, that is not a valid entry, please try again.");
            System.out.println("Would you like to log into an existing account or create a new one? (Please enter either \"Existing\" or \"Create\")");
            accountActive = input.next();
        }

        //calling manager menu
        if (accountActive.equalsIgnoreCase("manager"))
        {
            ManagerMenu.managerMenu(input, casino, filesCasino);
        }
        //calling log into account
        else if (accountActive.equalsIgnoreCase("Existing"))
        {
            currentPlr = filesUser.logIntoAccount();
        }
        //calling create account
        else
        {
            currentPlr = filesUser.createAccount();

        }

        filesUser.rewriteData(currentPlr);

        //menu
        do {
            menuChoice = Menu.menu(input, currentPlr);

            boolean success = true;
            switch (menuChoice) {
                case 1:
                    success = Menu.addFunds(currentPlr, input);
                    if (!success) {
                        System.out.println("Funds were not added.");
                    }
                    else
                    {
                        System.out.println("Success, the funds were added to your account.");
                        filesUser.rewriteData(currentPlr);
                    }
                    break;
                case 2:
                    success = Menu.withdrawFunds(currentPlr, input);
                    if (!success) {
                        System.out.println("Funds were not withdrawn.");
                    }
                    else
                    {
                        System.out.println("Success, the funds were withdraw from your account.");
                        filesUser.rewriteData(currentPlr);
                    }
                    break;
                case 3:
                    PlayGames.playBlackJack(currentPlr, input, casino, filesCasino, filesUser);
                    break;
                case 4:
                    PlayGames.playRoulette(currentPlr, input, casino, filesCasino, filesUser);
                    break;
                case 5:
                    PlayGames.playSlots(currentPlr, input, casino, filesCasino, filesUser);
                    break;
                case 6:
                    System.out.print("Thank you for use the ");
                    Menu.displayRainbowString("HOMI CASINO");
                    System.out.println(" we hope you have an amazing day");
                    break;
            }
        } while (menuChoice != 6);

        filesUser.closeFile(currentPlr);
        filesCasino.closeFile(casino);
    }
}