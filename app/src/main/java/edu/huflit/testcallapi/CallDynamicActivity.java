package edu.huflit.testcallapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.huflit.testcallapi.api.ApiService;
import edu.huflit.testcallapi.model.MyResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CallDynamicActivity extends AppCompatActivity {

    private EditText etID;
    private Button btnCall;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_dynamic);

        etID = findViewById(R.id.etId);
        btnCall = findViewById(R.id.btnCallAPI);
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CallDynamicAPI(Integer.parseInt(String.valueOf(etID.getText()).trim()));
            }
        });
    }

    private void CallDynamicAPI(int id) {
        ApiService.apiServiceDynamic.getReponse(id).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                Toast.makeText(CallDynamicActivity.this,"CALL GET DC",Toast.LENGTH_SHORT).show();
                MyResponse myResponse= response .body();
                if(myResponse != null){
                    Log.e( "CallDynamicAPI: ",myResponse.toString() );
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
                Toast.makeText(CallDynamicActivity.this,"CALL GET KO DC",Toast.LENGTH_SHORT).show();
            }
        });
    }
}