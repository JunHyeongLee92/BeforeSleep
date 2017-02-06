package com.mobile.group22;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.text.DecimalFormat;

/**
 * Created by moonsu on 2016-02-14.
 */
public class Finish extends AppCompatActivity {
    String _str;
    ImageView imageView1, imageView2, imageView3;
    TextView txt_first, txt_second, txt_middle;
    TextView score_first, score_second, score_middle;
    int first_index, second_index;
    String first, second, str_first, str_second;
    Intent intent;
    Double d1,d2;
    TextView guna;
    int s1,s2;
    ImageButton line_btn;
    DecimalFormat t = new DecimalFormat("##0");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);

        final int img[] = {R.drawable.anger, R.drawable.contempt, R.drawable.disgust, R.drawable.fear, R.drawable.happiness, R.drawable.netural, R.drawable.sadness, R.drawable.surprise};
        intent = getIntent();
        first = intent.getStringExtra("first");
        second = intent.getStringExtra("second");
        d1 = Double.parseDouble(first);
        d2 = Double.parseDouble(second);
        str_first = intent.getStringExtra("str_first");
        str_second = intent.getStringExtra("str_second");
        first_index = intent.getIntExtra("first_index", 0);
        second_index = intent.getIntExtra("second_index", 0);
        txt_first = (TextView)findViewById(R.id.finish_name_left);
        txt_second = (TextView)findViewById(R.id.finish_name_right);
        txt_middle = (TextView)findViewById(R.id.finish_name_middle);
        score_first = (TextView)findViewById(R.id.finish_emotion_left);
        score_second = (TextView)findViewById(R.id.finish_emotion_right);
        score_middle = (TextView)findViewById(R.id.finish_emotion_middle);
        imageView1 = (ImageView)findViewById(R.id.finish_imageView_middle);
        imageView2 = (ImageView)findViewById(R.id.finish_imageView_left);
        imageView3 = (ImageView)findViewById(R.id.finish_imageView_right);
        guna = (TextView)findViewById(R.id.guna);
        Log.d("uniton", "=================변수값 출력===========");
        Log.d("uniton", "first : " + first);
        Log.d("uniton", "str_first : " + str_first);
        Log.d("uniton", "second : " + second);
        Log.d("uniton", "str_second : " + str_second);
        Log.d("uniton", "=======================================");
        if(str_second == null) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("EmotionSting");
            query.getInBackground("7IyxxUu1pl", new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    String tmp = IdxToStr(first_index);
                    String str = object.getString(tmp);

                    Log.d("123", "=================================================================");

                    _str = str;
                    _str += " 일이 있진 않았니?";
                    guna.setText(_str);

                    imageView1.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.INVISIBLE);
                    imageView3.setVisibility(View.INVISIBLE);
                    txt_middle.setText(str_first);
                    txt_middle.setVisibility(View.VISIBLE);
                    score_middle.setVisibility(View.VISIBLE);
                    txt_first.setVisibility(View.INVISIBLE);
                    score_first.setVisibility(View.INVISIBLE);
                    txt_second.setVisibility(View.INVISIBLE);
                    score_second.setVisibility(View.INVISIBLE);
                    score_middle.setText("" + t.format(d1) + "%");
                    Drawable drawable = getResources().getDrawable(img[first_index]);
                    imageView1.setBackground(drawable);

                    line_btn = (ImageButton)findViewById(R.id.line_btn);
                    line_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            StepFour.first = t.format(d1) + "%";
                            StepFour.second = t.format(d2) + "%";
                            StepFour.str_first = str_first;
                            StepFour.str_second = str_second;
                            StepFour.first_index = first_index;
                            StepFour.second_index = second_index;
                            Intent intent = new Intent(Finish.this, StepOne.class);
                            startActivity(intent);
                            finish();
                        }
                    });

                }
            });
        }
        else {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("EmotionSting");
            query.getInBackground("7IyxxUu1pl", new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    Log.d("123", "=================================================================");
                    String tmp = IdxToStr(first_index)+"2";
                    String str = object.getString(tmp);

                    _str = str;
                    _str += "거나, ";
                    tmp = IdxToStr(second_index);
                    str = object.getString(tmp);
                    _str += str;
                    _str += " 일이 있진 않았니?";
                    guna.setText(_str);

                    imageView3.setVisibility(View.VISIBLE);
                    imageView2.setVisibility(View.VISIBLE);
                    imageView1.setVisibility(View.INVISIBLE);
                    txt_first.setText(str_first);
                    txt_second.setText(str_second);

                    score_first.setText(""+t.format(d1)+"%");
                    score_second.setText("" + t.format(d2) + "%");
                    Drawable drawable = getResources().getDrawable(img[first_index]);
                    imageView2.setBackground(drawable);
                    Drawable drawable2 = getResources().getDrawable(img[second_index]);
                    imageView3.setBackground(drawable2);
                    txt_middle.setVisibility(View.INVISIBLE);
                    score_middle.setVisibility(View.INVISIBLE);
                    txt_first.setVisibility(View.VISIBLE);
                    score_first.setVisibility(View.VISIBLE);
                    txt_second.setVisibility(View.VISIBLE);
                    score_second.setVisibility(View.VISIBLE);

                    line_btn = (ImageButton)findViewById(R.id.line_btn);
                    line_btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            StepFour.first = t.format(d1) + "%";
                            StepFour.second = t.format(d2) + "%";
                            StepFour.str_first = str_first;
                            StepFour.str_second = str_second;
                            StepFour.first_index = first_index;
                            StepFour.second_index = second_index;
                            Intent intent = new Intent(Finish.this, StepOne.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                }
            });
        }
    }
    public String IdxToStr(int _idx) {
        switch (_idx) {
            case 0:
                return "Anger";
            case 1:
                return "Contempt";
            case 2:
                return "Disgust";
            case 3:
                return "Fear";
            case 4:
                return "Happiness";
            case 5:
                return "Neutral";
            case 6:
                return "Sadness";
            case 7:
                return "Surprise";

        }
        return "no";
    }
}