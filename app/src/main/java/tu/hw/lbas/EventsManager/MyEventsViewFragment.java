package tu.hw.lbas.EventsManager;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import tu.hw.lbas.Event;
import tu.hw.lbas.EventsManager.CreateNewEventViewFragment;
import tu.hw.lbas.MainActivity;
import tu.hw.lbas.R;
import tu.hw.lbas.UserManager.User;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyEventsViewFragment extends Fragment {


    FloatingActionButton fabCreateEvent;
    ListView lvMyEvents;
    static ArrayList<Event> myEventsList;
    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_events_view, container, false);
        fabCreateEvent = view.findViewById(R.id.fabCreateEvent);
        lvMyEvents = view.findViewById(R.id.lvMyEvents);
        fabCreateEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.changeFragment(new CreateNewEventViewFragment());
            }
        });
        EventsManager.getMyEvents((User) getActivity().getIntent().getSerializableExtra("user"));
        return view;
    }
    public void setMyEventsList(ArrayList<Event> myEventsList)
    {
        Log.d("ViewMyEvent",myEventsList.toString());
        MyEventsViewFragment.myEventsList = myEventsList;
        MyEventsListAdapter myEventsListAdapter = new MyEventsListAdapter(context,myEventsList);
        lvMyEvents.setAdapter(myEventsListAdapter);

    }
}
