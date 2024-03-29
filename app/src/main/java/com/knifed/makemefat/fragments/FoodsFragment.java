package com.knifed.makemefat.fragments;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.knifed.makemefat.R;

import java.util.ArrayList;

public class FoodsFragment extends Fragment {
    private ListView listView;

    public FoodsFragment() {
        super(R.layout.fragment_foods);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        ArrayList<String> gogo = requireArguments().getStringArrayList("data");

        listView = (ListView) view.findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<String >(this.getContext(), android.R.layout.simple_list_item_1, gogo);
        if (listView != null) {
            listView.setAdapter(adapter);
        }
    }
}
