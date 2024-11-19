package com.example.festiva;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.button.MaterialButton;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardsAndGreetingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardsAndGreetingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CardsAndGreetingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CardsAndGreetingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardsAndGreetingsFragment newInstance(String param1, String param2) {
        CardsAndGreetingsFragment fragment = new CardsAndGreetingsFragment();
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
        View view = inflater.inflate(R.layout.fragment_cards_and_greetings, container, false);

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

        MaterialButton cards = view.findViewById(R.id.cards);
        MaterialButton greetings = view.findViewById(R.id.greetings);

        cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cards.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.text_date));
                cards.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.page_color));
                greetings.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), android.R.color.transparent));
                greetings.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.buttons_color));
            }
        });

        greetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                greetings.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.text_date));
                greetings.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.page_color));
                cards.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), android.R.color.transparent));
                cards.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.buttons_color));
            }
        });

        // Inflate the layout for this fragment

        return view;
    }
}