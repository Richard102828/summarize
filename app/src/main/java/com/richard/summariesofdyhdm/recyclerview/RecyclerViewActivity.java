package com.richard.summariesofdyhdm.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.richard.summariesofdyhdm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @param
 * @author Richard
 * @time 2019/7/16 10:50
 * @description RecyclerView的界面
 */
public class RecyclerViewActivity extends AppCompatActivity {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private List<String> imageList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);
        sendRequestWithHttpURLConnection(this, new Runnable() {
            @Override
            public void run() {
                RecyclerViewAdapter adapter = new RecyclerViewAdapter(RecyclerViewActivity.this,imageList);
                StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(3,
                        StaggeredGridLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(adapter);
            }
        });

    }



    //使用HttpURLConnection向服务器发送请求，返回接口数据.调用jsonData方法，将解析后的数据传入；

    private void sendRequestWithHttpURLConnection(final Activity activity, final Runnable runnable){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try {
                    URL url = new URL("https://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1");
                    connection = (HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");      //请求方法；
                    connection.setConnectTimeout(10000);
                    connection.setReadTimeout(10000);
                    InputStream in = connection.getInputStream();
                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder respond = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        respond.append(line);
                    }
                    jsonData(respond.toString());
                    if (activity != null) {
                        activity.runOnUiThread(runnable);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if (reader != null){
                        try {
                            reader.close();      //关闭流；
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null){
                        connection.disconnect();     //断开连接；
                    }
                }
            }
        }).start();      //启动子线程；
    }

    //使用JSON框架解析数据；
    private void jsonData(String respond){
        //     Log.d("MainActivity",respond);
        try {

            JSONObject object = new JSONObject(respond );
            JSONArray array = object.getJSONArray("results");

            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);
                imageList.add(jsonObject.getString("url"));
                imageList.add(jsonObject.getString("url"));
                Log.d("RecyclerViewActivity",jsonObject.getString("url"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
