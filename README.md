# Mango
### 简介
Mango是一款用来方便实现图片浏览的控件
### 使用
#### Gradle
	compile 'com.jelly:mango:1.2.3'
#### 具体使用
#### 快速开始
	Mango.setImages(List<MultiplexImage> images) //设置图片源
	Mango.setPosition(int position) //设置初始显示位置
	Mango.setImageSelectListener(ImageSelectListener listener) //设置滚动监听
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