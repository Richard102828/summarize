package com.richard.summariesofdyhdm.sixth_database.sp_store;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.richard.summariesofdyhdm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @param
 * @author Richard
 * @time 2019/7/16 14:29
 * @description SharedPreferences存储页面
 */
public class SpStoreActivity extends AppCompatActivity {

    @BindView(R.id.account)
    EditText account;
    @BindView(R.id.password)
    EditText password;
    @BindView(R.id.remember_pass)
    CheckBox rememberPass;
    @BindView(R.id.login)
    Button   login;

    //SP变量
    private SharedPreferences.Editor editor;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sp_store);
        ButterKnife.bind(this);
        preferences =  getSharedPreferences("data",MODE_PRIVATE);
        editor = preferences.edit();
        boolean isRememble = preferences.getBoolean("remember_password",false);
        if (isRememble) {
            String accountGet = preferences.getString("account","");
            String passwordGet = preferences.getString("password","");
            account.setText(accountGet);
            password.setText(passwordGet);
            rememberPass.setChecked(true);
        }

    }

    @OnClick({R.id.login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.login:
                //账号、密码
                String accountGet = account.getText().toString();
                String passwordGet = password.getText().toString();
                if (accountGet.equals("Richard") && passwordGet.equals("123456")) {
                    if (rememberPass.isChecked()) {
                        editor.putBoolean("remember_password",true);
                        editor.putString("account","Richard");
                        editor.putString("password","123456");
                    }else {
                        editor.clear();
                    }
                    editor.apply();
                    Toast.makeText(this,"登陆成功",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(this,"账号或密码错误",Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
