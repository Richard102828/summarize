package com.richard.summariesofdyhdm.eleventh_location;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.richard.summariesofdyhdm.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BDLocationActivity extends AppCompatActivity {

    @BindView(R.id.position_text_view)
    TextView positionTextView;

    @BindView(R.id.bmapView)
    MapView  bmapView;


    private LocationClient client;
    private BaiduMap baiduMap;      //地图的总控制器；
    private boolean isFirstLocate = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//在setContentView(R.layout.activity_main)方法之前初始化
        SDKInitializer.initialize(getApplicationContext());

        setContentView(R.layout.activity_bdlocation);
        ButterKnife.bind(this);
//获取LocationClient实例；
        client = new LocationClient(getApplicationContext());
        client.registerLocationListener(new MyLocationListener());

//用一个集合装3个权限；
        List<String> permissionList = new ArrayList<>();
//如果没有进行运行时权限操作，就加到集合中；
        if (ContextCompat.checkSelfPermission(BDLocationActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (ContextCompat.checkSelfPermission(BDLocationActivity.this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (ContextCompat.checkSelfPermission(BDLocationActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
//如果集合里面非空，就转化为数组，开始申请权限；
            String[] permissons = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(BDLocationActivity.this, permissons, 1);
        } else {
            requsteLocation();
        }
//实例化BaiMap：
        baiduMap = bmapView.getMap();
        baiduMap.setMyLocationEnabled(true);    //启用显示"我"的位置的功能；
    }

    @Override
    protected void onResume() {
        super.onResume();
        bmapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        bmapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.stop();      //活动销毁时，停止定位；
        bmapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);   //关闭显示我位置的功能；
    }

    //开始定位：(5秒更新一次定位)
    private void requsteLocation() {
        initLocation();
        client.start();
    }

    //对返回的结构进行处理；
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            Toast.makeText(this, "必须同意所有权限才能使用本程序", Toast.LENGTH_SHORT).show();
                            finish();   //将程序关闭；
                            return;
                        }
                    }
                    requsteLocation();
                } else {
                    Toast.makeText(this, "发生未知错误", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    //上面的定位操作会将结果回调到这个我们注册的监听器中；
    public class MyLocationListener extends BDAbstractLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    StringBuilder builder = new StringBuilder();
                    builder.append("维度：").append(bdLocation.getLatitude()).append("\n");
                    builder.append("经度：").append(bdLocation.getLongitude()).append("\n");
                    builder.append("国家：").append(bdLocation.getCountry()).append("\n");
                    builder.append("省：").append(bdLocation.getProvince()).append("\n");
                    builder.append("市：").append(bdLocation.getCity()).append("\n");
                    builder.append("区：").append(bdLocation.getDistrict()).append("\n");
                    builder.append("街道：").append(bdLocation.getStreet()).append("\n");
                    builder.append("定位方式：");
                    if (bdLocation.getLocType() == BDLocation.TypeGpsLocation) {
                        builder.append("GPS");
                    } else if (bdLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
                        builder.append("网络");
                    }
                    positionTextView.setText(builder);
                }
            });
            if (bdLocation.getLocType() == BDLocation.TypeGpsLocation
            || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
                navigateTo(bdLocation);
            }
        }
    }

    //更新当前的位置；
    private void initLocation() {
        LocationClientOption locationClientOption = new LocationClientOption();
        locationClientOption.setScanSpan(1000);     //设置更新位置的间隔；
        locationClientOption.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);     //传感器模式(只使用GPS定位)；
        locationClientOption.setIsNeedAddress(true);        //设置可以获取当前位置的详细信息；
        client.setLocOption(locationClientOption);
    }

    //显示当前位置；
    private void navigateTo(BDLocation location){
        if (isFirstLocate){
            LatLng lng = new LatLng(location.getLatitude(),location.getLongitude());    //存放当前的纬度、经度；
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(lng);
            baiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16);     //设置缩放精度；
            baiduMap.animateMapStatus(update);      //更新地图状态；
            isFirstLocate = false;
        }
//将我显示在地图上；
        MyLocationData.Builder builder = new MyLocationData.Builder();
        builder.latitude(location.getLatitude());
        builder.longitude(location.getLongitude());
        MyLocationData myLocationData = builder.build();
        baiduMap.setMyLocationData(myLocationData);
    }
}


