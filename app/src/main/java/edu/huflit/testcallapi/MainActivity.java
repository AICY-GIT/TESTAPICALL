package edu.huflit.testcallapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;

import edu.huflit.testcallapi.api.ApiService;
import edu.huflit.testcallapi.model.Currency;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tvTerm;
    private TextView tvSouce;
    private TextView tvUSD;
    private Button btnCallAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTerm = findViewById(R.id.tv_Term);
        tvSouce = findViewById(R.id.tv_Source);
        tvUSD = findViewById(R.id.tv_USDVND);
        btnCallAPI= findViewById(R.id.btn_Callapi);
        btnCallAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickCallApi();
            }
        });
    }

    private void clickCallApi() {
        //Ling API : http://apilayer.net/api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1
        ApiService.apiService.convertUsdToVnd("843d4d34ae72b3882e3db642c51e28e6","VND","USD",1).enqueue(new Callback<Currency>() {
            @Override
            public void onResponse(Call<Currency> call, Response<Currency> response) {
                Toast.makeText(MainActivity.this,"CALL DC",Toast.LENGTH_SHORT).show();
                Currency currency = response.body();
                if(currency != null&&currency.isSuccess()){
                    tvTerm.setText(currency.getTerms());
                    tvSouce.setText(currency.getSource());
                    tvUSD.setText(String.valueOf(currency.getQuotes().getUSDVND()));
                }
            }

            @Override
            public void onFailure(Call<Currency> call, Throwable t) {
                Toast.makeText(MainActivity.this,"CALL KO DC",Toast.LENGTH_SHORT).show();
            }
        });
    }
}