package tu.hw.lbas;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Cap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.JointType;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{

    private GoogleMap map;
    Button btnSave, btnClear;
    CircleOptions area;
    SeekBar sbRad;
    int areaRad;
    TextView tvCurrentRad;
     LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        btnSave = findViewById(R.id.btnSave);
        btnClear = findViewById(R.id.btnClear);
        sbRad = findViewById(R.id.sBRad);
        tvCurrentRad = findViewById(R.id.tvCurrentRad);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveLocation();
            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();
                sbRad.setEnabled(false);
            }
        });

        areaRad = 500;
        sbRad.setProgress(areaRad);
        tvCurrentRad.setText("Current Circle Raduis is: " + areaRad + " m");
        if (area == null) {
            sbRad.setEnabled(false);
        }
        sbRad.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tvCurrentRad.setText("Current Circle Raduis is:" + progress + "m");
                areaRad = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                LatLng loc = area.getCenter();
                map.clear();
                createCircle(areaRad, loc);
            }
        });

        if ((ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)) {
            Toast.makeText(getApplicationContext(), "Need Permition", Toast.LENGTH_SHORT).show();
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},1);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},2);

        }
    }
    public static Bundle bundle;
    private void saveLocation()
    {
        bundle = new Bundle();
        bundle.putFloat("areaRad",areaRad);
        bundle.putDouble("areaCenterLat",area.getCenter().latitude);
        bundle.putDouble("areaCenterLng",area.getCenter().longitude);
        this.onBackPressed();
    }

    private void createCircle(double radius, LatLng latLng) {
        area = new CircleOptions().center(latLng);
        area.radius(radius);
        area.strokeColor(Color.GREEN);
        map.addCircle(area);
        map.addMarker(new MarkerOptions().position(latLng));
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        map.setMyLocationEnabled(true);
        map.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener()
        {
            @Override
            public void onMapLongClick(LatLng latLng)
            {
                map.clear();
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).draggable(true);
                map.addMarker(markerOptions);
                createCircle(areaRad,latLng);
                sbRad.setEnabled(true);
                Toast.makeText(getApplicationContext(), "Added", Toast.LENGTH_SHORT).show();
            }
        });
        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                marker.remove();
                map.clear();
                sbRad.setEnabled(false);
                return true;
            }
        });

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,2000,5,locationListener);


        Toast.makeText(this, "Map is ready", Toast.LENGTH_SHORT).show();
    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {

            if(area !=null) {
                float[] distance = new float[2];
                Location.distanceBetween(area.getCenter().latitude, area.getCenter().longitude, location.getLatitude(), location.getLongitude(), distance);
                if (distance[0] > areaRad) {
                    Toast.makeText(MapsActivity.this, "You are Out" + distance[0], Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MapsActivity.this, "You are in" + distance[0], Toast.LENGTH_SHORT).show();
                }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}
