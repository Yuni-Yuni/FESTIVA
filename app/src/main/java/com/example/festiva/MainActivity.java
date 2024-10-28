package com.example.festiva;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.festiva.databinding.ActivityMainBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {



    ActivityMainBinding binding;
    int year, month, day; // Переменные для хранения выбранной дат

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        loadFragment(new HomeFragment());

        binding.bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.more) {
                    loadFragment(new MoreFragment());
                } else if (id == R.id.profile) {
                    loadFragment(new ProfileFragment());
                }

                return true;
            }
        });

        FloatingActionButton button = findViewById(R.id.fab);

        RecyclerView recyclerView = findViewById(R.id.listOfEvents);

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
                                editTextTimeStart.setText( selectedHour + ":" + selectedMinute);
                            }
                        }, hour, minute, true);
                        timePickerDialog.setTitle("Время начала");
                        timePickerDialog.show();
                    }
                });

                editTextTimeEnd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog timePickerDialog1;
                        timePickerDialog1 = new TimePickerDialog(MainActivity.this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                editTextTimeEnd.setText( selectedHour + ":" + selectedMinute);
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
                            /*myDB.addEvent(editTextEventName.getText().toString().trim(), editTextEventDescription.getText().toString().trim(),
                                    Integer.parseInt(editTextDate.getText().toString().trim()), Integer.parseInt(editTextTimeStart.getText().toString().trim()),
                                    Integer.parseInt(editTextTimeEnd.getText().toString().trim()));*/

                            myDB.addEvent(editTextEventName.getText().toString().trim(), editTextEventDescription.getText().toString().trim(),
                                    editTextDate.getText().toString().trim(), editTextTimeStart.getText().toString().trim(),
                                    editTextTimeEnd.getText().toString().trim());


                            Toast.makeText(MainActivity.this, editTextEventName.getText().toString(), Toast.LENGTH_LONG).show();
                            bottomSheetDialog.dismiss();
                        }
                    }
                });

                bottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        Toast.makeText(MainActivity.this, "Событие создано", Toast.LENGTH_LONG).show();

                    }
                });
            }
        });


    }

    private void loadFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container , fragment);
        transaction.commit();
    }


}
