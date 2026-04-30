package com.semo.cisproject.campushub.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.semo.cisproject.campushub.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.*;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class AdminDashboardFragment extends Fragment {

    private PieChart departmentChart, locationChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_admin_dashboard, container, false);

        departmentChart = view.findViewById(R.id.departmentPieChart);
        locationChart = view.findViewById(R.id.locationPieChart);

        loadDepartmentData();
        loadLocationData();

        return view;
    }

    private void loadDepartmentData() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(40f, "Books"));
        entries.add(new PieEntry(30f, "Clothing"));
        entries.add(new PieEntry(30f, "Electronics"));

        PieDataSet dataSet = new PieDataSet(entries, "Departments");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        PieData data = new PieData(dataSet);

        departmentChart.setData(data);
        departmentChart.invalidate();
    }

    private void loadLocationData() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(50f, "Main Campus"));
        entries.add(new PieEntry(30f, "Dorm Pickup"));
        entries.add(new PieEntry(20f, "Off-Campus"));

        PieDataSet dataSet = new PieDataSet(entries, "Pickup Locations");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        PieData data = new PieData(dataSet);

        locationChart.setData(data);
        locationChart.invalidate();
    }
}