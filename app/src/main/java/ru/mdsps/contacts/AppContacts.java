package ru.mdsps.contacts;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AuthenticatorDescription;
import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.Groups;

import com.bettervectordrawable.Convention;
import com.bettervectordrawable.VectorDrawableCompat;

import java.util.ArrayList;
import java.util.HashMap;

import ru.mdsps.contacts.core.model.AccountData;
import ru.mdsps.contacts.core.model.Group;

/**
 * Created by Михаил on 26.01.2016.
 * Главный файл приложения. Запускается первым и содержит кучу переменных
 */
public class AppContacts extends Application {

    // LOADER CODE
    public static final int CNN_CONTACTS_LIST_LOADER = 0;
    public static final int CNN_FAVORITES_LIST_LOADER = 1;
    public static final int CNN_INBOX_LIST_LOADER = 2;

    // FRAGMENTS CODE
    public static final int CNN_CONTACT_FRAGMENT = 101;
    public static final int CNN_CONTACT_CHANGE_FRAGMENT = 102;

    private static Context context;
    private HashMap<Integer, Group> mGroupList;



    @Override
    public void onCreate(){
        super.onCreate();

        AppContacts.context = getApplicationContext();

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

}
