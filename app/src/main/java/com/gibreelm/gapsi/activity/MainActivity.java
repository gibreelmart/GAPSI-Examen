package com.gibreelm.gapsi.activity;

import android.os.Bundle;

import com.gibreelm.gapsi.viewmodel.MainViewModel;
import com.gibreelm.gapsi.viewmodel.ViewModelFactory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, new ViewModelFactory(this)).get(MainViewModel.class);
    }

    @Override
    public void onBackPressed() {
    }
}
