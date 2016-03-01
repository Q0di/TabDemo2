package com.coda.tabdemo2;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SongFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(container == null){

            return null;

        }
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_song, container, false);
    }


}
