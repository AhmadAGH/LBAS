package tu.hw.lbas.UserManager;

import java.util.Objects;

import tu.hw.lbas.DataManager.DatabaseManager;
import tu.hw.lbas.UserManager.User;

public class UsersManager
{
    public static boolean register(User user)
    {
        String result = DatabaseManager.registerNewUser(user);
        return (Objects.equals(result, "1"));
    }
}
