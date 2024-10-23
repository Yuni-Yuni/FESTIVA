package com.example.festiva;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.festiva.databinding.FragmentProfileBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    FragmentProfileBinding binding;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        // Программная установка ширины и высоты для LinearLayout
        LinearLayout linearLayout = binding.layoutCircleProfile;

        // Получение размеров экрана
        DisplayMetrics displayMetrics = new DisplayMetrics();
        requireActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int screenWidth = displayMetrics.widthPixels + 800;  // Ширина экрана в пикселях
        int screenHeight = displayMetrics.heightPixels; // Высота экрана в пикселях

        Log.d("ScreenInfo", "Ширина экрана: " + screenWidth + " пикселей");
        Log.d("ScreenInfo", "Высота экрана: " + screenHeight + " пикселей");

        if (linearLayout != null) {
            // Используем FrameLayout.LayoutParams, так как родительский элемент — это FrameLayout

            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    screenWidth, // ширина
                    screenHeight  // высота
            );

            // Преобразуем dp в пиксели
            int marginTopInDp = 220; // например, 16 dp
            int marginTopInPx = (int) TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP, marginTopInDp, getResources().getDisplayMetrics());

            params.setMargins(-400, marginTopInPx, params.rightMargin, params.bottomMargin);
            linearLayout.setLayoutParams(params);
        } else {
            // Лог ошибки, если элемент не найден
            Log.e("FragmentProfile", "LinearLayout не найден в разметке");
        }

        binding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeFragment homeFragment = new HomeFragment();
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, homeFragment);
                transaction.commit();

            }
        });

        /*View view = findViewById(R.id.nutrition_bar_filled);
        LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = newWidth;
        view.setLayoutParams(layoutParams);*/



        // Inflate the layout for this fragment
        return binding.getRoot();
        //return inflater.inflate(R.layout.fragment_profile, container, false);
    }
}