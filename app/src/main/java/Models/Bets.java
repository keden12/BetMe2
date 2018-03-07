package Models;

import java.util.Objects;

/**
 * Created by Kamil on 2018-02-10.
 */

public class Bets {


    public static Long counter = 0l;

        public long id;
        String condition;
        String place;
        long hours;
        double amount;


        public Bets(String condition, String place,long hours, double amount)
        {
            this.id = counter++;
            this.condition = condition;
            this.place = place;
            this.hours = hours;
            this.amount = amount;

        }

        public double getAmount()
        {

            amount = amount * 2;
            return amount;

        }
    public int hashCode()
    {
        return Objects.hash(this.condition, this.place,this.hours, this.amount);
    }

    public boolean equals(final Object obj)
    {
        if (obj instanceof Bets)
        {
            final Bets other = (Bets) obj;
            return Objects.equals(condition,  other.condition)
                    && Objects.equals(place,  other.place)
                    && Objects.equals(hours,  other.hours)
                    && Objects.equals(amount,     other.amount);

        }
        else
        {
            return false;
        }
    }





}
