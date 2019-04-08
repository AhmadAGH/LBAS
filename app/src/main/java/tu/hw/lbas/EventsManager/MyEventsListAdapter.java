package tu.hw.lbas.EventsManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tu.hw.lbas.Event;
import tu.hw.lbas.R;

public class MyEventsListAdapter extends ArrayAdapter<Event>
{
    Context context;
    List<Event> myEvents;
    public MyEventsListAdapter(Context context, List<Event> myEvents)
    {
        super(context,R.layout.my_events_list,myEvents);
        this.context = context;
        this.myEvents = myEvents;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent)
    {
        @SuppressLint("ViewHolder") View view = LayoutInflater.from(context).inflate(R.layout.my_events_list,null);
        EditText etEventNameL = view.findViewById(R.id.etEventNameL);
        EditText etEventStartTimeL = view.findViewById(R.id.etEventStartTimeL);
        EditText etEventEndTimeL = view.findViewById(R.id.etEventEndTimeL);
        EditText etEventDays = view.findViewById(R.id.etEventNameL);

        etEventNameL.setText(myEvents.get(i).getEventName());
        etEventStartTimeL.setText(myEvents.get(i).getStartTime().toString());
        etEventEndTimeL.setText(myEvents.get(i).getEndTime().toString());
/*
        for(String day: myEvents.get(i).getEventDays())
        {
            etEventDays.setText(etEventDays.getText()+" "+day);
        }
        */
        return view;
    }
}
