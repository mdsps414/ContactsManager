package ru.mdsps.contacts;

import android.app.Application;
import android.content.Context;

import com.bettervectordrawable.Convention;
import com.bettervectordrawable.VectorDrawableCompat;

import java.io.File;

public class AppContacts extends Application {

    public static String SDCARD_PHOTO_PATH = null;

    private static Context mContext;


    public static Context getContext(){
        return mContext;
    }

    @Override
    public void onCreate(){
        super.onCreate();

        mContext = getApplicationContext();

        readPhotoPath();

        //Подключение svg
        int[] ids = VectorDrawableCompat.findVectorResourceIdsByConvention(getResources(),
                R.drawable.class, Convention.RESOURCE_NAME_HAS_VECTOR_PREFIX);
        VectorDrawableCompat.enableResourceInterceptionFor(getResources(), ids);

        if (BuildConfig.DEBUG && !VectorDrawableCompat.isResourceInterceptionEnabled()) {
            throw new AssertionError();
        }

    }

    private void readPhotoPath(){
        String mContactPathName = "Android/data/" + getPackageName() +"/files/photo";
        String sdState = android.os.Environment.getExternalStorageState();
        File fileName = null;
        if(sdState.equals(android.os.Environment.MEDIA_MOUNTED)){
            File sdDir = android.os.Environment.getExternalStorageDirectory();
            fileName = new File(sdDir, mContactPathName);
        }

        if (!fileName.exists()) {
            fileName.mkdirs();
            SDCARD_PHOTO_PATH = fileName.getPath();
        } else {
            SDCARD_PHOTO_PATH = fileName.getPath();
        }
    }
}
