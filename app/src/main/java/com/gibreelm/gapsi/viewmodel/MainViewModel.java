package com.gibreelm.gapsi.viewmodel;

import android.os.StrictMode;
import android.view.View;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.gibreelm.gapsi.R;
import com.gibreelm.gapsi.activity.MainActivity;
import com.gibreelm.gapsi.adapter.RecyclerViewAdapterResult;
import com.gibreelm.gapsi.databinding.ActivityMainBinding;
import com.gibreelm.gapsi.model.Root;
import com.google.gson.Gson;

import java.io.IOException;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static androidx.recyclerview.widget.RecyclerView.VERTICAL;

public class MainViewModel extends ViewModel {
    private MainActivity activity;
    private ActivityMainBinding binding;
    private String searchTxt = "";

    public ObservableField<Boolean> isFilterVisible = new ObservableField<>(false);

    public MainViewModel(MainActivity activity) {
        this.activity = activity;

        binding = DataBindingUtil.setContentView(activity, R.layout.activity_main);
        binding.setLifecycleOwner(activity);
        binding.setMainViewModel(this);

        initViews();
    }

    private void initViews() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        binding.floatingSearchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, final String newQuery) {

                searchTxt = newQuery;
            }
        });

        binding.btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    request(searchTxt, 1, 3);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void request(String criterio, int page, int itemsByPage) throws IOException {
        String url = "https://shoppapp.liverpool.com.mx/appclienteservices/services/v3/plp?force-plp=true&search-string=" + criterio +
                "&page-number=" + page +
                "&number-of-items-per-page=" + itemsByPage;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            Gson g = new Gson();
            String res = "";
            res = response.body().string();
            Root resultObj = g.fromJson(res, Root.class);

            setResult(resultObj);
        }
    }

    private void setResult(Root resultObj) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvRes.setLayoutManager(linearLayoutManager);
        RecyclerViewAdapterResult recyclerViewAdapter = new RecyclerViewAdapterResult(resultObj.plpResults.records, activity);
        binding.rvRes.setAdapter(recyclerViewAdapter);
        binding.rvRes.addItemDecoration(new DividerItemDecoration(activity, VERTICAL));
    }

}
