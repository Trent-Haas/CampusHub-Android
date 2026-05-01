package com.semo.cisproject.campushub.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.adapter.CategoryAdapter;
import com.semo.cisproject.campushub.adapter.NewProductAdapter;
import com.semo.cisproject.campushub.adapter.PopularProductAdapter;
import com.semo.cisproject.campushub.util.Data;

public class HomeFragment extends Fragment {

    private Data data;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        data = new Data();

        setupRecyclerViews(view);


        return view;
    }

    private void setupRecyclerViews(View view) {
        RecyclerView categoryRv = view.findViewById(R.id.category_rv);
        if (categoryRv != null) {
            categoryRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            categoryRv.setItemAnimator(new DefaultItemAnimator());
            categoryRv.setAdapter(new CategoryAdapter(data.getCategoryList(), getContext(), "Home"));
        }

        RecyclerView newProductRv = view.findViewById(R.id.new_product_rv);
        if (newProductRv != null) {
            newProductRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            newProductRv.setItemAnimator(new DefaultItemAnimator());
            newProductRv.setAdapter(new NewProductAdapter(data.getNewList(), getContext(), "Home"));
        }

        RecyclerView popularProductRv = view.findViewById(R.id.popular_product_rv);
        if (popularProductRv != null) {
            popularProductRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
            popularProductRv.setItemAnimator(new DefaultItemAnimator());
            popularProductRv.setAdapter(new PopularProductAdapter(data.getPopularList(), getContext(), "Home"));
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            getActivity().setTitle("CampusHub Home");
        }
    }
}