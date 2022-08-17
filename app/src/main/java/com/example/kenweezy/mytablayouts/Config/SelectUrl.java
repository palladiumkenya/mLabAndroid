package com.example.kenweezy.mytablayouts.Config;

import static android.R.layout.simple_spinner_item;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.kenweezy.mytablayouts.Mylogin;
import com.example.kenweezy.mytablayouts.R;
import com.example.kenweezy.mytablayouts.models.urlModel;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SelectUrl extends AppCompatActivity {


    urlModel url_Model;
    ArrayList<String> urlModelArrayList;
    ArrayList<urlModel> names;
    Spinner spinner1;
    int dataId;
    String base_url;
    String stage_name;
    SharedPreferences sharedPreferences1;
    Button btn_prcd;
    RequestPerms requestPerms;
    private static final int PERMS_REQUEST_CODE = 12345;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_url);
        //setScreen();

        requestPerms = new RequestPerms(SelectUrl.this, this);


        requestNewPerms();
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (!isConnected(SelectUrl.this)){
                    //Toast.makeText(LoginActivity.this, "Please connect to internet", Toast.LENGTH_LONG).show();
                    Snackbar.make(findViewById(R.id.lay), "Please connect to internet", Snackbar.LENGTH_LONG).show();
                }else{
                    geturls1();
                }
            }
        });
        //setScreen();


        SharedPreferences sharedPreferences = getSharedPreferences("Settings_url", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        btn_prcd = findViewById(R.id.login_proceed);
        spinner1 =findViewById(R.id.spCompany);

       // geturls1();


        btn_prcd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("keyName", base_url);
                editor.apply();

                String getValue = sharedPreferences.getString("keyName", "defaultValue");

                // Toast.makeText(MainActivity.this, x, Toast.LENGTH_SHORT).show();


                if (getValue.equals("defaultValue")){
                    Toast.makeText(SelectUrl.this, "Invalid", Toast.LENGTH_LONG).show();
                }else {
                    //Toast.makeText(SelectUrls.this, getValue, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SelectUrl.this, Config.class);
                    intent.putExtra("url", getValue);
                    intent.putExtra("stage_key", stage_name);
                    startActivity(intent);
                }



            }
        });

    }
    public void geturls1(){

        String URLstring = "https://ushaurinode.mhealthkenya.co.ke/config";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, URLstring, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("", response.toString());

                try {
                    urlModelArrayList = new ArrayList<>();
                    names = new ArrayList<>();

                    urlModelArrayList.clear();
                    names.clear();


                    JSONArray jsonArray =response.getJSONArray("MLAB");

                    for (int i =0; i<jsonArray.length(); i++){

                        JSONObject jsonObject =jsonArray.getJSONObject(i);
                        int url_id = jsonObject.getInt("id");
                        String url_stage =jsonObject.getString("stage");
                        String main_urls =jsonObject.getString("url");

                        url_Model = new urlModel(url_id, url_stage, main_urls);
                        names.add(url_Model);
                        urlModelArrayList.add(url_Model.getStage());

                    }


                    names.add(new urlModel(0, "", "--Select the system to connect to--"));
                    urlModelArrayList.add("--Select the system to connect to--");


                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(SelectUrl.this, simple_spinner_item, urlModelArrayList);
                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view
                    spinner1.setAdapter(spinnerArrayAdapter);
                    //removeSimpleProgressDialog();

                    spinner1.setSelection(spinnerArrayAdapter.getCount()-1);
                    dataId =names.get(spinnerArrayAdapter.getCount()-1).getId();

                    spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                            dataId = names.get(position).getId();

                            if (dataId==3){

                                base_url = names.get(position).getUrl();

                                stage_name =names.get(position).getStage();

                            }
                            else if(dataId==4){

                                base_url = names.get(position).getUrl();
                                stage_name =names.get(position).getStage();
                                //Toast.makeText(SelectUrls.this, base_url, Toast.LENGTH_LONG).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(SelectUrl.this);
        requestQueue.add(jsonObjectRequest);
    }

    private void setScreen(){
        SharedPreferences preferencesS =getSharedPreferences("PREFERENCE", MODE_PRIVATE);

        String FirstTime =preferencesS.getString("FirstTimeInstall", "");

        if (FirstTime.equals("Yes")){
            Intent intent1 =new Intent(SelectUrl.this, Mylogin.class);
            startActivity(intent1);

        }else{
            SharedPreferences.Editor editor =preferencesS.edit();
            editor.putString("FirstTimeInstall", "Yes");
            editor.apply();
        }
    }
      private  boolean isConnected(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo =connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);

        if((wifiInfo !=null && wifiInfo.isConnected())|| (mobileInfo !=null && mobileInfo.isConnected())){
            return true;
        }
        else{
            return false;
        }

    }

    public void requestNewPerms() {

        try {
            requestPerms.requestPerms();

        } catch (Exception e) {
            Toast.makeText(this, "error in granting permissions " + e, Toast.LENGTH_SHORT).show();


        }
    }

    private boolean hasPermissions() {

        int res = 0;

        String[] permissions = new String[]{
                Manifest.permission.INTERNET,
//                android.Manifest.permission.READ_SMS,
//                android.Manifest.permission.RECEIVE_SMS,
//                android.Manifest.permission.CALL_PHONE


        };

        for (String perms : permissions) {
            res = checkCallingOrSelfPermission(perms);

            if (!(res == PackageManager.PERMISSION_GRANTED)) {
                return false;
            }

        }
        return true;


    }


private void requestPerms() {

        String[] permissions = new String[]{
                Manifest.permission.INTERNET,
//                android.Manifest.permission.SEND_SMS,
//                android.Manifest.permission.READ_SMS,
//                android.Manifest.permission.RECEIVE_SMS,
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, PERMS_REQUEST_CODE);


            NotificationCompat.Builder builder = new NotificationCompat.Builder(
                    getApplicationContext());
            builder.setAutoCancel(true);

        }

    }


}