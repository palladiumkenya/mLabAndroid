package com.example.kenweezy.mytablayouts;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kenweezy.mytablayouts.encryption.MCrypt;
import com.example.kenweezy.mytablayouts.messagedialog.MessageDialog;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;

/**
 * Created by KENWEEZY on 2017-01-12.
 */

public class FragmentEidAll extends Fragment {


    private List<Mydata> mymesslist;
    private static FragmentEidAll inst;
    ArrayList<String> smsMessagesList = new ArrayList<String>();
    ListView smsListView;
    ArrayAdapter arrayAdapter;
    int counter=0;
    MessageDialog mdialog;

    MCrypt mcrypt=new MCrypt();

    Myshortcodes msc=new Myshortcodes();
    String smsMessage = "";
    FrameLayout fl;
    public static FragmentEidAll instance() {
        return (new FragmentEidAll());
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
    }

//    ArrayAdapter<String> myadapter;
    ListView lv;
    private MessagesAdapter myadapter;
    @Nullable


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragmenteidall, container, false);
        lv=(ListView) v.findViewById(R.id.lveidall);
        fl=(FrameLayout) v.findViewById(R.id.eid);

        mymesslist=new ArrayList<>();
        mdialog=new MessageDialog(getActivity());
        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%' group by m_body", null);

//        if (bdy.isEmpty())
//            return 0;
//        myadapter.clear();


        for(int x=0;x<bdy.size();x++){

            counter += 1;
            String messbdy=bdy.get(x).getmBody();
            String messId=bdy.get(x).getMessageId();
            String ndate = bdy.get(x).getmTimeStamp();
            String read=bdy.get(x).getRead();

            String mvcnt=bdy.get(x).getViralCount();
            int vcount=Integer.parseInt(mvcnt);

            String mychk=bdy.get(x).getChkd();
            boolean mychkB;
            if(mychk.contentEquals("true")){
                mychkB=true;
            }
            else{
                mychkB=false;
            }

            String[] checkSplitdate=ndate.split("/");


            if(checkSplitdate.length>1){

            }
            else{
                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(Long.parseLong(ndate));
                ndate = formatter.format(calendar.getTime());

            }

            mymesslist.add(new Mydata(mychkB,messbdy,ndate,read,vcount,messId));


        }

//        myadapter=new ArrayAdapter<String>(getActivity(),R.layout.listview_row,R.id.mytext,smsMessagesList){
//
//            @NonNull
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//
//                View v=super.getView(position, convertView, parent);
//
//                int[] positions={0,1,4,5,8};
//                int x=0;
//                int x2=1;
//                int x3=5;
//
//                TextView tv1=(TextView) v.findViewById(R.id.mytext);
//               if(position==2){
//
////                   Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
//               }
//               else{}
//
//
//                return v;
//
//            }
//        };

        Collections.sort(mymesslist,Mydata.VlcountComparator);
        myadapter=new MessagesAdapter(getActivity(),mymesslist);
        lv.setAdapter(myadapter);


//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                try {
//
//                    String[] msgbdy=smsMessagesList.get(position).split("@");
//                    String date=msgbdy[1];
//                    String bdycont=msgbdy[0];
////                    Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();
//
//                    final String[] smsMessages = smsMessagesList.get(position).split("\n");
//                    final String address = smsMessages[0];
//
//
//
//                    List myl=Messages.findWithQuery(Messages.class,"Select * from Messages where m_body=?",bdycont);
//                    for(int x=0;x<myl.size();x++){
//
//                        Messages ms=(Messages) myl.get(x);
//                        ms.getId();
//                        ms.setRead("read");
////                    Toast.makeText(getActivity(), "id: "+ms.getId(), Toast.LENGTH_SHORT).show();
//                        ms.save();
//                    }
//
//
//                    List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages", null);
//
//                    for(int x=0;x<bdy.size();x++) {
//                        String messbdy = bdy.get(x).getmBody();
//                        String read=bdy.get(x).getRead();
//                        System.out.println(messbdy+" /*** "+read);
//                    }
//
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//        });



        onclick();
        onLongClick();

        CheckVisibility();


