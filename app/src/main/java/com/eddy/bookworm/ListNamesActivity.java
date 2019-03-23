package com.eddy.bookworm;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.eddy.data.AppDataManager;
import com.eddy.data.models.ListName;

import java.util.List;

public class ListNamesActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.booksTextView);

        fetchListNames();
    }

    private void fetchListNames() {
        @SuppressLint("StaticFieldLeak") AsyncTask<Void, Void, List<ListName>> listNamesTask = new AsyncTask<Void, Void, List<ListName>> () {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Toast.makeText(ListNamesActivity.this, "Fetching list names ...", Toast.LENGTH_LONG)
                        .show();
            }

            @Override
            protected List<ListName> doInBackground(Void... voids) {
                AppDataManager appDataManager = new AppDataManager();
                return appDataManager.getListNames();
            }

            @Override
            protected void onPostExecute(List<ListName> listNames) {
                Log.i(ListNamesActivity.class.toString(), "LIST NAMES: "+ listNames.toString());
                textView.setText(listNames.toString());
            }
        };

        listNamesTask.execute();
    }
}
