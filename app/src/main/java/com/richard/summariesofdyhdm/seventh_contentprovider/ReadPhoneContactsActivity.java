package com.richard.summariesofdyhdm.seventh_contentprovider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.richard.summariesofdyhdm.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @param
 * @author Richard
 * @time 2019/7/16 17:21
 * @description 读取手机联系人的页面
 */
public class ReadPhoneContactsActivity extends AppCompatActivity {

    private List<String> contactsList = new ArrayList<>();
    private ArrayAdapter arrayAdapter;

    @BindView(R.id.list_view_read_phone_contacts)
    ListView listViewReadPhoneContacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_phone_contacts);
        ButterKnife.bind(this);
        arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,contactsList);
        listViewReadPhoneContacts.setAdapter(arrayAdapter);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ReadPhoneContactsActivity.this,
                    new String[] {Manifest.permission.READ_CONTACTS},1);
        }else {
            readContacts();

        }
    }

    //读取手机联系人方法
    @SuppressLint("LongLogTag")
    private void readContacts() {
        Cursor cursor = null;
        try {
            cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,null,null,null,
                    null);
            if (cursor.moveToNext()) {
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.
                        CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(ContactsContract.
                        CommonDataKinds.Phone.NUMBER));
                contactsList.add(name + "\n" + number);
                Log.d("ReadPhoneContactsActivity",name);
            }
            arrayAdapter.notifyDataSetChanged();
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    readContacts();
                }else {
                    Toast.makeText(this,"你没有授权",Toast.LENGTH_SHORT).show();
                }
                break;
                default:
                    break;
        }
    }

}
