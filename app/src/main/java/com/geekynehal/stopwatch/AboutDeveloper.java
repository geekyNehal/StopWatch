package com.geekynehal.stopwatch;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;


/**
 * A simple {@link Fragment} subclass.
 */
public class AboutDeveloper extends Fragment {

    Activity myActivity;
    LinearLayout aboutMe;
    public AboutDeveloper() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_about_developer, container, false);
        setHasOptionsMenu(true);
        // Inflate the layout for this fragment
        aboutMe=view.findViewById(R.id.aboutMe);
        return view;
    }

}
