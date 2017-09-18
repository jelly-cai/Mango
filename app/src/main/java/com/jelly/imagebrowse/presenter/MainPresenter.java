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

        images.add(new MultiplexImage("http://tulian.net/images/2017/09/02/IMG_09109f57a.th.jpg","http://tulian.net/images/2017/09/02/IMG_09109f57a.md.jpg",MultiplexImage.ImageType.NORMAL));
        images.add(new MultiplexImage("http://tulian.net/images/2017/09/02/IMG_0983b6454.th.jpg","http://tulian.net/images/2017/09/02/IMG_0983b6454.jpg",MultiplexImage.ImageType.NORMAL));
        images.add(new MultiplexImage("http://tulian.net/images/2017/09/02/IMG_098592f0f.th.jpg","http://tulian.net/images/2017/09/02/IMG_098592f0f.jpg", MultiplexImage.ImageType.NORMAL));
        images.add(new MultiplexImage("http://tulian.net/images/2017/09/02/IMG_09885300c.th.jpg",null,MultiplexImage.ImageType.NORMAL));
        view.setImages(images);
        view.initRecycler();
    }

}
