package com.example.festiva;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.switchmaterial.SwitchMaterial;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateEventActivity extends AppCompatActivity {

    EditText title, description, date, startTime, endTime;//date_data, date_month, date_year, startHour, startMinute, endHour, endMinute;
    Button updateButton, deleteButton;

    private String holidayName = "";
    private String fromPerson = "";
    private String toPerson = "";

    String textGreeting;

    SwitchMaterial switchHoliday, switch_Reminder;

    TextView greeting, greetingTitle;

    String id_str, title_str, description_str, date_data_str, date_month_str, date_year_str, startHour_str, startMinute_str, endHour_str, endMinute_str;

    Integer reminder, greeting_ID;
    Integer ins_reminder;
    private int createGreeting;
    private int GreetingID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_event);

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

        greeting = findViewById(R.id.greeting);
        greetingTitle = findViewById(R.id.greetingTitle);

        getAndSetIntentData();

        switchHoliday.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if (!isInternetAvailable()) {
                    // Показать Toast
                    Toast.makeText(UpdateEventActivity.this, "Нет подключения к интернету", Toast.LENGTH_SHORT).show();

                    // Отключить свитч
                    switchHoliday.setChecked(false);
                } else {
                    showHolidayDialog();
                    createGreeting = 1;
                }
            } else {
                createGreeting = 0;
            }
        });

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
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateEventActivity.this);
                //Log.e("createGreeting", String.valueOf(createGreeting));
                if (createGreeting == 1)
                    {
                        ConfirmDialogGenerateGreeting();
                        createGreeting = 0;
                    } else {
                        if (ins_reminder == null){
                            ins_reminder = reminder;
                        }

                        if (greeting_ID != 0){
                            myDB.updateData(id_str, title.getText().toString().trim(), description.getText().toString().trim(),
                                    Integer.parseInt(date_data_str), Integer.parseInt(date_month_str), Integer.parseInt(date_year_str),
                                    Integer.parseInt(startHour_str), Integer.parseInt(startMinute_str), Integer.parseInt(endHour_str), Integer.parseInt(endMinute_str), ins_reminder, greeting_ID);
                        } else {
                            myDB.updateData(id_str, title.getText().toString().trim(), description.getText().toString().trim(),
                                    Integer.parseInt(date_data_str), Integer.parseInt(date_month_str), Integer.parseInt(date_year_str),
                                    Integer.parseInt(startHour_str), Integer.parseInt(startMinute_str), Integer.parseInt(endHour_str), Integer.parseInt(endMinute_str), ins_reminder, 0);
                        }

                        /**/

                        if (ins_reminder == 1)
                        {
                            createNotificationForEvent(UpdateEventActivity.this, Integer.parseInt(id_str));
                        } else {
                            if (isNotificationScheduled(UpdateEventActivity.this, Integer.parseInt(id_str))){
                                cancelNotification(UpdateEventActivity.this, Integer.parseInt(id_str));
                            }
                        }
                        createGreeting = 0;
                        finish();
                }

            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(UpdateEventActivity.this, R.style.CustomDatePickerDialog, new DatePickerDialog.OnDateSetListener() {
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
                        positiveButton.setTextColor(ContextCompat.getColor(UpdateEventActivity.this, R.color.buttons_color));
                        negativeButton.setTextColor(ContextCompat.getColor(UpdateEventActivity.this, R.color.buttons_color));
                    }
                });

                datePickerDialog.show();
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog;
                timePickerDialog = new TimePickerDialog(UpdateEventActivity.this, R.style.CustomTimePickerDialog, new TimePickerDialog.OnTimeSetListener() {
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
                timePickerDialog = new TimePickerDialog(UpdateEventActivity.this, R.style.CustomTimePickerDialog, new TimePickerDialog.OnTimeSetListener() {
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
            greeting_ID = getIntent().getIntExtra("greeting_id", 0);

            Log.e("greeting_ID", String.valueOf(greeting_ID));

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

            if (greeting_ID == 0){
                switchHoliday.setVisibility(View.VISIBLE);
                greeting.setVisibility(View.GONE);
                greetingTitle.setVisibility(View.GONE);
            } else {
                switchHoliday.setVisibility(View.GONE);
                greeting.setVisibility(View.VISIBLE);
                greetingTitle.setVisibility(View.VISIBLE);
                MyDatabaseHelper db = new MyDatabaseHelper(UpdateEventActivity.this);
                Cursor cursor = db.getGreetingBuID(String.valueOf(greeting_ID));

                if (cursor != null && cursor.moveToFirst()) {
                    do {
                        textGreeting = cursor.getString(cursor.getColumnIndexOrThrow("greeting_text"));
                    } while (cursor.moveToNext());
                }
                if (cursor != null) {
                    cursor.close();
                }
                greeting.setText(textGreeting);
            }
        } else {
            //Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }

    }

    public void backToHomeFragment(View view) {
        Intent returnIntent = new Intent();
        setResult(RESULT_OK, returnIntent); // Указываем успешный результат
        finish(); // Завершаем SecondActivity

    }

    void ConfirmDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.ConfirmDialogTheme);
        builder.setTitle("Удалить событие?");
        builder.setMessage("Вы уверены, что хотите удалить событие?");
        builder.setPositiveButton("Да", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateEventActivity.this);
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
                    //Toast.makeText(UpdateEventActivity.this, "Данные сохранены", Toast.LENGTH_SHORT).show();
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
            }/**/
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

        Log.e("AlarmManager", hour + ":" + minute + "   " + day + "." + month + "." + year);

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

    void ConfirmDialogGenerateGreeting(){
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(UpdateEventActivity.this, R.style.ConfirmDialogTheme) //
                .setTitle("Проверьте корректность данных для поздравления!")
                .setMessage("После нажатия кнопки «Изменить», изменить данные для генерации поздравления уже не получится.")
                .setCancelable(false)
                //call button
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateEventActivity.this);

                        GreetingID = saveToDatabase(holidayName, fromPerson, toPerson);
                        generateText(holidayName, fromPerson, toPerson, GreetingID);

                        if (ins_reminder == null){
                            ins_reminder = reminder;
                        }

                        myDB.updateData(id_str, title.getText().toString().trim(), description.getText().toString().trim(),
                                Integer.parseInt(date_data_str), Integer.parseInt(date_month_str), Integer.parseInt(date_year_str),
                                Integer.parseInt(startHour_str), Integer.parseInt(startMinute_str), Integer.parseInt(endHour_str), Integer.parseInt(endMinute_str), ins_reminder, GreetingID);/**/

                        if (ins_reminder == 1)
                        {
                            createNotificationForEvent(UpdateEventActivity.this, Integer.parseInt(id_str));
                        } else {
                            if (isNotificationScheduled(UpdateEventActivity.this, Integer.parseInt(id_str))){
                                cancelNotification(UpdateEventActivity.this, Integer.parseInt(id_str));
                            }
                        }
                        createGreeting = 0;
                        finish();
                    }
                })
                //cancel button
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //что то при кансел
                    }
                }).show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(UpdateEventActivity.this, R.color.buttons_color));
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(UpdateEventActivity.this, R.color.buttons_color));
    }

    private void generateText(String holidayName, String fromPerson, String toPerson, int GreetingID) {
        new Thread(() -> {
            try {
                String url = "https://llm.api.cloud.yandex.net/foundationModels/v1/completion";
                String apiKey = "AQVNxK6uX87oexJoy46jRHmAMikDCbt1HBS7XEGx";

                // Создание JSON-объекта программно
                JSONObject prompt = new JSONObject();
                prompt.put("modelUri", "gpt://b1g96ft3q1np7uts57fu/yandexgpt-lite");

                JSONObject completionOptions = new JSONObject();
                completionOptions.put("stream", false);
                completionOptions.put("temperature", 0.6);
                completionOptions.put("maxTokens", "2000");
                prompt.put("completionOptions", completionOptions);

                JSONArray messages = new JSONArray();
                JSONObject message = new JSONObject();
                message.put("role", "user");

                String input = "Напиши поздравление для " + toPerson + " с " + holidayName;
                if (!fromPerson.isEmpty()){
                    input = input + " от " + fromPerson;
                }
                Log.e("greetingText", input);

                message.put("text", input);
                messages.put(message);
                prompt.put("messages", messages);

                // Преобразование JSON в строку
                String json = prompt.toString();

                // Настройка клиента и запроса
                OkHttpClient client = new OkHttpClient();
                MediaType JSON = MediaType.get("application/json; charset=utf-8");
                RequestBody body = RequestBody.create(json, JSON);
                Request request = new Request.Builder()
                        .url(url)
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization", "Api-Key " + apiKey)
                        .post(body)
                        .build();

                // Выполнение запроса
                Response response = client.newCall(request).execute();
                if (response.isSuccessful() && response.body() != null) {
                    String responseData = response.body().string();

                    String formattedText = extractTextFromJson(responseData);

                    MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateEventActivity.this);

                    // Обновление UI
                    runOnUiThread(() -> myDB.updateGreeting(String.valueOf(GreetingID), formattedText));
                    //runOnUiThread(() -> textView.setText(formattedText));
                } else {
                    //runOnUiThread(() -> textView.setText("Ошибка: " + response.code()));
                }/**/

            } catch (Exception e) {
                //runOnUiThread(() -> textView.setText("Ошибка: " + e.getMessage()));
            }
        }).start();
    }

    private String extractTextFromJson(String jsonResponse) {
        try {
            JSONObject jsonObject = new JSONObject(jsonResponse);
            JSONObject resultObject = jsonObject.getJSONObject("result");
            JSONObject alternativesObject = resultObject.getJSONArray("alternatives").getJSONObject(0);
            JSONObject messageObject = alternativesObject.getJSONObject("message");

            String rawText = messageObject.getString("text");
            return rawText;
        } catch (Exception e) {
            return "Ошибка при обработке ответа: " + e.getMessage();
        }
    }

    private int saveToDatabase(String holidayName, String from, String to) {
        MyDatabaseHelper myDB = new MyDatabaseHelper(UpdateEventActivity.this);

        myDB.addGreeting(holidayName, from, to);

        Cursor cursor = myDB.getGreeting(holidayName, from, to);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                GreetingID = cursor.getInt(cursor.getColumnIndexOrThrow("greeting_id"));
            } while (cursor.moveToNext());
        }
        if (cursor != null) {
            cursor.close();
        }

        return GreetingID;

    }

    private boolean isInternetAvailable() {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager != null) {
            // Получаем активное соединение
            android.net.Network network = connectivityManager.getActiveNetwork();
            if (network == null) {
                return false; // Нет активного соединения
            }

            // Проверяем возможности сети
            android.net.NetworkCapabilities capabilities =
                    connectivityManager.getNetworkCapabilities(network);

            return capabilities != null &&
                    (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET));
        }
        return false;
    }

}