//        refreshSmsInbox();




        return v;
    }


   public void onLongClick(){

       try{

           lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
               @Override
               public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {


                    TextView tvread=(TextView) view.findViewById(R.id.mstitle);
//                    tvread.setText("read");
                    boolean txtChkd;

                    try{

                        String msgbdy=mymesslist.get(position).getMsgbody();
                        String msgdate=mymesslist.get(position).getDate();

//                    Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();


//                        MydialogBuilder(msgbdy,msgdate);

                        System.out.println("/*****///// "+msgbdy);
                        List myl=Messages.findWithQuery(Messages.class,"Select * from Messages where m_body=? group by m_body",msgbdy);
                        for(int x=0;x<myl.size();x++){

                            Messages ms=(Messages) myl.get(x);
                            ms.getId();
//                            ms.setRead("read");
//                    Toast.makeText(getActivity(), "id: "+ms.getId(), Toast.LENGTH_SHORT).show();
                            ms.save();
                        }


                        mymesslist.clear();
                        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%' group by m_body", null);

                        if (bdy.isEmpty())
                            return false;
//        myadapter.clear();


                        for(int x=0;x<bdy.size();x++){

                            counter += 1;
                            String messbdy=bdy.get(x).getmBody();
                            String messId=bdy.get(x).getMessageId();
                            String ndate = bdy.get(x).getmTimeStamp();
                            String read=bdy.get(x).getRead();
                            String mvcnt=bdy.get(x).getViralCount();
                            int vcount=Integer.parseInt(mvcnt);

                            String chkds=bdy.get(x).getChkd();
                            if(chkds.contentEquals("true")){

                                txtChkd=true;
                            }
                            else{

                                txtChkd=false;
                            }

                            String[] checkSplitdate=ndate.split("/");


                            if(checkSplitdate.length>1){

                            }
                            else{
                                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(Long.parseLong(ndate));
                                ndate = formatter.format(calendar.getTime());

                            }

                            mymesslist.add(new Mydata(txtChkd,messbdy,ndate,read,vcount,messId));


                        }

                        Collections.sort(mymesslist,Mydata.VlcountComparator);

                        Mydata model = mymesslist.get(position);

                        if (model.isSelected()) {

                            model.setSelected(false);
                            for(int x=0;x<myl.size();x++){

                                Messages ms=(Messages) myl.get(x);


                                ms.getId();

                                ms.setChkd("false");
                                ms.save();

                            }

                        }
                        else{

                            model.setSelected(true);
                            for(int x=0;x<myl.size();x++){

                                Messages ms=(Messages) myl.get(x);


                                ms.getId();

                                ms.setChkd("true");
                                ms.save();

                            }

                        }
                        mymesslist.set(position, model);

                        myadapter.notifyDataSetChanged();



                    }

                    catch(Exception e){}

                   return true;


               }
           });
       }
       catch(Exception e){


       }
   }

    public void onclick(){

        try{


            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {





                    TextView tvread=(TextView) view.findViewById(R.id.mstitle);
                    tvread.setText("read");
                    boolean sending=false;
                    boolean txtChkd;

                    try{

                        String msgbdy=mymesslist.get(position).getMsgbody();
                        String msgId=mymesslist.get(position).getMsgId();
                        String msgdate=mymesslist.get(position).getDate();

                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            System.out.println("testing "+timestamp);
                        String mytime=timestamp.toString();

//                    Toast.makeText(getActivity(), ""+date, Toast.LENGTH_SHORT).show();


//                        MydialogBuilder(msgbdy,msgdate);
                        mdialog.displayMessage(msgbdy,msgdate);

                        System.out.println("/*****///// "+msgbdy);
                        List myl=Messages.findWithQuery(Messages.class,"Select * from Messages where m_body=? group by m_body",msgbdy);
                        for(int x=0;x<myl.size();x++){

                            Messages ms=(Messages) myl.get(x);
                            if(ms.getRead().contentEquals("read")){
                                sending=false;

                            }
                            else{
                                sending=true;
                                ms.getId();
                                ms.setRead("read");
                                ms.setDateRead(mytime);
//                    Toast.makeText(getActivity(), "id: "+ms.getId(), Toast.LENGTH_SHORT).show();
                                ms.save();
                            }
                        }


                        if(sending){

                            String sendMessage="read*"+msgId;
                            SmsManager sm = SmsManager.getDefault();

                            String encrypted = MCrypt.bytesToHex( mcrypt.encrypt(sendMessage));

                            ArrayList<String> parts = sm.divideMessage(encrypted);
                            sm.sendMultipartTextMessage(msc.sendSmsShortcode, null, parts, null, null);

                        }


                        mymesslist.clear();
                        List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%' group by m_body", null);

                        if (bdy.isEmpty())
                            return;
//        myadapter.clear();


                        for(int x=0;x<bdy.size();x++){

                            counter += 1;
                            String messbdy=bdy.get(x).getmBody();
                            String ndate = bdy.get(x).getmTimeStamp();
                            String messId=bdy.get(x).getMessageId();
                            String read=bdy.get(x).getRead();
                            String mvcnt=bdy.get(x).getViralCount();
                            int vcount=Integer.parseInt(mvcnt);

                            String chkds=bdy.get(x).getChkd();
                            if(chkds.contentEquals("true")){

                                txtChkd=true;
                            }
                            else{

                                txtChkd=false;
                            }

                            String[] checkSplitdate=ndate.split("/");


                            if(checkSplitdate.length>1){

                            }
                            else{
                                DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTimeInMillis(Long.parseLong(ndate));
                                ndate = formatter.format(calendar.getTime());

                            }

                            mymesslist.add(new Mydata(txtChkd,messbdy,ndate,read,vcount,messId));


                        }

                        Collections.sort(mymesslist,Mydata.VlcountComparator);


                        myadapter.notifyDataSetChanged();



                    }

                    catch(Exception e){}








                }
            });
        }
        catch(Exception e){


        }
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        View v=inflater.inflate(R.layout.fragmenteidall, container, false);
//        lv=(ListView) v.findViewById(R.id.lveidall);
//
//        fl=(FrameLayout) v.findViewById(R.id.eid);
//
//        myadapter=new ArrayAdapter<String>(getActivity(),R.layout.listview_row,R.id.mytext,smsMessagesList);
//        lv.setAdapter(myadapter);
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                try {
//
//                    final String[] smsMessages = smsMessagesList.get(position).split("\n");
//                    final String address = smsMessages[0];
////
////                    new AlertDialog.Builder(view.getContext())
////
////                            // .setNeutralButton("Share",null)
////                            // .setPositiveButton("Print",null)
////                            .setNeutralButton("Print", new DialogInterface.OnClickListener()
////
////                            {
////                                @Override
////                                public void onClick(DialogInterface dialog, int which) {
////                                    Intent sendIntent = new Intent();
////                                    sendIntent.setAction(Intent.ACTION_SEND);
////                                    sendIntent.putExtra(Intent.EXTRA_TEXT, address);
////                                    sendIntent.setType("text/plain");
////                                    startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));
////
////
////                                }
////                            })
////                            .setMessage(address)
////                            .setNegativeButton("Close", null).show();
////
////
////
////
////
//               MydialogBuilder(address,"mydate here");
//                }
//                catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//
//        CheckVisibility();
//
//        refreshSmsInbox();
//
//
//
//
//        return v;
//    }

    public void MydialogBuilder(final String message,final String date){

        AlertDialog.Builder b = new AlertDialog.Builder(getActivity());

        b.setMessage(message+"\n"+date);
        b.setCancelable(false);

        b.setNegativeButton("Close", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        b.setNeutralButton("Print", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                Intent sendIntent = new Intent();
                                    sendIntent.setAction(Intent.ACTION_SEND);
                                    sendIntent.putExtra(Intent.EXTRA_TEXT, message+"\n"+date);
//                                    sendIntent.putExtra(Intent.EXTRA_TEXT, date);
                                    sendIntent.setType("text/plain");
                                    startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share)));

            }
        });

        AlertDialog a=b.create();

        a.show();

        Button bq = a.getButton(DialogInterface.BUTTON_NEGATIVE);
        Button bn = a.getButton(DialogInterface.BUTTON_NEUTRAL);
        bq.setTextColor(Color.RED);
        bn.setTextColor(Color.BLUE);
    }


