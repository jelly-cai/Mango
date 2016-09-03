package com.jelly.imagebrowse.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.jelly.imagebrowse.R;
import com.jelly.imagebrowse.adapter.ViewPageAdapter;
import com.jelly.imagebrowse.presenter.ImageBrowsePresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jelly on 2016/9/3.
 */
public class ImageBrowseActivity extends Activity implements ViewPager.OnPageChangeListener,View.OnClickListener,ImageBrowseView{

    private ViewPager vp;
    private TextView hint;
    private TextView save;
    private ViewPageAdapter adapter;
    private ImageBrowsePresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_image_browse);
        vp = (ViewPager) this.findViewById(R.id.viewPager);
        hint = (TextView) this.findViewById(R.id.hint);
        save = (TextView) this.findViewById(R.id.save);
        save.setOnClickListener(this);
        initPresenter();
        presenter.loadImage();
    }

    public void initPresenter(){
        presenter = new ImageBrowsePresenter(this);
    }

    @Override
    public Intent getDataIntent() {
        return getIntent();
    }

    @Override
    public Context getMyContext() {
        return this;
    }

    @Override
    public void setImageBrowse(List<String> images,int position) {
        if(adapter == null && images != null && images.size() != 0){
            adapter = new ViewPageAdapter(this,images);
            vp.setAdapter(adapter);
            vp.setCurrentItem(position);
            vp.addOnPageChangeListener(this);
            hint.setText(position + 1 + "/" + images.size());
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        presenter.setPosition(position);
        hint.setText(position + 1 + "/" + presenter.getImages().size());
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        presenter.saveImage();
    }


    public static void startActivity(Context context, ArrayList<String> images, int position){
        Intent intent = new Intent(context,ImageBrowseActivity.class);
        intent.putStringArrayListExtra("images",images);
        intent.putExtra("position",position);
        context.startActivity(intent);
    }


}
