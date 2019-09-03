package com.ashstudios.safana.activities;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.ashstudios.safana.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class WorkerDashboardActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ImageView mProfileImage;
    private TextView mTvName,mTvEmail;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_worker_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        //setting nav header items
        View header = navigationView.getHeaderView(0);
        mProfileImage = header.findViewById(R.id.profile_image);
        mTvName = header.findViewById(R.id.nav_name);
        mTvEmail = header.findViewById(R.id.nav_email);
        ColorGenerator colorGenerator = ColorGenerator.MATERIAL;
        int color = colorGenerator.getRandomColor();
        TextDrawable textDrawable = TextDrawable.builder().buildRect("R",color);
        mProfileImage.setImageDrawable(textDrawable);
        mTvName.setText("Rohit Suthar");
        mTvEmail.setText("rohit@suthar.com");
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_tasks, R.id.nav_calendar, R.id.nav_search,
                R.id.nav_leave, R.id.nav_allowance, R.id.nav_project_status)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        navigationView.setItemIconTintList(null);
        //setting icon tint to white for other menu items
        setDefaultIconTint();
        //setting custom icon tint to display project status
        MenuItem favoriteItem = navigationView.getMenu().findItem(R.id.nav_project_status);
        Drawable favoriteIcon = DrawableCompat.wrap(favoriteItem.getIcon());
        ColorStateList colorSelector = ResourcesCompat.getColorStateList(getResources(), R.color.success, getTheme());
        DrawableCompat.setTintList(favoriteIcon, colorSelector);
        favoriteItem.setIcon(favoriteIcon);
    }

    private void setDefaultIconTint() {
        ColorStateList colorSelector1 = ResourcesCompat.getColorStateList(getResources(), android.R.color.white, getTheme());

        MenuItem favoriteItem = navigationView.getMenu().findItem(R.id.nav_tasks);
        Drawable favoriteIcon = DrawableCompat.wrap(favoriteItem.getIcon());
        DrawableCompat.setTintList(favoriteIcon, colorSelector1);
        favoriteItem.setIcon(favoriteIcon);

        MenuItem favoriteItem1 = navigationView.getMenu().findItem(R.id.nav_calendar);
        Drawable favoriteIcon1 = DrawableCompat.wrap(favoriteItem1.getIcon());
        DrawableCompat.setTintList(favoriteIcon1, colorSelector1);
        favoriteItem1.setIcon(favoriteIcon1);

        MenuItem favoriteItem2 = navigationView.getMenu().findItem(R.id.nav_search);
        Drawable favoriteIcon2 = DrawableCompat.wrap(favoriteItem2.getIcon());
        DrawableCompat.setTintList(favoriteIcon2, colorSelector1);
        favoriteItem2.setIcon(favoriteIcon2);

        MenuItem favoriteItem3 = navigationView.getMenu().findItem(R.id.nav_allowance);
        Drawable favoriteIcon3 = DrawableCompat.wrap(favoriteItem3.getIcon());
        DrawableCompat.setTintList(favoriteIcon3, colorSelector1);
        favoriteItem3.setIcon(favoriteIcon3);

        MenuItem favoriteItem4 = navigationView.getMenu().findItem(R.id.nav_leave);
        Drawable favoriteIcon4 = DrawableCompat.wrap(favoriteItem4.getIcon());
        DrawableCompat.setTintList(favoriteIcon4, colorSelector1);
        favoriteItem4.setIcon(favoriteIcon4);

        MenuItem favoriteItem5 = navigationView.getMenu().findItem(R.id.nav_worker_laws);
        Drawable favoriteIcon5 = DrawableCompat.wrap(favoriteItem5.getIcon());
        DrawableCompat.setTintList(favoriteIcon5, colorSelector1);
        favoriteItem5.setIcon(favoriteIcon5);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.worker_dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
