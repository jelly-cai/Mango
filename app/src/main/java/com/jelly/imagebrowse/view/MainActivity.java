package com.jelly.imagebrowse.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jelly.imagebrowse.R;
import com.jelly.imagebrowse.adapter.ImageRecyclerAdapter;
import com.jelly.imagebrowse.adapter.OnRecyclerItemClickListener;
import com.jelly.imagebrowse.presenter.MainPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainView{

    private RecyclerView rv;
    private List<String> images;
    private ImageRecyclerAdapter adapter;
    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initView();
        initPresenter();
        presenter.loadImage();
    }

    public void initView(){
        rv = (RecyclerView) findViewById(R.id.recyclerView);
    }

    public void initPresenter(){
        presenter = new MainPresenter(this);
    }

    @Override
    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public void initRecycler() {
        if(images == null || images.size() == 0) return;

        if(adapter == null){
            rv.setLayoutManager(new GridLayoutManager(this,3));
            rv.setItemAnimator(new DefaultItemAnimator());
            adapter = new ImageRecyclerAdapter(this,images);
            adapter.setItemClickListener(new OnRecyclerItemClickListener() {
                @Override
                public void click(View item, int position) {
                    ImageBrowseActivity.startActivity(MainActivity.this, (ArrayList<String>) images,position);
                }
            });
            rv.setAdapter(adapter);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
