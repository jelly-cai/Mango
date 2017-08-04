package com.jelly.imagebrowse.presenter;

import com.jelly.imagebrowse.view.MainView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jelly on 2016/9/3.
 */
public class MainPresenter {

    private MainView view;
    private List<String> images;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    public void loadImage() {
        if (images == null) images = new ArrayList<String>();

        images.add("http://img1.imgtn.bdimg.com/it/u=3649068744,3901725653&fm=26&gp=0.jpg");
        images.add("http://img3.duitang.com/uploads/blog/201605/29/20160529072811_ZPWcY.thumb.224_0.jpeg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1981331305,170397280&fm=26&gp=0.jpg");
        images.add("http://cdnq.duitang.com/uploads/item/201504/15/20150415H0546_YGatC.thumb.224_0.jpeg");
        images.add("http://i1.buimg.com/1949/3fc9e01c986f974bs.jpg");
        images.add("http://i2.tiimg.com/1949/e0a2843359c93bf3.jpg");

        view.setImages(images);
        view.initRecycler();
    }

}
