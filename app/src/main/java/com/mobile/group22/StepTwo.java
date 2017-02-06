package com.mobile.group22;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.KeyEventCompat;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;

public class StepTwo extends Activity {
	public static Activity Two;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.steptwo);
		Two = this;

	}

	public void onMyButtonClick(View v) {

		Intent intent = new Intent();
		ComponentName componentName;

		switch (v.getId()) {
		case R.id.btnBefore:
			new Thread(new Runnable() {
				public void run() {
					KeyEvent event1 = new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK);
					new Instrumentation().sendKeySync(event1);
					KeyEvent event2 = new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK);
					new Instrumentation().sendKeySync(event2);
				}
			}).start();
			
			

			break;
		case R.id.btnNext:
			EditText text = (EditText) findViewById(R.id.edt);

			StepFour.edtTextGood = text.getText().toString();

			componentName = new ComponentName(getApplicationContext(), StepThree.class);
			intent.setComponent(componentName);
			startActivity(intent);
		}
	}
}
