package com.ashstudios.safana.ui.project__details;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ashstudios.safana.R;
import com.ashstudios.safana.activities.NewProjectActivity;
import com.ashstudios.safana.ui.projectstatus.ProjectStatusViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import lecho.lib.hellocharts.view.PieChartView;

public class ProjectDetailsFragment extends Fragment {

    private ProjectStatusViewModel projectStatusViewModel;
    private PieChartView pieLineChartView;
    private ProjectDetailsViewModel projectDetailsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        projectDetailsViewModel =
                ViewModelProviders.of(this).get(ProjectDetailsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_project_details, container, false);
        ProgressBar progressBar = root.findViewById(R.id.progress_bar);
        FloatingActionButton fab = root.findViewById(R.id.fab);

        progressBar.setProgress(60);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent generateEmployeeId = new Intent(getContext(), NewProjectActivity.class);
                startActivity(generateEmployeeId);
            }
        });

//        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        LineChartView lineChartView = root.findViewById(R.id.chart);
        pieLineChartView = root.findViewById(R.id.pie_chart);
        pieCharData();
        lineChartView.setInteractive(true);
        lineChartView.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);
        List<PointValue> values = new ArrayList<>();
        values.add(new PointValue(0, 2));
        values.add(new PointValue(1, 4));
        values.add(new PointValue(2, 3));
        values.add(new PointValue(3, 4));

        //In most cased you can call data model methods in builder-pattern-like manner.
        Line line = new Line(values).setColor(Color.BLUE).setCubic(true);
        List<Line> lines = new ArrayList<>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);

        LineChartView chart = new LineChartView(getActivity());
        lineChartView.setLineChartData(data);
        lineChartView.animate();

        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        menu.findItem(R.id.action_settings).setVisible(false);
        super.onPrepareOptionsMenu(menu);
    }


    private void pieCharData() {
        int numValues = 4;

        List<SliceValue> values = new ArrayList<SliceValue>();
        for (int i = 0; i < numValues; ++i) {
            SliceValue sliceValue = new SliceValue((float) Math.random() * 30 + 15, ChartUtils.pickColor());
            values.add(sliceValue);
        }

        //pie chart
        PieChartData data = new PieChartData(values);
        boolean hasLabels = false;
        data.setHasLabels(hasLabels);
        boolean hasLabelForSelected = false;
        data.setHasLabelsOnlyForSelected(hasLabelForSelected);
        boolean hasLabelsOutside = false;
        data.setHasLabelsOutside(hasLabelsOutside);
        boolean hasCenterCircle = false;
        data.setHasCenterCircle(hasCenterCircle);

        boolean isExploded = false;
        if (isExploded) {
            data.setSlicesSpacing(24);
        }

        boolean hasCenterText1 = false;
        if (hasCenterText1) {
            data.setCenterText1("Hello!");

            // Get roboto-italic font.
            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");
            data.setCenterText1Typeface(tf);

            // Get font size from dimens.xml and convert it to sp(library uses sp values).
            data.setCenterText1FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    15));
        }

        boolean hasCenterText2 = false;
        if (hasCenterText2) {
            data.setCenterText2("Charts (Roboto Italic)");

            Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Italic.ttf");

            data.setCenterText2Typeface(tf);
            data.setCenterText2FontSize(ChartUtils.px2sp(getResources().getDisplayMetrics().scaledDensity,
                    20));
        }

        pieLineChartView.setPieChartData(data);
    }

}