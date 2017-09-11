package com.jelly.mango.module;

import com.jelly.mango.presenter.ImageBrowsePresenter;
import com.jelly.mango.view.ImageBrowseView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jelly on 2017/9/11.
 */
@Module
public class ActivityModule {
    private ImageBrowseView view;

    public ActivityModule(ImageBrowseView view){
        this.view = view;
    }

    @Provides
    public ImageBrowsePresenter providePresenter(){
        return new ImageBrowsePresenter(view);
    }
}
