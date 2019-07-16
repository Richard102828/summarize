package com.richard.summariesofdyhdm.recyclerview;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.richard.summariesofdyhdm.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Richard
 * @date: 2019/7/16
 * @describe:   RecyclerView的适配器
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private Context context;
    private List<String> imageList;
    View view;
    private List<Integer> heightList = new ArrayList<>();

    public RecyclerViewAdapter(Context context, List<String> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_recyclerview,parent,
                false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewHolder viewHolder = new ViewHolder(view);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                viewHolder.imageView.getLayoutParams());
        getRandomHeight();
        layoutParams.height = heightList.get(position);
        viewHolder.imageView.setLayoutParams(layoutParams);
        Glide.with(context).load(imageList.get(position)).into(viewHolder.imageView);

    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.img_item_picture);
        }
    }

    /**
     * @author Richard
     * @time 2019/7/16 11:27
     * @param
     * @description 方法：获取随机数
     */
    private void getRandomHeight(){
        for (int i = 0; i <= imageList.size(); i++) {
            int params = 300 + (int)(Math.random() * (100+1));
            heightList.add(params);
        }
    }

}
