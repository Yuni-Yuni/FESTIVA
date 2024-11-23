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
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity{

    private static final int RC_NOTIFICATION = 99;
    private int data_selected, month_selected, year_selected, hour_start, hour_end, minute_start, minute_end;

    BottomSheetDialog bottomSheetDialog;
    TextInputEditText editTextEventName;
    TextInputEditText editTextEventDescription;

    boolean statement = false;
    int year, month, day;// Переменные для хранения выбранной дат

    SharedPreferences.Editor editor;
    SharedPreferences sharedPreferences;

    MyDatabaseHelper db;

    SwitchMaterial switchHoliday, switch_Reminder;
    private String holidayName = "";
    private String fromPerson = "";
    private String toPerson = "";
    private String greetingText = "";

    private int RemindMe;
    private int createGreeting;
    private int GreetingID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        setContentView(R.layout.activity_main);

        Window window = getWindow();
        window.setNavigationBarColor(Color.TRANSPARENT);
        window.setStatusBarColor(Color.TRANSPARENT);

        createNotificationChannel(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU){
            requestPermissions(new String[]{"android.permission.POST_NOTIFICATIONS"}, RC_NOTIFICATION);
        }

        int isChecked = sharedPreferences.getInt("isChecked", 1);
        //Log.e("isCheckedNew", String.valueOf(isChecked));

        if (isChecked == 2) {
            loadFragment(new This_month_EventFragment());
            editor.putInt("isChecked", 1);
            editor.apply(); // Или editor.commit()
        } else if (isChecked == 1) {
            loadFragment(new HomeFragment());
            editor.putInt("isChecked", 1);
            editor.apply(); // Или editor.commit()
        } else {
            loadFragment(new CardsAndGreetingsFragment());
            editor.putInt("isChecked", 1);
            editor.apply(); // Или editor.commit()
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
                    editor.putBoolean("lastOrFuture", false);
                    editor.apply();
                    return true;
                } else if (id == R.id.profile) {
                    loadFragment(new ProfileFragment());
                    editor.putBoolean("lastOrFuture", false);
                    editor.apply();
                    return true;
                }
                return false;
            }
        });

        FloatingActionButton button = findViewById(R.id.fab);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holidayName = "";
                fromPerson = "";
                toPerson = "";

                bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                View view1 = LayoutInflater.from(MainActivity.this).inflate(R.layout.bottom_dialog_sheet, null);
                bottomSheetDialog.setContentView(view1);
                bottomSheetDialog.show();

                TextInputLayout textInputLayout = view1.findViewById(R.id.TextFieldLayout1);

                editTextEventName = view1.findViewById(R.id.eventName);
                editTextEventDescription = view1.findViewById(R.id.eventDescription);
                EditText editTextDate = view1.findViewById(R.id.EventData);
                EditText editTextTimeStart = view1.findViewById(R.id.EventStartTime);
                EditText editTextTimeEnd = view1.findViewById(R.id.EventEndTime);

                switchHoliday = view1.findViewById(R.id.switch_card_sender);
                Log.e("switchHoliday", String.valueOf(createGreeting));

                switchHoliday.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        showHolidayDialog();
                        createGreeting = 1;
                    } else {
                        createGreeting = 0;
                    }
                });

                switch_Reminder = view1.findViewById(R.id.switch_Reminder);

                switch_Reminder.setOnCheckedChangeListener((buttonView, isChecked) -> {
                    if (isChecked) {
                        RemindMe = 1;
                    } else {
                        RemindMe = 0;
                    }
                });

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
                    editTextDate.setText(day_cur+1 + "." + (month_cur) + "." + year_cur);
                }

                hour_start = hour;
                minute_start = 0;
                hour_end = hour + 1;
                minute_end = 0;

                editTextTimeStart.setText(hour + ":00");
                editTextTimeEnd.setText((hour + 1) + ":00");

                /*        */

                editTextDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Получаем текущую дату
                        Calendar calendar = Calendar.getInstance();
                        year = calendar.get(Calendar.YEAR);
                        month = calendar.get(Calendar.MONTH);
                        day = calendar.get(Calendar.DAY_OF_MONTH);

                        // Создаем и показываем DatePickerDialog
                        DatePickerDialog datePickerDialog = new DatePickerDialog(MainActivity.this, R.style.CustomDatePickerDialog, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                data_selected = dayOfMonth;
                                month_selected = month + 1;
                                year_selected = year;
                                // Форматируем выбранную дату в строку и устанавливаем в EditText
                                editTextDate.setText(dayOfMonth + "." + (month + 1) + "." + year);
                            }
                        }, year, month, day);

                        datePickerDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                            @Override
                            public void onShow(DialogInterface dialogInterface) {
                                Button positiveButton = datePickerDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                                Button negativeButton = datePickerDialog.getButton(DialogInterface.BUTTON_NEGATIVE);

                                // Задаем фон кнопкам
                                positiveButton.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.buttons_color));
                                negativeButton.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.buttons_color));
                            }
                        });

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
                        timePickerDialog = new TimePickerDialog(MainActivity.this, R.style.CustomTimePickerDialog, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                hour_start = selectedHour;
                                minute_start = selectedMinute;
                                statement = true;
                                if (selectedMinute < 10) {
                                    if (selectedMinute == 0)
                                    {
                                        editTextTimeStart.setText( selectedHour + ":00");
                                    } else {
                                        editTextTimeStart.setText( selectedHour + ":0" + selectedMinute);
                                    }
                                }
                                else {
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
                        timePickerDialog1 = new TimePickerDialog(MainActivity.this, R.style.CustomTimePickerDialog, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                hour_end = selectedHour;
                                minute_end = selectedMinute;
                                if (selectedMinute < 10) {
                                    if (selectedMinute == 0)
                                    {
                                        editTextTimeEnd.setText( selectedHour + ":00");
                                    } else {
                                        editTextTimeEnd.setText( selectedHour + ":0" + selectedMinute);
                                    }
                                }
                                else {
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
                            if (createGreeting == 1)
                            {
                                ConfirmDialog();
                                createGreeting = 0;
                            } else {
                                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);

                                myDB.addEvent(editTextEventName.getText().toString().trim(), editTextEventDescription.getText().toString().trim(),
                                        data_selected, month_selected, year_selected, hour_start, minute_start, hour_end, minute_end, RemindMe, createGreeting);

                                bottomSheetDialog.dismiss();
                                createGreeting = 0;
                            }
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
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2) {
            editor.putInt("isChecked", 2);
            editor.apply(); // Или editor.commit()
            boolean lastOrFuture = sharedPreferences.getBoolean("lastOrFuture", false);
            if (lastOrFuture){
                editor.putBoolean("lastOrFuture", true);
                editor.apply();
            } else {
                editor.putBoolean("lastOrFuture", false);
                editor.apply();
            }
        }

        if (requestCode == 5) {
            editor.putInt("isChecked", 5);
            editor.apply(); // Или editor.commit()
        }

        if (requestCode == 1) {
            editor.putInt("isChecked", 1);
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

    private void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            String channelId = "test_channel_id";
            String channelName = "Test Channel";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            channel.setDescription("Test notifications");
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == RC_NOTIFICATION){
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(this, "ALLOWED", Toast.LENGTH_SHORT).show();
            } else {
                //Toast.makeText(this, "DENIED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isNotificationScheduled(Context context, int eventId) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                context, eventId, intent, PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE);

        return pendingIntent != null; // Вернёт true, если PendingIntent существует
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

    public void CreateNotification(Context context){
        db = new MyDatabaseHelper(context);

        // Получите все события из базы данных
        Cursor cursor = db.readAllData();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                String eventName = cursor.getString(cursor.getColumnIndexOrThrow("event_name"));
                String eventDescription = cursor.getString(cursor.getColumnIndexOrThrow("event_description"));
                int day = cursor.getInt(cursor.getColumnIndexOrThrow("event_data_data"));
                int month = cursor.getInt(cursor.getColumnIndexOrThrow("event_data_month"));
                int year = cursor.getInt(cursor.getColumnIndexOrThrow("event_data_year"));
                int startHour = cursor.getInt(cursor.getColumnIndexOrThrow("event_start_time_hour"));
                int startMinute = cursor.getInt(cursor.getColumnIndexOrThrow("event_start_time_minute"));
                int eventId = cursor.getInt(cursor.getColumnIndexOrThrow("_id")); // Уникальный ID события
                boolean remindMe = cursor.getInt(cursor.getColumnIndexOrThrow("event_reminder")) == 1; // Проверка флага "напомнить мне"

                // Запланируйте уведомление
                if (remindMe && !isNotificationScheduled(context, eventId)) {
                    scheduleNotification(context, eventName, eventDescription, year, month, day, startHour, startMinute, eventId);
                }
            } while (cursor.moveToNext());
        }

        if (cursor != null) {
            cursor.close();
        }
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

                    //Toast.makeText(MainActivity.this, "Данные сохранены", Toast.LENGTH_SHORT).show();
                    dialog.dismiss(); // Закрываем диалог
                }
            });
        });

        dialog.show();
    }

    private int saveToDatabase(String holidayName, String from, String to) {
        MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);

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

    void ConfirmDialog(){
        AlertDialog alertDialog;
        alertDialog = new AlertDialog.Builder(MainActivity.this, R.style.ConfirmDialogTheme) //
                .setTitle("Проверьте корректность данных для поздравления!")
                .setMessage("После нажатия кнопки «Создать», изменить данные для генерации поздравления уже не получится.")
                .setCancelable(false)
                //call button
                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);

                        GreetingID = saveToDatabase(holidayName, fromPerson, toPerson);
                        generateText(holidayName, fromPerson, toPerson, GreetingID);

                        myDB.addEvent(editTextEventName.getText().toString().trim(), editTextEventDescription.getText().toString().trim(),
                                data_selected, month_selected, year_selected, hour_start, minute_start, hour_end, minute_end, RemindMe, GreetingID);

                        if (RemindMe == 1)
                        {
                            CreateNotification(MainActivity.this);
                        }
                        bottomSheetDialog.dismiss();
                    }
                })
                //cancel button
                .setNegativeButton("Нет", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //что то при кансел
                        switchHoliday.setChecked(false);
                    }
                }).show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(MainActivity.this, R.color.buttons_color));
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(MainActivity.this, R.color.buttons_color));
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

                    MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);

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

}
