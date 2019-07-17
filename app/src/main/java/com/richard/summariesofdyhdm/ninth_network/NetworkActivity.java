package com.richard.summariesofdyhdm.ninth_network;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.richard.summariesofdyhdm.R;
import com.richard.summariesofdyhdm.ninth_network.parsejsondata.GsonActivity;
import com.richard.summariesofdyhdm.ninth_network.parsejsondata.JSONActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @param
 * @author Richard
 * @time 2019/7/17 9:11
 * @description 网络请求技术页面
 */
public class NetworkActivity extends AppCompatActivity {


    @BindView(R.id.btn_web_view)
    Button btnWebView;
    @BindView(R.id.btn_json_http)
    Button btnJsonHttp;
    @BindView(R.id.btn_gson_okhttp)
    Button btnGsonOkhttp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_network);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_web_view, R.id.btn_json_http, R.id.btn_gson_okhttp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_web_view:
                Intent intent = new Intent(NetworkActivity.this,
                        WebViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_json_http:
                Intent intent1 = new Intent(NetworkActivity.this,
                        JSONActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_gson_okhttp:
                Intent intent2 = new Intent(NetworkActivity.this,
                        GsonActivity.class);
                startActivity(intent2);
                break;
        }
    }

}
