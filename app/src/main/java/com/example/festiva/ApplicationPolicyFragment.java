package com.example.festiva;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ApplicationPolicyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ApplicationPolicyFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ApplicationPolicyFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ApplicationPolicyFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ApplicationPolicyFragment newInstance(String param1, String param2) {
        ApplicationPolicyFragment fragment = new ApplicationPolicyFragment();
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
        View view = inflater.inflate(R.layout.fragment_application_policy, container, false);

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

        TextView aboutCompany = view.findViewById(R.id.aboutCompany);

        TextView aboutCompanyText = view.findViewById(R.id.aboutCompanyText);
        TextView contacts = view.findViewById(R.id.contacts);

        aboutCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (aboutCompanyText.getVisibility() == View.GONE) {
                    aboutCompanyText.setVisibility(View.VISIBLE);
                    contacts.setVisibility(View.VISIBLE);
                } else {
                    aboutCompanyText.setVisibility(View.GONE);
                    contacts.setVisibility(View.GONE);
                }
            }
        });

        TextView dataAndPrivacy = view.findViewById(R.id.dataAndPrivacy);

        TextView dataAndPrivacyText = view.findViewById(R.id.dataAndPrivacyText);

        dataAndPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (dataAndPrivacyText.getVisibility() == View.GONE) {
                    dataAndPrivacyText.setVisibility(View.VISIBLE);
                } else {
                    dataAndPrivacyText.setVisibility(View.GONE);
                }
            }
        });

        TextView installationAndRegistration = view.findViewById(R.id.installationAndRegistration);

        TextView installationAndRegistrationTextTitle1 = view.findViewById(R.id.installationAndRegistrationTextTitle1);

        TextView installationAndRegistrationTextTitle1Part1 = view.findViewById(R.id.installationAndRegistrationTextTitle1Part1);
        TextView installationAndRegistrationTextTitle1Part2 = view.findViewById(R.id.installationAndRegistrationTextTitle1Part2);
        TextView installationAndRegistrationTextTitle1Part3 = view.findViewById(R.id.installationAndRegistrationTextTitle1Part3);
        TextView installationAndRegistrationTextTitle1Part4 = view.findViewById(R.id.installationAndRegistrationTextTitle1Part4);

        TextView installationAndRegistrationTextTitle2 = view.findViewById(R.id.installationAndRegistrationTextTitle2);

        TextView installationAndRegistrationTextTitle2Subtitle1 = view.findViewById(R.id.installationAndRegistrationTextTitle2Subtitle1);

        TextView installationAndRegistrationTextTitle2Subtitle1Part1 = view.findViewById(R.id.installationAndRegistrationTextTitle2Subtitle1Part1);
        TextView installationAndRegistrationTextTitle2Subtitle1Part2 = view.findViewById(R.id.installationAndRegistrationTextTitle2Subtitle1Part2);
        TextView installationAndRegistrationTextTitle2Subtitle1Part3 = view.findViewById(R.id.installationAndRegistrationTextTitle2Subtitle1Part3);
        TextView installationAndRegistrationTextTitle2Subtitle1Part4 = view.findViewById(R.id.installationAndRegistrationTextTitle2Subtitle1Part4);

        TextView installationAndRegistrationTextTitle2Subtitle2 = view.findViewById(R.id.installationAndRegistrationTextTitle2Subtitle2);

        TextView installationAndRegistrationTextTitle2Subtitle2Part1 = view.findViewById(R.id.installationAndRegistrationTextTitle2Subtitle2Part1);
        TextView installationAndRegistrationTextTitle2Subtitle2Part2 = view.findViewById(R.id.installationAndRegistrationTextTitle2Subtitle2Part2);
        TextView installationAndRegistrationTextTitle2Subtitle2Part3 = view.findViewById(R.id.installationAndRegistrationTextTitle2Subtitle2Part3);

        TextView installationAndRegistrationTextTitle3 = view.findViewById(R.id.installationAndRegistrationTextTitle3);

        TextView installationAndRegistrationTextTitle3Part1 = view.findViewById(R.id.installationAndRegistrationTextTitle3Part1);
        TextView installationAndRegistrationTextTitle3Part2 = view.findViewById(R.id.installationAndRegistrationTextTitle3Part2);
        TextView installationAndRegistrationTextTitle3Part3 = view.findViewById(R.id.installationAndRegistrationTextTitle3Part3);

        installationAndRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (installationAndRegistrationTextTitle1.getVisibility() == View.GONE){
                    installationAndRegistrationTextTitle1.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle1Part1.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle1Part2.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle1Part3.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle1Part4.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle2.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle2Subtitle1.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle2Subtitle1Part1.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle2Subtitle1Part2.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle2Subtitle1Part3.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle2Subtitle1Part4.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle2Subtitle2.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle2Subtitle2Part1.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle2Subtitle2Part2.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle2Subtitle2Part3.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle3.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle3Part1.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle3Part2.setVisibility(View.VISIBLE);
                    installationAndRegistrationTextTitle3Part3.setVisibility(View.VISIBLE);
                } else {
                    installationAndRegistrationTextTitle1.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle1Part1.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle1Part2.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle1Part3.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle1Part4.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle2.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle2Subtitle1.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle2Subtitle1Part1.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle2Subtitle1Part2.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle2Subtitle1Part3.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle2Subtitle1Part4.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle2Subtitle2.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle2Subtitle2Part1.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle2Subtitle2Part2.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle2Subtitle2Part3.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle3.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle3Part1.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle3Part2.setVisibility(View.GONE);
                    installationAndRegistrationTextTitle3Part3.setVisibility(View.GONE);
                }
            }
        });

        TextView accountAndSettings = view.findViewById(R.id.accountAndSettings);

        TextView accountAndSettingsTextTitle1 = view.findViewById(R.id.accountAndSettingsTextTitle1);
        TextView accountAndSettingsTextTitle1Part1 = view.findViewById(R.id.accountAndSettingsTextTitle1Part1);
        TextView accountAndSettingsTextTitle1Part2 = view.findViewById(R.id.accountAndSettingsTextTitle1Part2);
        TextView accountAndSettingsTextTitle1Part3 = view.findViewById(R.id.accountAndSettingsTextTitle1Part3);
        TextView accountAndSettingsTextTitle2 = view.findViewById(R.id.accountAndSettingsTextTitle2);
        TextView accountAndSettingsTextTitle2Part1 = view.findViewById(R.id.accountAndSettingsTextTitle2Part1);
        TextView accountAndSettingsTextTitle2Part2 = view.findViewById(R.id.accountAndSettingsTextTitle2Part2);
        TextView accountAndSettingsTextTitle2Part3 = view.findViewById(R.id.accountAndSettingsTextTitle2Part3);
        TextView accountAndSettingsTextTitle3 = view.findViewById(R.id.accountAndSettingsTextTitle3);
        TextView accountAndSettingsTextTitle3Part1 = view.findViewById(R.id.accountAndSettingsTextTitle3Part1);
        TextView accountAndSettingsTextTitle4 = view.findViewById(R.id.accountAndSettingsTextTitle4);
        TextView accountAndSettingsTextTitle4Part1 = view.findViewById(R.id.accountAndSettingsTextTitle4Part1);
        TextView accountAndSettingsTextTitle4Part2 = view.findViewById(R.id.accountAndSettingsTextTitle4Part2);

        accountAndSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (accountAndSettingsTextTitle1.getVisibility() == View.GONE){
                    accountAndSettingsTextTitle1.setVisibility(View.VISIBLE);
                    accountAndSettingsTextTitle1Part1.setVisibility(View.VISIBLE);
                    accountAndSettingsTextTitle1Part2.setVisibility(View.VISIBLE);
                    accountAndSettingsTextTitle1Part3.setVisibility(View.VISIBLE);
                    accountAndSettingsTextTitle2.setVisibility(View.VISIBLE);
                    accountAndSettingsTextTitle2Part1.setVisibility(View.VISIBLE);
                    accountAndSettingsTextTitle2Part2.setVisibility(View.VISIBLE);
                    accountAndSettingsTextTitle2Part3.setVisibility(View.VISIBLE);
                    accountAndSettingsTextTitle3.setVisibility(View.VISIBLE);
                    accountAndSettingsTextTitle3Part1.setVisibility(View.VISIBLE);
                    accountAndSettingsTextTitle4.setVisibility(View.VISIBLE);
                    accountAndSettingsTextTitle4Part1.setVisibility(View.VISIBLE);
                    accountAndSettingsTextTitle4Part2.setVisibility(View.VISIBLE);
                } else {
                    accountAndSettingsTextTitle1.setVisibility(View.GONE);
                    accountAndSettingsTextTitle1Part1.setVisibility(View.GONE);
                    accountAndSettingsTextTitle1Part2.setVisibility(View.GONE);
                    accountAndSettingsTextTitle1Part3.setVisibility(View.GONE);
                    accountAndSettingsTextTitle2.setVisibility(View.GONE);
                    accountAndSettingsTextTitle2Part1.setVisibility(View.GONE);
                    accountAndSettingsTextTitle2Part2.setVisibility(View.GONE);
                    accountAndSettingsTextTitle2Part3.setVisibility(View.GONE);
                    accountAndSettingsTextTitle3.setVisibility(View.GONE);
                    accountAndSettingsTextTitle3Part1.setVisibility(View.GONE);
                    accountAndSettingsTextTitle4.setVisibility(View.GONE);
                    accountAndSettingsTextTitle4Part1.setVisibility(View.GONE);
                    accountAndSettingsTextTitle4Part2.setVisibility(View.GONE);
                }
            }
        });

        TextView supportAndFeedback = view.findViewById(R.id.supportAndFeedback);

        TextView supportAndFeedbackTextPart1 = view.findViewById(R.id.supportAndFeedbackTextPart1);
        TextView supportAndFeedbackTextPart2 = view.findViewById(R.id.supportAndFeedbackTextPart2);
        TextView supportAndFeedbackTextPart3 = view.findViewById(R.id.supportAndFeedbackTextPart3);
        TextView supportAndFeedbackTextPart4 = view.findViewById(R.id.supportAndFeedbackTextPart4);
        TextView supportAndFeedbackTextPart5 = view.findViewById(R.id.supportAndFeedbackTextPart5);
        TextView supportAndFeedbackTextPart6 = view.findViewById(R.id.supportAndFeedbackTextPart6);
        TextView supportAndFeedbackTextPart7 = view.findViewById(R.id.supportAndFeedbackTextPart7);
        TextView supportAndFeedbackTextPart8 = view.findViewById(R.id.supportAndFeedbackTextPart8);
        TextView supportAndFeedbackTextPart9 = view.findViewById(R.id.supportAndFeedbackTextPart9);
        TextView supportAndFeedbackTextPart10 = view.findViewById(R.id.supportAndFeedbackTextPart10);
        TextView supportAndFeedbackTextPart11 = view.findViewById(R.id.supportAndFeedbackTextPart11);

        supportAndFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (supportAndFeedbackTextPart1.getVisibility() == View.GONE){
                    supportAndFeedbackTextPart1.setVisibility(View.VISIBLE);
                    supportAndFeedbackTextPart2.setVisibility(View.VISIBLE);
                    supportAndFeedbackTextPart3.setVisibility(View.VISIBLE);
                    supportAndFeedbackTextPart4.setVisibility(View.VISIBLE);
                    supportAndFeedbackTextPart5.setVisibility(View.VISIBLE);
                    supportAndFeedbackTextPart6.setVisibility(View.VISIBLE);
                    supportAndFeedbackTextPart7.setVisibility(View.VISIBLE);
                    supportAndFeedbackTextPart8.setVisibility(View.VISIBLE);
                    supportAndFeedbackTextPart9.setVisibility(View.VISIBLE);
                    supportAndFeedbackTextPart10.setVisibility(View.VISIBLE);
                    accountAndSettingsTextTitle4.setVisibility(View.VISIBLE);
                } else {
                    supportAndFeedbackTextPart1.setVisibility(View.GONE);
                    supportAndFeedbackTextPart2.setVisibility(View.GONE);
                    supportAndFeedbackTextPart3.setVisibility(View.GONE);
                    supportAndFeedbackTextPart4.setVisibility(View.GONE);
                    supportAndFeedbackTextPart5.setVisibility(View.GONE);
                    supportAndFeedbackTextPart6.setVisibility(View.GONE);
                    supportAndFeedbackTextPart7.setVisibility(View.GONE);
                    supportAndFeedbackTextPart8.setVisibility(View.GONE);
                    supportAndFeedbackTextPart9.setVisibility(View.GONE);
                    supportAndFeedbackTextPart10.setVisibility(View.GONE);
                    supportAndFeedbackTextPart11.setVisibility(View.GONE);
                }
            }
        });

        installationAndRegistrationTextTitle1.setVisibility(View.GONE);
        installationAndRegistrationTextTitle1Part1.setVisibility(View.GONE);
        installationAndRegistrationTextTitle1Part2.setVisibility(View.GONE);
        installationAndRegistrationTextTitle1Part3.setVisibility(View.GONE);
        installationAndRegistrationTextTitle1Part4.setVisibility(View.GONE);
        installationAndRegistrationTextTitle2.setVisibility(View.GONE);
        installationAndRegistrationTextTitle2Subtitle1.setVisibility(View.GONE);
        installationAndRegistrationTextTitle2Subtitle1Part1.setVisibility(View.GONE);
        installationAndRegistrationTextTitle2Subtitle1Part2.setVisibility(View.GONE);
        installationAndRegistrationTextTitle2Subtitle1Part3.setVisibility(View.GONE);
        installationAndRegistrationTextTitle2Subtitle1Part4.setVisibility(View.GONE);
        installationAndRegistrationTextTitle2Subtitle2.setVisibility(View.GONE);
        installationAndRegistrationTextTitle2Subtitle2Part1.setVisibility(View.GONE);
        installationAndRegistrationTextTitle2Subtitle2Part2.setVisibility(View.GONE);
        installationAndRegistrationTextTitle2Subtitle2Part3.setVisibility(View.GONE);
        installationAndRegistrationTextTitle3.setVisibility(View.GONE);
        installationAndRegistrationTextTitle3Part1.setVisibility(View.GONE);
        installationAndRegistrationTextTitle3Part2.setVisibility(View.GONE);
        installationAndRegistrationTextTitle3Part3.setVisibility(View.GONE);

        accountAndSettingsTextTitle1.setVisibility(View.GONE);
        accountAndSettingsTextTitle1Part1.setVisibility(View.GONE);
        accountAndSettingsTextTitle1Part2.setVisibility(View.GONE);
        accountAndSettingsTextTitle1Part3.setVisibility(View.GONE);
        accountAndSettingsTextTitle2.setVisibility(View.GONE);
        accountAndSettingsTextTitle2Part1.setVisibility(View.GONE);
        accountAndSettingsTextTitle2Part2.setVisibility(View.GONE);
        accountAndSettingsTextTitle2Part3.setVisibility(View.GONE);
        accountAndSettingsTextTitle3.setVisibility(View.GONE);
        accountAndSettingsTextTitle3Part1.setVisibility(View.GONE);
        accountAndSettingsTextTitle4.setVisibility(View.GONE);
        accountAndSettingsTextTitle4Part1.setVisibility(View.GONE);
        accountAndSettingsTextTitle4Part2.setVisibility(View.GONE);

        supportAndFeedbackTextPart1.setVisibility(View.GONE);
        supportAndFeedbackTextPart2.setVisibility(View.GONE);
        supportAndFeedbackTextPart3.setVisibility(View.GONE);
        supportAndFeedbackTextPart4.setVisibility(View.GONE);
        supportAndFeedbackTextPart5.setVisibility(View.GONE);
        supportAndFeedbackTextPart6.setVisibility(View.GONE);
        supportAndFeedbackTextPart7.setVisibility(View.GONE);
        supportAndFeedbackTextPart8.setVisibility(View.GONE);
        supportAndFeedbackTextPart9.setVisibility(View.GONE);
        supportAndFeedbackTextPart10.setVisibility(View.GONE);
        supportAndFeedbackTextPart11.setVisibility(View.GONE);

        // Inflate the layout for this fragment
        return view;
    }
}
