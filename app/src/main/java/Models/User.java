package Models;

import java.util.Objects;

/**
 * Created by Kamil on 2018-02-09.
 */

public class User {

   public String username;
   public String password;
    String email;
    public Double balance;
    Long bets;


    public User(String username, String password, String email, Double balance, Long bets)
    {
        this.username = username;
        this.password = password;
        this.email = email;
        this.balance = balance;
        this.bets = bets;
    }



    public int hashCode()
    {
        return Objects.hash(this.username, this.password, this.email, this.balance,this.bets);
    }


    public boolean equals(final Object obj)
    {
        if (obj instanceof User)
        {
            final User other = (User) obj;
            return Objects.equals(username,  other.username)
                    && Objects.equals(password,  other.password)
                    && Objects.equals(email,  other.email)
                    && Objects.equals(balance,     other.balance)
                    && Objects.equals(bets,  other.bets);

        }
        else
        {
            return false;
        }
    }







}
