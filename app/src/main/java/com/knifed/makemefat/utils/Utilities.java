package com.knifed.makemefat.utils;

import android.app.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import com.knifed.makemefat.dialogs.AddItemDialogFragment;

public class Utilities extends AppCompatActivity {
    public static void buildAndShowDialog(FragmentActivity activity, String message, int layout) {
        AddItemDialogFragment addItemDialogFragment = new AddItemDialogFragment(message, layout);
        addItemDialogFragment.show(activity.getSupportFragmentManager(), "Dialog");
    }
}
