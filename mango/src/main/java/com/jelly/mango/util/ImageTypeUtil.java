package com.jelly.mango.util;

import com.jelly.mango.MultiplexImage;

import java.util.ArrayList;

/**
 * Created by Jelly on 2017/8/14.
 */

public class ImageTypeUtil {

    public static ArrayList<MultiplexImage> getImagesArrayList(ArrayList<String> images,ArrayList<Integer> types) throws Exception{
        if(images.size() != types.size()){
            throw new Exception("长度必须相同");
        }


        return null;
    }

    public static ArrayList<MultiplexImage> getNormalImagesArrayList(ArrayList<String> images) {
        MultiplexImage multiplexImage = null;
        ArrayList<MultiplexImage> multiplexImages = new ArrayList<MultiplexImage>();
        for (String img:images){
            multiplexImage = new MultiplexImage(img, MultiplexImage.ImageType.NORMAL);
            multiplexImages.add(multiplexImage);
        }
        return multiplexImages;
    }


}
