package com.zhoujian.gps;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;


/**





 LocationManager.PASSIVE_PROVIDER
 LocationManager.GPS_PROVIDER;
 LocationManager.NETWORK_PROVIDER;



 */

public class MainActivity extends Activity
{
    private List<String> mProviders;
    private TextView content;
    private ListView listview;
    private Location mLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        //getAllProviders();
        //criteriaProviders();
        getLocation();
    }

    private void getLocation()
    {
        //获取系统的LocationManager对象
        final LocationManager mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try
        {
            mLocation = mLocationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            updateLocation(mLocation);
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, new LocationListener() {
                @Override
                public void onLocationChanged(Location location)
                {
                    updateLocation(location);
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras)
                {
                }
                @Override
                public void onProviderEnabled(String provider)
                {
                    try
                    {
                        updateLocation(mLocationManager.getLastKnownLocation(provider));
                    }
                    catch (SecurityException e)
                    {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onProviderDisabled(String provider)
                {

                }
            });
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }
    }







    private void updateLocation(Location location)
    {
        if (location != null)
        {
            StringBuffer sb = new StringBuffer();
            sb.append("位置信息：\n");
            sb.append("经度： ");
            sb.append(location.getLongitude());
            sb.append("\n纬度： ");
            sb.append(location.getLatitude());
            sb.append("\n高度： ");
            sb.append(location.getAltitude());
            sb.append("\n速度： ");
            sb.append(location.getSpeed());
            sb.append("\n方向： ");
            sb.append(location.getBearing());
            content.setText(sb.toString());
        }
    }


    private void criteriaProviders()
    {

        //获取系统的LocationManager对象
        LocationManager mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        //创建过滤条件Criteria
        Criteria mCriteria = new Criteria();

        //设置要求LocationProvider必须是免费的
        mCriteria.setCostAllowed(false);

        //设置要求LocationProvider能提供高度信息
        mCriteria.setAltitudeRequired(true);

        //设置要求LocationProvider能提供方向信息
        mCriteria.setBearingRequired(true);

        //获取符合要求的LocationProvider
        List<String> providers =  mLocationManager.getProviders(mCriteria,false);
        //创建Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,providers);
        listview.setAdapter(adapter);


    }

    private void getAllProviders()
    {

        //获取系统的LocationManager对象
        LocationManager mLocationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //获取系统所有的LocationProvider
        mProviders = mLocationManager.getAllProviders();
        //创建Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,mProviders);
        listview.setAdapter(adapter);
        // LocationProvider locationProvider= mLocationManager.getProvider(LocationManager.GPS_PROVIDER);
    }

    private void initView()
    {
        listview =(ListView)findViewById(R.id.listview);
        content=(TextView)findViewById(R.id.content);

    }
}
