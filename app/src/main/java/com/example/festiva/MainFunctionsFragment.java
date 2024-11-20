package com.example.festiva;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainFunctionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFunctionsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainFunctionsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainFunctionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFunctionsFragment newInstance(String param1, String param2) {
        MainFunctionsFragment fragment = new MainFunctionsFragment();
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

        View view = inflater.inflate(R.layout.fragment_main_functions, container, false);

        TextView back = view.findViewById(R.id.backToUserGuide);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserGuideFragment userGuideFragment = new UserGuideFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, userGuideFragment);
                transaction.commit();
            }
        });

        TextView calendarAndEventCreation = view.findViewById(R.id.calendarAndEventCreation);

        TextView calendarAndEventCreationTextPart1 = view.findViewById(R.id.calendarAndEventCreationTextPart1);
        TextView calendarAndEventCreationTextPart2 = view.findViewById(R.id.calendarAndEventCreationTextPart2);
        TextView calendarAndEventCreationTextPart3 = view.findViewById(R.id.calendarAndEventCreationTextPart3);
        TextView calendarAndEventCreationTextPart4 = view.findViewById(R.id.calendarAndEventCreationTextPart4);
        TextView calendarAndEventCreationTextPart5 = view.findViewById(R.id.calendarAndEventCreationTextPart5);
        TextView calendarAndEventCreationTextPart6 = view.findViewById(R.id.calendarAndEventCreationTextPart6);
        TextView calendarAndEventCreationTextPart7 = view.findViewById(R.id.calendarAndEventCreationTextPart7);
        TextView calendarAndEventCreationTextPart8 = view.findViewById(R.id.calendarAndEventCreationTextPart8);
        TextView calendarAndEventCreationTextPart9 = view.findViewById(R.id.calendarAndEventCreationTextPart9);
        TextView calendarAndEventCreationTextPart10 = view.findViewById(R.id.calendarAndEventCreationTextPart10);

        calendarAndEventCreation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (calendarAndEventCreationTextPart1.getVisibility() == View.GONE) {
                    calendarAndEventCreationTextPart1.setVisibility(View.VISIBLE);
                    calendarAndEventCreationTextPart2.setVisibility(View.VISIBLE);
                    calendarAndEventCreationTextPart3.setVisibility(View.VISIBLE);
                    calendarAndEventCreationTextPart4.setVisibility(View.VISIBLE);
                    calendarAndEventCreationTextPart5.setVisibility(View.VISIBLE);
                    calendarAndEventCreationTextPart6.setVisibility(View.VISIBLE);
                    calendarAndEventCreationTextPart7.setVisibility(View.VISIBLE);
                    calendarAndEventCreationTextPart8.setVisibility(View.VISIBLE);
                    calendarAndEventCreationTextPart9.setVisibility(View.VISIBLE);
                    calendarAndEventCreationTextPart10.setVisibility(View.VISIBLE);
                } else {
                    calendarAndEventCreationTextPart1.setVisibility(View.GONE);
                    calendarAndEventCreationTextPart2.setVisibility(View.GONE);
                    calendarAndEventCreationTextPart3.setVisibility(View.GONE);
                    calendarAndEventCreationTextPart4.setVisibility(View.GONE);
                    calendarAndEventCreationTextPart5.setVisibility(View.GONE);
                    calendarAndEventCreationTextPart6.setVisibility(View.GONE);
                    calendarAndEventCreationTextPart7.setVisibility(View.GONE);
                    calendarAndEventCreationTextPart8.setVisibility(View.GONE);
                    calendarAndEventCreationTextPart9.setVisibility(View.GONE);
                    calendarAndEventCreationTextPart10.setVisibility(View.GONE);
                }
            }
        });

        TextView reminder = view.findViewById(R.id.reminder);

        TextView reminderTextPart1 = view.findViewById(R.id.reminderTextPart1);
        TextView reminderTextPart2 = view.findViewById(R.id.reminderTextPart2);
        TextView reminderTextPart3 = view.findViewById(R.id.reminderTextPart3);

        reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (reminderTextPart1.getVisibility() == View.GONE) {
                    reminderTextPart1.setVisibility(View.VISIBLE);
                    reminderTextPart2.setVisibility(View.VISIBLE);
                    reminderTextPart3.setVisibility(View.VISIBLE);
                } else {
                    reminderTextPart1.setVisibility(View.GONE);
                    reminderTextPart2.setVisibility(View.GONE);
                    reminderTextPart3.setVisibility(View.GONE);
                }
            }
        });

        TextView generationGreetingsPostcards = view.findViewById(R.id.generationGreetingsPostcards);

        TextView generationGreetingsPostcardsTextPart1 = view.findViewById(R.id.generationGreetingsPostcardsTextPart1);
        TextView generationGreetingsPostcardsTextPart2 = view.findViewById(R.id.generationGreetingsPostcardsTextPart2);
        TextView generationGreetingsPostcardsTextPart3 = view.findViewById(R.id.generationGreetingsPostcardsTextPart3);
        TextView generationGreetingsPostcardsTextPart4 = view.findViewById(R.id.generationGreetingsPostcardsTextPart4);
        TextView generationGreetingsPostcardsTextPart5 = view.findViewById(R.id.generationGreetingsPostcardsTextPart5);

        generationGreetingsPostcards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (generationGreetingsPostcardsTextPart1.getVisibility() == View.GONE) {
                    generationGreetingsPostcardsTextPart1.setVisibility(View.VISIBLE);
                    generationGreetingsPostcardsTextPart2.setVisibility(View.VISIBLE);
                    generationGreetingsPostcardsTextPart3.setVisibility(View.VISIBLE);
                    generationGreetingsPostcardsTextPart4.setVisibility(View.VISIBLE);
                    generationGreetingsPostcardsTextPart5.setVisibility(View.VISIBLE);
                } else {
                    generationGreetingsPostcardsTextPart1.setVisibility(View.GONE);
                    generationGreetingsPostcardsTextPart2.setVisibility(View.GONE);
                    generationGreetingsPostcardsTextPart3.setVisibility(View.GONE);
                    generationGreetingsPostcardsTextPart4.setVisibility(View.GONE);
                    generationGreetingsPostcardsTextPart5.setVisibility(View.GONE);
                }
            }
        });

        TextView subscriptionManagement = view.findViewById(R.id.subscriptionManagement);

        TextView subscriptionManagementTextPart1 = view.findViewById(R.id.subscriptionManagementTextPart1);
        TextView subscriptionManagementTextPart2 = view.findViewById(R.id.subscriptionManagementTextPart2);
        TextView subscriptionManagementTextPart3 = view.findViewById(R.id.subscriptionManagementTextPart3);

        subscriptionManagement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (subscriptionManagementTextPart1.getVisibility() == View.GONE) {
                    subscriptionManagementTextPart1.setVisibility(View.VISIBLE);
                    subscriptionManagementTextPart2.setVisibility(View.VISIBLE);
                    subscriptionManagementTextPart3.setVisibility(View.VISIBLE);
                } else {
                    subscriptionManagementTextPart1.setVisibility(View.GONE);
                    subscriptionManagementTextPart2.setVisibility(View.GONE);
                    subscriptionManagementTextPart3.setVisibility(View.GONE);
                }
            }
        });

        calendarAndEventCreationTextPart1.setVisibility(View.GONE);
        calendarAndEventCreationTextPart2.setVisibility(View.GONE);
        calendarAndEventCreationTextPart3.setVisibility(View.GONE);
        calendarAndEventCreationTextPart4.setVisibility(View.GONE);
        calendarAndEventCreationTextPart5.setVisibility(View.GONE);
        calendarAndEventCreationTextPart6.setVisibility(View.GONE);
        calendarAndEventCreationTextPart7.setVisibility(View.GONE);
        calendarAndEventCreationTextPart8.setVisibility(View.GONE);
        calendarAndEventCreationTextPart9.setVisibility(View.GONE);
        calendarAndEventCreationTextPart10.setVisibility(View.GONE);
        reminderTextPart1.setVisibility(View.GONE);
        reminderTextPart2.setVisibility(View.GONE);
        reminderTextPart3.setVisibility(View.GONE);
        generationGreetingsPostcardsTextPart1.setVisibility(View.GONE);
        generationGreetingsPostcardsTextPart2.setVisibility(View.GONE);
        generationGreetingsPostcardsTextPart3.setVisibility(View.GONE);
        generationGreetingsPostcardsTextPart4.setVisibility(View.GONE);
        generationGreetingsPostcardsTextPart5.setVisibility(View.GONE);
        subscriptionManagementTextPart1.setVisibility(View.GONE);
        subscriptionManagementTextPart2.setVisibility(View.GONE);
        subscriptionManagementTextPart3.setVisibility(View.GONE);

        // Inflate the layout for this fragment
        return view;
    }
}