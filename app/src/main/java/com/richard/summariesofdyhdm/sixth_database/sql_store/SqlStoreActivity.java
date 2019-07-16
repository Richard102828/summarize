package com.richard.summariesofdyhdm.sixth_database.sql_store;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.richard.summariesofdyhdm.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SqlStoreActivity extends AppCompatActivity {

    @BindView(R.id.btn_create_table)
    Button btnCreateTable;
    @BindView(R.id.btn_add_data)
    Button btnAddData;
    @BindView(R.id.btn_update_data)
    Button btn;
    @BindView(R.id.btn_delete_data)
    Button btnDeleteData;
    @BindView(R.id.btn_query_data)
    Button btnQueryData;

    private MyDatabaseHelper mhelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_store);
        ButterKnife.bind(this);
        mhelper = new MyDatabaseHelper(this,"BookStore.db",null,1);
    }

    @OnClick({R.id.btn_create_table, R.id.btn_add_data, R.id.btn_update_data, R.id.btn_delete_data, R.id.btn_query_data})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_create_table:
                mhelper.getWritableDatabase();
                Toast.makeText(this,"创建数据库成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_add_data:
                SQLiteDatabase db = mhelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put("name","The Da Vinci Code");
                values.put("author","Dan Brown");
                values.put("pages",454);
                values.put("price",16.96);
                db.insert("Book",null,values);
                values.clear();
                //插入第二组数据；
                values.put("name","The Lost Symbol");
                values.put("author","Dan Brown");
                values.put("pages",510);
                values.put("price",19.95);
                db.insert("book",null,values);
                Toast.makeText(this,"添加数据成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_update_data:
                SQLiteDatabase db1 = mhelper.getWritableDatabase();
                ContentValues values1 = new ContentValues();
                values1.put("price",10.99);
                db1.update("Book",values1,"name = ?",
                        new String[]{"The Da Vinci Code"});
                Toast.makeText(this,"更新数据成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_delete_data:
                SQLiteDatabase db2 = mhelper.getWritableDatabase();
                db2.delete("Book","pages > ?",new String[] {"500"});
                Toast.makeText(this,"删除数据成功",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_query_data:
                SQLiteDatabase db3 = mhelper.getWritableDatabase();
                Cursor cursor = db3.query("Book",null,null,null,
                        null,null,null);
                if (cursor.moveToFirst()) {
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        Toast.makeText(this,"名字为：" + name,Toast.LENGTH_SHORT).
                                show();
                    }while (cursor.moveToNext());
                }
                cursor.close();
                break;

        }
    }
}
