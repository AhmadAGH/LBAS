package tu.hw.lbas.DataManager;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import tu.hw.lbas.Event;
import tu.hw.lbas.UserManager.User;

public class DatabaseManager {
    public static String registerNewUser(User user) {
        try {
            String name = URLEncoder.encode(user.getName(), "utf-8");
            String email = URLEncoder.encode(user.getEmail(), "utf-8");
            String password = URLEncoder.encode(user.getPassword(), "utf-8");
            String companyName = URLEncoder.encode(user.getCompanyName(), "utf-8");
            String regUrl = Urls.getRegisterURL(name, email, password, companyName);
            Log.d("User", regUrl);
            URL url = new URL(regUrl);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            return (bufferedReader.readLine());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Log.d("User", user.toString());
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            Log.d("User", user.toString());
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("User", user.toString());
            return null;
        }
    }

    public static User login(String email, String password)
    {
        try
        {
            String loginUrl = Urls.getLoginURL(URLEncoder.encode(email,"utf-8"),URLEncoder.encode(password,"utf-8"));
            URL url = new URL(loginUrl);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String jsonUser = bufferedReader.readLine();
            //Log.d("LoginDataManager",jsonUser);
            if(jsonUser==null)
            {
                return null;
            }else
            {
                try
                {
                    JSONObject jsonObjectUser = new JSONObject(jsonUser);
                    User user = new User
                                    (jsonObjectUser.getString("Name")
                                    ,jsonObjectUser.getString("password")
                                    ,jsonObjectUser.getString("email")
                                    ,jsonObjectUser.getString("companyName")
                                    ,jsonObjectUser.getInt("userID")
                            );
                    return user;
                } catch (JSONException e) {
                    e.printStackTrace();
                    return null;
                }
            }

        } catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
//    private static String addToDB(String query) {
//        try {
//            String insertProfileUrl = Urls.getInsertDataUrl(URLEncoder.encode(query, "utf-8"));
//            URL url = new URL(insertProfileUrl);
//            URLConnection urlConnection = url.openConnection();
//            InputStream inputStream = urlConnection.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            return (bufferedReader.readLine());
//
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//            return null;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
    public static int addNewEventToDatabase(Event event)
    {
        try {
            String userID = (URLEncoder.encode(event.getUser().getUserID() + "", "utf-8"));
            String eventName = (URLEncoder.encode(event.getEventName(), "utf-8"));
            String startTime =(URLEncoder.encode(event.getStartTime().toString(), "utf-8"));
            String endTime = (URLEncoder.encode(event.getEndTime().toString(), "utf-8"));
            String lng = (URLEncoder.encode(event.getLng() + "", "utf-8"));
            String lat = (URLEncoder.encode(event.getLat() + "", "utf-8"));
            String rad = (URLEncoder.encode(event.getRad() + "", "utf-8"));
            String createNewEventUrl = Urls.getCreateNewEventUrl(userID, eventName, lat, lng, rad, startTime, endTime);
            URL url = new URL(createNewEventUrl);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String result = (bufferedReader.readLine());
            Log.d("CreateNewEventDatabase",result) ;
            JSONObject jsonObjectEventID = new JSONObject(result);
            Log.d("CreateNewEventDatabase",jsonObjectEventID.getInt("MAX(eventID)")+" Is The ID") ;
            return jsonObjectEventID.getInt("MAX(eventID)");

        }catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return -1;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return -1;
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        } catch (JSONException e) {
            e.printStackTrace();
            return -1;
        }
    }

    public static boolean addDayToEvent(String dayName,int eventID)
    {
        try {
            String dayNameEnc = (URLEncoder.encode(dayName, "utf-8"));
            String eventIDEnc = (URLEncoder.encode(eventID +"", "utf-8"));
            String addDayToEventUrl = Urls.getAddDayToEventUrl(dayNameEnc,eventIDEnc);
            URL url = new URL(addDayToEventUrl);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String result = (bufferedReader.readLine());
            Log.d("CreaEventDatabaseAddDay",Boolean.parseBoolean(result)+"");
            return Boolean.parseBoolean(result);
        }catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
            return false;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ArrayList<Event> getMyEvents(User user)
    {
        try {
            String getMyEventsUrl = Urls.getMyEventsUrl(URLEncoder.encode(user.getUserID() + "", "utf-8"));
            URL url = new URL(getMyEventsUrl);
            URLConnection urlConnection = url.openConnection();
            InputStream inputStream = urlConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            ArrayList<Event> myEventsList = new ArrayList<>();
            String line;
            while ((line = bufferedReader.readLine())!=null)
            {
                JSONArray jsonArray= new JSONArray(line);
                Log.d("JSON",line);
                for(int i = 0;i<jsonArray.length();i++)
                {
                    JSONObject jsonObjectEvent = (JSONObject) jsonArray.get(i);
                    Event event = new Event(
                            jsonObjectEvent.getInt("eventID"),
                            user,
                            jsonObjectEvent.getString("eventName"),
                            jsonObjectEvent.getDouble("eventLocationLat"),
                            jsonObjectEvent.getDouble("eventLocationLng"),
                            jsonObjectEvent.getDouble("eventLocationRad"),
                            Time.valueOf(jsonObjectEvent.getString("eventStartTime")),
                            Time.valueOf(jsonObjectEvent.getString("eventStartTime"))
                    );
                    myEventsList.add(event);
                }
            }
            return myEventsList;

        } catch (UnsupportedEncodingException e){
            e.printStackTrace();
            return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
    /*
    public static String (User) {}*/
