package edu.huflit.testcallapi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.huflit.testcallapi.adapter.UserAdapter;
import edu.huflit.testcallapi.api.ApiService;
import edu.huflit.testcallapi.model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecyclerViewActivity extends AppCompatActivity {

    private RecyclerView rcvUser;
    private List<User> listUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        rcvUser = findViewById(R.id.rcv_User);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvUser.setLayoutManager(linearLayoutManager);
        rcvUser.addItemDecoration(itemDecoration);

        listUser= new ArrayList<>();

        callApiGetUser();
    }
    private void callApiGetUser(){
        ApiService.apiServiceGetUser.getUserList(1).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if (response.isSuccessful()) {
                    listUser = response.body();
                    UserAdapter userAdapter = new UserAdapter(listUser);
                    rcvUser.setAdapter(userAdapter);
                    // Update your RecyclerView or do other operations with the list of users
                } else {
                    Toast.makeText(RecyclerViewActivity.this, "CALL USERLIST KO DC onrespone", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(RecyclerViewActivity.this, "CALL USERLIST KO DC onfailuire", Toast.LENGTH_SHORT).show();
            }
        });
    }
}