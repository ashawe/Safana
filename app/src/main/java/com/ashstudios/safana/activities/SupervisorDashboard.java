package com.ashstudios.safana.activities;

import android.os.Bundle;

import com.ashstudios.safana.BottomSheetSortFragment;
import com.ashstudios.safana.R;
import com.ashstudios.safana.adapters.WorkerRVAdapter;
import com.ashstudios.safana.others.Msg;
import com.ashstudios.safana.ui.worker_details.WorkerDetailsFragment;
import com.ashstudios.safana.ui.worker_details.WorkerDetailsViewModel;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Adapter;

public class SupervisorDashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Bundle workerSortBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        workerSortBundle = new Bundle();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.supervisor_dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                BottomSheetSortFragment bottomSheetFragment = new BottomSheetSortFragment();
                workerSortBundle = initWorkerBundle();  // for remembering the sorting. Otherwise default sorting is always displayed not the selected one
                bottomSheetFragment.setArguments(workerSortBundle);
                bottomSheetFragment.show(getSupportFragmentManager(), "hello");
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onWorkerDetailsSortingChanged(Bundle b)
    {
        workerSortBundle = (Bundle)b.clone();
        WorkerDetailsFragment.sort(getBaseContext(),workerSortBundle);
    }

    private Bundle initWorkerBundle()
    {
        if(workerSortBundle.isEmpty())
        {
            workerSortBundle.putBoolean("nameChip",false);
            workerSortBundle.putBoolean("maleChip",true);
            workerSortBundle.putBoolean("femaleChip",true);
            workerSortBundle.putBoolean("otherChip",true);
//          workerSortBundle.putString("role","all");
//          workerSortBundle.putString("shift","all");
            return workerSortBundle;
        }
        else
        {
            return workerSortBundle;
        }
    }
}
