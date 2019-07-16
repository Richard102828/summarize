package com.richard.summariesofdyhdm.main;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.richard.summariesofdyhdm.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @param
 * @author Richard
 * @time 2019/7/16 10:10
 * @description 一个listview，每个item设置点击事件，跳转到对应的页面
 */
public class MainActivity extends AppCompatActivity {

    //数据源
    String[] data = {"RecyclerView", "DataBase", "ContentProvider", "Multimedia", "Network",
            "Service", "Location", "MaterialDesign", "Context", "Advertisement"};

    @BindView(R.id.list_view)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        DataAdapter adapter = new DataAdapter(this,data);
        listView.setAdapter(adapter);
    }
}
