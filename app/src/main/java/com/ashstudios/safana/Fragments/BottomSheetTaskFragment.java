package com.ashstudios.safana.Fragments;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;

import com.ashstudios.safana.R;
import com.ashstudios.safana.activities.SupervisorDashboard;
import com.ashstudios.safana.activities.WorkerDashboardActivity;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link BottomSheetTaskFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link BottomSheetTaskFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BottomSheetTaskFragment extends BottomSheetDialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Button button;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public BottomSheetTaskFragment() {
        // Required empty public constructor
    }

    public static BottomSheetTaskFragment newInstance(String param1, String param2) {
        BottomSheetTaskFragment fragment = new BottomSheetTaskFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_bottom_sheet_task, container, false);
        final Chip chip_date = view.findViewById(R.id.chip_sort_date);
        final Chip chip_complete = view.findViewById(R.id.chip_filter_complete);
        final Chip chip_incomplete = view.findViewById(R.id.chip_filter_incomplete);

        // for remembering the sorting. Otherwise default sorting is always displayed not the selected one
        final Bundle sorting = getArguments();
        if( !sorting.isEmpty() )
        {
            chip_date.setChecked(sorting.getBoolean("dateChip"));
            chip_complete.setChecked(sorting.getBoolean("completedChip"));
            chip_incomplete.setChecked(sorting.getBoolean("incompleteChip"));

            if(chip_date.isChecked())
                chip_date.setChipIcon(getResources().getDrawable(R.drawable.ic_sort_by_attributes));
            else
                chip_date.setChipIcon(getResources().getDrawable(R.drawable.ic_sort_by_attributes_interface_button_option));
        }


        chip_date.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    chip_date.setChipIcon(getResources().getDrawable(R.drawable.ic_sort_by_attributes));
                }
                else
                {
                    chip_date.setChipIcon(getResources().getDrawable(R.drawable.ic_sort_by_attributes_interface_button_option));
                }
            }
        });


        button = view.findViewById(R.id.apply_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Getting sorting data;
                boolean dateChip = chip_date.isChecked();
                boolean completeChip = (chip_complete).isChecked();
                boolean incompleteChip = (chip_incomplete).isChecked();

                Bundle b = new Bundle();
                b.putBoolean("dateChip",dateChip);
                b.putBoolean("completedChip",completeChip);
                b.putBoolean("incompleteChip",incompleteChip);
                b.putBoolean("isSupervisor",sorting.getBoolean("isSupervisor"));

                if(sorting.getBoolean("isSupervisor"))
                    ((SupervisorDashboard)getActivity()).onSupervisorTaskSortChange(b);
                else
                    ((WorkerDashboardActivity)getActivity()).onWorkerDetailsSortingChanged(b);
                dismiss();
            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
