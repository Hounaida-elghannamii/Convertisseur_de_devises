package com.example.mcd;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.content.ContentValues.TAG;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    TextView tvR,text1,text2,t;
    Spinner sp1,sp2;
    EditText et1;
    Double quote;
    Double from=1.0;
    Double to=1.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvR=findViewById(R.id.tvR);
        sp1=findViewById(R.id.sp1);
        sp2=findViewById(R.id.sp2);
        et1=findViewById(R.id.e1);
        text1=findViewById(R.id.text1);
        text2=findViewById(R.id.text2);
        t=findViewById(R.id.t);
        et1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                Convert();
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        sp1.setOnItemSelectedListener(this);
        sp2.setOnItemSelectedListener(this);
    }
    public  void Convert(){
        try {
            RetrInt retrInt = RetBuild.getRetrofitInstance().create(RetrInt.class);
            Call<JsonObject> call = retrInt.getExchangeCurrency(sp1.getSelectedItem().toString());
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                    JsonObject res = response.body();
                    try {
                        String cc=res.get("price").toString();
                        cc= cc.substring(1,cc.length()-1);

                        from= Double.parseDouble(cc);

                        String d=res.get("datedeUpp").toString();
                        String[] gg=d.split(" ");
                        String u="Last Update :";
                        for(int i=0;i<4;i++){
                            u+=" "+gg[i];
                        }
t.setText(u);

                        SetValues();
                    }
                    catch (Exception e){}

                }


                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });

            Call<JsonObject> call2 = retrInt.getExchangeCurrency(sp2.getSelectedItem().toString());
            call2.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    JsonObject res = response.body();

                    try {
                        String cc=res.get("price").toString();
                        cc= cc.substring(1,cc.length()-1);
                        to= Double.parseDouble(cc);

                        SetValues();
                    }
                    catch (Exception e){

                    }

                }
                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {

                }
            });
        }
        catch (Exception e){

        }
    }

    public void SetValues(){
        quote = to/from;
        tvR.setText(""+ new DecimalFormat("##.##").format(quote * Double.parseDouble(et1.getText().toString()) ));
        text1.setText(String.valueOf(""+1.00));
        text2.setText(""+new DecimalFormat("##.##").format(quote));
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Convert();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}