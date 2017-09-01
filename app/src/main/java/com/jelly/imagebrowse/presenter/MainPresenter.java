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

        images.add(new MultiplexImage("http://i1.bvimg.com/607307/69da53a13af7290es.jpg","http://i1.bvimg.com/607307/0a3fa5f69c3fd395.png",MultiplexImage.ImageType.NORMAL));
        images.add(new MultiplexImage("http://i1.bvimg.com/607307/0a3fa5f69c3fd395s.png","http://i1.bvimg.com/607307/0a3fa5f69c3fd395.png",MultiplexImage.ImageType.NORMAL));
        images.add(new MultiplexImage("http://i1.bvimg.com/607307/4c07fa5cad421be5s.jpg","http://i1.bvimg.com/607307/4c07fa5cad421be5.jpg", MultiplexImage.ImageType.NORMAL));
        images.add(new MultiplexImage("http://i1.bvimg.com/607307/be4fe41bde84a84fs.jpg","http://i1.bvimg.com/607307/be4fe41bde84a84f.jpg",MultiplexImage.ImageType.NORMAL));
        view.setImages(images);
        view.initRecycler();
    }

}
