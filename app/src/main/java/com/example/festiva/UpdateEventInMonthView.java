package com.example.festiva;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class UpdateEventInMonthView extends AppCompatActivity {

    EditText title, description, date, startTime, endTime;//date_data, date_month, date_year, startHour, startMinute, endHour, endMinute;
    Button updateButton, deleteButton;

    String id_str, title_str, description_str, date_data_str, date_month_str, date_year_str, startHour_str, startMinute_str, endHour_str, endMinute_str;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event_in_month_view);

        Window window = getWindow();
        window.setNavigationBarColor(Color.TRANSPARENT);
        window.setStatusBarColor(Color.TRANSPARENT);

        title = findViewById(R.id.editEventTitle);
        description = findViewById(R.id.editEventDescription);
        date = findViewById(R.id.editEventDate);
        startTime = findViewById(R.id.editEventStartTime);
        endTime = findViewById(R.id.editEventEndTime);
        updateButton = findViewById(R.id.updateButton);
        deleteButton = findViewById(R.id.deleteButton);

        getAndSetIntentData();

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateEventInMonthView.this);

                myDB.updateData(id_str, title.getText().toString().trim(), description.getText().toString().trim(),
                        Integer.parseInt(date_data_str), Integer.parseInt(date_month_str), Integer.parseInt(date_year_str),
                        Integer.parseInt(startHour_str), Integer.parseInt(startMinute_str), Integer.parseInt(endHour_str), Integer.parseInt(endMinute_str));/**/

                finish();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateEventInMonthView.this, R.style.DialogTheme, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        date_data_str = Integer.toString(dayOfMonth);
                        date_month_str = Integer.toString(month + 1);
                        date_year_str = Integer.toString(year);
                        date.setText(dayOfMonth + "." + (month + 1) + "." + year);
                    }
                }, Integer.parseInt(date_year_str), Integer.parseInt(date_month_str) - 1, Integer.parseInt(date_data_str));
                datePickerDialog.show();
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(UpdateEventInMonthView.this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startHour_str = Integer.toString(selectedHour);
                        startMinute_str = Integer.toString(selectedMinute);
                        if (selectedMinute == 0) {
                            startTime.setText( selectedHour + ":00");
                        } else {
                            startTime.setText( selectedHour + ":" + selectedMinute);
                        }
                    }
                }, Integer.parseInt(startHour_str), Integer.parseInt(startMinute_str), true);
                timePickerDialog.setTitle("Время начала");
                timePickerDialog.show();
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(UpdateEventInMonthView.this, R.style.DialogTheme, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endHour_str = Integer.toString(selectedHour);
                        endMinute_str = Integer.toString(selectedMinute);
                        if (selectedMinute == 0) {
                            endTime.setText( selectedHour + ":00");
                        } else {
                            endTime.setText( selectedHour + ":" + selectedMinute);
                        }
                    }
                }, Integer.parseInt(endHour_str), Integer.parseInt(endMinute_str), true);
                timePickerDialog.setTitle("Время окончания");
                timePickerDialog.show();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmDialog();
            }
        });
    }

    void getAndSetIntentData(){
        if (getIntent().hasExtra("id")){

            id_str = getIntent().getStringExtra("id");
            title_str = getIntent().getStringExtra("title");
            description_str = getIntent().getStringExtra("description");
            date_data_str = getIntent().getStringExtra("dateData");
            date_month_str = getIntent().getStringExtra("dateMonth");
            date_year_str = getIntent().getStringExtra("dateYear");
            startHour_str = getIntent().getStringExtra("startTimeHour");
            startMinute_str = getIntent().getStringExtra("startTimeMinute");
            endHour_str = getIntent().getStringExtra("endTimeHour");
            endMinute_str = getIntent().getStringExtra("endTimeMinute");

            //====
            title.setText(title_str);
            description.setText(description_str);
            date.setText(date_data_str + "." + date_month_str + "." + date_year_str);
            startTime.setText(startHour_str + ":" + startMinute_str);
            endTime.setText(endHour_str + ":" + endMinute_str);


        } else {
            //Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }

    }

    public void backToHomeFragment(View view) {
        finish();
    }

    void ConfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.DialogTheme);
        builder.setTitle("Удалить событие?");
        builder.setMessage("Вы уверены, что хотите удалить событие?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateEventInMonthView.this);
                myDB.deleteOneRow(id_str);
                finish();
            }
        });
        builder.setNegativeButton("Нет", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}