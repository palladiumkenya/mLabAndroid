package com.example.kenweezy.mytablayouts.Config;

import static android.R.layout.simple_spinner_item;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.kenweezy.mytablayouts.Mylogin;
import com.example.kenweezy.mytablayouts.R;
import com.example.kenweezy.mytablayouts.models.urlModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Config extends AppCompatActivity {

    //begin selectURLs
    urlModel url_Model;
    ArrayList<String> urlModelArrayList;
    ArrayList<urlModel> names;
    Spinner spinner1;
    int dataId;
    SharedPreferences sharedPreferences1;
    Button btn_prcd;
    //End selectURLS


    public static String BASE_URL="";
    public static String mainShortcode="40147";
    public static String sendSmsShortcode="40147";
    public static String registerShortcode="40147";

    public static final String JSON_ARRAYRESULTS = "results";
    public static final String KEY_MESSAGECODE="message";

    public static final String[] SPINNERLISTLABS = {"KU Teaching and Referring Hospital",
            "Kemri Nairobi",
            "Kisumu lab",
            "Alupe",
            "Walter Reed",
            "Ampath",
            "Coast lab",
            "KNH"};

    public static final String statusBarColor="#3F51B5";


    public static final String[] SPINNERLISTSEX = {"Male", "Female"};
    public static final String[] SPINNERLISTDELIVERYPOINT = {"OPD", "MCH","IPD","CCC","Community"};
    public static final String[] SPINNERLISTTESTKITS = {"Screening kit-determine", "Confirmatory-first response"};
    public static final String[] SPINNERLISTFINALRESULTS = {"Negative", "Positive"};
    public static final String[] SPINNERLISTFIRSTRESULTS = {"Negative", "Positive"};
    public static final String[] SPINNERLISTENTRYPOINT = {"IPD", "OPD","MATERNITY","CCC","MCH/PMTCT","Other"};
    public static final String[] SPINNERLISTPROPHYLAXISCODE = {"AZT for 6 weeks + NVP for 12 weeks", "AZT for 6 weeks + NVP for >12 weeks","None","Other"};
    public static final String[] SPINNERLISTINFANTFEEDING = {"EBF (Exclusive Breast Feeding)", "ERF (Exclusive Replacement Feeding)","MF (Mixed feeding)","BF (Breast Feeding)","NBF (Not Breast Feeding)"};
    public static final String[] SPINNERLISTPCR = {"1= Initial PCR","2= 2nd PCR","3= 3rd PCR","4= Confirmatory PCR and Baseline VL","5= Discrepant PCR (tie breaker)","6= Sample redraw"};




    public static final String[] SPINNERLISTALIVEDEAD = {"Alive", "Dead"};


    public static final String[] SPINNERLISTREGIMEN = {"PM1X=Any other Regimen",
            "PM3= AZT+3TC+NVP",
            "PM4= AZT+ 3TC+ EFV",
            "PM5= AZT+3TC+ LPV/r",
            "PM6= TDC+3TC+NVP",
            "PM7= TDF+3TC+LPV/r",
            "PM9= TDF+3TC+EFV",
            "PM10= AZT+3TC+ATV/r",
            "PM11= TDF+3TC+ATV/r",
            "PM12=TDF+3TC+DTG",
            "PM13=None"};
    public static final String[] SPINNERLISTSAMPLETYPE = {"Frozen plasma","Venous blood(EDTA)","DBS capillary(infants only)","DBS venous","PPT"};



   // public static final String EIDVL_DATA_URL = "https://mlab.kenyahmis.org/api/remote/login/all";
    public static final String EIDVL_DATA_URL1 = "https://mlab.kenyahmis.org/api/remote/login/all";

    //public static final String HTS_DATA_URL = "https://mlab.kenyahmis.org/api/remote/login/hts";
    public static final String HTS_DATA_URL1 = "https://mlab.kenyahmis.org/api/remote/login/hts";

    //public static final String RESULTS_DATA_URL = "https://mlab.kenyahmis.org/api/get/results";
    public static final String RESULTS_DATA_URL1 = "https://mlab.kenyahmis.org/api/get/results";

    //public static final String HISTORICALRESULTS_DATA_URL = "https://mlab.kenyahmis.org/api/historical/results";
    public static final String HISTORICALRESULTS_DATA_URL1 = "https://mlab.kenyahmis.org/api/historical/results";
    //public static final String GETHTSRESULTS_DATA_URL = "https://mlab.kenyahmis.org/api/hts_results";
    public static final String GETHTSRESULTS_DATA_URL1 = "https://mlab.kenyahmis.org/api/hts_results";

    //public static final String GETTBRESULTS_DATA_URL = "https://mlab.mhealthkenya.co.ke/api/tb_results";
    public static final String GETTBRESULTS_DATA_URL1 = "https://mlab.mhealthkenya.co.ke/api/tb_results";
    public static final String[] CURRENTARTREGIMENCODES = {
            "1= TDF+ 3TC+ EFV",
            "2=TDF+3TC+NVP",
            "3=TDF+3TC+ DTG",
            "4=AZT+3TC+NVP",
            "5=AZT+3TC+EFV|",
            "6=ABC+3TC+NVP",
            "7=ABC+3TC+EFV",
            "8= ABC+3TC+DTG",
            "9=ABC+3TC+LPV/r|"
            ,"10=AZT+3TC+LPV/r+ RTV",
            "11=TDF+3TC +ATV/r",
            "12=ABC+3TC+DTG",
            "13=ABC+3TC+ATV/r",
            "14=AZT+3TC+ATV/r",
            "15=AZT+3TC+DRV/r",
            "16=Other"};

    public static final String[] LINES = {
            "First Line",
            "Second Line",
            };

    public static final String[] JUSTIFICATIONCODE = {
            "1=Routine VL",
            "2=Confirmation of treatment failure (repeat VL)",
            "3=Clinical failure",
            "4=Single drug substitution",
            "5=Baseline VL (for infants diagnosed through EID)"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        //selectURLS

        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        btn_prcd = findViewById(R.id.login_proceed);
        spinner1 =findViewById(R.id.spCompany);
        getUrls();

        //end SelectURLS

        btn_prcd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                editor.putString("keyName", BASE_URL);
                editor.apply();

                String getValue = sharedPreferences.getString("keyName", "defaultValue");

                if (getValue.equals("--select baseURL--")){
                    Toast.makeText(Config.this, "Invalid", Toast.LENGTH_LONG).show();
                }else if (dataId==-1) {
                    Toast.makeText(Config.this, "Invalid", Toast.LENGTH_LONG).show();

                }else{
                    getAlert();
                }

            }
        });

    }
    private void getAlert(){

        AlertDialog.Builder builder1 = new AlertDialog.Builder(Config.this);
        builder1.setMessage("You have connected to"+ ""+ BASE_URL);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "Proceed",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(Config.this, Mylogin.class);
                        startActivity(intent);

                        //dialog.cancel();
                    }
                });

        builder1.setNegativeButton(
                "Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        AlertDialog alert11 = builder1.create();
        alert11.show();
    }

    //fetch URLS

    private void getUrls(){
        String URLstring = "https://ushaurinode.mhealthkenya.co.ke/config";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, URLstring, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("", response.toString());

                try {
                    urlModelArrayList = new ArrayList<>();
                    names = new ArrayList<>();

                    urlModelArrayList.clear();
                    names.clear();

                    for (int i=0; i<response.length(); i++){

                        JSONObject jsonObject =response.getJSONObject(i);

                        int url_id = jsonObject.getInt("id");
                        String url_stage =jsonObject.getString("stage");
                        String main_urls =jsonObject.getString("url");

                        url_Model = new urlModel(url_id, url_stage, main_urls);
                        names.add(url_Model);
                        urlModelArrayList.add(url_Model.getStage());

                    }

                    names.add(new urlModel(0, "", "--select baseURL--"));
                    urlModelArrayList.add("--select baseURL--");

                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(Config.this, simple_spinner_item, urlModelArrayList);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinner1.setAdapter(spinnerArrayAdapter);

                    spinner1.setSelection(spinnerArrayAdapter.getCount()-1);
                    dataId =names.get(spinnerArrayAdapter.getCount()-1).getId();

                    spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            dataId = names.get(position).getId();

                            if (dataId==1){

                                BASE_URL = names.get(position).getUrl();

                            }
                            else if(dataId==2){

                                BASE_URL = names.get(position).getUrl();
                            }


                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(Config.this);
        requestQueue.add(jsonArrayRequest);
    }



}
