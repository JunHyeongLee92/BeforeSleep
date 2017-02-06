package com.mobile.group22;

import android.app.Activity;
import android.os.Bundle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.GetCallback;
import com.parse.GetDataCallback;
import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.ByteArrayOutputStream;

/**
 * Created by moonsu on 2016-02-13.
 */
public class Test extends Activity {
    private TextView m_TextViewId = null;
    private ImageView ad1 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        m_TextViewId = (TextView)findViewById(R.id.textView_id);
        ad1 = (ImageView)findViewById(R.id.imageView);

        Button buttonPut = (Button)findViewById(R.id.button_put);
        buttonPut.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                        R.drawable.common_ic_googleplayservices);
                // Convert it to byte
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                // Compress image to lower quality scale 1 - 100
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] image = stream.toByteArray();

                // Create the ParseFile
                ParseFile file = new ParseFile("androidbegin.png", image);
                // Upload the image into Parse Cloud
                file.saveInBackground();

                final ParseObject Test = new ParseObject("test");
                Test.setACL(new ParseACL(ParseUser.getCurrentUser()));

                // Create a column named "ImageName" and set the string
                Test.put("ImageName", "AndroidBegin Logo");

                // Create a column named "ImageFile" and insert the image
                Test.put("ImageFile", file);

                // Create the class and the columns
                Test.saveInBackground();

                Test.put("Emotion1", "슬픈");
                Test.put("Emotion2", "화나");

                Test.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (null == e) {
                            Toast.makeText(Test.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                            m_TextViewId.setText(Test.getObjectId());
                        }
                    }
                });
            }
        });

        Button buttonGet = (Button)findViewById(R.id.button_get);
        buttonGet.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("test");
                query.getInBackground(m_TextViewId.getText().toString(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        String str1 = object.getString("Emotion1");
                        String str2 = object.getString("Emotion2");
                        ParseFile fileObject = (ParseFile) object.get("ImageFile");
                        fileObject.getDataInBackground(new GetDataCallback() {
                            public void done(byte[] data,
                                             ParseException e) {
                                if (e == null) {
                                    Log.d("test",
                                            "We've got data in data.");
                                    // Decode the Byte[] into
                                    // Bitmap
                                    Bitmap bmp = BitmapFactory.decodeByteArray(data, 0,data.length);
                                    // Get the ImageView from main.xml
                                    //ImageView image = (ImageView) findViewById(R.id.ad1);
                                    ImageView ad1=(ImageView) findViewById(R.id.imageView);
                                    // Set the Bitmap into the
                                    // ImageView
                                    ad1.setImageBitmap(bmp);

                                } else {
                                    Log.d("test",
                                            "There was a problem downloading the data.");
                                }
                            }
                        });

                        Toast.makeText(getApplicationContext(), str1 + ", " + str2, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Button buttonUpdate = (Button)findViewById(R.id.button_update);
        buttonUpdate.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("test");
                query.getInBackground(m_TextViewId.getText().toString(), new GetCallback<ParseObject>() {
                    @Override
                    public void done(ParseObject object, ParseException e) {
                        object.put("Emotion1", "화난");
                        object.put("Emotion2", "슬프");
                        object.saveInBackground();
                        Toast.makeText(getApplicationContext(), "update success!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        Button buttonDelete = (Button)findViewById(R.id.button_delete);
        buttonDelete.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {

            }
        });
    }
}