//    public void refreshSmsInbox() {
//        try {
//            ContentResolver contentResolver = getActivity().getContentResolver();
//            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40149'", null, null);
//            int indexBody = smsInboxCursor.getColumnIndex("body");
//            int indexAddress = smsInboxCursor.getColumnIndex("address");
//
//            if (indexBody < 0 || !smsInboxCursor.moveToFirst())
//                return;
//            myadapter.clear();
//
//
//            do {
//                String str = smsInboxCursor.getString(indexBody);
//                String mystrbdy = smsInboxCursor.getString(indexBody);
//                String stw = new String(mystrbdy);
//                String msgaddr = smsInboxCursor.getString(indexAddress);
//
//                if (stw.contains("FFEID")) {
//                    counter += 1;
//
//                    myadapter.add(mystrbdy);
//
//                }
//
//
//            } while (smsInboxCursor.moveToNext());
//        }
//        catch(Exception e){
//
//        }
//
//
//    }

    public void refreshSmsInbox() {
        try {

            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%' group by m_body", null);

            if (bdy.isEmpty())
                return;
//            myadapter.clear();


            for(int x=0;x<bdy.size();x++){

                counter += 1;
                String messbdy=bdy.get(x).getmBody();



                String ndate = bdy.get(x).getmTimeStamp();
                String read=bdy.get(x).getRead();
                String messId=bdy.get(x).getMessageId();
                String mvcnt=bdy.get(x).getViralCount();
                int vcount=Integer.parseInt(mvcnt);


                String mychkd=bdy.get(x).getChkd();
                boolean txtChkd;

                if(mychkd.contentEquals("true")){

                    txtChkd=true;
                }
                else{
                    txtChkd=false;


                }

                String bdycont=messbdy+"@"+ndate;


                String[] checkSplitdate=ndate.split("/");


                if(checkSplitdate.length>1){

                }
                else{
                    DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(Long.parseLong(ndate));
                    ndate = formatter.format(calendar.getTime());

                }
                mymesslist.add(new Mydata(txtChkd,messbdy,ndate,read,vcount,messId));

//                myadapter.add(bdycont);



            }

        }
        catch(Exception e){

        }


    }

    public void updateList(final String smsMessage) {
        try {
//            myadapter.insert(smsMessage, 0);
            refreshSmsInbox();
            Collections.sort(mymesslist,Mydata.VlcountComparator);
            myadapter=new MessagesAdapter(getActivity(),mymesslist);
            myadapter.notifyDataSetChanged();
        }
        catch(Exception e){

        }
    }








