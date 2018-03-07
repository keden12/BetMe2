package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Models.Bets;
import Models.User;
import Utils.Serializer;
import app.betme.betme.MainActivity;
import app.betme.betme.R;

/**
 * Created by Kamil on 2018-02-09.
 */

public class BetMeAPI {

private static Serializer serializer;
private static Map<String, User> users = new HashMap<>();
private static Map<Long, Bets> bets = new HashMap<>();

public BetMeAPI(Serializer serializer)
{
    this.serializer = serializer;
}

public static String PACKAGE_NAME;

public User addUser(String username, String password, String email, Double balance, Long bets)
{
    User user = new User(username,password,email,balance,Long.valueOf(0));
    users.put(user.username,user);

    return user;

}

public User getUserByUsername(String username)
{
    return users.get(username);
}

//Login check
public static boolean authenticate(String username, String password)
    {

        if (users.containsKey(username))
        {
            User user = users.get(username);
            if (user.password.matches(password))
            {
                return true;
            }
        }
        return false;
    }

    public void load() throws Exception
    {
        serializer.read();
        users = (Map<String, User>) serializer.pop();
        bets  = (Map<Long, Bets>) serializer.pop();

        //ID bug fix
        Bets.counter = (Long)serializer.pop();
    }


    public static void store() throws Exception
    {
//	serializer.push(AdminLogin);
        serializer.push(Bets.counter);
        serializer.push(users);
        serializer.push(bets);
        serializer.write();

    }


    public void InitialLoad() throws Exception {

        MainActivity load = new MainActivity();
        List<User> usersdata = load.loadUsers(R.raw.users);
        for(User user: usersdata)
        {
            users.put(user.username,user);
        }

        List<Bets> betsdata = load.loadBets(R.raw.bets);
        for (Bets betting : betsdata) {
            bets.put(betting.id, betting);
        }




    }


}
