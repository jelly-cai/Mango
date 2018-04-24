package com.jelly.imagebrowse.presenter;

import com.jelly.imagebrowse.view.MainView;
import com.jelly.mango.MultiplexImage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jelly on 2016/9/3.
 */
public class MainPresenter {

    private MainView view;
    private List<MultiplexImage> images;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    public void loadImage() {
        if (images == null) images = new ArrayList<MultiplexImage>();

        images.add(new MultiplexImage("http://img07.tooopen.com/images/20170412/tooopen_sy_205649374814.jpg",MultiplexImage.ImageType.NORMAL));
        images.add(new MultiplexImage("https://i3.wenshen520.com/25257_0.jpg",MultiplexImage.ImageType.NORMAL));
        images.add(new MultiplexImage("https://truth.bahamut.com.tw/s01/201601/88f5d73bb1e77e536bdd3e619bb041aa.JPG",MultiplexImage.ImageType.NORMAL));
        images.add(new MultiplexImage("http://i2.bvimg.com/607307/5d1d51c2d25e5c5ct.jpg","http://lensbuyersguide.com/gallery/219/2/23_iso100_14mm.jpg",MultiplexImage.ImageType.NORMAL));
        view.setImages(images);
        view.initRecycler();
    }

}
