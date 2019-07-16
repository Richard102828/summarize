package com.richard.summariesofdyhdm.sixth_database;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.richard.summariesofdyhdm.R;
import com.richard.summariesofdyhdm.sixth_database.file_store.FileStoreActivity;
import com.richard.summariesofdyhdm.sixth_database.litepal_store.LitepalStoreActivity;
import com.richard.summariesofdyhdm.sixth_database.sp_store.SpStoreActivity;
import com.richard.summariesofdyhdm.sixth_database.sql_store.SqlStoreActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @param
 * @author Richard
 * @time 2019/7/16 13:30
 * @description 总的第六章节的数据持续化技术
 */
public class TheSixthChapterActivity extends AppCompatActivity {

    @BindView(R.id.btn_file_store)
    Button btnFileStore;
    @BindView(R.id.btn_sp_store)
    Button btnSpStore;
    @BindView(R.id.btn_sql_store)
    Button btnSqlStore;
    @BindView(R.id.btn_lp_store)
    Button btnLpStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_sixth_chapter);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_file_store, R.id.btn_sp_store, R.id.btn_sql_store, R.id.btn_lp_store})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_file_store:
                Intent intent1 = new Intent(this, FileStoreActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_sp_store:
                Intent intent2 = new Intent(this, SpStoreActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_sql_store:
                Intent intent3 = new Intent(this, SqlStoreActivity.class);
                startActivity(intent3);
                break;
            case R.id.btn_lp_store:
                Intent intent4 = new Intent(this, LitepalStoreActivity.class);
                startActivity(intent4);
                break;
        }
    }

}
