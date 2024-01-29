package com.example.collegescheduler.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.collegescheduler.R;
import com.example.collegescheduler.databinding.FragmentHomeBinding;
import com.example.collegescheduler.ui.home.ListViewTest;

import java.sql.Array;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    ListView simpleList;
    String countryList[] = {"India", "France", "USA", "Switzerland", "UK"};
    String ratingsList[] = {"4/5", "3/5", "5/5", "6/5", "3/5"};

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        simpleList = (ListView)root.findViewById(R.id.list_view);
        CustomAdapter customAdapter = new CustomAdapter(root.getContext(), countryList, ratingsList);
        simpleList.setAdapter(customAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}