package com.example.mauri.intergrami.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mauri.intergrami.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class RentasFragment extends Fragment {


    public RentasFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_rentas, container, false);
    }

}
