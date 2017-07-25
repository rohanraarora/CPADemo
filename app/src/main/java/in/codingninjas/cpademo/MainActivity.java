package in.codingninjas.cpademo;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> mItems;
    ArrayAdapter<String> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        listView = (ListView)findViewById(R.id.listView);
//        mItems = new ArrayList<>();
//        mAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,mItems);
//        listView.setAdapter(mAdapter);
//
//        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
//
//        if(cursor != null){
//            mItems.clear();
//            while (cursor.moveToNext()){
//                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
//                mItems.add(name);
//            }
//            mAdapter.notifyDataSetChanged();
//        }


//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_PICK).setType(ContactsContract.Contacts.CONTENT_TYPE);
//        startActivityForResult(intent,100);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_INSERT).setData(CalendarContract.Events.CONTENT_URI);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,System.currentTimeMillis());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,System.currentTimeMillis() + 100000L);
        intent.putExtra(CalendarContract.Events.TITLE,"Test Title");
        startActivity(intent);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 100 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri,null,null,null,null);
            if(cursor != null && cursor.moveToFirst()){
                String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                Cursor phoneCursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                        new String[]{id},
                        null);
                if(phoneCursor != null && phoneCursor.moveToFirst()){
                    String phone = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    name = name + " ( " + phone + " )";
                    Toast.makeText(this,name,Toast.LENGTH_LONG).show();
                }

            }
        }
    }
}
