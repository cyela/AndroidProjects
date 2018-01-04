package com.example.chandrakanth.passwordgenerator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    ExecutorService threadpool;
    ProgressDialog p;
    Handler handler;
    SeekBar stc,stl,sac,sal;
    ArrayList<String> t;
    ArrayList<String> as;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        stc=(SeekBar)findViewById(R.id.seekBarTC);
        stl=(SeekBar)findViewById(R.id.seekBarTL);
        sac=(SeekBar)findViewById(R.id.seekBarAC);
        sal=(SeekBar)findViewById(R.id.seekBarAL);
        final TextView tc=(TextView)findViewById(R.id.textViewCountt);
        final TextView tl=(TextView)findViewById(R.id.textViewLengtht);
        final TextView ac=(TextView)findViewById(R.id.textViewCounta);
        final TextView al=(TextView)findViewById(R.id.textViewLengtha);
        Button b=(Button)findViewById(R.id.button);
        p =new ProgressDialog(MainActivity.this);
        threadpool= Executors.newFixedThreadPool(2);
        t=new ArrayList<String>();
        as=new ArrayList<String>();
        stc.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tc.setText(String.valueOf(stc.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        stl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tl.setText(String.valueOf(stl.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sac.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ac.setText(String.valueOf(sac.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sal.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                al.setText(String.valueOf(sal.getProgress()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stc.getProgress() < 1 || stl.getProgress() < 7 || sac.getProgress() < 1 || sal.getProgress() < 7) {
                    Toast.makeText(getApplicationContext(), "Count should be atleast 1 and length of atleast 7", Toast.LENGTH_LONG).show();
                } else {
                    as.clear();
                    t.clear();
                    handler = new Handler(new Handler.Callback() {
                        @Override
                        public boolean handleMessage(Message msg) {

                            switch (msg.what) {
                                case DoWork.Status_Start:

                                    p.setTitle("Generating Passwords");
                                    p.setCancelable(false);
                                    p.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                                    p.setMax(stc.getProgress() + sac.getProgress());
                                    p.show();
                                    break;
                                case DoWork.Status_Progress:

                                    p.setProgress(msg.getData().getInt("count_progress"));

                                    t.add(msg.getData().getString("password"));
                                    //p.setProgress(1);
                                    break;
                                case DoWork.Status_End:
                                    if (stc.getProgress() > sac.getProgress()) {
                                        p.dismiss();
                                        Intent i = new Intent(getApplicationContext(), GenPassActivity.class);
                                        Bundle b = new Bundle();
                                        //b.putInt("count",stc.getProgress());
                                        //b.putSerializable("Thread_Password",a);
                                        b.putSerializable("AP", as);
                                        b.putSerializable("TP", t);
                                        i.putExtras(b);
                                        startActivity(i);

                                        break;
                                    }
                            }
                            return false;
                        }
                    });
                    threadpool.execute(new DoWork());
                    new AsyncPassword().execute(sac.getProgress(), sal.getProgress());
                }
            }
        });
    }

    class DoWork implements Runnable
    {
        static  final int Status_Start=0;
        static  final int Status_Progress=1;
        static  final int Status_End=2;
        @Override
        public void run() {
            Message msg=new Message();
            msg.what=Status_Start;
            handler.sendMessage(msg);
            int count=stc.getProgress();
            int length=stl.getProgress();
            for (int i=1;i<=count;i++)
            {
                String password= UtilPass.getPassword(length);
                msg=new Message();
                msg.what=Status_Progress;
                Bundle extras=new Bundle();
                extras.putString("password",password);
                extras.putInt("count_progress",i);
                msg.setData(extras);
                handler.sendMessage(msg);
            }
            msg=new Message();
            msg.what=Status_End;
            handler.sendMessage(msg);

        }
    }
    private class AsyncPassword extends AsyncTask<Integer,String,String> {


        @Override
        protected String doInBackground(Integer... params)
        {
            int scount=params[0];
            int slength=params[1];
            for(int i=1;i<=scount;i++)
            {
                String s= UtilPass.getPassword(slength);

                publishProgress(s);
            }

            return null;
        }
        @Override
        protected void onPreExecute()
        {
            /*super.onPreExecute();
            ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            progressDialog.setMax(10);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Generating Psswords");
            progressDialog.show();*/
        }
        public void onProgressUpdate(String... args)
        {
            p.setProgress(p.getProgress()+1);
            as.add(args[0]);

        }
        protected void onPostExecute(String result)
        {
            //  do something after execution
            super.onPostExecute(result);

            if(stc.getProgress()<=sac.getProgress()) {
                p.dismiss();
                Intent i = new Intent(getApplicationContext(), GenPassActivity.class);
                Bundle b = new Bundle();
                //b.putInt("count",stc.getProgress());
                //b.putSerializable("Thread_Password",a);
                b.putSerializable("AP", as);
                b.putSerializable("TP", t);
                i.putExtras(b);
                startActivity(i);

                as.clear();
                t.clear();


            }

        }
    }



}

