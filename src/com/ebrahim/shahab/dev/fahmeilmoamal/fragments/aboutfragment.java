package com.ebrahim.shahab.dev.fahmeilmoamal.fragments;


import com.ebrahimhaq.dev.fahmeilmoamal.R;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class aboutfragment extends Fragment{

	public static aboutfragment newInstance(String text) {

		aboutfragment f = new aboutfragment();
		return f;
	}
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ViewGroup rootView = (ViewGroup) inflater.inflate(
				R.layout.aboutlayout, container, false);
		
		return rootView;
	}
}
