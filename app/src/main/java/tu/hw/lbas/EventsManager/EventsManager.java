package tu.hw.lbas.EventsManager;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import tu.hw.lbas.DataManager.DatabaseManager;
import tu.hw.lbas.Event;
import tu.hw.lbas.MainActivity;
import tu.hw.lbas.UserManager.User;

public class EventsManager
{
    static Event newEvent;
    static ArrayList<Event> myEventsList;
    private static Context context;
    public EventsManager(Context context)
    {
        this.context = context;
    }

    public static void getMyEvents(User user)
    {
        GetMyEventsTask getMyEventsTask = new GetMyEventsTask();
        getMyEventsTask.execute(user);
    }

    public void createNewEvent(Event event)
    {
        newEvent = event;
        CreateNewEventTask createNewEventTask = new CreateNewEventTask();
        createNewEventTask.execute(event);
    }

    public static Context getContext() {
        return context;
    }
}


class CreateNewEventTask extends AsyncTask<Event,Void,Integer>
{

    @Override
    protected Integer doInBackground(Event... events)
    {
        return DatabaseManager.addNewEventToDatabase(events[0]);
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer>=0) {
            EventsManager.newEvent.setEventID(integer);
            new AddDaysToEventTask().execute(EventsManager.newEvent);
        }
    }
}
class AddDaysToEventTask extends AsyncTask<Event,Void,Boolean>
{

    @Override
    protected Boolean doInBackground(Event... event) {

        for(String day:event[0].getEventDays())
        {
            Log.d("Days",day);
        }
        for(String day:event[0].getEventDays())
        {
            if(DatabaseManager.addDayToEvent(day,event[0].getEventID()))
            {
                return false;
            }
        }
        return true;
    }

    @Override
    protected void onPostExecute(Boolean success) {
        super.onPostExecute(success);
        CreateNewEventViewFragment.afterAddingNewEvent(success,EventsManager.getContext());
    }
}
class GetMyEventsTask extends AsyncTask<User,Void,ArrayList<Event>>
{

    @Override
    protected ArrayList<Event> doInBackground(User... users)
    {
        Log.d("EventManagerMyEvent",users[0].toString());
        return DatabaseManager.getMyEvents(users[0]);
    }

    @Override
    protected void onPostExecute(ArrayList<Event> events) {
        super.onPostExecute(events);
        Log.d("EventManagerMyEvent",events.toString());
       MainActivity.myEventsViewFragment.setMyEventsList(events);
    }
}
