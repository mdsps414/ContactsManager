package ru.mdsps.contacts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Environment;
import android.provider.ContactsContract.Groups;

import com.bettervectordrawable.Convention;
import com.bettervectordrawable.VectorDrawableCompat;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import ru.mdsps.contacts.core.model.AccountData;
import ru.mdsps.contacts.core.model.Group;

/**
 * Created by Михаил on 26.01.2016.
 * Главный файл приложения. Запускается первым и содержит кучу переменных
 */
public class AppContacts extends Application {

    public static String SDCARD_PHOTO_PATH = null;

    // LOADER CODE
    public static final int CNN_CONTACTS_LIST_LOADER = 0;
    public static final int CNN_FAVORITES_LIST_LOADER = 1;
    public static final int CNN_INBOX_LIST_LOADER = 2;

    // FRAGMENTS CODE
    public static final int CNN_CONTACT_FRAGMENT = 101;
    public static final int CNN_CONTACT_CHANGE_FRAGMENT = 102;

    // SETTINGS LISTENER TYPE
    public static final int SETTINGS_LISTENER_COLOR = 0;
    public static final int SETTINGS_LISTENER_ACCOUNT_SHOW = 1;
    public static final int SETTINGS_LISTENER_ACCOUNT_HIDE = -1;


    private static Context context;
    private HashMap<Integer, Group> mGroupList;



    @Override
    public void onCreate(){
        super.onCreate();

        AppContacts.context = getApplicationContext();

        readPhotoPath();

        //Подключение svg
        int[] ids = VectorDrawableCompat.findVectorResourceIdsByConvention(getResources(),
                R.drawable.class, Convention.RESOURCE_NAME_HAS_VECTOR_PREFIX);
        VectorDrawableCompat.enableResourceInterceptionFor(getResources(), ids);

        if (BuildConfig.DEBUG && !VectorDrawableCompat.isResourceInterceptionEnabled()) {
            throw new AssertionError();
        }

        // Обновляем список групп;
        changeGroupList();
    }

    public static Context getContext() {
        return context;
    }

    public HashMap<Integer, Group> getGroupList(){
        return mGroupList;
    }

    public Group getGroup(int groupId){
        return mGroupList.get(groupId);
    }

    public void changeGroupList(){
        ContentResolver cr = getContentResolver();
        Cursor group = cr.query(
                Groups.CONTENT_URI,
                new String[]{Groups._ID, Groups.TITLE},
                null,
                null,
                Groups._ID + " ASC"
        );
        mGroupList = new HashMap<>();
        while(group.moveToNext()){
            int gId = group.getInt(0);
            String title = group.getString(1);
            Group grp = new Group(gId, title);
            mGroupList.put(gId, grp);
        }
        group.close();
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
