package com.jelly.imagebrowse.view;

import android.app.Application;


/**
 *
 * Created by Jelly on 2016/9/15.
 */
public class MyApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
       // Glide.get(this).register(GlideUrl.class, InputStream.class,new OkHttpUrlLoader.Factory(new OkHttpClient()));
        //LeakCanary.install(this);
    }

}
