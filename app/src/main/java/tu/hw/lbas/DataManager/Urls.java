package tu.hw.lbas.DataManager;

import android.util.Log;

import java.sql.Time;
import java.util.ArrayList;

import tu.hw.lbas.Event;
import tu.hw.lbas.UserManager.User;

public class Urls
{
    final static String ROOT_URL = "http://itrips.hostingerapp.com/";
    static String loginFilePath = "LBAS/Login.php";
    static String getDataFilePath = "LBAS/getDataFromDB.php";
    static String insertDataFilePath = "LBAS/InsertDataToDB.php";
    static String connectionFilePath = "LBAS/ConnectToDB.php";
    static String registerFilePath = "LBAS/Register.php";
    static String createNewEventFilePath="LBAS/CreateNewEvent.php";
    static String addDayToEventFilePath="LBAS/AddDayToEvent.php";
    static String getMyEventsFilePath = "LBAS/GetMyEvents.php";
    public static String getLoginURL(String email,String password)
    {
        return ROOT_URL+loginFilePath+"?email="+email+"&password="+password;
    }
    public static String getWhatsappURL(String number)
    {
        return "https://api.whatsapp.com/send?phone="+number.replace("+","");
    }
    public static String getRegisterURL(String name,String email,String password,String companyName)
    {
        return ROOT_URL + registerFilePath+"?name="+name+"&password="+password+"&email="+email
                +"&cName="+companyName;
    }
    public static String getCreateNewEventUrl (String userID, String eventName, String lat, String lng, String rad, String startTime, String endTime)
    {
        String createNewEventUrl = ROOT_URL + createNewEventFilePath+"?" +
        "userID="+userID+
        "&eventName="+eventName+
        "&eventStartTime="+startTime.toString()+
        "&eventEndTime="+endTime.toString()+
        "&eventLocationLng="+lng+
        "&eventLocationLat="+lat+
        "&eventLocationRad="+rad;
        return createNewEventUrl;
    }
    public static String getAddDayToEventUrl(String dayName, String eventID)
    {
        return (ROOT_URL + addDayToEventFilePath + "?dayName="+dayName+"&eventID="+eventID);
    }
    public static String getMyEventsUrl(String userID)
    {
        Log.d("geyMyEventsUrl",ROOT_URL + getMyEventsFilePath + "?userID="+userID);
        return ROOT_URL + getMyEventsFilePath + "?userID="+userID;
    }


}
