package Player;

public class Player
{
    private String username;
    private String name;
    private String password;
    private int age;
    private long balance;

//============================================================
// Description: This is the fully loaded constructor for a
//              player
// Input: player data(passed)
// Output: player data assigned
//============================================================
    public Player(String username, String name, String password, int age, long balance)
    {
        this.username = username;
        this.name = name;
        this.password = password;
        this.age = age;
        this.balance = balance;
    }                 //end of Player(String, String, int, long)
//============================================================



//============================================================
// Description: getUsername function
// Input:
// Output: returns username
//============================================================
    public String getUsername()
    {
        return username;
    }                 //end of getUsername()
//============================================================



//============================================================
// Description: getName function
// Input:
// Output: returns name
//============================================================
    public String getName() {
        return name;
    }                 //end of getName()
//============================================================



//============================================================
// Description: getPassword function
// Input:
// Output: returns password
//============================================================
    public String getPassword()
    {
        return password;
    }                 //end of getPassword()
//============================================================



//============================================================
// Description: getAge function
// Input:
// Output: returns age
//============================================================
    public int getAge() {
        return age;
    }                 //end of getAge()
//============================================================



//============================================================
// Description: getBalance function
// Input:
// Output: returns balance
//============================================================
    public long getBalance() {
        return balance;
    }                 //end of getBalance()
//============================================================



//============================================================
// Description: checkPassword function
// Input:
// Output: true or false depending on if the passwords are the
//         same
//============================================================
    public boolean checkPassword(String password)
    {
        return (this.password.equals(password));
    }                 //end of checkPassword(String)
//============================================================



//============================================================
// Description: checks to make sure that the player has
//              enough money and then subtracts it from their
//              account if they do.
// Input: money to take
// Output: true or false depending on if there is enough money.
//         If there is, the money is taken from their account.
//============================================================
    public boolean spend(long cost)
    {
        if (balance - cost < 0)
        {
            return false;
        }
        else
        {
            balance -= cost;
            return true;
        }
    }                 //end of spend(long)
//============================================================



//============================================================
// Description: checks to make sure that the player has
//              enough money to withdraw the amount and then
//              subtracts it from their account if they do.
// Input: money to withdraw
// Output: true or false depending on if there is enough money.
//         If there is, the money is taken from their account.
//============================================================
    public boolean withdraw(long amount)
    {
        if (balance - amount < 0)
        {
            return false;
        }
        else
        {
            balance -= amount;
            return true;
        }
    }                 //end of withdraw(long)
//============================================================



//============================================================
// Description: This function adds funds to the player's
//              balance.
// Input: money to add
// Output: true or false depending on if the money was
//         successfully added (please note that this always
//         returns true as there currently is no proper system
//         to accept payment be if from credit card, check, etc.)
//         The funds are then added to the account
//============================================================
    public boolean addFunds(long funds)
    {
        balance += funds;
        return true;
    }                 //end of addFunds(long)
//============================================================



//============================================================
// Description: This function overrides the standard toString
//              function and instead returns a String of the
//              user's information (not including the password)
// Input:
// Output: returns String of user's information
//============================================================
    @Override
    public String toString()
    {
        return "Name:   \t" + name + "\nage:    \t" + age + "\nbalance:\t$" + balance;
    }                 //end of toString()
//============================================================
}
