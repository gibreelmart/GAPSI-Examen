package com.gibreelm.gapsi.viewmodel;

import android.app.Activity;
import android.content.Context;
import android.os.StrictMode;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.gibreelm.gapsi.R;
import com.gibreelm.gapsi.activity.MainActivity;
import com.gibreelm.gapsi.adapter.RecyclerViewAdapterResult;
import com.gibreelm.gapsi.databinding.ActivityMainBinding;
import com.gibreelm.gapsi.model.Record;
import com.gibreelm.gapsi.model.Root;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModel;
import androidx.recyclerview.widget.LinearLayoutManager;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainViewModel extends ViewModel {
    private MainActivity activity;
    private ActivityMainBinding binding;
    private String searchTxt = "";
    private int index = 0;

    List<Record> records = new ArrayList<>();

    public MainViewModel(MainActivity activity) {
        this.activity = activity;

        binding = DataBindingUtil.setContentView(activity, R.layout.activity_main);
        binding.setLifecycleOwner(activity);
        binding.setMainViewModel(this);

        initViews();
    }

    public static void HideKeyboard(Activity activity) {
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void initViews() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        binding.floatingSearchView.setOnQueryChangeListener((oldQuery, newQuery) -> searchTxt = newQuery);

        binding.btnBuscar.setOnClickListener(v -> {
            try {
                records.clear();
                request(searchTxt, 1, 5);
                index = 1;
                HideKeyboard(activity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        binding.btnBuscarMas.setOnClickListener(v -> {
            try {
                index = index + 1;
                request(searchTxt, index, 5);
                HideKeyboard(activity);
            } catch (IOException e) {
                e.printStackTrace();
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
            records.addAll(resultObj.plpResults.records);
            setResult(records);
        }
    }

    private void setResult(List<Record> records) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(activity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvRes.setLayoutManager(linearLayoutManager);
        RecyclerViewAdapterResult recyclerViewAdapter = new RecyclerViewAdapterResult(records, activity);
        binding.rvRes.setAdapter(recyclerViewAdapter);
        binding.btnBuscarMas.setVisibility(View.VISIBLE);
    }

}
