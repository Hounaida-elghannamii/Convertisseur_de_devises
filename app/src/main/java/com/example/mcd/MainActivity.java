package com.example.mcd;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    TextView tvR,ol;
    Spinner sp1,sp2;
 EditText et1;
    Button conv;

Float convR=0f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvR=findViewById(R.id.tvR);
        sp1=findViewById(R.id.sp1);
        sp2=findViewById(R.id.sp2);
        conv=findViewById(R.id.button);
        et1=findViewById(R.id.e1);
conv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
RetrInt retrInt=RetBuild.getRetrofitInstance().create(RetrInt.class);
Call<JsonObject> call=retrInt.getExchangeCurrency(sp1.getSelectedItem().toString());
call.enqueue(new Callback<JsonObject>() {
    @Override
    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

       JsonObject res=response.body();
       JsonObject rates=res.getAsJsonObject("rates");


        double currency=Double.valueOf(Double.parseDouble(et1.getText().toString()));
        double multiplier=Double.valueOf(rates.get(sp2.getSelectedItem().toString()).toString());

        double result=currency*multiplier;
        tvR.setText(String.valueOf(result));


    }


    @Override
    public void onFailure(Call<JsonObject> call, Throwable t) {

    }
});
    }
});



    }

}