package com.ebrahim.shahab.dev.fahmeilmoamal;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

import com.ebrahim.shahab.dev.fahmeilmoamal.fragments.datamodel;


public class GetTitle {
	
	private static String url = "http://fahmeilmoamal.org/mobile/menuTitle.php/";

	// JSON Node names
	private static final String TAG_FILES = "Files";
	private static final String TAG_ID = "id";
	
	private String titleString; 
	private String title;
	
	public String getTitleString() {
		return titleString;
	}


	public void setTitleString(String titleString) {
		this.titleString = titleString;
	}


		// contacts JSONArray
		JSONArray files = null;

		// Hashmap for ListView
		ArrayList<datamodel> filesList = new ArrayList<datamodel>();
	
	public String fetchTitle(){
		// Calling async task to get json
				new GetContacts().execute();
				if(title != null){
		return title;
				}
				else{
					return "Misc";
				}
		
		
	}
	
	
	/**
	 * Async task class to get json by making HTTP call
	 * */
	private class GetContacts extends AsyncTask<Void, Void, Void> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			// Showing progress dialog
		/*	pDialog = new ProgressDialog(getActivity());
			pDialog.setMessage("Please wait...");
			pDialog.setCancelable(false);
			pDialog.show();*/

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
						
						title = c.getString(TAG_ID);
					
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
		/*	if (pDialog.isShowing())
				pDialog.dismiss();
			*//**
			 * Updating parsed JSON data into ListView
			 * *//*
			Dataadapter adapter = new Dataadapter(filesList, getActivity());
			lv.setAdapter(adapter);*/
		}

	}


}
