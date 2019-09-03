package com.ashstudios.safana.ui.allowances;

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

import com.ashstudios.safana.R;
import com.ashstudios.safana.adapters.AllowanceAdapter;
import com.ashstudios.safana.adapters.TaskAdapter;
import com.ashstudios.safana.models.AllowanceModel;

import java.util.ArrayList;

public class AllowancesFragment extends Fragment {

    private RecyclerView recyclerView;
    private AllowancesViewModel alloweViewModel;
    private AllowanceAdapter allowanceAdapter;
    private ArrayList<AllowanceModel> arrayListMutableLiveData;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        alloweViewModel =
                ViewModelProviders.of(this).get(AllowancesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_allowances, container, false);
        arrayListMutableLiveData = new ArrayList<>();
        getData();
        recyclerView = root.findViewById(R.id.rv_allowances);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
        recyclerView.setFocusable(false);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //set the adapter
        allowanceAdapter = new AllowanceAdapter(getActivity(),arrayListMutableLiveData);
        recyclerView.setAdapter(allowanceAdapter);

        return root;
    }

    private void getData() {
        arrayListMutableLiveData.add(new AllowanceModel(getActivity().getResources().getString(R.string.food),"Food","2 Months"));
        arrayListMutableLiveData.add(new AllowanceModel(getActivity().getResources().getString(R.string.laptop),"Laptop","Lifetime"));
        arrayListMutableLiveData.add(new AllowanceModel(getActivity().getResources().getString(R.string.car),"Vehicle","3 Months"));
        arrayListMutableLiveData.add(new AllowanceModel(getActivity().getResources().getString(R.string.building),"Flat","4 Months"));
        arrayListMutableLiveData.add(new AllowanceModel(getActivity().getResources().getString(R.string.insurence),"Insurance","6 Months"));
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