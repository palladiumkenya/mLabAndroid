package com.example.kenweezy.mytablayouts.hts.Htssampleremote;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.AccessServer.AccessServer;
import com.example.kenweezy.mytablayouts.Config.Config;
import com.example.kenweezy.mytablayouts.DateTimePicker.DateTimePicker;
import com.example.kenweezy.mytablayouts.R;
import com.example.kenweezy.mytablayouts.SSLTrustCertificate.SSLTrust;
import com.example.kenweezy.mytablayouts.UsersTable;
import com.example.kenweezy.mytablayouts.encryption.Base64Encoder;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import java.util.List;

public class HtsSampleRemoteLogin extends AppCompatActivity {

    DateTimePicker dtp;
    LinearLayout testkit1ll,testkit2ll;
    EditText samplenumber,clientname,dob,telephone,testdate,lotnumber1,expirydate1,lotnumber2,expirydate2,sampletestername,dbsdate,dbsdispatchdate,requestingprovider;
    MaterialBetterSpinner SpinnerSex,SpinnerdeliveryPoint,Spinnertestkit1,Spinnertestkit2,Spinnerfinalresult;
    String SelectedSex,SelecteddeliveryPoint,Selectedtestkit1,Selectedtestkit2,Selectedfinalresult;
    String samplenumberS,clientnameS,dobS,telephoneS,testdateS,lotnumber1S,expirydate1S,lotnumber2S,expirydate2S,sampletesternameS,dbsdateS,dbsdispatchdateS,requestingproviderS;

    private ArrayAdapter<String> arrayAdapterSex,arrayAdapterDeliveryPoint,arrayAdapterTestkit1,arrayAdapterTestkit2,arrayAdapterFinalResult;

