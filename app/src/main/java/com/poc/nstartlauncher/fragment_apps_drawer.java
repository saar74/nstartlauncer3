package com.poc.nstartlauncher;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_apps_drawer#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class fragment_apps_drawer extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private boolean mbVisible;
    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_apps_drawer.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_apps_drawer newInstance(String param1, String param2) {
        fragment_apps_drawer fragment = new fragment_apps_drawer();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public fragment_apps_drawer() {
        // Required empty public constructor
        mbVisible = false;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        mbVisible = false;
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
        return inflater.inflate(R.layout.fragment_apps_drawer, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



        recyclerView = view.findViewById(R.id.appDrawer_recylerView);

        adapter = new AppsDrawerAdapter(getContext());

        layoutManager = new LinearLayoutManager(getContext());


        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onResume() {
        mbVisible = true;
        super.onResume();
    }

    @Override
    public void onPause() {

        super.onPause();
      //  mbVisible = false;
    }

    @Override
    public void onStop() {

        super.onStop();
        mbVisible = false;
    }

    public boolean getIsVisibleNow() {
        return mbVisible;
    }

}