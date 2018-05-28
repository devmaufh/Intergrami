package com.example.mauri.intergrami.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mauri.intergrami.Activities.login;
import com.example.mauri.intergrami.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class start2Fragment extends Fragment {


    public start2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_start2, container, false);
        Button btnContinue=(Button)v.findViewById(R.id.fragment_start2_btnContinuar);
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),login.class));
            }
        });


        return v;
    }

}
