package com.example.contentprovide;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ContentNameActivity extends ListActivity {
	ListView listViewData;
	ArrayList<String> listData = new ArrayList<String>();
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		listViewData = getListView();

		// fetch data from Contacts app

		Uri uriForContact = ContactsContract.Contacts.CONTENT_URI;

		Log.e("uri is:", uriForContact.toString());

		ContentResolver resolver = getContentResolver();
		/* resolver.query(uri, projection, selection, selectionArgs, sortOrder); */

		Cursor cur = resolver.query(uriForContact, null, null, null, null);

		for (int i = 0; i < cur.getColumnCount(); i++) {
			Log.e("Col:" + i, cur.getColumnName(i));
		}

		cur.close();

		// again send query with specific columns

		String columns[] = { "_id", "display_name", "last_time_contacted" };
		cur = resolver
				.query(uriForContact, columns, "display_name like 'a%'", null, "display_name");

		while (cur.moveToNext()) {
			String id = cur.getString(0);// id at 0th position
			String name = cur.getString(1);
			String time = cur.getString(2);

			String data = id + "," + name + "," + time;
			listData.add(data);
		}
		cur.close();
		adapter = new ArrayAdapter<String>(ContentNameActivity.this,
				android.R.layout.simple_list_item_1, listData);
		listViewData.setAdapter(adapter);

	}

}
