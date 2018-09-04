package Util;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Creado por dvillanueva el  27/04/2018 (FiltrosLysApp).
 */

public class Contacts {
    public static boolean addContactList(Context context,int id, String contactName, String contactNumber){


        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI);
        builder.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null);
        builder.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null);
        ops.add(builder.build());

        // Name
        builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
        builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0);
        builder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        builder.withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, contactName);
        ops.add(builder.build());

        // Number
        builder = ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI);
        builder.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0);
        builder.withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        builder.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, contactNumber);
        builder.withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_HOME);
        ops.add(builder.build());
        try {
            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static HashMap<String,String> getContactList(Context context){

        HashMap<String,String> contact = new HashMap<>();

        Cursor c = context.getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                new String[] {ContactsContract.Data._ID,
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.LABEL}, null, null, null);

        if(null != c){
            String data = "";
            while(c.moveToNext()){
                if(c.getString(3) != null){
                    data = c.getString(0)+" "+c.getString(1)+" "+c.getString(2)+" "+c.getString(3)+"\n";
                    //position 0 - id
                    //position 1 - contact
                    //position 2 - type
                    //position 3 - label/name
                    contact.put(c.getString(3),c.getString(1));
                }
            }
            c.close();
        }
        return  contact;
    }


    public static boolean updateContactList(Context context,String name,String newPhoneNumber){
        ArrayList<ContentProviderOperation> ops = new ArrayList<>();

        String where = ContactsContract.Data.DISPLAY_NAME + " = ? AND " +
                ContactsContract.Data.MIMETYPE + " = ? AND " +
                String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE) + " = ? ";

        String[] params = new String[] {name,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE,
                String.valueOf(ContactsContract.CommonDataKinds.Phone.TYPE_HOME)};
        ops.add(ContentProviderOperation.newUpdate(ContactsContract.Data.CONTENT_URI)
                .withSelection(where, params)
                .withValue(ContactsContract.CommonDataKinds.Phone.DATA, newPhoneNumber)
                .build());
        try {
            context.getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteContactList(Context context,String name){
        ContentResolver cr = context.getContentResolver();
        String where = ContactsContract.Data.DISPLAY_NAME + " = ? ";
        String[] params = new String[] {name};

        ArrayList<ContentProviderOperation> ops = new ArrayList<>();
        ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
                .withSelection(where, params)
                .build());
        try {
            cr.applyBatch(ContactsContract.AUTHORITY, ops);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean contactExists(Context context, String name) {
        Uri lookupUri = Uri.withAppendedPath(
                ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(name));
        String[] mPhoneNumberProjection = { ContactsContract.PhoneLookup._ID, ContactsContract.PhoneLookup.NUMBER, ContactsContract.PhoneLookup.DISPLAY_NAME };
        Cursor cur = context.getContentResolver().query(lookupUri,mPhoneNumberProjection, null, null, null);
        try {
            if (cur.moveToFirst()) {
                return true;
            }
        } finally {
            if (cur != null)
                cur.close();
        }
        return false;
    }
}
