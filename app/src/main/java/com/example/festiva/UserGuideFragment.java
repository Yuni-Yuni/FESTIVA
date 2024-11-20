package com.example.festiva;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.example.festiva.databinding.FragmentUserGuideBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserGuideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserGuideFragment extends Fragment {

    FragmentUserGuideBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public UserGuideFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment UserGuideFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserGuideFragment newInstance(String param1, String param2) {
        UserGuideFragment fragment = new UserGuideFragment();
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
        View view = inflater.inflate(R.layout.fragment_user_guide, container, false);

        ImageButton homeButton = view.findViewById(R.id.homeButton);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoreFragment moreFragment = new MoreFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, moreFragment);
                transaction.commit();
            }
        });

        Button ApplicationPolicity = view.findViewById(R.id.ApplicationPolicy);

        ApplicationPolicity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ApplicationPolicyFragment applicationPolicyFragment = new ApplicationPolicyFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, applicationPolicyFragment);
                transaction.commit();
            }
        });

        Button FAQ = view.findViewById(R.id.FAQ);

        FAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FAQFragment FAQFragment = new FAQFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, FAQFragment);
                transaction.commit();
            }
        });

        Button MainFunctions = view.findViewById(R.id.MainFunctional);

        MainFunctions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainFunctionsFragment MainFunctionsFragment = new MainFunctionsFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, MainFunctionsFragment);
                transaction.commit();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}