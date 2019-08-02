package c346.rp.edu.sg.rpwebsites;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    Spinner spnCategory, spnSubCategory;
    Button btnGo;
    ArrayList<String> alCategory;
    ArrayAdapter<String> aaCategory;
    WebView wv;
    String url;
    TextView tvCategory, tvSubCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spnCategory = findViewById(R.id.spnCategory);
        spnSubCategory = findViewById(R.id.spnSubCategory);
        wv = findViewById(R.id.wv);
        btnGo = findViewById(R.id.btnGo);
        tvCategory = findViewById(R.id.tvCategory);
        tvSubCategory = findViewById(R.id.tvSubCategory);

        alCategory = new ArrayList<>();
        aaCategory = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1, alCategory);

        wv.setWebViewClient(new WebViewClient());

        spnCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch(position){
                    case 0:
                        alCategory.clear();
                        String[] strOptions = getResources().getStringArray(R.array.rpSubCategory);
                        alCategory.addAll(Arrays.asList(strOptions));
                        spnSubCategory.setAdapter(aaCategory);
                        break;
                    case 1:
                        alCategory.clear();
                        strOptions = getResources().getStringArray(R.array.soiSubCategory);
                        alCategory.addAll(Arrays.asList(strOptions));
                        spnSubCategory.setAdapter(aaCategory);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (spnCategory.getSelectedItemPosition()){
                    case 0:
                        switch(spnSubCategory.getSelectedItemPosition()){
                            case 0:
                                url = "https://www.rp.edu.sg/";
                                break;
                            case 1:
                                url = "https://www.rp.edu.sg/student-life";
                                break;
                        }
                        break;
                    case 1:
                        switch(spnSubCategory.getSelectedItemPosition()){
                            case 0:
                                url = "https://www.rp.edu.sg/soi/full-time-diplomas/details/r47";
                                break;
                            case 1:
                                url = "https://www.rp.edu.sg/soi/full-time-diplomas/details/r12";
                                break;
                        }
                }

                wv.loadUrl(url);
                tvCategory.setVisibility(View.GONE);
                spnCategory.setVisibility(View.GONE);
                tvSubCategory.setVisibility(View.GONE);
                spnSubCategory.setVisibility(View.GONE);
                btnGo.setVisibility(View.GONE);

                int pos1 = spnCategory.getSelectedItemPosition();
                int pos2 = spnSubCategory.getSelectedItemPosition();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                SharedPreferences.Editor preEdit = prefs.edit();

                preEdit.putInt("spnCat", pos1);
                preEdit.putInt("spnSub", pos2);
                preEdit.commit();
            }
        });
    }

    @Override
    public void onBackPressed(){
        tvCategory.setVisibility(View.VISIBLE);
        spnCategory.setVisibility(View.VISIBLE);
        tvSubCategory.setVisibility(View.VISIBLE);
        spnSubCategory.setVisibility(View.VISIBLE);
        btnGo.setVisibility(View.VISIBLE);
        wv.setVisibility(View.GONE);
    }


    protected void onResume(){
        super.onResume();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        int pos1 = prefs.getInt("spnCat", 0);
        int pos2 = prefs.getInt("spnSub", 0);

        spnCategory.setSelection(pos1);
        spnSubCategory.setSelection(pos2);
    }
}
