package com.jelly.imagebrowse.view;

import android.app.Application;

import com.bumptech.glide.Glide;
import com.bumptech.glide.integration.okhttp.OkHttpUrlLoader;
import com.bumptech.glide.load.model.GlideUrl;
import com.squareup.okhttp.OkHttpClient;

import java.io.InputStream;

/**
 *
 * Created by Jelly on 2016/9/15.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        Glide.get(this).register(GlideUrl.class, InputStream.class,new OkHttpUrlLoader.Factory(new OkHttpClient()));
    }
}
