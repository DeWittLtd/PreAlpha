package ltd.vblago.prealpha.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ltd.vblago.prealpha.R;
import ltd.vblago.prealpha.model.Compass;
import ltd.vblago.prealpha.model.Point;
import ltd.vblago.prealpha.util.CalcTool;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.savedLocation)
    TextView savedLocationTv;
    @BindView(R.id.currentLocation)
    TextView currentLocationTv;
    @BindView(R.id.distance)
    TextView distanceTv;
    @BindView(R.id.requiredBeaming)
    TextView requiredBeamingTv;
    @BindView(R.id.currentBeaming)
    TextView currentBearingTv;
    @BindView(R.id.notice_background)
    RelativeLayout noticeBackgroundTv;

    Compass compass;
    Point currentPoint;
    Point savedPoint;
    CalcTool calcTool;
    int requiredBearing;
    boolean isVibrate;
    Vibrator vibrator;

    private static final String TAG = "MainActivity";
    private LocationManager locationManager;
    private LocationListener listener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        currentPoint = new Point();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                currentPoint.setLongitude(location.getLongitude());
                currentPoint.setLatitude(location.getLatitude());

                currentLocationTv.setText(currentPoint.getStrParams());

                if (!savedPoint.isEmpty()) {
                    calcTool.setPoint2(currentPoint);
                    requiredBearing = (int) Math.round(calcTool.getBearing());

                    distanceTv.setText(calcTool.getStrDistance());
                    requiredBeamingTv.setText(calcTool.getStrBearing());
                }
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        calcTool = new CalcTool(new Point(0, 0), new Point(0, 0));
        savedPoint = new Point(0, 0);
        setupCompass();
        setupLocation();
        isVibrate = false;
        vibrator = ((Vibrator) getSystemService(VIBRATOR_SERVICE));
    }

    private void setupCompass() {
        compass = new Compass(this);
        Compass.CompassListener cl = new Compass.CompassListener() {

            @Override
            public void onNewAzimuth(float azimuth) {
                setBackgroundColor(Math.round(azimuth));
                String strAz = Math.round(azimuth) + "°";
                currentBearingTv.setText(strAz);
            }
        };
        compass.setListener(cl);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 10:
                setupLocation();
                break;
            default:
                break;
        }
    }

    @OnClick(R.id.saveBtn)
    public void saveLocation() {
        savedPoint = currentPoint.copyPoint();
        calcTool.setPoint1(savedPoint);
        savedLocationTv.setText(savedPoint.getStrParams());
    }

    public void setBackgroundColor(int azimuth) {
        if (!savedPoint.isEmpty()) {
            if (CalcTool.isZone(requiredBearing, azimuth)) {
                noticeBackgroundTv.setBackgroundColor(Color.parseColor("#2e7d32"));
                startVibration();
            } else {
                noticeBackgroundTv.setBackgroundColor(Color.parseColor("#c62828"));
                stopVibration();
            }
        }
    }

    private void setupLocation() {
        // first check for permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.INTERNET}
                        , 10);
            }
        }
        locationManager.requestLocationUpdates("gps", 1000, 0, listener);
    }

    private void startVibration() {
        if (!isVibrate){
            if (Build.VERSION.SDK_INT >= 26) {
                vibrator.vibrate(VibrationEffect.createOneShot(9999999, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                Objects.requireNonNull(vibrator).vibrate(9999999);
            }
        }
        isVibrate = true;
    }

    private void stopVibration(){
        vibrator.cancel();
        isVibrate = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "start compass");
        compass.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        compass.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        compass.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "stop compass");
        compass.stop();
    }
}
