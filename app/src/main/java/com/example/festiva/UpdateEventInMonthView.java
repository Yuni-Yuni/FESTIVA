package com.example.festiva;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Calendar;

public class UpdateEventInMonthView extends AppCompatActivity {

    EditText title, description, date, startTime, endTime;//date_data, date_month, date_year, startHour, startMinute, endHour, endMinute;
    Button updateButton, deleteButton;

    private String holidayName = "";
    private String fromPerson = "";
    private String toPerson = "";

    SwitchMaterial switchHoliday, switch_Reminder;

    String id_str, title_str, description_str, date_data_str, date_month_str, date_year_str, startHour_str, startMinute_str, endHour_str, endMinute_str;

    Integer reminder;
    Integer ins_reminder;

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

        switchHoliday = findViewById(R.id.switch_card_sender);
        switch_Reminder = findViewById(R.id.switch_Reminder);

        getAndSetIntentData();

        switch_Reminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                ins_reminder = 1;
            } else {
                ins_reminder = 0;
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateEventInMonthView.this);

                if (ins_reminder == null){
                    ins_reminder = reminder;
                }

                myDB.updateData(id_str, title.getText().toString().trim(), description.getText().toString().trim(),
                        Integer.parseInt(date_data_str), Integer.parseInt(date_month_str), Integer.parseInt(date_year_str),
                        Integer.parseInt(startHour_str), Integer.parseInt(startMinute_str), Integer.parseInt(endHour_str), Integer.parseInt(endMinute_str) , ins_reminder);/**/

                if (ins_reminder == 1)
                {
                    createNotificationForEvent(UpdateEventInMonthView.this, Integer.parseInt(id_str));
                } else {
                    if (isNotificationScheduled(UpdateEventInMonthView.this, Integer.parseInt(id_str))){
                        cancelNotification(UpdateEventInMonthView.this, Integer.parseInt(id_str));
                    }
                }
                finish();
            }
        });

        switchHoliday.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                showHolidayDialog();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateEventInMonthView.this, R.style.CustomDatePickerDialog, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                        date_data_str = Integer.toString(dayOfMonth);
                        date_month_str = Integer.toString(month + 1);
                        date_year_str = Integer.toString(year);
                        date.setText(dayOfMonth + "." + (month + 1) + "." + year);
                    }
                }, Integer.parseInt(date_year_str), Integer.parseInt(date_month_str) - 1, Integer.parseInt(date_data_str));

                datePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialogInterface) {
                        Button positiveButton = datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                        Button negativeButton = datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE);

                        // Задаем фон кнопкам
                        positiveButton.setTextColor(ContextCompat.getColor(UpdateEventInMonthView.this, R.color.buttons_color));
                        negativeButton.setTextColor(ContextCompat.getColor(UpdateEventInMonthView.this, R.color.buttons_color));
                    }
                });

                datePickerDialog.show();
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(UpdateEventInMonthView.this, R.style.CustomTimePickerDialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        startHour_str = Integer.toString(selectedHour);
                        startMinute_str = Integer.toString(selectedMinute);
                        if (selectedMinute < 10) {
                            if (selectedMinute == 0)
                            {
                                startTime.setText( selectedHour + ":00");
                            } else {
                                startTime.setText( selectedHour + ":0" + selectedMinute);
                            }
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
                timePickerDialog = new TimePickerDialog(UpdateEventInMonthView.this, R.style.CustomTimePickerDialog, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        endHour_str = Integer.toString(selectedHour);
                        endMinute_str = Integer.toString(selectedMinute);
                        if (selectedMinute < 10) {
                            if (selectedMinute == 0)
                            {
                                endTime.setText( selectedHour + ":00");
                            } else {
                                endTime.setText( selectedHour + ":0" + selectedMinute);
                            }
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
            reminder = getIntent().getIntExtra("reminder", 0);

            //====
            title.setText(title_str);
            description.setText(description_str);
            date.setText(date_data_str + "." + date_month_str + "." + date_year_str);
            startTime.setText(startHour_str + ":" + startMinute_str);
            endTime.setText(endHour_str + ":" + endMinute_str);

            if (reminder == 1){
                switch_Reminder.setChecked(true);
            } else {
                switch_Reminder.setChecked(false);
            }


        } else {
            //Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }

    }

    public void backToHomeFragment(View view) {
        finish();
    }

    void ConfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomTimePickerDialog);
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

    private void showHolidayDialog() {

        LayoutInflater inflater = LayoutInflater.from(this);
        View dialogView = inflater.inflate(R.layout.dialog_for_event_greeting, null);

        EditText inputHolidayName = dialogView.findViewById(R.id.greetingName);
        EditText inputFrom = dialogView.findViewById(R.id.fromWho);
        EditText inputTo = dialogView.findViewById(R.id.toWho);

        inputHolidayName.setText(holidayName);
        inputFrom.setText(fromPerson);
        inputTo.setText(toPerson); // Восстановление данных

        // Создаем диалог
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.CustomTimePickerDialog);
        builder.setTitle("Введите данные для поздравления")
                .setPositiveButton("ОК", null)
                .setNegativeButton("Отмена", (dialog, which) -> {
                    // Сохраняем данные, если пользователь нажал "Отмена"
                    holidayName = inputHolidayName.getText().toString();
                    fromPerson = inputFrom.getText().toString();
                    toPerson = inputTo.getText().toString();
                    switchHoliday.setChecked(false);
                })
                .setOnCancelListener(dialog -> {
                    // Сохраняем данные, если пользователь случайно закрыл диалог
                    holidayName = inputHolidayName.getText().toString();
                    fromPerson = inputFrom.getText().toString();
                    toPerson = inputTo.getText().toString();
                    switchHoliday.setChecked(false);
                });

        // Добавляем поля ввода в диалог
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        // Устанавливаем кастомный слушатель для кнопки "ОК"
        dialog.setOnShowListener(d -> {
            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
                boolean allFieldsValid = true;

                // Проверка каждого поля на пустоту
                if (TextUtils.isEmpty(inputHolidayName.getText())) {
                    inputHolidayName.setError("Введите название праздника");
                    allFieldsValid = false;
                } else {
                    inputHolidayName.setError(null);
                }

                if (TextUtils.isEmpty(inputTo.getText())) {
                    inputTo.setError("Введите получателя поздравления");
                    allFieldsValid = false;
                } else {
                    inputTo.setError(null);
                }

                // Если все поля заполнены, закрываем диалог и сохраняем данные
                if (allFieldsValid) {
                    holidayName = inputHolidayName.getText().toString();
                    fromPerson = inputFrom.getText().toString();
                    toPerson = inputTo.getText().toString();

                    //saveToDatabase(holidayName, fromPerson, toPerson);
                    Toast.makeText(UpdateEventInMonthView.this, "Данные сохранены", Toast.LENGTH_SHORT).show();
                    dialog.dismiss(); // Закрываем диалог
                }
            });
        });

        dialog.show();
    }

    private void cancelNotification(Context context, int eventId) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, eventId, intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
            Log.d("AlarmManager", "Cancelled notification for event ID: " + eventId);
        }
    }

    private boolean isNotificationScheduled(Context context, int eventId) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, eventId, intent, PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE);

        return pendingIntent != null; // Вернёт true, если PendingIntent существует
    }

    public void createNotificationForEvent(Context context, int eventId) {
        // Получение данных события из базы данных
        MyDatabaseHelper db = new MyDatabaseHelper(context);
        Cursor cursor = db.getEventById(eventId); // Метод для получения события по ID

        if (cursor != null && cursor.moveToFirst()) {
            String eventName = cursor.getString(cursor.getColumnIndexOrThrow("event_name"));
            String eventDescription = cursor.getString(cursor.getColumnIndexOrThrow("event_description"));
            int day = cursor.getInt(cursor.getColumnIndexOrThrow("event_data_data"));
            int month = cursor.getInt(cursor.getColumnIndexOrThrow("event_data_month"));
            int year = cursor.getInt(cursor.getColumnIndexOrThrow("event_data_year"));
            int hour = cursor.getInt(cursor.getColumnIndexOrThrow("event_start_time_hour"));
            int minute = cursor.getInt(cursor.getColumnIndexOrThrow("event_start_time_minute"));

            // Установка уведомления, если оно ещё не создано
            if (!isNotificationScheduled(context, eventId)) {
                scheduleNotification(context, eventName, eventDescription, year, month, day, hour, minute, eventId);
                Log.d("AlarmManager", "Notification created for event ID: " + eventId);
            } else {
                Log.d("AlarmManager", "Notification already exists for event ID: " + eventId);
            }
        } else {
            Log.e("AlarmManager", "Event not found in database for ID: " + eventId);
        }

        if (cursor != null) {
            cursor.close();
        }
    }

    public void scheduleNotification(Context context, String title, String message, int year, int month, int day, int hour, int minute, int eventId) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        intent.putExtra("title", title);
        if (!message.isEmpty()) {
            intent.putExtra("message", message);
        }

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, eventId, intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        long triggerTime = calendar.getTimeInMillis();
        if (triggerTime < System.currentTimeMillis()) {
            Log.e("AlarmManager", "Scheduled time is in the past. Alarm not set.");
            return;
        }

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            } else {
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent);
            }
            Log.d("AlarmManager", "Alarm set for: " + calendar.getTime());
        } else {
            Log.e("AlarmManager", "AlarmManager is null. Alarm not set.");
        }
    }

}