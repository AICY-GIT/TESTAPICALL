package edu.huflit.testcallapi.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.huflit.testcallapi.R;
import edu.huflit.testcallapi.model.ObjectData;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.DataViewHolder>{
    private final List<ObjectData> mListData;
    public DataAdapter(List<ObjectData> mListData) {
        this.mListData = mListData;
    }

    @NonNull
    @Override
    public DataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data,parent,false);
        return new DataViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DataViewHolder holder, int position) {
        ObjectData  objectData =mListData.get(position);
        if(objectData==null){
            return;
        }
        holder.tvtile.setText(objectData.getTitle());
        holder.tvbody.setText(objectData.getBody());
    }

    @Override
    public int getItemCount() {
        if(mListData !=null)
            return mListData.size();
        return 0;
    }

    public static class DataViewHolder extends RecyclerView.ViewHolder{

        private final TextView tvtile;
        private final TextView tvbody;
        public DataViewHolder(@NonNull View itemView) {
            super(itemView);
            tvtile=itemView.findViewById(R.id.tv_title);
            tvbody=itemView.findViewById(R.id.tv_body);
        }
    }
}
