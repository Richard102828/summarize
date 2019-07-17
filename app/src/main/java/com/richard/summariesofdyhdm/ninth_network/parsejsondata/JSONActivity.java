package com.richard.summariesofdyhdm.ninth_network.parsejsondata;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.richard.summariesofdyhdm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @param
 * @author Richard
 * @time 2019/7/17 9:45
 * @description HttpURlConnection + JsonObject解析数据
 */
public class JSONActivity extends AppCompatActivity {

    @BindView(R.id.btn_json)
    Button   btnJson;
    @BindView(R.id.tv_display_data_json)
    TextView tvDisplayDataJson;

    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json);
        ButterKnife.bind(this);
    }

    private void sendRequsetHttp() {
       new Thread(new Runnable() {
           @Override
           public void run() {
               HttpURLConnection connection = null;
               BufferedReader reader = null;
               try {
                   URL url = new URL("https://gank.io/api/data/%E7%A6%8F%E5%88%A9/10/1");
                   connection = (HttpURLConnection) url.openConnection();
                   connection.setRequestMethod("GET");
                   connection.setConnectTimeout(8000);
                   connection.setReadTimeout(8000);
                   InputStream in = connection.getInputStream();
                   reader = new BufferedReader(new InputStreamReader(in));
                   StringBuilder builder = new StringBuilder();
                   String line;
                   while ((line = reader.readLine()) != null) {
                       builder.append(line);
                   }
                   parseJSONObject(builder.toString());
               } catch (MalformedURLException e) {
                   e.printStackTrace();
               } catch (IOException e) {
                   e.printStackTrace();
               } finally {
                   if (reader != null) {
                       try {
                           reader.close();
                       } catch (IOException e) {
                           e.printStackTrace();
                       }
                   }
                   if (connection != null) {
                       connection.disconnect();
                   }
               }

           }
       }).start();

    }

    private void parseJSONObject(String jsonData) {
        try {
            JSONObject object = new JSONObject(jsonData);
            JSONArray array = object.getJSONArray("results");
            for (int i = 0; i < array.length(); i++) {
                JSONObject object1 = array.getJSONObject(i);
                String imageUrl = object1.getString("url");
                Log.d("JSONActivity",imageUrl);
                list.add(imageUrl);
            }
            if (list != null) {
                upDateUi();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void upDateUi() {
        runOnUiThread(new Runnable() {
            @SuppressLint("SetTextI18n")
            @Override
            public void run() {
                for (int i = 0; i < list.size(); i++) {
                    tvDisplayDataJson.setText(list.get(i) + "\n");
                }
                list.clear();
            }
        });
    }

    @OnClick(R.id.btn_json)
    public void onViewClicked() {
        sendRequsetHttp();
    }

}
