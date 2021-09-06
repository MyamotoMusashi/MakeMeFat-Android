package com.knifed.makemefat.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.knifed.makemefat.R;

import java.util.ArrayList;

public class PlacesFragment extends Fragment {

    public PlacesFragment(){
        super(R.layout.fragment_places);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ArrayList<String> placesBundle = requireArguments().getStringArrayList("data");

        ListView listView = (ListView) view.findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String >(this.getContext(), android.R.layout.simple_list_item_1, placesBundle);
        if (listView != null) {
            listView.setAdapter(adapter);
        }
    }
}
