package com.knifed.makemefat;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

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

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AddItemDialogFragment.AddItemDialogListener {
    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private ListView listView;
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
                    buildAndShowDialog("Add food", R.layout.dialog_add_food);
                }

                if (getSupportFragmentManager().findFragmentByTag("SleepFragment") != null) {
                    buildAndShowDialog("Add Sleep", R.layout.dialog_add_sleep);
                }

                if (getSupportFragmentManager().findFragmentByTag("PlacesFragment") != null) {
                    buildAndShowDialog("Add Place", R.layout.dialog_add_place);
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

                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, FoodsFragment.class, bundle, "FoodsFragment")
                        .commit();
                break;
            case R.id.action_places:
                this.placesArrayList = new ArrayList<String>();
                for (Place place: this.placeDao.getAll()) {
                    this.placesArrayList.add(place.name);
                }

                Bundle placesBundle = new Bundle();
                placesBundle.putStringArrayList("data", this.placesArrayList);
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, PlacesFragment.class, placesBundle, "PlacesFragment")
                        .commit();
                break;
            case R.id.action_weightin:
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, WeightinFragment.class, null, "WeightinFragment")
                        .commit();
                break;
            case R.id.action_toilet:
                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, ToiletFragment.class, null, "ToiletFragment")
                        .commit();
                break;
            case R.id.action_sleep:
                this.sleeps = this.db.sleepDao().getAll();
                this.sleepArrayList = new ArrayList<String>();
                for (Sleep sleep: this.sleeps) {
                    this.sleepArrayList.add(String.valueOf(sleep.duration));
                }
                Bundle sleepBundle = new Bundle();
                sleepBundle.putStringArrayList("data", this.sleepArrayList);

                getSupportFragmentManager().beginTransaction()
                        .setReorderingAllowed(true)
                        .replace(R.id.fragment_container_view, SleepFragment.class, sleepBundle, "SleepFragment")
                        .commit();
                break;
            case R.id.action_workout:
                initializeFragmentView(WorkoutFragment.class, null, "WorkoutFragment");
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

    private void buildAndShowDialog(String message, int layout) {
        AddItemDialogFragment addItemDialogFragment = new AddItemDialogFragment(message, layout);
        addItemDialogFragment.show(getSupportFragmentManager(), "Dialog");
    }

    private void initializeFragmentView(Class fragmentClass, Bundle bundle, String tag) {
        getSupportFragmentManager().beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.fragment_container_view, fragmentClass, bundle, tag)
                .commit();
    }
}