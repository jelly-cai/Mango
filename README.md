写一篇利用Picasso图片加载框架，PhotoView图片缩放控件以及ViewPager的图片浏览器

###首先建立依赖：
在这里我们加入PhotoView和picasso的依赖

    //PhotoView       
    compile 'com.github.chrisbanes.photoview:library:+'
    //picasso
    compile 'com.squareup.picasso:picasso:+'
###然后我们写一个图片列表展示的首页
图片的展示页采用RecyclerView去展示,布局文件如下所示

    <android.support.v7.widget.RecyclerView
        android:id="@+id/recyclerView"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    </android.support.v7.widget.RecyclerView>
加入数据，图片是从网上找的几张图片，加入数据集合中
    images.add("http://img3.imgtn.bdimg.com/it/u=3993672553,267575750&fm=21&gp=0.jpg");
    images.add("http://img5.imgtn.bdimg.com/it/u=2652905594,2769975769&fm=21&gp=0.jpg");
    images.add("http://i.zeze.com/attachment/forum/201511/06/180208mwwxx8oer7wirv89.jpg");
    images.add("http://imgsrc.baidu.com/forum/w%3D580/sign=fe8a7e7f78ec54e741ec1a1689399bfd/4b3f9682d158ccbfb334467b1ad8bc3eb33541cd.jpg");
    images.add("http://static.wgpet.com/editor/attached/image/20141124/20141124225933_71813.jpg");
    images.add("http://v1.qzone.cc/pic/201405/25/13/57/5381864e3b8af886.jpg%21600x600.jpg");
    images.add("http://wanzao2.b0.upaiyun.com/system/pictures/23660313/original/8d4f08e50be02ae3.png");
    images.add("http://zyline-photo.qiniudn.com/1392705051156.jpg");
为RecyclerView创建Adapter，在ViewHolder中加入列表的点击事件

    package com.jelly.imagebrowse.adapter;    
    
    import android.content.Context;
    import android.support.v7.widget.RecyclerView;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    
    import com.jelly.imagebrowse.R;
    import com.squareup.picasso.Picasso;
    
    import java.util.List;
    
    /**
     *  图片列表适配器
     * Created by Jelly on 2016/9/3.
     */
    public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ImageHolder>{

    private List<String> dataList;
    private Context context;
    private LayoutInflater inflater;
    private OnRecyclerItemClickListener itemClickListener;

    public ImageRecyclerAdapter(Context context,List<String> dataList) {
        this.context = context;
        this.dataList = dataList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageHolder(inflater.inflate(R.layout.item_image,parent,false),itemClickListener);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        Picasso.with(context).load(dataList.get(position)).resize(200,200).centerCrop().into(holder.image);
    }

    public void setItemClickListener(OnRecyclerItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public static class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private ImageView image;
        private OnRecyclerItemClickListener itemClickListener;

        public ImageHolder(View itemView,OnRecyclerItemClickListener itemClickListener) {
            super(itemView);
            this.itemClickListener = itemClickListener;
            itemView.setOnClickListener(this);
            image = (ImageView) itemView.findViewById(R.id.image);
        }

        @Override
        public void onClick(View v) {
            if(itemClickListener == null) return;
            itemClickListener.click(itemView,getAdapterPosition());
        }

    }
    }
