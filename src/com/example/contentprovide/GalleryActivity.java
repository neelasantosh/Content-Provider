package com.example.contentprovide;

import java.util.ArrayList;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class GalleryActivity extends ListActivity {
	ListView listViewData;
	ArrayList<String> listData = new ArrayList<String>();
	ArrayAdapter<String> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		listViewData = getListView();

		// fetch data from Gallery app

		// Uri uriForSMS = Uri.Telephony.Sms.Outbox.CONTENT_URI;

		Uri uriForGallery = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

		Log.e("uri is:", uriForGallery.toString());

		ContentResolver resolver = getContentResolver();
		/* resolver.query(uri, projection, selection, selectionArgs, sortOrder); */

		Cursor cur = resolver.query(uriForGallery, null, null, null, null);

		for (int i = 0; i < cur.getColumnCount(); i++) {
			Log.e("Col:" + i, cur.getColumnName(i));
		}

		cur.close();

		// again send query with specific columns

		String columns[] = { "_id", "_size", "_data", "_display_name" };
		cur = resolver.query(uriForGallery, columns, null, null, null);

		while (cur.moveToNext()) {
			String id = cur.getString(0);// id at 0th position
			String size = cur.getString(1);
			String data1 = cur.getString(2);
			String name = cur.getString(3);
			String data = id + "," + size + "," + data1 + "," + name;
			listData.add(data1);
		}
		cur.close();
		adapter = new ArrayAdapter<String>(GalleryActivity.this,
				android.R.layout.simple_list_item_1, listData);
		listViewData.setAdapter(adapter);

		// display image in full screen on selection
		listViewData.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String path = listData.get(arg2);
				Bitmap bmp = BitmapFactory.decodeFile(path);
				// display bitmap on dialog
				Dialog dlg = new Dialog(GalleryActivity.this);
				dlg.setContentView(R.layout.dialog);
				ImageView img = (ImageView) dlg.findViewById(R.id.imageView1);
				img.setImageBitmap(bmp);
				dlg.show();

			}
		});

	}

}
