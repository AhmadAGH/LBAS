package tu.hw.lbas.EventsManager;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.ArrayList;

import tu.hw.lbas.Event;
import tu.hw.lbas.MainActivity;
import tu.hw.lbas.MapsActivity;
import tu.hw.lbas.R;
import tu.hw.lbas.UserManager.User;

public class CreateNewEventViewFragment extends Fragment implements OnClickListener
{
    EditText etEventName,etStartTime,etEndTime;
    Button btnLocation,btnCreateEvent;
    TimePickerDialog timePickerDialog;
    Context context;
    float locationAreaRad;
    double locationAreaCenterLat,locationAreaCenterLng;
    User user;
    CheckBox cbSun,cbMon,cbTue,cbWed,cbThu,cbFri,cbSat;
    EventsManager eventsManager;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_create_new_event_view, container, false);
        etEventName = view.findViewById(R.id.etEventName);
        etStartTime = view.findViewById(R.id.etStartTime);
        etEndTime = view.findViewById(R.id.etEndTime);
        btnLocation = view.findViewById(R.id.btnLocation);
        btnCreateEvent = view.findViewById(R.id.btnCreateEvent);

        etStartTime.setOnClickListener(this);
        etEndTime.setOnClickListener(this);
        btnLocation.setOnClickListener(this);
        btnCreateEvent.setOnClickListener(this);
        cbFri = view.findViewById(R.id.cbFri);
        cbSat = view.findViewById(R.id.cbSat);
        cbSun = view.findViewById(R.id.cbSun);
        cbMon = view.findViewById(R.id.cbMon);
        cbTue = view.findViewById(R.id.cbTue);
        cbWed = view.findViewById(R.id.cbWed);
        cbThu = view.findViewById(R.id.cbThu);

        user =(User) getActivity().getIntent().getSerializableExtra("user");

        eventsManager = new EventsManager(context);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.etStartTime:
                seletTime(v);
                break;
            case R.id.etEndTime:
                seletTime(v);
                break;
            case R.id.btnLocation:
                btnCreateEvent.setEnabled(true);
                startActivity(new Intent(context, MapsActivity.class));
                break;
            case R.id.btnCreateEvent:
                attmeptSaveEvent();
        }
    }

    private void attmeptSaveEvent()
    {
        String eventName = etEventName.getText().toString();
        ArrayList<String> eventDays = new ArrayList<>();
        if(cbFri.isChecked()) eventDays.add(cbFri.getText().toString());
        if(cbSat.isChecked()) eventDays.add(cbSat.getText().toString());
        if(cbSun.isChecked()) eventDays.add(cbSun.getText().toString());
        if(cbMon.isChecked()) eventDays.add(cbMon.getText().toString());
        if(cbTue.isChecked()) eventDays.add(cbTue.getText().toString());
        if(cbWed.isChecked()) eventDays.add(cbWed.getText().toString());
        if(cbThu.isChecked()) eventDays.add(cbThu.getText().toString());
        locationAreaRad = MapsActivity.bundle.getFloat("areaRad");
        locationAreaCenterLat = MapsActivity.bundle.getDouble("areaCenterLat");
        locationAreaCenterLng = MapsActivity.bundle.getDouble("areaCenterLng");
         if(isDataValied(eventDays,eventName,startTime,endTime))
         {
             Event event = new Event(user,eventName,locationAreaCenterLat,locationAreaCenterLng,locationAreaRad,
                     startTime,endTime,eventDays);
             eventsManager.createNewEvent(event);
         }else
         {
             Toast.makeText(context, "Failed, Please Check Your Inputs !!", Toast.LENGTH_SHORT).show();
         }

    }

    private boolean isDataValied(ArrayList<String> eventDays, String eventName, Time startTime, Time endTime)
    {
        if(eventDays.isEmpty() || eventName.isEmpty() || startTime == null || endTime == null)
        {
            return false;
        }
        return true;
    }
    Time startTime,endTime;
    private void seletTime(final View v)
    {
        timePickerDialog = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) { ;
            if(v.getId() == R.id.etStartTime)
            {
                startTime = new Time(hourOfDay,minute,0);
            }else
            {
                endTime = new Time(hourOfDay,minute,0);
            }
                ((EditText) v).setText(hourOfDay+":"+minute);
            }
        },24,59,true);
        timePickerDialog.show();
    }
    public static void afterAddingNewEvent(boolean success,Context context)
    {
        if(success) {
            Toast.makeText(context, "Done, Created New Event Successfully", Toast.LENGTH_SHORT).show();
            MainActivity.changeFragment(new MyEventsViewFragment());
        }else
        {
            Toast.makeText(context, "Fail, There was an error", Toast.LENGTH_SHORT).show();
        }
    }
}
