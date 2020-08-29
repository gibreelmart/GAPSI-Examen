package com.gibreelm.gapsi.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.gibreelm.gapsi.BR;
import com.gibreelm.gapsi.R;
import com.gibreelm.gapsi.databinding.NewItemFormatoBinding;
import com.gibreelm.gapsi.model.Record;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewAdapterResult extends RecyclerView.Adapter<RecyclerViewAdapterResult.ViewHolder> {

    private List<Record> resultList;
    private Activity activity;

    public RecyclerViewAdapterResult(List<Record> resultList, Activity activity) {
        this.resultList = resultList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerViewAdapterResult.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewItemFormatoBinding itemFormatoBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.new_item_formato, parent, false);

        return new ViewHolder(itemFormatoBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Record recordResult = resultList.get(position);

        holder.itemFormatoBinding.setResult(recordResult);
        holder.bind(recordResult);

    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private NewItemFormatoBinding itemFormatoBinding;

        public ViewHolder(NewItemFormatoBinding itemFormatoBinding) {
            super(itemFormatoBinding.getRoot());
            this.itemFormatoBinding = itemFormatoBinding;
        }

        public void bind(Object obj) {
            itemFormatoBinding.setVariable(BR.mainViewModel, obj);
            itemFormatoBinding.executePendingBindings();
        }
    }
}


