package com.jc.android.baselib.imageloader;

import android.content.Context;
import android.util.Log;

import com.jc.android.baselib.imageloader.compenents.Fresco;
import com.jc.android.baselib.imageloader.compenents.Glide;
import com.jc.android.baselib.imageloader.compenents.Picasso;
import com.jc.android.baselib.imageloader.compenents.Universal;

public class ImageLoaderUtils {
    public static final int PIC_LARGE = 0;
    public static final int PIC_MEDIUM = 1;
    public static final int PIC_SMALL = 2;

    private static ImageLoaderUtils mInstance;
    private IimageLoaderProvider mStrategy;

    private ImageLoaderUtils() {

    }

    //single instance
    public static ImageLoaderUtils getInstance() {
        if (mInstance == null) {
            synchronized (ImageLoaderUtils.class) {
                if (mInstance == null) {
                    mInstance = new ImageLoaderUtils();
                }
            }
        }
        return mInstance;
    }


    public void loadImage(Context context, ImageLoader loader) {
        Strategy strategy = loader.getStrategy();
        if(strategy==null){
            //TODO If it fails to get strategy from imageloader,we can also check this attribute from localfilesmanager in globalmanager.It will throw a nullexception unless the the attribute of it is also empty.
            throw new NullPointerException("You haven't appointed any strategy for loading image!");
        }
        Log.e("*************", "loadImage: =="+strategy);
        switch (strategy) {
            case GLIDE:
                mStrategy = new Glide();
                break;
            case FRESCO:
                mStrategy = new Fresco();
                break;
            case PICASSO:
                mStrategy = new Picasso();
                break;
            case UNIVERSAL:
                mStrategy = new Universal();
                break;
            default:
                throw new NullPointerException("You haven't appointed any strategy for loading image!");
        }
        mStrategy.loadImage(context, loader);
    }
}
