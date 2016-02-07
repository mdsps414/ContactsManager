package ru.mdsps.contacts.core.utility;


import java.io.File;

import ru.mdsps.contacts.AppContacts;

public class FileUtility {

    public static boolean checkFile(String contactID){
        String mFilePath = AppContacts.SDCARD_PHOTO_PATH + "/" + contactID + ".photo";
        File mFile = new File(mFilePath);
        return mFile.exists();
    }

    public static File getFile(String contactID){
        if(checkFile(contactID)){
            return new File(AppContacts.SDCARD_PHOTO_PATH + "/" + contactID + ".photo");
        }
        return null;
    }
}
