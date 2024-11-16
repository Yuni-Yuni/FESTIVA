package com.example.festiva;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.festiva.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity{

    private int data_selected, month_selected, year_selected, hour_start, hour_end, minute_start, minute_end;

    ActivityMainBinding binding;
    boolean statement = false;
    int year, month, day;// Переменные для хранения выбранной дат
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        SharedPreferences sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        setContentView(binding.getRoot());

        boolean isChecked = sharedPreferences.getBoolean("isChecked", false);

        if (isChecked) {
            loadFragment(new This_month_EventFragment());
        } else {
            loadFragment(new HomeFragment());
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.clearFocus();
        bottomNavigationView.setItemIconTintList(null);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.more) {
                    loadFragment(new MoreFragment());
                    return true;
                } else if (id == R.id.profile) {
                    loadFragment(new ProfileFragment());
                    return true;
                }
                return false;
            }
        });

        FloatingActionButton button = findViewById(R.id.fab);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.bottom_dialog_sheet, null);
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();

                TextInputLayout textInputLayout = view1.findViewById(R.id.TextFieldLayout1);

                TextInputEditText editTextEventName = view1.findViewById(R.id.eventName);
                TextInputEditText editTextEventDescription = view1.findViewById(R.id.eventDescription);
                EditText editTextDate = view1.findViewById(R.id.EventData);
                EditText editTextTimeStart = view1.findViewById(R.id.EventStartTime);
                EditText editTextTimeEnd = view1.findViewById(R.id.EventEndTime);

                MaterialButton dismissBtn = view1.findViewById(R.id.dismiss);

                /* Пресеты */

                Calendar calendar = Calendar.getInstance();
                int year_cur = calendar.get(Calendar.YEAR);
                int month_cur = calendar.get(Calendar.MONTH) + 1;
                int day_cur = calendar.get(Calendar.DAY_OF_MONTH);

                data_selected = day_cur;
                month_selected = month_cur;
                year_selected = year_cur;

                editTextDate.setText(day_cur + "." + (month_cur) + "." + year_cur);

                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                if (minute >=30) {
                    hour += 1;
                }
                if (hour>=24){
                    hour = 0;
                }

                hour_start = hour;
                minute_start = 0;
                hour_end = hour + 1;
                minute_end = 0;


                editTextTimeStart.setText(hour + ":00");
                editTextTimeEnd.setText((hour + 1) + ":00");
                /*     */

                editTextDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Получаем текущую дату
                        Calendar calendar = Calendar.getInstance();
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                        day = calendar.get(Calendar.DAY_OF_MONTH);

                        // Создаем и показываем DatePickerDialog
                        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                data_selected = dayOfMonth;
                                month_selected = month + 1;
                                year_selected = year;
                                // Форматируем выбранную дату в строку и устанавливаем в EditText
                                editTextDate.setText(dayOfMonth + "." + (month + 1) + "." + year);
                            }
                        }, year, month, day);

                        datePickerDialog.show();
                    }
                });

                editTextTimeStart.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog;
                        timePickerDialog = new TimePickerDialog(MainActivity.this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                hour_start = selectedHour;
                                minute_start = selectedMinute;
                                statement = true;
                                if (selectedMinute == 0) {
                                    editTextTimeStart.setText( selectedHour + ":00");
                                } else {
                                    editTextTimeStart.setText( selectedHour + ":" + selectedMinute);
                                }

                            }
                        }, hour, minute, true);
                        timePickerDialog.setTitle("Время начала");
                        timePickerDialog.show();
                    }
                });

                editTextTimeEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int hour, minute;
                        if (!statement)
                        {
                        Calendar mcurrentTime = Calendar.getInstance();
                        hour = mcurrentTime.get(Calendar.HOUR_OF_DAY) + 1;
                        minute = mcurrentTime.get(Calendar.MINUTE);
                        } else {
                            hour = hour_start + 1;
                            minute = minute_start;
                        }
                        TimePickerDialog timePickerDialog1;
                        timePickerDialog1 = new TimePickerDialog(MainActivity.this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                hour_end = selectedHour;
                                minute_end = selectedMinute;
                                if (selectedMinute == 0) {
                                    editTextTimeEnd.setText( selectedHour + ":00");
                                } else {
                                    editTextTimeEnd.setText( selectedHour + ":" + selectedMinute);
                                }
                            }
                        }, hour, minute, true);
                        timePickerDialog1.setTitle("Время окончания");
                        timePickerDialog1.show();
                    }
                });

                dismissBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (Objects.requireNonNull(editTextEventName.getText()).toString().isEmpty()) {
                            editTextEventName.setError("Введите название события");
                        } else {
                            MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);

                            myDB.addEvent(editTextEventName.getText().toString().trim(), editTextEventDescription.getText().toString().trim(),
                                    data_selected, month_selected, year_selected, hour_start, minute_start, hour_end, minute_end);

                            //Toast.makeText(MainActivity.this, editTextEventName.getText().toString(), Toast.LENGTH_LONG).show();

                            bottomSheetDialog.dismiss();
                        }
                    }
                });

                bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

                        Fragment currentFragment = getCurrentFragment();
                        if (currentFragment instanceof HomeFragment) {
                            loadFragment(new HomeFragment());
                        } else if (currentFragment instanceof MoreFragment) {
                            loadFragment(new MoreFragment());
                        } else if (currentFragment instanceof ProfileFragment) {
                            loadFragment(new ProfileFragment());
                        } else if (currentFragment instanceof AskQuestionFragment) {
                            loadFragment(new AskQuestionFragment());
                        } else if (currentFragment instanceof PremiumFragment) {
                            loadFragment(new PremiumFragment());
                        } else if (currentFragment instanceof This_month_EventFragment) {
                            loadFragment(new This_month_EventFragment());
                        } else if (currentFragment instanceof This_Week_EventFragment) {
                            loadFragment(new This_Week_EventFragment());
                        } else if (currentFragment instanceof UserGuideFragment) {
                            loadFragment(new UserGuideFragment());
                        } else if (currentFragment instanceof SettingsFragment) {
                            loadFragment(new SettingsFragment());
                        }

                        //Toast.makeText(MainActivity.this, "Закрыто нижнее диалоговое меню", Toast.LENGTH_LONG).show();
                    }
                });/**/
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            editor.putBoolean("isChecked", true);
            editor.apply(); // Или editor.commit()
        }

        if (requestCode == 1) {
            editor.putBoolean("isChecked", false);
            editor.apply(); // Или editor.commit()
        }
        recreate();
    }

    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container , fragment);
        transaction.commit();
    }

    public Fragment getCurrentFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        return fragmentManager.findFragmentById(R.id.fragment_container);
    }

}
