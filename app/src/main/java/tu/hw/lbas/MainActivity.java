package tu.hw.lbas;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Button;

import tu.hw.lbas.EventsManager.CreateNewEventViewFragment;
import tu.hw.lbas.EventsManager.MyEventsViewFragment;

public class MainActivity extends AppCompatActivity {

    Button btnAddLocation;
    Intent intent;
    static FragmentManager fragmentManager;
    public static CreateNewEventViewFragment createNewEventViewFragment;
    public static MyEventsViewFragment myEventsViewFragment;
    MyDatesViewFragment myDatesViewFragment;
    static BottomNavigationView navigation;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nMyEvents:
                    changeFragment(myEventsViewFragment);
                    return true;
                case R.id.nMyDates:
                    changeFragment(myDatesViewFragment);
                    return true;
                case R.id.nInvitations:

                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        createNewEventViewFragment = new CreateNewEventViewFragment();
        myEventsViewFragment = new MyEventsViewFragment();
        myDatesViewFragment = new MyDatesViewFragment();
        changeFragment(myEventsViewFragment);
    }

    public static void changeFragment(Fragment fragment)
    {
        fragmentManager.beginTransaction().replace(R.id.flFragmentHolder, fragment, null).
                addToBackStack(null).
                commit();
    }
    public static void changeFragmentBack()
    {
//        fragmentManager.beginTransaction().replace(R.id.flFragmentHolder, fragment, null).
//                addToBackStack(null).
//                commit();
        fragmentManager.popBackStack();
    }

}
