package com.semo.cisproject.campushub.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.semo.cisproject.campushub.R;
import com.semo.cisproject.campushub.activity.MainActivity;
import com.semo.cisproject.campushub.adapter.CategoryAdapter;
import com.semo.cisproject.campushub.adapter.HomeSliderAdapter;
import com.semo.cisproject.campushub.adapter.NewProductAdapter;
import com.semo.cisproject.campushub.adapter.PopularProductAdapter;
import com.semo.cisproject.campushub.util.Data;

import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private ViewPager viewPager;
    private LinearLayout sliderDotspanel;
    private Timer timer;
    private int page_position = 0;
    private int dotscount;
    private ImageView[] dots;
    private Data data;

    private final Integer[] images = {
            R.drawable.slider1,
            R.drawable.slider2,
            R.drawable.slider3,
            R.drawable.slider4,
            R.drawable.slider5
    };

    public HomeFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        data = new Data();

        setupRecyclerViews(view);
        setupSlider(view);

        return view;
    }

    private void setupRecyclerViews(View view) {
        RecyclerView categoryRv = view.findViewById(R.id.category_rv);
        categoryRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        categoryRv.setItemAnimator(new DefaultItemAnimator());
        categoryRv.setAdapter(new CategoryAdapter(data.getCategoryList(), getContext(), "Home"));

        RecyclerView newProductRv = view.findViewById(R.id.new_product_rv);
        newProductRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        newProductRv.setItemAnimator(new DefaultItemAnimator());
        newProductRv.setAdapter(new NewProductAdapter(data.getNewList(), getContext(), "Home"));

        RecyclerView popularProductRv = view.findViewById(R.id.popular_product_rv);
        popularProductRv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        popularProductRv.setItemAnimator(new DefaultItemAnimator());
        popularProductRv.setAdapter(new PopularProductAdapter(data.getPopularList(), getContext(), "Home"));
    }

    private void setupSlider(View view) {
        viewPager = view.findViewById(R.id.viewPager);
        sliderDotspanel = view.findViewById(R.id.SliderDots);

        HomeSliderAdapter adapter = new HomeSliderAdapter(getContext(), images);
        viewPager.setAdapter(adapter);

        dotscount = adapter.getCount();
        dots = new ImageView[dotscount];

        for (int i = 0; i < dotscount; i++) {
            dots[i] = new ImageView(getContext());
            dots[i].setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(8, 0, 8, 0);
            sliderDotspanel.addView(dots[i], params);
        }

        if (dotscount > 0) {
            dots[0].setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.active_dot));
        }

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < dotscount; i++) {
                    dots[i].setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.non_active_dot));
                }
                dots[position].setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.active_dot));
                page_position = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {}
        });

        scheduleSlider();
    }

    private void scheduleSlider() {
        final Handler handler = new Handler();
        final Runnable update = () -> {
            if (page_position >= dotscount) {
                page_position = 0;
            }
            viewPager.setCurrentItem(page_position++, true);
        };

        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(update);
            }
        }, 500, 4000);
    }

    @Override
    public void onStop() {
        if (timer != null) {
            timer.cancel();
        }
        super.onStop();
    }

    @Override
    public void onPause() {
        if (timer != null) {
            timer.cancel();
        }
        super.onPause();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getActivity() != null) {
            getActivity().setTitle("CampusHub Home");
        }
    }
}