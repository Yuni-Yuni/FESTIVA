package com.example.festiva;

import android.database.Cursor;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardsAndGreetingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardsAndGreetingsFragment extends Fragment {

    ImageView imageView;
    TextView textEmpty;
    boolean statement = false;

    MyDatabaseHelper myDB;
    ArrayList<String> event_title, greeting_text;
    ArrayList<Integer> event_greeting_id;
    AdapterForGreetings customAdapter;

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

        RecyclerView recyclerView = view.findViewById(R.id.listOfGreetings);

        myDB = new MyDatabaseHelper(getContext());
        event_title = new ArrayList<>();
        greeting_text = new ArrayList<>();
        event_greeting_id = new ArrayList<>();

        customAdapter = new AdapterForGreetings(getActivity(), getContext(), event_title, event_greeting_id, greeting_text);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        readDataFromTableGreetingsForLoad();

        cards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cards.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.text_date));
                cards.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.page_color));
                greetings.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), android.R.color.transparent));
                greetings.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.buttons_color));
                readDataFromTableCards();
                ImageNoDataAppearence(statement);
                textEmpty.setText("Генерация открыток недоступна");
            }
        });

        greetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                greetings.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.text_date));
                greetings.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.page_color));
                cards.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), android.R.color.transparent));
                cards.setTextColor(ContextCompat.getColorStateList(requireContext(), R.color.buttons_color));
                readDataFromTableGreetings();
                ImageNoDataAppearence(statement);
            }
        });

        imageView = (ImageView) view.findViewById(R.id.emptyField);
        textEmpty = (TextView) view.findViewById(R.id.emptyText);
        ImageNoDataAppearence(statement);

        // Inflate the layout for this fragment

        return view;
    }

    void readDataFromTableGreetingsForLoad(){
        Cursor cursor = myDB.getEventAndGreeting();
        if(cursor.getCount() == 0){
            //Toast.makeText(getContext(),"No data.", Toast.LENGTH_SHORT).show();
            statement = true;
        }else{
            statement = false;
            while (cursor.moveToNext()){
                event_title.add(cursor.getString(cursor.getColumnIndexOrThrow("event_name")));
                event_greeting_id.add(cursor.getColumnIndexOrThrow("event_greeting_id"));
                greeting_text.add(cursor.getString(cursor.getColumnIndexOrThrow("greeting_text")));
            }

        }
    }

    void readDataFromTableGreetings(){
        customAdapter.deleteData();
        Cursor cursor = myDB.getEventAndGreeting();
        if(cursor.getCount() == 0){
            //Toast.makeText(getContext(),"No data.", Toast.LENGTH_SHORT).show();
            statement = true;
        }else{
            statement = false;
            while (cursor.moveToNext()){
                event_title.add(cursor.getString(cursor.getColumnIndexOrThrow("event_name")));
                event_greeting_id.add(cursor.getColumnIndexOrThrow("event_greeting_id"));
                greeting_text.add(cursor.getString(cursor.getColumnIndexOrThrow("greeting_text")));
            }
        }

        customAdapter.updateData(event_title, event_greeting_id, greeting_text);
        customAdapter.notifyDataSetChanged();
    }

    void readDataFromTableCards(){
        customAdapter.deleteData();
        statement = true;
        customAdapter.notifyDataSetChanged();
    }

    void ImageNoDataAppearence(boolean statement){
        if (statement){
            imageView.setVisibility(View.VISIBLE);
            textEmpty.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
            textEmpty.setVisibility(View.GONE);
        }
    }
}