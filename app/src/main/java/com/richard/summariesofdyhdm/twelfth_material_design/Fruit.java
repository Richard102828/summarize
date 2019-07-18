package com.richard.summariesofdyhdm.twelfth_material_design;
/**
 * @author Richard
 * @time 2019/7/17 20:34
 * @param
 * @description 图片数据源类
 */
public class Fruit {
    private String name;
    private int imageId;
    public Fruit(String name, int imageId){
        this.name = name;
        this.imageId = imageId;
    }
    public String getName(){
        return name;
    }
    public int getImageId(){
        return imageId;
    }
}
