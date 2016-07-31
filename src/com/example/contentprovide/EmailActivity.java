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

public class EmailActivity extends ListActivity {
	ListView listViewData;
	ArrayList<String> listData = new ArrayList<String>();
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		listViewData = getListView();

		// fetch data from Contacts app

		Uri uriForEmail = ContactsContract.CommonDataKinds.Email.CONTENT_URI;

		Log.e("uri is:", uriForEmail.toString());

		ContentResolver resolver = getContentResolver();
		/* resolver.query(uri, projection, selection, selectionArgs, sortOrder); */

		Cursor cur = resolver.query(uriForEmail, null, null, null, null);

		for (int i = 0; i < cur.getColumnCount(); i++) {
			Log.e("Col:" + i, cur.getColumnName(i));
		}

		cur.close();

		// again send query with specific columns

		String columns[] = { "_id", "display_name", "data1","raw_contact_id" };
		cur = resolver
				.query(uriForEmail, columns, null, null, "display_name");

		while (cur.moveToNext()) {
			String id = cur.getString(0);// id at 0th position
			String name = cur.getString(1);
			String data1 = cur.getString(2);
			String rawID = cur.getString(3);
			String data = id + "," + name + "," + data1+ "," +rawID;
			listData.add(data);
		}
		cur.close();
		adapter = new ArrayAdapter<String>(EmailActivity.this,
				android.R.layout.simple_list_item_1, listData);
		listViewData.setAdapter(adapter);

	}

}
