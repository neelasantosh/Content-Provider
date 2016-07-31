package com.example.contentprovide;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.Telephony;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SMSActivity extends ListActivity {
	ListView listViewData;
	ArrayList<String> listData = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		listViewData = getListView();

		// fetch data from SMS app

		//Uri uriForSMS = Uri.Telephony.Sms.Outbox.CONTENT_URI;

		Uri uriForSMS = Uri.parse("content://sms/sent");
		
		Log.e("uri is:", uriForSMS.toString());

		ContentResolver resolver = getContentResolver();
		/* resolver.query(uri, projection, selection, selectionArgs, sortOrder); */

		Cursor cur = resolver.query(uriForSMS, null, null, null, null);

		for (int i = 0; i < cur.getColumnCount(); i++) {
			Log.e("Col:" + i, cur.getColumnName(i));
		}

		cur.close();

		// again send query with specific columns

		String columns[] = { "_id", "address", "body","type" };
		cur = resolver
				.query(uriForSMS, columns, null, null, null);

		while (cur.moveToNext()) {
			String id = cur.getString(0);// id at 0th position
			String address = cur.getString(1);
			String body = cur.getString(2);
			String type = cur.getString(3);
			String data = id + "," + address + "," + body+ "," +type;
			listData.add(data);
		}
		cur.close();
		adapter = new ArrayAdapter<String>(SMSActivity.this,
				android.R.layout.simple_list_item_1, listData);
		listViewData.setAdapter(adapter);

	}

}
