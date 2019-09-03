package com.ashstudios.safana.ui.calendar;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
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

    private CalendarViewModel calendarViewModel;
    private RecyclerView recyclerView;
    private CalendarView calendarView;
    private TaskAdapter taskAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        calendarViewModel =
                ViewModelProviders.of(this).get(CalendarViewModel.class);
        View root = inflater.inflate(R.layout.fragment_calendar, container, false);

        //calendar view
        calendarView = root.findViewById(R.id.calendar_view);
        calendarView.setWeekendDays(new HashSet(){{add(Calendar.SUNDAY); add(Calendar.SATURDAY);}});
        calendarView.setWeekendDayTextColor(Color.GREEN);
        calendarView.setSelectedDayBackgroundColor(getResources().getColor(R.color.colorAccent));
        //set not working days
        setNoWorkDays("10/09/2019");
        setNoWorkDays("12/09/2019");
        //set working days
        setWorkDays("07/09/2019");
        setWorkDays("09/09/2019");
        setWorkDays("11/09/2019");

        recyclerView =  root.findViewById(R.id.rv_calendar_task);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //set the adapter
        taskAdapter = new TaskAdapter(getActivity(),calendarViewModel.getArrayListMutableLiveData());
        recyclerView.setAdapter(taskAdapter);
        return root;
    }

    private void setNoWorkDays(String day) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Set<Long> days = new TreeSet<>();
        try {
            days.add(f.parse(day).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int textColor = Color.parseColor("#ff0000");
        int selectedTextColor = Color.parseColor("#ff4000");
        ConnectedDays connectedDays = new ConnectedDays(days, textColor, selectedTextColor);
        calendarView.addConnectedDays(connectedDays);
    }

    private void setWorkDays(String day) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
        Set<Long> days = new TreeSet<>();
        try {
            days.add(f.parse(day).getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int textColor = Color.parseColor("#0000FF");
        ConnectedDays connectedDays = new ConnectedDays(days, textColor);
        calendarView.addConnectedDays(connectedDays);
    }
}