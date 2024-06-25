package CasinoFileManager;

import Player.Player;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/*
This class manages user account data. In this class an account
can be created and data can be retrieved to log into an existing account.
Furthermore, this class also saves and the account data in an orderly
fashion to allow the file to be read easily by people. Lastly, this
class rewrites the file to save the new balance of the player.
*/
public class CasinoFileManagerUsers
{
    //needed variables for file operations and for getting data from user
    private Scanner input;
    private File file;
    private Scanner fileRead;
    private FileWriter output;
    private String fileName;



//============================================================
// Description: This function creates the needed file managing
//               variables based on the passed fileName.
// Input: string fileName(passed), Scanner scanner(passed)
// Output: variables created
//============================================================
    public CasinoFileManagerUsers(String fileName, Scanner scanner) throws IOException
    {
        input = scanner;
        this.fileName = fileName;
        file = new File(fileName);
        fileRead = new Scanner(file);
                fileRead.useDelimiter("_");
        output = new FileWriter(file, true);
    }                 //end of CasinoFileManagerUsers(String, Scanner)
//============================================================



//============================================================
// Description: This function allows for the creation of a new
//              account which is then returned.
// Input: account data (from user)
// Output: new account created
//============================================================
    public Player createAccount() throws Exception
    {
        String name;
        String username;
        String password;
        String passwordDoubleCheck;
        boolean passwordVerified;
        int age;
        long funds = 0;

        //creating account
        System.out.print("Please input your age: ");
        age = input.nextInt();

        while (age <= 0 || age >=126)
        {
            System.out.println("Error, that is not a valid age.\nPlease try again");
            System.out.print("Please input your age: ");
            age = input.nextInt();
        }

        //checking to see if they are over betting age
        if (age < 21)
        {
            System.out.println("We apologize but you may only play if you are 21 or older");
            System.exit(21);
        }

        input.nextLine();

        Player usernameTemp = null;
        do
        {
            System.out.print("Please enter a username: ");
            username = input.nextLine();
            usernameTemp = findPlayerByName(username);
            if (usernameTemp != null)
            {
                System.out.println("We're sorry, that username is already taken\nPlease try again with a new username");
            }
        } while (usernameTemp != null);


        System.out.print("Please enter your full name (first, middle, and last): ");
        name = input.nextLine();

        do {
            passwordVerified = true;
            System.out.print("Please enter a password (don't forget this as it will be needed to log into your account): ");
            password = input.nextLine();

            //verifying password
            System.out.print("Please enter your password again: ");
            passwordDoubleCheck = input.nextLine();

            //if passwords are not the same
            if (!password.equals(passwordDoubleCheck))
            {
                System.out.println("Error, those two passwords are not the same.\n");
                passwordVerified = false;
            }
        } while (!passwordVerified);


        char temp;

        System.out.print("Would you like to add initial funds to the account Y/N (note that this can be done later): ");
        temp = input.next().charAt(0);
        temp = Character.toUpperCase(temp);

        while (temp != 'Y' && temp != 'N')
        {
            System.out.println("Error, that is not a valid input, please try again");
            System.out.print("Would you like to add initial funds to the account Y/N (note that this can be done later): ");
            temp = input.next().charAt(0);
            temp = Character.toUpperCase(temp);
        }

        if(temp == 'Y')
        {
            System.out.print("Please enter the amount of funds you wish to add or type 0 to cancel adding funds ($1000.50 = 1000.50): ");
            funds = input.nextLong();

            while (funds < 0)
            {
                System.out.println("Error, -1 or less is not a valid input, please try again or type 0 to cancel adding funds");
                System.out.print("Please enter the amount of funds you wish to add ($1000.50 = 1000.50): ");
                funds = input.nextLong();
            }
        }

        //saving data to file
        output.write(username + '_' + name + '_' + password + '_' + age + '_' + funds + '\n');
        return new Player(username, name, password, age, funds);
    }                 //end of createAccount()
//============================================================



//============================================================
// Description: This function allows for the user to log into
//              an existing account and returns the account.
// Input: account name and then password (from user)
// Output: account logged into
//============================================================
    public Player logIntoAccount() throws Exception
    {
        Player player = null;
        input.nextLine();

        do {
            System.out.print("Please enter your username: ");
            String username = input.nextLine();


            //finding player
            player = findPlayerByName(username);
            if (player != null)
            {
                System.out.println(player);
                logIntoAccountPasswordHelper(player.getPassword());
            }
            else
            {
                //giving user option since their account was not found
                char temp;
                System.out.println("Username not found.....\nWould you like to try again, create a new account, or end to program A/N/E");
                temp = input.next().charAt(0);
                temp = Character.toUpperCase(temp);


                while (temp != 'A' && temp != 'N' && temp != 'E')
                {
                    System.out.println("Error, that is not a valid input, please try again");
                    System.out.println("Would you like to try again, create a new account, or end to program A/N/E");
                    temp = input.next().charAt(0);
                    temp = Character.toUpperCase(temp);
                }

                if (temp == 'A')
                {
                    input.nextLine();
                }
                else if (temp == 'N')
                {
                    return createAccount();
                }
                else
                {
                    System.exit(0);
                }
            }
        } while (player == null);

        return player;
    }                 //end of logIntoAccount()
//============================================================



//============================================================
// Description: This function checks to see if the password
//              entered by the user matches the password on
//              record.
// Input: password (from user)
// Output: password verified
//============================================================
    public void logIntoAccountPasswordHelper(String playerPassword)
    {
        String password;
        do
        {
            System.out.print("Please input your password: ");
            password = input.nextLine();

            //checking to see if they entered the correct password
            if (!password.equals(playerPassword))
            {
                char temp;
                System.out.println("Error, password incorrect try again Y/N: ");

                temp = input.next().charAt(0);
                temp = Character.toUpperCase(temp);

                while (temp != 'Y' && temp != 'N')
                {
                    System.out.println("Error, that is not a valid input, please try again");
                    System.out.println("Error, password incorrect try again Y/N: ");
                    temp = input.next().charAt(0);
                    temp = Character.toUpperCase(temp);
                }
                if (temp == 'N')
                {
                    System.exit(1);
                }
                input.nextLine();
            }
        } while (!password.equals(playerPassword));
    }                 //end of logIntoAccountPasswordHelper(String[])
//============================================================



//============================================================
// Description: This function takes a name and searches the
//              file for the name to load the corresponding
//              account.
// Input: String name
// Output: account data
//============================================================
    private Player findPlayerByName(String username) {
        fileRead.close();
        try {
            fileRead = new Scanner(file); // Create a new Scanner to reset the pointer
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (fileRead.hasNext()) {
            String[] userData = fileRead.nextLine().split("_");
            if (username.equalsIgnoreCase(userData[0])) {
                return new Player(userData[0], userData[1], userData[2], Integer.parseInt(userData[3]), Long.parseLong(userData[4]));
            }
        }
        return null;
    }                 //end of findPlayerByName(String name)
//============================================================



//============================================================
// Description: This function takes all the data of the file,
//              rewrites any needed data, and finally saves
//              the data back to the file.
// Input: Player player
// Output: new file data saved
//============================================================
    public void rewriteData(Player player) throws IOException {
        output.close();
        fileRead.close();
        fileRead = new Scanner(file);
        fileRead.useDelimiter("_");
        StringBuilder updatedData = new StringBuilder();

        //building string
        while (fileRead.hasNext()) {
            String currentLine = fileRead.nextLine();
            String[] userData = currentLine.split("_");

            if (player.getUsername().equalsIgnoreCase(userData[0]))
            {
                userData[4] = String.valueOf(player.getBalance());

                currentLine = String.join("_", userData);
            }
            updatedData.append(currentLine).append("\n");
        }

        output = new FileWriter(file, false);
        // Write the updated data back to the file with append off
        output.write(updatedData.toString());
        output.close();

        fileRead.close();
        fileRead = new Scanner(file);
        fileRead.useDelimiter("_");
        output = new FileWriter(file, true);
    }                 //end of rewriteData()
//============================================================



//============================================================
// Description: This function closes the file
// Input:
// Output: file closed
//============================================================
    public void closeFile(Player player) throws IOException
    {
        rewriteData(player);
        fileRead.close();
        output.close();
    }                 //end of closeFile()
//============================================================
}
