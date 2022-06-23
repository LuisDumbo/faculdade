package com.example.lembrete_fantastic_6;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Objects;

public class MainActivity  extends AppCompatActivity implements Runnable {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private WifiManager wifiManager;

    //conencçaõ com bsade dados
    private connection dbHelper;


    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);

        bottomSheetDialog.setContentView(R.layout.bottom_sheet_dialog_layout);

        LinearLayout tud = bottomSheetDialog.findViewById(R.id.tudo);
        LinearLayout containerText = bottomSheetDialog.findViewById(R.id.containerText);
        LinearLayout fom = bottomSheetDialog.findViewById(R.id.form);
        LinearLayout campos = bottomSheetDialog.findViewById(R.id.campo);
        Button sairButoa = bottomSheetDialog.findViewById(R.id.sair);
        Button adicionar = bottomSheetDialog.findViewById(R.id.adicionar);
        dateButton= bottomSheetDialog.findViewById(R.id.datePickerButton);
        Button  time =  bottomSheetDialog.findViewById(R.id.time);


        dateButton.setText(getTodaysDate());

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(MainActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText(selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });



        sairButoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        initDatePicker();
        bottomSheetDialog.show();
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dbHelper = new connection(this, "NotaStore.db", null, 1);
        dbHelper.getWritableDatabase();

        Button mBottton = findViewById(R.id.nvo);

        //Thread
        //MainActivity mainActivity= new MainActivity();
        //runOnUiThread(mainActivity);

       // new Thread(this).start();


        mBottton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showBottomSheetDialog();
            }
        });




    }

    @Override
    public void run() {
        try {
            while (true){
                this.getNetworkType();
                Thread.sleep(3000);

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //veririfcar se a rede esta ligada
    public void  getNetworkType() throws IOException, InterruptedException {
        ConnectivityManager connectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null) { // alguma rede lifada ??
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                if (isConnected()) {
                   // Toast.makeText(getApplicationContext(), "Ligado a wifi,Mas sem internet Internet, ligando a rede movel", Toast.LENGTH_SHORT).show();

                    ///deslidar a rede wifi


                }else{
                    this.wifiTor(false);
                }

            } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
               // networkType = "Mobile";

                if (isConnected()) {
                    ///Toast.makeText(getApplicationContext(), "Internet Connected", Toast.LENGTH_SHORT).show();
                   // Toast.makeText(getApplicationContext(), "Ligado a dados,Mas sem internet Internet, ligando a rede wifi", Toast.LENGTH_SHORT).show();
                    ///ligar wifi


                }else{
                    this.wifiTor(true);

                }
            }
        } else {
            ///ligar wifi
            this.wifiTor(true);
        }

    }


    ///verificar se ha conecção a internet
    public boolean isConnected() throws InterruptedException, IOException {
        String command = "ping -c 1 google.com";
        return Runtime.getRuntime().exec(command).waitFor() == 0;
    }

    //ligar e desligar rede wifi
    public  void wifiTor(boolean valor){
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if (valor){

            wifiManager.setWifiEnabled(valor);
        }else{

            wifiManager.setWifiEnabled(false);
        }
    }


    ///pegar a data de hoje
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }



    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }


}