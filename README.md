# Mango
### 简介
Mango是一款用来方便实现图片浏览的控件
### 使用
#### Gradle
	compile 'com.jelly:mango:1.3.3'
#### 如果想减小第三方依赖的大小
    本项目采用了以下第三方依赖:
    compile "com.github.chrisbanes.photoview:library:1.2.4"
    compile "com.github.bumptech.glide:glide:4.0.0"
    annotationProcessor  "com.github.bumptech.glide:compiler:4.0.0"
    如果自己的项目本身就存在这些相关联的依赖库(版本不同可能会发生问题)，想减少第三方依赖库的大小
    compile ('com.jelly:mango:1.3.0'){
        exclude group: 'com.github.chrisbanes.photoview'
        exclude group: 'com.github.bumptech.glide'
        exclude group: 'com.squareup.okhttp3'
    }
#### 具体使用
#### 快速开始
	Mango.setImages(List<MultiplexImage> images) //设置图片源
	Mango.setPosition(int position) //设置初始显示位置
	Mango.setImageSelectListener(ImageSelectListener listener) //设置滚动监听
	Mango.setIsShowLoading(boolean isShowLoading); //在加载图片的时候是否显示Loading,但是如果有原图，加载原图的时候就一定会显示loading
	Mango.open(Context context) //开启图片浏览
#### 参数解释
	MultiplexImage：图片加载源
		构造函数
		    MultiplexImage(String TPath, int type)
		    MultiplexImage(String TPath,String OPath, int type)
		        TPath:初始显示图片
	        	OPath:原图
		        type:图片类型（1：正常图片(MultiplexImage.ImageType.NORMAL)，2：GIF动图(MultiplexImage.ImageType.GIF)）
### 显示效果
![显示效果](http://upload-images.jianshu.io/upload_images/2098384-534c8f1685f438f4.png)