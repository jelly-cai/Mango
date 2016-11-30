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

        if (images == null) images = new ArrayList<>();

        images.add("http://img3.imgtn.bdimg.com/it/u=3993672553,267575750&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=2652905594,2769975769&fm=21&gp=0.jpg");
        images.add("http://i.zeze.com/attachment/forum/201511/06/180208mwwxx8oer7wirv89.jpg");
        images.add("http://imgsrc.baidu.com/forum/w%3D580/sign=fe8a7e7f78ec54e741ec1a1689399bfd/4b3f9682d158ccbfb334467b1ad8bc3eb33541cd.jpg");
        images.add("http://static.wgpet.com/editor/attached/image/20141124/20141124225933_71813.jpg");
        images.add("http://v1.qzone.cc/pic/201405/25/13/57/5381864e3b8af886.jpg%21600x600.jpg");
        images.add("http://wanzao2.b0.upaiyun.com/system/pictures/23660313/original/8d4f08e50be02ae3.png");
        images.add("http://zyline-photo.qiniudn.com/1392705051156.jpg");
        view.setImages(images);
        view.initRecycler();

    }

}
