package com.knifed.makemefat;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.knifed.makemefat.dialogs.AddItemDialogFragment;
import com.knifed.makemefat.utils.Utilities;

public class PlaceActivity extends AppCompatActivity implements AddItemDialogFragment.AddItemDialogListener{

    private Utilities utilities = new Utilities();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        FloatingActionButton floatingActionButton = this.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utilities.buildAndShowDialog(PlaceActivity.this,"add food", R.layout.dialog_add_food);
            }
        });
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialogFragment) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }
}
