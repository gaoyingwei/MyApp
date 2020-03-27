package com.example.myapplication.recylerview;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ContactsManager {
    /**
     * @param context
     * @return List
     */
    @NonNull
    public static ArrayList<ShareContactsBean> getPhoneContacts(Context context) {
        ArrayList<ShareContactsBean> result = new ArrayList<>();
        ContentResolver resolver = context.getContentResolver();
        Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME}, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor.getString(0).replace(" ", "").replace("-", "");
                String contactName = phoneCursor.getString(1);
                result.add(new ShareContactsBean(contactName, phoneNumber));
            }
            phoneCursor.close();
        }

        Collections.sort(result, new Comparator<ShareContactsBean>() {
            @Override
            public int compare(ShareContactsBean l, ShareContactsBean r) {
                return l.compareTo(r);
            }
        });
        return result;
    }
}