Activity中设置RecyclerView，加入RecyclerView的点击事件，进入图片浏览的Activity

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
显示效果
![图片展示.png](http://upload-images.jianshu.io/upload_images/2098384-f9786393e12e8deb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
###图片浏览Activity
在上一Activity上传入需要显示的图片的集合和显示的Position

    public static void startActivity(Context context, ArrayList<String> images, int position){
        Intent intent = new Intent(context,ImageBrowseActivity.class);
        intent.putStringArrayListExtra("images",images);
        intent.putExtra("position",position);
        context.startActivity(intent);
    }
布局Activity，显示图片的hint和保存按钮

    <?xml version="1.0" encoding="utf-8"?>
    <RelativeLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="@android:color/black">
    
        <com.jelly.imagebrowse.view.ViewPagerFixed
            android:id="@+id/viewPager"
            android:layout_width="match_parent"
            android:layout_height="match_parent">
    
        </com.jelly.imagebrowse.view.ViewPagerFixed>
    
    
    
        <TextView
            android:id="@+id/hint"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:padding="20dp"
            android:layout_margin="10dp"
            android:layout_alignParentBottom="true"
            android:textColor="@android:color/white"
            android:text="0/0"/>
    
        <TextView
            android:id="@+id/save"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:padding="20dp"
            android:layout_margin="10dp"
            android:layout_alignParentBottom="true"
            android:layout_alignParentRight="true"
            android:textColor="@android:color/white"
            android:text="保存"/>
    
    </RelativeLayout>
在布局文件中使用的ViewPagerFixed，修复了在使用PhotoView时的一个Bug

    package com.jelly.imagebrowse.view;
    
    import android.content.Context;
    import android.util.AttributeSet;
    import android.view.MotionEvent;
    
    public class ViewPagerFixed extends android.support.v4.view.ViewPager {
    
        public ViewPagerFixed(Context context) {
            super(context);
        }
    
        public ViewPagerFixed(Context context, AttributeSet attrs) {
            super(context, attrs);
        }
    
        @Override
        public boolean onTouchEvent(MotionEvent ev) {
            try {
                return super.onTouchEvent(ev);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    
        @Override
        public boolean onInterceptTouchEvent(MotionEvent ev) {
            try {
                return super.onInterceptTouchEvent(ev);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            }
            return false;
        }
    }
ViewPager的Adapter，这里使用SparseArray进行缓存，PhotoViewAttacher设置ImageView可以放大缩小 ，使用Picasso加载图片，Bitmap.Config.RGB_565提高图片加载效率，在Picasso的回调中调用PhotoViewAttacher的Update刷新，不刷新的话会导致图片错位。

    package com.jelly.imagebrowse.adapter;
    
    import android.app.Activity;
    import android.content.Context;
    import android.graphics.Bitmap;
    import android.support.v4.view.PagerAdapter;
    import android.util.SparseArray;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.ImageView;
    
    import com.jelly.imagebrowse.R;
    import com.squareup.picasso.Callback;
    import com.squareup.picasso.Picasso;
    
    import java.util.List;
    
    import uk.co.senab.photoview.PhotoViewAttacher;
    
    
    /**
     * 图片浏览ViewPageAdapter
     * Created by Jelly on 2016/3/10.
     */
    public class ViewPageAdapter extends PagerAdapter {
        private Context context;
        private List<String> images;
        private SparseArray<View> cacheView;
        private ViewGroup containerTemp;
    
        public ViewPageAdapter(Context context, List<String> images) {
            this.context = context;
            this.images = images;
            cacheView = new SparseArray<>(images.size());
        }
    
        @Override
        public Object instantiateItem(final ViewGroup container, int position) {
            if(containerTemp == null) containerTemp = container;
    
            View view = cacheView.get(position);
    
            if(view == null){
                view = LayoutInflater.from(context).inflate(R.layout.vp_item_image,container,false);
                view.setTag(position);
                final ImageView image = (ImageView) view.findViewById(R.id.image);
                final PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(image);
    
                Picasso.with(context).load(images.get(position)).config(Bitmap.Config.RGB_565).into(image, new Callback() {
                    @Override
                    public void onSuccess() {
                        photoViewAttacher.update();
                    }
    
                    @Override
                    public void onError() {
    
                    }
                });
    
                photoViewAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                    @Override
                    public void onPhotoTap(View view, float x, float y) {
                        Activity activity = (Activity) context;
                        activity.finish();
                    }
                });
                cacheView.put(position,view);
            }
            container.addView(view);
            return view;
        }
    
        @Override
        public int getCount() {
            return images.size();
        }
    
        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    
    }
在图片浏览的Activity中获取需要显示的图片和设置ViewPager的Adapter

    //获取data
    Intent intent = view.getDataIntent();
    images = intent.getStringArrayListExtra("images");
    position = intent.getIntExtra("position",0);
    view.setImageBrowse(images,position);
    //设置ViewPager
    adapter = new ViewPageAdapter(this,images);
    vp.setAdapter(adapter);
    vp.setCurrentItem(position);
    vp.addOnPageChangeListener(this);
    hint.setText(position + 1 + "/" + images.size());
监听ViewPager的滑动事件，设置hint的显示

    @Override
    public void onPageSelected(int position) {
        presenter.setPosition(position);
        hint.setText(position + 1 + "/" + presenter.getImages().size());
    }
加入保存功能

    Picasso.with(view.getMyContext()).load(imageUrl).into(new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            // 创建目录
            File appDir = new File(Environment.getExternalStorageDirectory(), "JellyImage");
            if (!appDir.exists()) {
                appDir.mkdir();
            }
    
            String imageType = getImageType(imageUrl); //获取图片类型
            String fileName = System.currentTimeMillis() + "." + imageType;
            File file = new File(appDir, fileName);
            //保存图片
            try {
                FileOutputStream fos = new FileOutputStream(file);
                if(TextUtils.equals(imageType,"jpg")) imageType = "jpeg";
                imageType = imageType.toUpperCase();
                bitmap.compress(Bitmap.CompressFormat.valueOf(imageType), 100, fos);
                fos.flush();
                fos.close();
                Toast.makeText(view.getMyContext(),"保存成功",Toast.LENGTH_SHORT).show(); //Toast
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    
            // 其次把文件插入到系统图库
            try {
                MediaStore.Images.Media.insertImage(view.getMyContext().getContentResolver(), file.getAbsolutePath(), fileName, null);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            // 最后通知图库更新
            view.getMyContext().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + file.getPath())));
        }
    
        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
    
        }
    
        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
    
        }
    });
到这里图片浏览器的功能就实现，效果图：

![放大之前.png](http://upload-images.jianshu.io/upload_images/2098384-ad84e37ecd53b8b8.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

![放大后.png](http://upload-images.jianshu.io/upload_images/2098384-4d3af5d46ac54012.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
具体代码：https://github.com/JellyCai1995/ImageBrowse
