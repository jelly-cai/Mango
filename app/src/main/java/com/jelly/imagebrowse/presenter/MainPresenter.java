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
        images.add(new MultiplexImage("http://a3.att.hudong.com/26/50/19300534462526138123508838344_950.jpg",MultiplexImage.ImageType.NORMAL));
        view.setImages(images);
        view.initRecycler();
    }

}
