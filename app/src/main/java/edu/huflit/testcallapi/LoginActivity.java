package edu.huflit.testcallapi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import edu.huflit.testcallapi.api.ApiService;
import edu.huflit.testcallapi.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText etID;
    private  EditText etTile;
    private Button Login;
    private List<User> mListUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etID=findViewById(R.id.et_Id);
        etTile=findViewById(R.id.et_Title);
        Login=findViewById(R.id.btnDangNhap);

        getListUser();
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClickLogin();
            }
        });
    }

    private void ClickLogin() {
        String StrId=etID.getText().toString().trim();
        String StrTitle=etTile.getText().toString().trim();
        if(mListUser==null ||mListUser.isEmpty()){
            return;
        }
        boolean hasUser=false;
        for(User user:mListUser){
            if(StrId.equals(user.getId())&&StrTitle.equals(user.getTitle())){
                hasUser=true;
                break;
            }
        }
        if(hasUser=true){
            Intent i=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(i);
        }else {
            Toast.makeText(LoginActivity.this, "Wrong id or title", Toast.LENGTH_SHORT).show();
        }
    }

    private void getListUser(){
        ApiService.apiServiceGetUser.getUserList(1).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                mListUser=response.body();
                Log.e( "ListUser size ",mListUser.size()+"" );
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "CALL USERLIST KO DC onfailuire", Toast.LENGTH_SHORT).show();
            }
        });
    }
}