package com.mobile.group22;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseACL;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class StepFour extends Activity {
	public static String edtTextGood;
	public static String edtTextBad;
	public static String edtTextPurpose;
	public static String first, second, str_first, str_second;
	public static int first_index, second_index;
	ImageView imageview1, imageview2, imageview3;
	TextView str_middle, str_left, str_right;
	TextView middle, left, right;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stepfour);
		int img[] = {R.drawable.anger, R.drawable.contempt, R.drawable.disgust, R.drawable.fear, R.drawable.happiness, R.drawable.netural, R.drawable.sadness, R.drawable.surprise};
		str_middle = (TextView)findViewById(R.id.remind_name_middle);
		str_left = (TextView)findViewById(R.id.remind_name_left);
		str_right = (TextView)findViewById(R.id.remind_name_right);
		middle = (TextView)findViewById(R.id.remind_emotion_middle);
		left = (TextView)findViewById(R.id.remind_emotion_left);
		right = (TextView)findViewById(R.id.remind_emotion_right);
		imageview1 = (ImageView)findViewById(R.id.remind_imageView_middle);
		imageview2 = (ImageView)findViewById(R.id.remind_imageView_left);
		imageview3 = (ImageView)findViewById(R.id.remind_imageView_right);
		TextView textBad = (TextView) findViewById(R.id.textBad);
		TextView textGood = (TextView) findViewById(R.id.textGood);
		TextView textPurpose = (TextView) findViewById(R.id.textPurpose);
		Log.d("uniton", "=================변수값 출력===========");
		Log.d("uniton", "first : " + first);
		Log.d("uniton", "str_first : " + str_first);
		Log.d("uniton", "second : " + second);
		Log.d("uniton", "str_second : " + str_second);
		Log.d("uniton", "first_index : " + first_index);
		Log.d("uniton", "second_index : " + second_index);
		Log.d("uniton", "=======================================");
		textBad.setText(edtTextBad);
		textGood.setText(edtTextGood);
		textPurpose.setText(edtTextPurpose);
		if (str_second==null)
		{
			imageview1.setVisibility(View.VISIBLE);
			imageview2.setVisibility(View.INVISIBLE);
			imageview3.setVisibility(View.INVISIBLE);
			str_middle.setText(str_first);
			str_middle.setVisibility(View.VISIBLE);
			middle.setVisibility(View.VISIBLE);
			str_left.setVisibility(View.INVISIBLE);
			left.setVisibility(View.INVISIBLE);
			str_right.setVisibility(View.INVISIBLE);
			right.setVisibility(View.INVISIBLE);
			middle.setText(first);
			Drawable drawable = getResources().getDrawable(img[first_index]);
			imageview1.setBackground(drawable);
		}
		else {
			imageview3.setVisibility(View.VISIBLE);
			imageview2.setVisibility(View.VISIBLE);
			imageview1.setVisibility(View.INVISIBLE);
			str_left.setText(str_first);
			str_right.setText(str_second);

			left.setText(first);
			right.setText(second);
			Drawable drawable = getResources().getDrawable(img[first_index]);
			imageview2.setBackground(drawable);
			Drawable drawable2 = getResources().getDrawable(img[second_index]);
			imageview3.setBackground(drawable2);
			str_middle.setVisibility(View.INVISIBLE);
			middle.setVisibility(View.INVISIBLE);
			str_left.setVisibility(View.VISIBLE);
			left.setVisibility(View.VISIBLE);
			str_right.setVisibility(View.VISIBLE);
			right.setVisibility(View.VISIBLE);
		}

		StepOne.One.finish();
		StepTwo.Two.finish();
		StepThree.Three.finish();
		Date date = new Date();

		String date_str = ""+date.getYear()+".0"+date.getMonth()+"."+date.getDate();
		Log.d("uniton", "========date_str : " + date_str);
		final ParseObject Test = new ParseObject("history");

		// Create a column named "ImageName" and set the string
		Test.put("bad", edtTextBad);

		// Create a column named "ImageFile" and insert the image
		Test.put("good", edtTextGood);

		Test.put("plan", edtTextPurpose);

		Test.put("date", date_str);

		Test.put("first", first);

		Test.put("second", second);

		Test.put("str_first", str_first);

		Test.put("str_second", str_second);
		// Create the class and the columns
		Test.saveInBackground();
	}

	public void onMyButtonClick(View v) {
		Intent intent = new Intent(StepFour.this, Dialog.class);
		startActivity(intent);
		finish();

	}
}
