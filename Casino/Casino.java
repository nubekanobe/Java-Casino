package Casino;

public class Casino
{
    private long balance;
    private float multiplier;
    private long[] balanceMultipliers = new long[4];


//============================================================
// Description: This is the fully loaded constructor for a
//              casino
// Input: casino data(passed)
// Output: casino data assigned
//============================================================
    public Casino(long balance, float multiplier, long BM1, long BM2, long BM3, long BM4)
    {
        this.balance = balance;
        this.multiplier = multiplier;
        this.balanceMultipliers[0] = BM1;
        this.balanceMultipliers[1] = BM2;
        this.balanceMultipliers[2] = BM3;
        this.balanceMultipliers[3] = BM4;
    }                 //end of Casino(long, float, long, long, long, long)
//============================================================



//============================================================
// Description: This function changes the balance multipliers
// Input: new balance multipliers(passed)
// Output: new balance multipliers assigned
//============================================================
    public void changeBMs(long BM1, long BM2, long BM3, long BM4)
    {
        this.balanceMultipliers[0] = BM1;
        this.balanceMultipliers[1] = BM2;
        this.balanceMultipliers[2] = BM3;
        this.balanceMultipliers[3] = BM4;
    }                 //end of changeBMs(long, long, long, long)
//============================================================



//============================================================
// Description: This function changes the default multiplier
// Input: new multiplier(passed)
// Output: new multiplier assigned
//============================================================
    public void changeMultiplier(float multiplier)
    {
        this.multiplier = multiplier;
    }                 //end of changeMultiplier(float)
//============================================================



//============================================================
// Description: This function adds to the casino's balance
// Input: amount to be added (passed)
// Output: funds added to balance
//============================================================
    public void addToBalance(long balance)
    {
        this.balance += balance;
    }                 //end of addToBalance(long)
//============================================================



//============================================================
// Description: This function takes to the casino's balance
// Input: amount to be taken (passed)
// Output: funds taken from balance
//============================================================
    public void takeFromBalance(long balance)
    {
        this.balance = this.balance - balance;
    }                 //end of takeFromBalance(long)
//============================================================



//============================================================
// Description: the getBalance function
// Input:
// Output: balance returned
//============================================================
    public long getBalance()
    {
        return balance;
    }                 //end of getBalance()
//============================================================



//============================================================
// Description: the getMultiplier function
// Input:
// Output: multiplier returned
//============================================================

    public float getMultiplier() {
        return multiplier;
    }                 //end of getMultiplier()
//============================================================



//============================================================
// Description: the getMultiplier function
// Input: which balance multiplier to return (accepts 0-3)
// Output: balance multiplier returned
//============================================================
    public long getBalanceMultiplier(int i)
    {
        if (i < 0 || i > 4)
        {
            System.out.println("An error has occurred, getBalanceMultiplier must be given a value between 0-3"
            + "\nReturning the multiplier 0");
            return balanceMultipliers[0];
        }
        return balanceMultipliers[i];
    }                 //end of getBalanceMultiplier0()
//============================================================



//============================================================
// Description: the negative function checks to see if
//              balance would be negative if a number is taken
// Input: how much to take(passed)
// Output: true or false depending on if the balance would be
//         negative (true if it would be negative)
//============================================================
    public boolean negative(long temp)
    {
        return (this.balance - temp < 0);
    }                 //end of negative(long)
//============================================================



//============================================================
// Description: This function finds the correct multiplier
//              depending on the balance of the casino.
// Input:
// Output: balance multiplier rate
//============================================================
    public double getBalanceMultiplierRate()
    {
        if (balance <= balanceMultipliers[0])
        {
            return 0.85;
        }
        else if (balance <= balanceMultipliers[1])
        {
            return 0.95;
        }
        else if (balance <= balanceMultipliers[2])
        {
            return 1;
        }
        else
        {
            return 1.1;
        }
    }                 //end of getBalanceMultiplier()
//============================================================



//============================================================
// Description: This function overrides the standard toString
//              function and instead returns a String of the
//              casino's information
// Input:
// Output: returns String of casino's information
//============================================================
    @Override
    public String toString()
    {
        return "Balance:   \t" + balance + "\nMultiplier:    \t" + multiplier + "\nBalanceMultiplier0:\t" + balanceMultipliers[0]
                + "\nBalanceMultiplier1:\t" + balanceMultipliers[1] + "\nBalanceMultiplier2:\t" + balanceMultipliers[2] + "\nBalanceMultiplier3:\t" + balanceMultipliers[3];
    }                 //end of toString()
//============================================================
}
