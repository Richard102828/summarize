package com.richard.summariesofdyhdm.main;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.richard.summariesofdyhdm.R;
import com.richard.summariesofdyhdm.eighth_multimedia.MultimediaActivity;
import com.richard.summariesofdyhdm.ninth_network.NetworkActivity;
import com.richard.summariesofdyhdm.recyclerview.RecyclerViewActivity;
import com.richard.summariesofdyhdm.sixth_database.TheSixthChapterActivity;
import com.richard.summariesofdyhdm.seventh_contentprovider.ContentProviderActivity;
import com.richard.summariesofdyhdm.tenth_service.MyActivity;

/**
 * @author: Richard
 * @date: 2019/7/16
 * @describe:   主界面的ListView的适配器
 */
public class DataAdapter extends BaseAdapter {

    private Context context;
    private String[] data;

    public DataAdapter(Context context, String[] data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int i) {
        return data[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View itemView;
        ViewHolder viewHolder;
        if (view == null){
            itemView = LayoutInflater.from(context).inflate(R.layout.item_listview,
                    viewGroup,false);
            viewHolder = new ViewHolder(itemView);
            viewHolder.textView.setText(data[i]);
            //点击事件
            viewHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    //根据判断子项数据，进行跳转对应的页面
                    if (data[i].equals("RecyclerView")) {

                        Intent intent = new Intent(context, RecyclerViewActivity.class);
                        context.startActivity(intent);

                    }else if (data[i].equals("DataBase")) {

                        Intent intent = new Intent(context, TheSixthChapterActivity.class);
                        context.startActivity(intent);

                    }else if (data[i].equals("ContentProvider")) {

                        Intent intent = new Intent(context, ContentProviderActivity.class);
                        context.startActivity(intent);

                    }else if (data[i].equals("Multimedia")) {

                        Intent intent = new Intent(context, MultimediaActivity.class);
                        context.startActivity(intent);

                    }else if (data[i].equals("Context")) {

//                        Intent intent = new Intent(MainActivity.this,);
//                        context.startActivity(intent);

                    }else if (data[i].equals("Network")) {

                        Intent intent = new Intent(context, NetworkActivity.class);
                        context.startActivity(intent);

                    }else if (data[i].equals("Service")) {

                        Intent intent = new Intent(context, MyActivity.class);
                        context.startActivity(intent);

                    }else if (data[i].equals("Location")) {

//                        Intent intent = new Intent(MainActivity.this,);
//                        context.startActivity(intent);

                    }else if (data[i].equals("MaterialDesign")) {

//                        Intent intent = new Intent(MainActivity.this,);
//                        context.startActivity(intent);

                    }else if (data[i].equals("Advertisement")) {

//                        Intent intent = new Intent(MainActivity.this,);
//                        context.startActivity(intent);

                    }
                }
            });
        }else {
            itemView = view;
        }

        return itemView;
    }

    //ViewHolder
    class ViewHolder {
        TextView textView;

        public ViewHolder(View view) {
            textView = (TextView)view.findViewById(R.id.tv_item_name);
        }
    }

}
