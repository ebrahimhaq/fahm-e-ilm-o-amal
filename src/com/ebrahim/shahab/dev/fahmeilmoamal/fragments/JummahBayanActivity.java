package com.ebrahim.shahab.dev.fahmeilmoamal.fragments;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ebrahim.shahab.dev.fahmeilmoamal.ServiceHandler;


import com.ebrahimhaq.dev.fahmeilmoamal.R;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class JummahBayanActivity extends Fragment {
	private ProgressDialog pDialog;

	// URL to get contacts JSON
	private static String url = "http://fahmeilmoamal.org/mobile/darseJummahMP3.php/";

	// JSON Node names
	private static final String TAG_FILES = "Files";
	private static final String TAG_ID = "id";
	private static final String TAG_TYPE = "type";
	private static final String TAG_FILE_PATH = "file_path";
	private ListView lv;

	// contacts JSONArray
	JSONArray files = null;

	ArrayList<datamodel> filesList = new ArrayList<datamodel>();
	public static JummahBayanActivity newInstance(String text) {

		JummahBayanActivity f = new JummahBayanActivity();
		Bundle b = new Bundle();
		b.putString("msg", text);
		f.setArguments(b);
		return f;
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.activity_jummah_bayan, container, false);
		lv = (ListView) rootView.findViewById(android.R.id.list);
		
		
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// getting values from selected ListItem
				/*String fileid = ((TextView) view.findViewById(R.id.id))
						.getText().toString();*/
				String filePath = filesList.get(position).getFilepath();

				
				Uri myUri = Uri.parse(filePath);
				Intent intent = new Intent(android.content.Intent.ACTION_VIEW); 
				intent.setDataAndType(myUri, "audio/*"); 
				startActivity(intent);
				
			/*	
				// Starting single contact activity
				Intent in = new Intent(getApplicationContext(),
						PlayBayan.class);
				in.putExtra(TAG_ID, fileid);
				in.putExtra(TAG_TYPE, fileType);
				in.putExtra(TAG_FILE_PATH, filePath);
				startActivity(in);*/


			}
		});

		// Calling async task to get json
		new GetContacts().execute();
		return rootView;
	}
	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetContacts extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
			pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// Creating service handler class instance
			ServiceHandler sh = new ServiceHandler();

			// Making a request to url and getting response
			String jsonStr = sh.makeServiceCall(url, ServiceHandler.GET);

			Log.d("Response: ", "> " + jsonStr);

			if (jsonStr != null) {
				try {
					JSONObject jsonObj = new JSONObject(jsonStr);
					
					// Getting JSON Array node
					files = jsonObj.getJSONArray(TAG_FILES);

					// looping through All Contacts
					for (int i = 0; i < files.length(); i++) {
						JSONObject c = files.getJSONObject(i);
						
						String id = c.getString(TAG_ID);
						String fileType = c.getString(TAG_TYPE);
						String filePath = c.getString(TAG_FILE_PATH);

						// tmp hashmap for single contact
						HashMap<String, String> file = new HashMap<String, String>();

						datamodel data= new datamodel();
						data.setFilename(fileType);
						data.setFilepath(filePath);

						// adding contact to contact list
						filesList.add(data);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				Log.e("ServiceHandler", "Couldn't get any data from the url");
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
			// Dismiss the progress dialog
			if (pDialog.isShowing())
				pDialog.dismiss();
			/**
			 * Updating parsed JSON data into ListView
			 * */
			Dataadapter adapter = new Dataadapter(filesList, getActivity());
			lv.setAdapter(adapter);
		}

	}

	}