    AccessServer acs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hts_sample_remote_login);
        setToolBar();
        changeStatusBarColor("#3F51B5");

        SSLTrust.nuke();

        initialise();
        setSpinnerAdapters();
        setSpinnerListeners();

        setDbsdate();
        setDbsdispatchdate();
        setDob();
        setExpirydate1();
        setExpirydate2();
        setTestDate();
    }
    //start functions to set datepickers
    private void setDob(){

        try{
            dob.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dtp.setDatePicker(dob);
                }
            });

        }
        catch(Exception e){

        }
    }

    private void setTestDate(){

        try{

            testdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dtp.setDatePicker(testdate);

                }
            });

        }
        catch(Exception e){

        }
    }

    private void setExpirydate1(){

        try{

            expirydate1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dtp.setDatePicker(expirydate1);

                }
            });

        }
        catch(Exception e){

        }
    }

    private void setExpirydate2(){

        try{

            expirydate2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dtp.setDatePicker(expirydate2);

                }
            });

        }
        catch(Exception e){

        }
    }

    private void setDbsdate(){

        try{

            dbsdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dtp.setDatePicker(dbsdate);

                }
            });

        }
        catch(Exception e){

        }
    }

    private void setDbsdispatchdate(){

        try{

            dbsdispatchdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    dtp.setDatePicker(dbsdispatchdate);

                }
            });

        }
        catch(Exception e){

        }
    }
    //end functions to set datepickers


    private void validate(){

        try{


            samplenumberS=samplenumber.getText().toString();
            clientnameS=clientname.getText().toString();
            dobS=dob.getText().toString();
            telephoneS=telephone.getText().toString();
            testdateS=testdate.getText().toString();
            lotnumber1S=lotnumber1.getText().toString();
            expirydate1S=expirydate1.getText().toString();
            lotnumber2S=lotnumber2.getText().toString();
            expirydate2S=expirydate2.getText().toString();
            sampletesternameS=sampletestername.getText().toString();
            dbsdateS=dbsdate.getText().toString();
            dbsdispatchdateS=dbsdispatchdate.getText().toString();
            requestingproviderS=requestingprovider.getText().toString();

            if(samplenumberS.trim().isEmpty()){
                Toast.makeText(this, "sample number is required", Toast.LENGTH_SHORT).show();
            }
            else if(clientnameS.trim().isEmpty()){

                Toast.makeText(this, "client name is required", Toast.LENGTH_SHORT).show();
            }
            else if(dobS.trim().isEmpty()){

                Toast.makeText(this, "DOB is required", Toast.LENGTH_SHORT).show();
            }
            else if(SelectedSex.trim().isEmpty()){

                Toast.makeText(this, "sex is required", Toast.LENGTH_SHORT).show();
            }
            else if(telephoneS.trim().isEmpty()){

                Toast.makeText(this, "telephone is required", Toast.LENGTH_SHORT).show();
            }
            else if(testdateS.trim().isEmpty()){

                Toast.makeText(this, "Test date is required", Toast.LENGTH_SHORT).show();
            }
            else if(SelecteddeliveryPoint.trim().isEmpty()){

                Toast.makeText(this, "Delivery point is required", Toast.LENGTH_SHORT).show();
            }
            else if(Selectedtestkit1.trim().isEmpty()){

                Toast.makeText(this, "Test kit 1 is required", Toast.LENGTH_SHORT).show();
            }
            else if(lotnumber1S.trim().isEmpty()){

                Toast.makeText(this, "Lot number 1 is required", Toast.LENGTH_SHORT).show();
            }
            else if(expirydate1S.trim().isEmpty()){

                Toast.makeText(this, "expiry date 1 is required", Toast.LENGTH_SHORT).show();
            }
            else if(Selectedtestkit2.trim().isEmpty()){

                Toast.makeText(this, "Test kit 2 is required", Toast.LENGTH_SHORT).show();
            }
            else if(lotnumber2S.trim().isEmpty()){

                Toast.makeText(this, "Lot number 2 is required", Toast.LENGTH_SHORT).show();
            }
            else if(expirydate2S.trim().isEmpty()){

                Toast.makeText(this, "expiry date 2 is required", Toast.LENGTH_SHORT).show();
            }
            else if(Selectedfinalresult.trim().isEmpty()){

                Toast.makeText(this, "final result is required", Toast.LENGTH_SHORT).show();
            }
            else if(sampletesternameS.trim().isEmpty()){

                Toast.makeText(this, "sample tester name is required", Toast.LENGTH_SHORT).show();
            }
            else if(dbsdateS.trim().isEmpty()){

                Toast.makeText(this, "DBS date is required", Toast.LENGTH_SHORT).show();
            }
            else if(dbsdispatchdateS.trim().isEmpty()){

                Toast.makeText(this, "DBS dispatch date is required", Toast.LENGTH_SHORT).show();
            }
            else if(requestingproviderS.trim().isEmpty()){

                Toast.makeText(this, "requesting provider is required", Toast.LENGTH_SHORT).show();
            }
            else{

                String userPhoneNumber="";

                List<UsersTable> myl=UsersTable.findWithQuery(UsersTable.class,"select * from Users_table limit 1");
                for(int y=0;y<myl.size();y++){

                    userPhoneNumber=myl.get(y).getPhonenumber();
                }
//                Toast.makeText(this, "submitting", Toast.LENGTH_SHORT).show();
                String message=samplenumberS+"*"+clientnameS+"*"+dobS+"*"+SelectedSex+"*"+telephoneS+"*"+testdateS+"*"
                        +SelecteddeliveryPoint+"*"+Selectedtestkit1+"*"+lotnumber1S+"*"+expirydate1S+"*"+Selectedtestkit2+
                        "*"+lotnumber2S+"*"+expirydate2S+"*"+Selectedfinalresult+"*"+sampletesternameS+"*"+dbsdateS+
                        "*"+dbsdispatchdateS+"*"+requestingproviderS;


                acs.submitHtsData(Base64Encoder.encryptString(userPhoneNumber),Base64Encoder.encryptString(message));
                clearfields();



            }


//            EditText samplenumber,clientname,dob,telephone,testdate,lotnumber1,expirydate1,lotnumber2,expirydate2,sampletestername,dbsdate,dbsdispatchdate,requestingprovider;
//            MaterialBetterSpinner SpinnerSex,SpinnerdeliveryPoint,Spinnertestkit1,Spinnertestkit2,Spinnerfinalresult;
//            String SelectedSex,SelecteddeliveryPoint,Selectedtestkit1,Selectedtestkit2,Selectedfinalresult;



        }
        catch(Exception e){


        }
    }

    public void CancelHtsSamples(View v){

        try{

            Toast.makeText(this, "cancelling", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){


        }
    }

    public void SubmitHtsSample(View v){

        try{

            validate();
        }
        catch(Exception e){


        }
    }



    private void setSpinnerListeners(){

        try{

            setSpinnerDeliveryPointListener();
            setSpinnerFinalresultListener();
            setSpinnerSexListener();
            setSpinnerTestkit1Listener();
            setSpinnerTestkit2Listener();
        }
        catch(Exception e){

            Toast.makeText(this, "error setting adapters", Toast.LENGTH_SHORT).show();
        }
    }

    private void initialise(){

        try{

            acs=new AccessServer(HtsSampleRemoteLogin.this);
            dtp=new DateTimePicker(HtsSampleRemoteLogin.this);
            testkit1ll=(LinearLayout) findViewById(R.id.lltestkit1);
            testkit2ll=(LinearLayout) findViewById(R.id.lltestkit2);

            samplenumber=(EditText) findViewById(R.id.htssamplenumber);
            clientname=(EditText) findViewById(R.id.htssampleclientname);
            dob=(EditText) findViewById(R.id.htssampledob);
            telephone=(EditText) findViewById(R.id.htssampletelephone);
            testdate=(EditText) findViewById(R.id.htssampletestdate);
            lotnumber1=(EditText) findViewById(R.id.htssamplelotnumber1);
            expirydate1=(EditText) findViewById(R.id.htssampleexpirydate1);
            lotnumber2=(EditText) findViewById(R.id.htssamplelotnumber2);
            expirydate2=(EditText) findViewById(R.id.htssampleexpirydate2);
            sampletestername=(EditText) findViewById(R.id.htssampletestersname);
            dbsdate=(EditText) findViewById(R.id.htssampledbsdate);
            dbsdispatchdate=(EditText) findViewById(R.id.htssampledbsdispatchdate);
            requestingprovider=(EditText) findViewById(R.id.htssamplerequestingprovider);

            SpinnerSex=(MaterialBetterSpinner) findViewById(R.id.htssamplesex);
            SpinnerdeliveryPoint=(MaterialBetterSpinner) findViewById(R.id.htssampleservicedeliverypoint);
            Spinnertestkit1=(MaterialBetterSpinner) findViewById(R.id.htssampletestkit1);
            Spinnertestkit2=(MaterialBetterSpinner) findViewById(R.id.htssampletestkit2);
            Spinnerfinalresult=(MaterialBetterSpinner) findViewById(R.id.htssamplefinalresult);

            SelectedSex="";
            SelecteddeliveryPoint="";
            Selectedtestkit1="";
            Selectedtestkit2="";
            Selectedfinalresult="";

            samplenumberS="";
            clientnameS="";
            dobS="";
            telephoneS="";
            testdateS="";
            lotnumber1S="";
            expirydate1S="";
            lotnumber2S="";
            expirydate2S="";
            sampletesternameS="";
            dbsdateS="";
            dbsdispatchdateS="";
            requestingproviderS="";


            arrayAdapterSex = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTSEX);
            arrayAdapterDeliveryPoint = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTDELIVERYPOINT);
            arrayAdapterTestkit1 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTTESTKITS);
            arrayAdapterTestkit2 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTTESTKITS);
            arrayAdapterFinalResult = new ArrayAdapter<String>(this,
                    android.R.layout.simple_dropdown_item_1line, Config.SPINNERLISTFINALRESULTS);

        }
        catch(Exception e){


        }
    }

    public void setSpinnerAdapters(){

        try{
            SpinnerSex.setAdapter(arrayAdapterSex);
            SpinnerdeliveryPoint.setAdapter(arrayAdapterDeliveryPoint);
            Spinnertestkit1.setAdapter(arrayAdapterTestkit1);
            Spinnertestkit2.setAdapter(arrayAdapterTestkit2);
            Spinnerfinalresult.setAdapter(arrayAdapterFinalResult);


        }
        catch(Exception e){


            Toast.makeText(this, "error setting adapters", Toast.LENGTH_SHORT).show();
        }
    }


    private void changeStatusBarColor(String color){
        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.parseColor(color));
        }
    }

    private void setToolBar(){

        try{

            Toolbar toolbar = (Toolbar) findViewById(R.id.htssampletoolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Hts sample remote login");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);

        }
        catch(Exception e){


        }
    }

    private void clearfields(){

        samplenumber.setText("");
        clientname.setText("");
        dob.setText("");
        telephone.setText("");
        testdate.setText("");
        lotnumber1.setText("");
        expirydate1.setText("");
        lotnumber2.setText("");
        expirydate2.setText("");
        sampletestername.setText("");
        dbsdate.setText("");
        dbsdispatchdate.setText("");
        requestingprovider.setText("");

        SpinnerSex.setText("");
        SpinnerdeliveryPoint.setText("");
        Spinnertestkit1.setText("");
        Spinnertestkit2.setText("");
        Spinnerfinalresult.setText("");

        SelectedSex="";
        SelecteddeliveryPoint="";
        Selectedtestkit1="";
        Selectedtestkit2="";
        Selectedfinalresult="";

        setSpinnerAdapters();

    }



    //start set spinner listeners

    private void setSpinnerSexListener(){

        try{


            SpinnerSex.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    SelectedSex = SpinnerSex.getText().toString();

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



                }
            });

        }
        catch(Exception e){


        }
    }


    private void setSpinnerDeliveryPointListener(){

        try{


            SpinnerdeliveryPoint.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    SelecteddeliveryPoint = SpinnerdeliveryPoint.getText().toString();

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



                }
            });

        }
        catch(Exception e){


        }
    }



    private void setSpinnerTestkit1Listener(){

        try{


            Spinnertestkit1.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    Selectedtestkit1= Spinnertestkit1.getText().toString();
                    Toast.makeText(HtsSampleRemoteLogin.this, ""+Selectedtestkit1, Toast.LENGTH_SHORT).show();
                    testkit1ll.setVisibility(View.VISIBLE);


//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



                }
            });

        }
        catch(Exception e){


        }
    }


    private void setSpinnerTestkit2Listener(){

        try{


            Spinnertestkit2.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    Selectedtestkit2 = Spinnertestkit2.getText().toString();
                    testkit2ll.setVisibility(View.VISIBLE);

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



                }
            });

        }
        catch(Exception e){


        }
    }


    private void setSpinnerFinalresultListener(){

        try{


            Spinnerfinalresult.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                    Selectedfinalresult = Spinnerfinalresult.getText().toString();

//                    Toast.makeText(Report.this, "selected "+selectedWhere, Toast.LENGTH_SHORT).show();



                }
            });

        }
        catch(Exception e){


        }
    }

    //end set spinner listeners


}
