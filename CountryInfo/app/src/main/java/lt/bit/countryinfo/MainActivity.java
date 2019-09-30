package lt.bit.countryinfo;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import org.json.JSONObject;
import java.util.Iterator;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private Spinner spinner;
    private String selectedCountry;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getCountries();

    }



    public void getCountries(){

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://poligonas.lt/world/countries.php", new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
               try {
                   List<String> countries = new ArrayList<>();
                   Iterator<String> iterator = response.keys();
                   while (iterator.hasNext()) {
                       String key = iterator.next();
                       String value = (String)response.get(key);
                       countries.add(value);
                   }
                   addItemsOnSpinner(countries);
               }catch (Exception e){
                   e.printStackTrace();
               }


            }

        });



    }

    public void addItemsOnSpinner(List<String> countries){
        spinner = findViewById(R.id.countryList);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCountry = spinner.getSelectedItem().toString();
                getSelectedCountry(selectedCountry);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        }

        public void getSelectedCountry(String country){
        this.selectedCountry = country;

        }

    public void getCountryInfo(View v){
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams rp=new RequestParams();
        rp.add("name", selectedCountry);
        client.get("http://poligonas.lt/world/country.php",
                rp, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        try {
                            TextView t = findViewById(R.id.Continent);
                            t.setText(response.getString("Continent"));
                            t=findViewById(R.id.SurfaceArea);
                            t.setText(response.getString("SurfaceArea"));
                            t=findViewById(R.id.Region);
                            t.setText(response.getString("Region"));
                            t=findViewById(R.id.Population);
                            t.setText(response.getString("Population"));
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
             });

        }

    }







