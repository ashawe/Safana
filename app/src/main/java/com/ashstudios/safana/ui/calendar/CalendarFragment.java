package com.ashstudios.safana.ui.calendar;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.applikeysolutions.cosmocalendar.settings.lists.connected_days.ConnectedDays;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.ashstudios.safana.R;
import com.ashstudios.safana.adapters.TaskAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class CalendarFragment extends Fragment {

    private CalendarView calendarView;
    @SuppressLint("SimpleDateFormat")
    private SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
    private Set<Long> NoWorkdays, Workdays;
    private int noWorkdaysColor = Color.parseColor("#ff0000");
    private int workdaysColor = Color.parseColor("#0000ff");

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalendarViewModel calendarViewModel = ViewModelProviders.of(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);
        //calendar view
        calendarView = root.findViewById(R.id.calendar_view);
        calendarView.setWeekendDays(new HashSet(){{add(Calendar.SUNDAY); add(Calendar.SATURDAY);}});
        calendarView.setWeekendDayTextColor(Color.GREEN);
        calendarView.setSelectedDayBackgroundColor(getResources().getColor(R.color.colorAccent));
        NoWorkdays = new TreeSet<>();
        Workdays = new TreeSet<>();

        RecyclerView recyclerView = root.findViewById(R.id.rv_calendar_task);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //set the adapter
        TaskAdapter taskAdapter = new TaskAdapter(getActivity(), calendarViewModel.getArrayListMutableLiveData());
        recyclerView.setAdapter(taskAdapter);
        return root;
    }

    private void setNoWorkDays(String day) {
        try {
            NoWorkdays.add(f.parse(day).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ConnectedDays connectedDays = new ConnectedDays(NoWorkdays, noWorkdaysColor);
        calendarView.addConnectedDays(connectedDays);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //not working days
        setNoWorkDays("10/09/2019");
        //set working days
        setWorkDays("07/09/2019");
        setWorkDays("11/09/2019");
    }

    private void setWorkDays(String day) {
        try {
            Workdays.add(f.parse(day).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ConnectedDays connectedDays = new ConnectedDays(Workdays, workdaysColor);
        calendarView.addConnectedDays(connectedDays);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
}