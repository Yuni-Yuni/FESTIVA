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
 * Use the {@link FAQFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FAQFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FAQFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FAQFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FAQFragment newInstance(String param1, String param2) {
        FAQFragment fragment = new FAQFragment();
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
        View view = inflater.inflate(R.layout.fragment_f_a_q, container, false);

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

        TextView howChangePassword = view.findViewById(R.id.howChangePassword);
        TextView howChangePasswordText = view.findViewById(R.id.howChangePasswordText);

        howChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (howChangePasswordText.getVisibility() == View.GONE) {
                    howChangePasswordText.setVisibility(View.VISIBLE);
                } else {
                    howChangePasswordText.setVisibility(View.GONE);
                }
            }
        });

        TextView howRenewSubscription = view.findViewById(R.id.howRenewSubscription);
        TextView howRenewSubscriptionText = view.findViewById(R.id.howRenewSubscriptionText);

        howRenewSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (howRenewSubscriptionText.getVisibility() == View.GONE) {
                    howRenewSubscriptionText.setVisibility(View.VISIBLE);
                } else {
                    howRenewSubscriptionText.setVisibility(View.GONE);
                }
            }
        });

        TextView howTurnOffReminders = view.findViewById(R.id.howTurnOffReminders);
        TextView howTurnOffRemindersText = view.findViewById(R.id.howTurnOffRemindersText);

        howTurnOffReminders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (howTurnOffRemindersText.getVisibility() == View.GONE) {
                    howTurnOffRemindersText.setVisibility(View.VISIBLE);
                } else {
                    howTurnOffRemindersText.setVisibility(View.GONE);
                }
            }
        });

        TextView howAddContactAutomaticGreetings = view.findViewById(R.id.howAddContactAutomaticGreetings);
        TextView howAddContactAutomaticGreetingsText = view.findViewById(R.id.howAddContactAutomaticGreetingsText);

        howAddContactAutomaticGreetings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (howAddContactAutomaticGreetingsText.getVisibility() == View.GONE) {
                    howAddContactAutomaticGreetingsText.setVisibility(View.VISIBLE);
                } else {
                    howAddContactAutomaticGreetingsText.setVisibility(View.GONE);
                }
            }
        });

        TextView howChangeGreetingText = view.findViewById(R.id.howChangeGreetingText);
        TextView howChangeGreetingTextText = view.findViewById(R.id.howChangeGreetingTextText);

        howChangeGreetingText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (howChangeGreetingTextText.getVisibility() == View.GONE) {
                    howChangeGreetingTextText.setVisibility(View.VISIBLE);
                } else {
                    howChangeGreetingTextText.setVisibility(View.GONE);
                }
            }
        });

        TextView howRestoreAccess = view.findViewById(R.id.howRestoreAccess);
        TextView howRestoreAccessText = view.findViewById(R.id.howRestoreAccessText);

        howRestoreAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (howRestoreAccessText.getVisibility() == View.GONE) {
                    howRestoreAccessText.setVisibility(View.VISIBLE);
                } else {
                    howRestoreAccessText.setVisibility(View.GONE);
                }
            }
        });

        TextView howCancelSubscription = view.findViewById(R.id.howCancelSubscription);
        TextView howCancelSubscriptionText = view.findViewById(R.id.howCancelSubscriptionText);

        howCancelSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (howCancelSubscriptionText.getVisibility() == View.GONE) {
                    howCancelSubscriptionText.setVisibility(View.VISIBLE);
                } else {
                    howCancelSubscriptionText.setVisibility(View.GONE);
                }
            }
        });

        TextView howRegularEvents = view.findViewById(R.id.howRegularEvents);
        TextView howRegularEventsText = view.findViewById(R.id.howRegularEventsText);

        howRegularEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (howRegularEventsText.getVisibility() == View.GONE) {
                    howRegularEventsText.setVisibility(View.VISIBLE);
                } else {
                    howRegularEventsText.setVisibility(View.GONE);
                }
            }
        });

        TextView howContactCustomerSupport = view.findViewById(R.id.howContactCustomerSupport);
        TextView howContactCustomerSupportText = view.findViewById(R.id.howContactCustomerSupportText);

        howContactCustomerSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (howContactCustomerSupportText.getVisibility() == View.GONE) {
                    howContactCustomerSupportText.setVisibility(View.VISIBLE);
                } else {
                    howContactCustomerSupportText.setVisibility(View.GONE);
                }
            }
        });

        howChangePasswordText.setVisibility(View.GONE);
        howRenewSubscriptionText.setVisibility(View.GONE);
        howTurnOffRemindersText.setVisibility(View.GONE);
        howAddContactAutomaticGreetingsText.setVisibility(View.GONE);
        howChangeGreetingTextText.setVisibility(View.GONE);
        howRestoreAccessText.setVisibility(View.GONE);
        howCancelSubscriptionText.setVisibility(View.GONE);
        howRegularEventsText.setVisibility(View.GONE);
        howContactCustomerSupportText.setVisibility(View.GONE);

        // Inflate the layout for this fragment
        return view;
    }
}