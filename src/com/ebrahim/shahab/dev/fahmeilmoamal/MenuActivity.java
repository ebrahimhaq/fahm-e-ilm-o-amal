package com.ebrahim.shahab.dev.fahmeilmoamal;

import com.ebrahim.shahab.dev.fahmeilmoamal.fragments.DarseHadeesActivity;
import com.ebrahim.shahab.dev.fahmeilmoamal.fragments.DarseQuranActivity;
import com.ebrahim.shahab.dev.fahmeilmoamal.fragments.HajjActivity;
import com.ebrahim.shahab.dev.fahmeilmoamal.fragments.JummahBayanActivity;
import com.ebrahim.shahab.dev.fahmeilmoamal.fragments.MyTabFactory;
import com.ebrahim.shahab.dev.fahmeilmoamal.fragments.SeasonalBayanActivity;
import com.ebrahim.shahab.dev.fahmeilmoamal.fragments.aboutfragment;


import com.ebrahimhaq.dev.fahmeilmoamal.R;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

public class MenuActivity extends FragmentActivity implements TabHost.OnTabChangeListener, OnPageChangeListener {
	TabHost mTabHost;
	MyPagerAdapter adapter;
	ViewPager pager;
	Context context;
	TextView tabtext1;
	RelativeLayout rel1;
	ImageView image1;
	TextView tabtext2;
	RelativeLayout rel2;
	ImageView image2;
	TextView tabtext3;
	RelativeLayout rel3;
	ImageView image3;
	TextView tabtext4;
	RelativeLayout rel4;
	ImageView image4;
	TextView header;
	boolean exit = false;
	private String menuTitle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		//Get Menu Title
		GetTitle title = new GetTitle();
		menuTitle = title.fetchTitle();
		
		setContentView(R.layout.activity_menu);
		
		pager = (ViewPager) findViewById(R.id.viewPager);
		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();
		header = (TextView)findViewById(R.id.header);
		header.setText("Dars e Quran");
		View tab1 = getLayoutInflater().inflate(R.layout.tab_item1, null);
		View tab2 = getLayoutInflater().inflate(R.layout.tab_item2, null);
		View tab3 = getLayoutInflater().inflate(R.layout.tab_item3, null);
		View tab4 = getLayoutInflater().inflate(R.layout.tab_item5, null);
		MenuActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("t")
				.setIndicator("Timings").setIndicator(tab1));
		MenuActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("q")
				.setIndicator("Qibla").setIndicator(tab2));
		MenuActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("m")
				.setIndicator("Mosque").setIndicator(tab3));
		MenuActivity.AddTab(this, this.mTabHost, this.mTabHost.newTabSpec("s")
				.setIndicator("Settings").setIndicator(tab4));
		tabtext1 = (TextView) tab1.findViewById(R.id.fst_child1);
		rel1 = (RelativeLayout) tab1.findViewById(R.id.mainlayout);
		//image1 = (ImageView) tab1.findViewById(R.id.timings_img);
		tabtext2 = (TextView) tab2.findViewById(R.id.fst_child2);
		rel2 = (RelativeLayout) tab2.findViewById(R.id.mainlayout);
		//image2 = (ImageView) tab2.findViewById(R.id.compass_img);
		tabtext3 = (TextView) tab3.findViewById(R.id.fst_child4);
		
		rel3 = (RelativeLayout) tab3.findViewById(R.id.mainlayout);
		//image3 = (ImageView) tab4.findViewById(R.id.timings_img);
		tabtext4 = (TextView) tab4.findViewById(R.id.fst_child3);
	
		rel4 = (RelativeLayout) tab4.findViewById(R.id.mainlayout);
		//image4 = (ImageView) tab1.findViewById(R.id.compass_img);
		mTabHost.setCurrentTab(0);
		mTabHost.setOnTabChangedListener(this);
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(this);
		pager.setOffscreenPageLimit(3);
	}
	
	public void about() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage("Maulana Fahimuddin Sahab, Iman o Khateeb Jama Masjid, Ghazi Mohammad Bin Qasim");
		alertDialogBuilder.show();
	}

	

	private static void AddTab(Context context2, TabHost tabHost,
			TabHost.TabSpec tabSpec) {
		tabSpec.setContent(new MyTabFactory(context2));
		tabHost.addTab(tabSpec);
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageSelected(int pos) {
		// TODO Auto-generated method stub
		mTabHost.getTabWidget().setCurrentTab(pos);
		if(pos==0)
			header.setText("Dars e Quran");
		else if(pos==1)
			header.setText("Dars e Hadees");
		else if(pos==2)
			header.setText("Jummah Bayan");
		else
			header.setText(menuTitle);
	}

	@Override
	public void onTabChanged(String arg0) {
		// TODO Auto-generated method stub
		int pos = mTabHost.getCurrentTab();
		pager.setCurrentItem(pos);
		if(pos==0)
			header.setText("Dars e Quran");
		else if(pos==1)
			header.setText("Dars e Hadees");
		else if(pos==2)
			header.setText("Jummah Bayan");
		else
			header.setText(menuTitle);
	}

	@Override
	public void onBackPressed() {
		if (pager.getCurrentItem() == 0) {
			// If the user is currently looking at the first step, allow the
			// system to handle the
			// Back button. This calls finish() on this activity and pops the
			// back stack.

			if (exit)
			{
				android.os.Process.killProcess(android.os.Process.myPid());
                System.exit(1);
			}
			else {
				Toast.makeText(this, "Press Back again to Exit.",
						Toast.LENGTH_SHORT).show();
				exit = true;
				new Handler().postDelayed(new Runnable() {
					@Override
					public void run() {
						exit = false;
					}
				}, 3 * 1000);

			}
/*			
			
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			builder.setMessage("Do you want to exit this application")
					.setTitle("Exit");
			builder.setPositiveButton("Ok",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// User clicked OK button
							finish();
						}
					});
			builder.setNegativeButton("Cancel",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int id) {
							// User cancelled the dialog
						}
					});
			AlertDialog dialog = builder.create();
			dialog.show();*/

		} else {
			// Otherwise, select the previous step.
			pager.setCurrentItem(pager.getCurrentItem() - 1);
		}
	}
	class MyPagerAdapter extends FragmentPagerAdapter {

		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int pos) {
			switch (pos) {

			case 0:
				return DarseQuranActivity
						.newInstance("SalahTimingsActivity, Instance 1");
			case 1:
				return DarseHadeesActivity.newInstance("qibla, Instance 2");
			case 2:
				return JummahBayanActivity.newInstance("mosque, Instance 3");
			case 3:
				return SeasonalBayanActivity
						.newInstance("SalahTimingsActivity, Instance 1");
			default:
				return DarseQuranActivity
						.newInstance("SalahTimingsActivity, Instance 1");
			}
		}

		@Override
		public int getCount() {
			return 4;
		}
	}
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    // Inflate the menu items for use in the action bar
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	    return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	     
	        case R.id.action_compose:
	            about();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	}
