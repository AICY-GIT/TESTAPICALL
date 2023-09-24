package edu.huflit.testcallapi;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.huflit.testcallapi.adapter.DataAdapter;
import edu.huflit.testcallapi.api.ApiService;
import edu.huflit.testcallapi.model.ObjectData;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class RxAndroidvRetrofitRecyclerViewActivity extends AppCompatActivity {

    private DataAdapter mDataApdapter;
    private  RecyclerView rcvData;
    private Button btncallapi;
    private List<ObjectData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_androidv_retrofit_recycler_view);

        btncallapi =findViewById(R.id.btnCallapi);
        rcvData= findViewById(R.id.rcv_data);
        RecyclerView rcvData=findViewById(R.id.rcv_data);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvData.setLayoutManager(linearLayoutManager);
        rcvData.addItemDecoration(itemDecoration);
        list = getListData();
        btncallapi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickCallAPI();
            }
        });

    }
    private void onClickCallAPI(){
        mDataApdapter = new DataAdapter(list);
        rcvData.setAdapter(mDataApdapter);
    }
    private List<ObjectData> getListData(){
        List<ObjectData>list = new ArrayList<>();

       // ApiService.apiServiceRxandroid.CallAPI().subscribeOn(Schedulers.io()).
        return list;
    }

}