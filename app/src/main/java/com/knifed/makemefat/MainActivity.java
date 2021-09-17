package com.knifed.makemefat;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import androidx.fragment.app.DialogFragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.knifed.makemefat.daos.PlaceDao;
import com.knifed.makemefat.daos.SleepDao;
import com.knifed.makemefat.daos.WaterDao;
import com.knifed.makemefat.database.AppDatabase;
import com.knifed.makemefat.databinding.ActivityMainBinding;
import com.knifed.makemefat.dialogs.AddItemDialogFragment;
import com.knifed.makemefat.entities.Place;
import com.knifed.makemefat.entities.Sleep;
import com.knifed.makemefat.entities.Water;
import com.knifed.makemefat.fragments.FoodsFragment;
import com.knifed.makemefat.fragments.PlacesFragment;
import com.knifed.makemefat.fragments.SleepFragment;
import com.knifed.makemefat.fragments.ToiletFragment;
import com.knifed.makemefat.fragments.WeightinFragment;
import com.knifed.makemefat.fragments.WorkoutFragment;
import com.knifed.makemefat.utils.Utilities;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddItemDialogFragment.AddItemDialogListener {
    private ActivityMainBinding binding;
    private List<Water> waters;
    private List<Sleep> sleeps;
    private ArrayList<String> gogo = new ArrayList<String>();
    private ArrayList<String> sleepArrayList;
    private ArrayList<String> placesArrayList;
    private AppDatabase db;
    private WaterDao waterDao;
    private SleepDao sleepDao;
    private PlaceDao placeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);

        db = Room.databaseBuilder(getApplicationContext(),AppDatabase.class, "database-name").fallbackToDestructiveMigration().allowMainThreadQueries().build();
        waterDao = db.waterDao();
        sleepDao = db.sleepDao();
        placeDao = db.placeDao();
        waters = db.waterDao().getAll();

        for (Water waterItem: waters) {
            gogo.add(String.valueOf(waterItem.amount));
        }

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putStringArrayList("data", gogo);

            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.fragment_container_view, FoodsFragment.class, bundle, "FoodsFragment")
                    .commit();
        }

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getSupportFragmentManager().findFragmentByTag("FoodsFragment") != null) {
                    Utilities.buildAndShowDialog(MainActivity.this,"Add food", R.layout.dialog_add_water);
                }

                if (getSupportFragmentManager().findFragmentByTag("SleepFragment") != null) {
                    Utilities.buildAndShowDialog(MainActivity.this,"Add Sleep", R.layout.dialog_add_sleep);
                }

                if (getSupportFragmentManager().findFragmentByTag("PlacesFragment") != null) {
                    Utilities.buildAndShowDialog(MainActivity.this,"Add Place", R.layout.dialog_add_place);
                }
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch(id) {
            case R.id.action_foods:
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("data", gogo);
                initializeFragmentView(FoodsFragment.class, bundle, "FoodsFragment");
                break;
            case R.id.action_places:
                this.placesArrayList = new ArrayList<String>();
                for (Place place: this.placeDao.getAll()) {
                    this.placesArrayList.add(place.name);
                }
                Bundle placesBundle = new Bundle();
                placesBundle.putStringArrayList("data", this.placesArrayList);
                this.initializeFragmentView(PlacesFragment.class, placesBundle, "PlacesFragment");
                break;
            case R.id.action_weightin:
                this.initializeFragmentView(WeightinFragment.class, null, "WeightinFragment");
                break;
            case R.id.action_toilet:
                this.initializeFragmentView(ToiletFragment.class, null, "ToiletFragment");
                break;
            case R.id.action_sleep:
                this.sleeps = this.db.sleepDao().getAll();
                this.sleepArrayList = new ArrayList<String>();
                for (Sleep sleep: this.sleeps) {
                    this.sleepArrayList.add(String.valueOf(sleep.duration));
                }
                Bundle sleepBundle = new Bundle();
                sleepBundle.putStringArrayList("data", this.sleepArrayList);
                this.initializeFragmentView(SleepFragment.class, sleepBundle, "SleepFragment");
                break;
            case R.id.action_workout:
                this.initializeFragmentView(WorkoutFragment.class, null, "WorkoutFragment");
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if (getSupportFragmentManager().findFragmentByTag("FoodsFragment") != null) {
            EditText amount = (EditText)dialog.getDialog().findViewById(R.id.amount);
            Water water = new Water(Integer.parseInt(amount.getText().toString()));
            waterDao.insertWater(water);
            gogo.add(String.valueOf(water.amount));
        }

        if (getSupportFragmentManager().findFragmentByTag("SleepFragment") != null) {
            EditText sleepDuration = (EditText) dialog.getDialog().findViewById(R.id.sleep_duration);
            Sleep sleep = new Sleep(Integer.parseInt(sleepDuration.getText().toString()));
            this.sleepDao.insertSleep(sleep);
            this.sleepArrayList.add(String.valueOf(sleep.duration));
        }

        if (getSupportFragmentManager().findFragmentByTag("PlacesFragment") != null) {
            EditText placeName = (EditText) dialog.getDialog().findViewById(R.id.place_name);
            Place place = new Place(placeName.getText().toString());
            this.placeDao.insertPlace(place);
            this.placesArrayList.add(place.name);
        }
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    private void initializeFragmentView(Class fragmentClass, Bundle bundle, String tag) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, fragmentClass, bundle, tag)
                .commit();
    }
}