//    public void refreshSmsInbox() {
//        try {
////            ContentResolver contentResolver = getActivity().getContentResolver();
////            Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, "address='40149'", null, null);
////            int indexBody = smsInboxCursor.getColumnIndex("body");
////            int indexAddress = smsInboxCursor.getColumnIndex("address");
//            List<Messages> bdy = Messages.findWithQuery(Messages.class, "Select * from Messages where m_body like'%FFEID%'", null);
//
//            if (bdy.isEmpty())
//                return;
//            myadapter.clear();
//
//
//            for(int x=0;x<bdy.size();x++){
//
//                counter += 1;
//                String messbdy=bdy.get(x).getmBody();
//
//                String ndate = bdy.get(x).getmTimeStamp();
//
//                String[] day=ndate.split("/");
//                String year=day[2];
//                String[] newyear=year.split("\\s+");
//                String myyear=newyear[0];
//                String bdycont=messbdy+"@"+ndate;
//
//                myadapter.add(bdycont);
//
//
//            }
//
//        }
//        catch(Exception e){
//
//        }
//
//
//    }
//
//    public void updateList(final String smsMessage) {
//        try {
//            myadapter.insert(smsMessage, 0);
//            myadapter.notifyDataSetChanged();
//        }
//        catch(Exception e){
//
//        }
//    }

    public void doSearching(CharSequence s){
        //refreshSmsInbox();
        try {
            myadapter.getFilter().filter(s.toString());
        }
        catch(Exception e){

            Toast.makeText(getActivity(), "unable to filter: "+e, Toast.LENGTH_SHORT).show();
        }



    }

    public void CheckVisibility(){

        if(lv.getVisibility()==View.GONE){
            lv.setVisibility(View.GONE);

        }

    }

    public void replaceContent(Fragment newfrag){
        getChildFragmentManager().beginTransaction().replace(R.id.eid,newfrag).commit();
        lv.setVisibility(View.GONE);
        fl.setVisibility(View.VISIBLE);


    }


    public Fragment getFrag(){

        return getChildFragmentManager().findFragmentById(R.id.eid);
    }
}
