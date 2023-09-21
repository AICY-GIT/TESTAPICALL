package edu.huflit.testcallapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import edu.huflit.testcallapi.api.ApiService;
import edu.huflit.testcallapi.model.Currency;
import edu.huflit.testcallapi.model.Post;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView tvTerm;
    private TextView tvSouce;
    private TextView tvUSD;
    private Button btnCallAPI;
    private Button btnChangeAct,btnChooseImg,btnCallDynamic;
    private TextView tvPostResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTerm = findViewById(R.id.tv_Term);
        tvSouce = findViewById(R.id.tv_Source);
        tvUSD = findViewById(R.id.tv_USDVND);
        btnCallAPI= findViewById(R.id.btn_Callapi);
        tvPostResult = findViewById(R.id.tvPostResult);
        btnChangeAct= findViewById(R.id.btnChangrActi);
        btnChooseImg = findViewById(R.id.btn_Act_Chon_Anh);
        btnCallDynamic = findViewById(R.id.btnCallDynamic);
        btnCallDynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,CallDynamicActivity.class);
                startActivity(intent);
            }
        });
        btnChooseImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,UploadImgActivity.class);
                startActivity(intent);
            }
        });
        btnCallAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //clickCallApi();
                sendPost();
            }
        });
        btnChangeAct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent I = new Intent(view.getContext(), RecyclerViewActivity.class);
                startActivity(I);
            }
        });
    }

    private void clickCallApi() {
        //Ling API : http://apilayer.net/api/live?access_key=843d4d34ae72b3882e3db642c51e28e6&currencies=VND&source=USD&format=1
        ApiService.apiService.convertUsdToVnd("843d4d34ae72b3882e3db642c51e28e6","VND","USD",1).enqueue(new Callback<Currency>() {
            @Override
            public void onResponse(Call<Currency> call, Response<Currency> response) {
                Toast.makeText(MainActivity.this,"CALL GET DC",Toast.LENGTH_SHORT).show();
                Currency currency = response.body();
                if(currency != null&&currency.isSuccess()){
                    tvTerm.setText(currency.getTerms());
                    tvSouce.setText(currency.getSource());
                    tvUSD.setText(String.valueOf(currency.getQuotes().getUSDVND()));
                }
            }

            @Override
            public void onFailure(Call<Currency> call, Throwable t) {
                Toast.makeText(MainActivity.this,"CALL GET KO DC",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void sendPost(){
        Post post= new Post(11,101,"TestPost","english plz");
        ApiService.apiServicePost.sendPost(post).enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                Toast.makeText(MainActivity.this,"CALL POST DC",Toast.LENGTH_SHORT).show();
                Post postResult= response.body();
                if (postResult!=null){
                    tvPostResult.setText(postResult.toString());
                }
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {
                Toast.makeText(MainActivity.this,"CALL POST KO DC",Toast.LENGTH_SHORT).show();
            }
        });
    }
}