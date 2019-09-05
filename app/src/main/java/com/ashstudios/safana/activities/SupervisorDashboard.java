package com.ashstudios.safana.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.ashstudios.safana.Fragments.BottomSheetSortFragment;
import com.ashstudios.safana.Fragments.BottomSheetSortLeaveFragment;
import com.ashstudios.safana.Fragments.BottomSheetTaskFragment;
import com.ashstudios.safana.R;
import com.ashstudios.safana.others.SharedPref;
import com.ashstudios.safana.ui.leave_management.LeaveManagementFragment;
import com.ashstudios.safana.ui.tasks.TasksFragment;
import com.ashstudios.safana.ui.worker_details.WorkerDetailsFragment;
import com.google.android.material.navigation.NavigationView;

public class SupervisorDashboard extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private Bundle workerSortBundle, taskSortBundle;
    private Bundle leaveSortBundle;
    private MenuItem menuItem;
    private LinearLayout linearLayout;
    NavigationView navigationView;
    private SharedPref sharedPref;
    private TextView nav_name, nav_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supervisor_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        linearLayout = findViewById(R.id.ll_logout);
        sharedPref = new SharedPref(SupervisorDashboard.this);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_worker, R.id.nav_project_details, R.id.nav_allowance_management,
                R.id.nav_sup_tasks, R.id.nav_leave_management, R.id.nav_worker_laws)
                .setDrawerLayout(drawer)
                .build();
        final NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        workerSortBundle = new Bundle();
        leaveSortBundle = new Bundle();
        taskSortBundle = new Bundle();
        menuItem = navigationView.getCheckedItem();
        View header = navigationView.getHeaderView(0);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), OwnWorkerProfileActivity.class);
                startActivity(intent);
            }
        });
        nav_name = header.findViewById(R.id.nav_name);
        nav_email = header.findViewById(R.id.nav_email);
        nav_name.setText(sharedPref.getNAME());
        nav_email.setText(sharedPref.getEMAIL());

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPref sharedPref = new SharedPref(getBaseContext());
                sharedPref.logout();
                finish();
            }
        });

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
                if(navigationView.getMenu().findItem(R.id.nav_worker).isChecked())
                {
                    BottomSheetSortFragment bottomSheetFragment = new BottomSheetSortFragment();
                    workerSortBundle = initWorkerBundle();  // for remembering the sorting. Otherwise default sorting is always displayed not the selected one
                    bottomSheetFragment.setArguments(workerSortBundle);
                    bottomSheetFragment.show(getSupportFragmentManager(), "bssf");
                }
                else if (navigationView.getMenu().findItem(R.id.nav_leave_management).isChecked())
                {
                    BottomSheetSortLeaveFragment bottomSheetSortLeaveFragment = new BottomSheetSortLeaveFragment();
                    leaveSortBundle = initLeaveSortBundle();  // for remembering the sorting. Otherwise default sorting is always displayed not the selected one
                    bottomSheetSortLeaveFragment.setArguments(leaveSortBundle);
                    bottomSheetSortLeaveFragment.show(getSupportFragmentManager(), "bsslf");
                }
                else if(navigationView.getMenu().findItem(R.id.nav_sup_tasks).isChecked()) {
                    BottomSheetTaskFragment bottomSheetTaskFragment = new BottomSheetTaskFragment();
                    taskSortBundle = initTaskSortBundle();  // for remembering the sorting. Otherwise default sorting is always displayed not the selected one
                    bottomSheetTaskFragment.setArguments(taskSortBundle);
                    bottomSheetTaskFragment.show(getSupportFragmentManager(), "bstf");
                }
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

    public void onLeaveSortingChanged(Bundle b)
    {
        leaveSortBundle = (Bundle)b.clone();
        LeaveManagementFragment.sort(getBaseContext(),leaveSortBundle);
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

    private Bundle initLeaveSortBundle()
    {
        if(leaveSortBundle.isEmpty())
        {
            leaveSortBundle.putBoolean("nameChip",false);
            leaveSortBundle.putBoolean("dateChip",false);
            leaveSortBundle.putBoolean("maleChip",true);
            leaveSortBundle.putBoolean("femaleChip",true);
            leaveSortBundle.putBoolean("otherChip",true);
//          workerSortBundle.putString("role","all");
//          workerSortBundle.putString("shift","all");
            return leaveSortBundle;
        }
        else
        {
            return leaveSortBundle;
        }
    }
    public void onSupervisorTaskSortChange(Bundle b)
    {
        taskSortBundle = (Bundle)b.clone();
        TasksFragment.sort(getBaseContext(),taskSortBundle);
    }

    private Bundle initTaskSortBundle()
    {
        if(taskSortBundle.isEmpty())
        {
            taskSortBundle.putBoolean("isSupervisor",true);
            taskSortBundle.putBoolean("dateChip",false);
            taskSortBundle.putBoolean("completedChip",false);
            taskSortBundle.putBoolean("incompleteChip",false);
            return taskSortBundle;
        }
        else
        {
            return taskSortBundle;
        }
    }
}
