package com.deringmobile.jbh.loadimagedemo.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.target.Target;
import com.deringmobile.jbh.loadimagedemo.R;
import com.deringmobile.jbh.loadimagedemo.adapter.CommonAdapter;
import com.deringmobile.jbh.loadimagedemo.loadimage.LoadImageManager;
import com.deringmobile.jbh.loadimagedemo.service.MyService;
import com.deringmobile.jbh.loadimagedemo.util.CallBack;
import com.deringmobile.jbh.loadimagedemo.util.LogUtil;
import com.deringmobile.jbh.loadimagedemo.util.Type;
import com.deringmobile.jbh.loadimagedemo.weights.MyItemDecoration;
import com.nostra13.universalimageloader.core.ImageLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    private static final int spanCount=2;
    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    private CommonAdapter adapter;
    private String[] imgs={
            "https://img10.360buyimg.com/mobilecms/s110x110_jfs/t1588/138/434981161/280119/a5895e83/55815d4dNd883af38.jpg",
            "https://img14.360buyimg.com/n7/s140x140_jfs/t3724/347/1016217917/255875/c5380734/581ae701N723b098d.jpg!q90",
            "https://img10.360buyimg.com/mobilecms/s200x140_jfs/t8596/65/71786488/76878/1ef95ed6/599fee55Nff18c3bf.jpg!cc_200x140",
            "https://img12.360buyimg.com/babel/s400x170_jfs/t5938/90/535830027/35925/30c06ab1/5928f90dN664d55ef.jpg!q90",
            "https://img10.360buyimg.com/n4/s130x130_jfs/t6925/75/2382158459/437865/f3931d24/598be5b1N24d949fe.jpg",
            "https://img12.360buyimg.com/babel/s193x260_jfs/t6871/130/2611878755/49912/9ab9a4a9/598d575cN55decfdf.jpg!q90",
            "https://img14.360buyimg.com/babel/s100x100_jfs/t8860/279/60943576/9177/7b4f784b/599fc0f1N06d987eb.jpg!q90",
            "https://img11.360buyimg.com/mobilecms/s180x225_jfs/t8392/335/71937118/36941/c7549b27/59a00160Nb4136ffb.jpg",
            "https://img11.360buyimg.com/da/jfs/t8221/293/233373328/100778/3faac736/59a3a41aNad8e4521.jpg",
    "https://img1.360buyimg.com/da/jfs/t7363/213/1432700762/196454/fdae1d3d/599d4b39N5af6118d.jpg"};

    private String[] str={
            "https://img10.360buyimg.com/mobilecms/s110x110_jfs/t158855815d4dNd883af38.jpg",
            "https://img14.360buyimg.com/n7/s140x140_jfs/t3724/347/1016217917/255875/c5380734/581ae701N723b098d.jpg!q90",
            "https://img10.360buyimg.com/mobilecmss/t8596/65/71786488/76878/1ef95ed6/599fee55Nff18c3bf.jpg!cc_200x140",
            "https://img12.360buyimg.com/babel/s400x170_jfs/t5938/90/5358308f90dN664d55ef.jpg!q90",
            "https://img10.360buyimg.com/n4/s130x130_jfs/t6925/75/2382158459/437865/f3931d24/598be5b1N24d949fe.jpg",
            "https://img12.360buyimg.com/babel/s193x260_jfs/t6871/130/261/9ab9a4a9/598d575cN55decfdf.jpg!q90",
            "https://img14.360buyimg.com/babel/s100x100_jfs/t8860/279/4f784b/599fc0f1N06d987eb.jpg!q90",
            "https://img11.360buyimg.com/mob27/59a00160Nb4136ffb.jpg",
    "https://img11.360buyimg.com/da/jfs/t8221/293/233373328/100778/3faac736/59a3a41aNad8e4521.jpg",
    "https://img1.360buyimg.com/da/jfs/t7363/213/1432700762/196454/fdae1d3d/599d4b39N5af6118d.jpg"};


    private Type type=Type.IMAGELOADER; //默认为imageLoader加载图片形式
    private MyService myService;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        /**使用Glide下载图片并且存储到手机中*/
        downLoaderForGlide();
        deleteCacheFromImgCache();
        connecotionService();
    }


    private void connecotionService() {
//        /**启动服务的方式一*/
//        startService(new Intent(MainActivity.this,MyService.class));
        /**启动服务的方式二*/
        Intent i=new Intent(this,MyService.class);
        bindService(i,conn, Context.BIND_AUTO_CREATE);
    }


    /**获取服务的实例*/
   private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            myService = ((MyService.MyBinder)service).getService();
            if(myService!=null){
                myService.fun(new CallBack<Integer>() {
                    @Override
                    public void result(Integer result) {
                        LogUtil.showLogV("========service=",String.valueOf(result));
                    }
                });
            }else {
                LogUtil.showLogV("========service=","null");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            myService=null;
        }
    };

    private void deleteCacheFromImgCache() {
        String path = MyApp.path + "/imgCache";
        File file = new File(path);
        if (!file.exists()) {
            return;
        }
        File[] list = file.listFiles();
        int i=0;
        for (File f : list) {
            if(f.isFile()){
                i++;
                Log.d("==========ss=","true"+i);
                f.delete();
            }
        }
    }


    /**下载图片并且存到sdcard中去*/
    private void downLoaderForGlide() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                FileOutputStream out=null;
                FileInputStream fis=null;

                try {
                    FutureTarget<File> target = Glide
                            .with(MainActivity.this)
                            .load(imgs[0])
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);

                    File imgFile = target.get();

                    File f0=new File(MyApp.path);
                    if (!f0.exists()){
                        f0.mkdir();
                    }

                    File f=new File(MyApp.path+"/aa.jpg");
                    f.createNewFile();

                    out=new FileOutputStream(f);

                    fis=new FileInputStream(imgFile);
                    byte[] b = new byte[1024];
                    int n;
                    while ((n = fis.read(b)) != -1) {
                        out.write(b,0,n);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    try {
                        if(out!=null){
                            out.close();
                        }
                        if(fis!=null){
                            fis.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }


    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    private void init() {
//        staggeredGridLayoutManager=new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.VERTICAL);
//        mRecyclerView.setLayoutManager(staggeredGridLayoutManager);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new MyItemDecoration());
        adapter=new CommonAdapter(this,R.layout.item_layout,imgs.length,0,0) {

            @Override
            public void content(RecyclerView.ViewHolder holder, int position) {
                TextView text=(TextView)holder.itemView.findViewById(R.id.text);
                text.setText(str[position]);

                final ImageView imge=(ImageView)holder.itemView.findViewById(R.id.img);
                switch (type){
                    case GLIDE:
                        LoadImageManager.newStanence().loadImage(MainActivity.this,imgs[position],0,0,imge);
                        break;
                    case IMAGELOADER:
                        ImageLoader mImageLoader = ImageLoader.getInstance();
                        mImageLoader .displayImage(imgs[position],imge);
                        break;
                }
            }

            @Override
            public void headContent(RecyclerView.ViewHolder holder, int position) {
            }


            @Override
            public void footerContent(RecyclerView.ViewHolder holder, int position) {

            }
        };
        mRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("dasdfsad","dfasdf");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if(savedInstanceState!=null){
            /**清空点数据缓存*/
            savedInstanceState.clear();
        }
    }
}
