package com.jelly.imagebrowse.view;

import com.jelly.mango.MultiplexImage;

import java.util.List;

/**
 * Created by Jelly on 2016/9/3.
 */
public interface MainView {

    void setImages(List<MultiplexImage> images);

    void initRecycler();

}
