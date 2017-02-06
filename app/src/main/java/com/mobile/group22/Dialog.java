package com.mobile.group22;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.WindowManager;

/**
 * Created by moonsu on 2015-10-19.
 */
public class Dialog extends AppCompatActivity {
    DialogThread dialogThread = null;
    boolean state = true;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dialog);

        dialogThread = new DialogThread();
        dialogThread.start();

    }
    public class DialogThread extends Thread {

        @Override
        public void run() {
            super.run();
            while (state)
            {
                try{
                    handle.sendMessage(handle.obtainMessage());
                    sleep(3000);
                    state = false;
                    Intent intent = new Intent(Dialog.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                } catch (Throwable t){}
            }

        }

        Handler handle = new Handler()
        {
            @Override
            public void handleMessage(Message msg) {
                Log.d("belt", "###############");
            }
        };
    }
}
