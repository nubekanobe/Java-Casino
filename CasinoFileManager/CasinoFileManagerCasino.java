package CasinoFileManager;

import Casino.Casino;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;



/*
This class manages the casino data. In this class a casino
is created if one does not exist. Otherwise, the casino data
is loaded. Lastly, this class rewrites the file to save the
new data of the casino.
*/
public class CasinoFileManagerCasino
{
    //variables needed for file operations and for user input
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
    public CasinoFileManagerCasino(String fileName, Scanner scanner) throws Exception
    {
        input = scanner;
        this.fileName = fileName;
        file = new File(fileName);
        fileRead = new Scanner(file);
        fileRead.useDelimiter("_");
        output = new FileWriter(file, true);
    }                 //end of CasinoFileManagerCasino(String, Scanner)
//============================================================



//============================================================
// Description: This function creations a new casino
// Input: casino data (from user)
// Output: new casino data saved to file
//============================================================
    public void createCasino() throws Exception
    {
        long balance;
        float multiplier;
        long balanceMultipliers[] = new long[4];

        System.out.print("Please input the casino's balance: ");
        balance = input.nextLong();

//============================================================
// balance
//============================================================
        while (balance <= 0 || balance >= 1_000_000_000)
        {
            System.out.println("Error, that is not a valid balance.\nPlease try again, the balance must be less than 0 and cannot be greater than 1 billion");
            System.out.print("Please input the casino's balance: ");
            balance = input.nextLong();
        }
//============================================================
// balance
//============================================================



//============================================================
// Multiplier
//============================================================
        System.out.print("Please input the casino's default winnings multiplier (NOTE the suggest lowest is 0.9 and the suggest highest is 1.5 (2 for special events)(NOTE this can be changed later)): ");
        multiplier = input.nextFloat();

        while (multiplier < 0.75 || multiplier > 2.5)
        {
            System.out.println("Error, that is not a valid multiplier.\nPlease try again, the multiplier must be less than 0.75 and cannot be greater than 2.5");
            System.out.print("Please input the casino's default winnings multiplier (NOTE the suggest lowest is 0.9 and the suggest highest is 1.5 (2 for special events)(NOTE this can be changed later)): ");
            multiplier = input.nextFloat();
        }
//============================================================
// Multiplier
//============================================================


//============================================================
// BalanceMultipliers
//============================================================
        double[] multiplierList = {0.85, 0.95, 1, 1.1};
        String[] stringListForMultipliers = {"(the lowest multiplier)", "(the second lowest multiplier)", "(the default multiplier)", "(the highest multiplier)"};

        for (int i = 0; i < multiplierList.length; i++) {
            System.out.print("Please input the casino's balance to trigger a " + multiplierList[i] + " multiplier " +
                    stringListForMultipliers[i] + "(NOTE this can be changed later): ");
            balanceMultipliers[i] = input.nextLong();

            if (i > 0)
            {
                while (balanceMultipliers[i] <= balanceMultipliers[i-1])
                {
                    System.out.println("Error, that is not a valid balance multiplier.\nPlease try again, this balance multiplier must be greater than the previous multiplier balance threshold");
                    System.out.print("Please input the casino's balance to trigger a " + multiplierList[i] + " multiplier " +
                            stringListForMultipliers[i] + "(NOTE this can be changed later): ");
                    balanceMultipliers[i] = input.nextLong();
                }
            }
        }
//============================================================
// BalanceMultipliers
//============================================================

        //writing the data to the file
        output.write(String.valueOf(balance) + '_' + String.valueOf(multiplier) + '_' + String.valueOf(balanceMultipliers[0]) + '_' + String.valueOf(balanceMultipliers[1])
                + '_' + String.valueOf(balanceMultipliers[2]) + '_' + String.valueOf(balanceMultipliers[3]) + '\n');
        output.close();
        System.exit(1);
    }                 //end of createCasino()
//============================================================



//============================================================
// Description: This function load the casino data from a file
// Input:
// Output: casino data returned
//============================================================
    public Casino loadCasino() throws Exception {
        if (!fileRead.hasNext())
        {
            createCasino();
        }
        
        Casino casino = null;

        fileRead.close();
        fileRead = new Scanner(file); // Create a new Scanner to reset the pointer
        String[] userData = fileRead.nextLine().split("_");

        Casino casino1 = new Casino(Long.parseLong(userData[0]), Float.parseFloat(userData[1]), Long.parseLong(userData[2]), Long.parseLong(userData[3])
                , Long.parseLong(userData[4]), Long.parseLong(userData[5]));

        return casino1;
    }                 //end of loadCasino()
//============================================================



//============================================================
// Description: This function rewrites the casino data to the
//              file.
// Input: Casino casino(passed)
// Output: casino data saved
//============================================================
    public void rewriteData(Casino casino) throws IOException {
        output.close();
        fileRead.close();
        fileRead = new Scanner(file);
        fileRead.useDelimiter("_");
        String updatedData = String.valueOf(casino.getBalance()) + '_' + String.valueOf(casino.getMultiplier()) + '_' + String.valueOf(casino.getBalanceMultiplier(0)) + '_'
                + String.valueOf(casino.getBalanceMultiplier(1)) + '_' + String.valueOf(casino.getBalanceMultiplier(2))
                + '_' + String.valueOf(casino.getBalanceMultiplier(3)) + '\n';

        output = new FileWriter(file, false);
        // Write the updated data back to the file
        output.write(updatedData);
        output.close();

        fileRead.close();
        fileRead = new Scanner(fileName);
        fileRead.useDelimiter("_");
        output = new FileWriter(file, true);
    }                 //end of rewriteData(Casino casino)
//============================================================



//============================================================
// Description: This function closes the file
// Input:
// Output: file closed
//============================================================
    public void closeFile(Casino casino) throws IOException
    {
        rewriteData(casino);
        fileRead.close();
        output.close();
    }                 //end of closeFile()
//============================================================
}
