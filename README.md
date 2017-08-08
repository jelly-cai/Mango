# Mongo
### 简介
Mango是一款用来方便实现图片查看的控件
### 使用
#### Gradle
	compile 'com.jelly:mango:1.0.0'
#### 具体使用
##### 设置Glide
	Glide.get(this).register(GlideUrl.class, InputStream.class,new OkHttpUrlLoader.Factory(new OkHttpClient()));
#### 开启浏览
	setImages(ArrayList<String> imageUrls) //设置图片源
	setPosition(int position) //设置初始显示位置
	setImageSelectListener(ImageSelectListener listener) //设置滚动监听
	open(Context context) //开启图片浏览
### 显示效果
![Markdown](http://i4.bvimg.com/1949/6aadc4d48b25b8eds.png)
