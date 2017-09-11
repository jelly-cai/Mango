package com.jelly.mango.component;

import com.jelly.mango.ImageBrowseActivity;
import com.jelly.mango.module.ActivityModule;

import dagger.Component;

/**
 * Created by Jelly on 2017/9/11.
 */
@Component(modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(ImageBrowseActivity activity);
}
