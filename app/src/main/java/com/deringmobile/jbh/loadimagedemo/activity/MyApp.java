package com.deringmobile.jbh.loadimagedemo.activity;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Environment;
import com.deringmobile.jbh.loadimagedemo.R;
import com.deringmobile.jbh.loadimagedemo.util.LogUtil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;

import java.io.File;


/**
 * Created by zbsdata on 2017/8/28.
 */

public class MyApp extends Application{


    public static String path= Environment.getExternalStorageDirectory().getAbsolutePath()+"/imgDemo";
    public static Context context;
    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        /**初始化配置*/
        initConfig();
        LogUtil.setDeBug(true);
        LogUtil.showLogV("=onCreate=","onCreate");
    }

    private void initConfig() {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this)
                /**缓存在内存中的长宽*/
                .memoryCacheExtraOptions(400,800)
                /**缓存在sdCard中的长宽width height 缓存的图片的最大高宽*/
                .diskCacheExtraOptions(400,800,null)
                /**设置缓存的详细信息*/
                /**线程中加载的数量*/
                .threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                /***/
                .denyCacheImageMultipleSizesInMemory()
                /**图片最大内存设置为2M*/
                .memoryCache(new UsingFreqLimitedMemoryCache(2*1024*1024))
                /***/
                .memoryCacheSize(2*1024*1024)
                /***/
                .diskCacheSize(50* 1024*1024)
                /**将保存图片的url进行Md5加密*/
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                /**请求队列的顺序*/
                .tasksProcessingOrder(QueueProcessingType.FIFO)
                /**文件缓存的数量*/
                .diskCacheFileCount(100)
                /**文件缓存的路径*/
                .diskCache(new UnlimitedDiskCache(new File(path+"/imgCache")))
                .defaultDisplayImageOptions(getDisPlayOptions())
                .imageDownloader(new BaseImageDownloader(this,5*1000,30*1000))
                /**打印日志*/
                .writeDebugLogs()
                .build();
        /**初始化配置*/
        ImageLoader.getInstance().init(configuration);
    }

    public DisplayImageOptions getDisPlayOptions() {
        DisplayImageOptions disPlayOptions=null;
        disPlayOptions=new DisplayImageOptions.Builder()
                /**设置加载中显示的图片*/
                .showImageOnLoading(R.mipmap.ic_launcher)
                /**设置加载中的图片地址为空显示的展位图*/
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                /**显示加载失败的时候显示的图片*/
                .showImageOnFail(R.mipmap.ic_launcher)
                /**同意缓存子内存中*/
                .cacheInMemory(true)
                /**同意缓存在内存*/
                .cacheOnDisc(true)
                .considerExifParams(true)
                .imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .resetViewBeforeLoading(true)
                .displayer(new RoundedBitmapDisplayer(20))
                .displayer(new FadeInBitmapDisplayer(1000))
                .build();
        return disPlayOptions;
    }



    public MyApp() {
        super();
    }



    @Override
    public void onTerminate() {
        super.onTerminate();
        LogUtil.showLogV("=onTerminate=","onTerminate");
    }


    @Override
    public void onLowMemory() {
        super.onLowMemory();
        LogUtil.showLogV("=onLowMemory=","onLowMemory");
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        LogUtil.showLogV("=onTrimMemory=","onTrimMemory");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.showLogV("=onConfigurationChanged=","onConfigurationChanged");
    }
}
