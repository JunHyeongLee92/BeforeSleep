package com.mobile.group22;

import android.annotation.SuppressLint;
import android.app.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.StrictMode;
import android.provider.MediaStore;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.internal.Constants;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.ResponseHeaderOverrides;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.parse.FindCallback;
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
import com.pixelcan.emotionanalysisapi.EmotionRestClient;
import com.pixelcan.emotionanalysisapi.ResponseCallback;
import com.pixelcan.emotionanalysisapi.models.FaceAnalysis;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import CustomList.CustomAdapter;
import CustomList.ItemData;



public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView imageView;
    ImageView imageView2;
    ImageView imageView3;
    //감정번호 1, 2
    double first;
    double second;
    double[] emotionArr = new double[8];

    String str_first;
    String str_second;

    GregorianCalendar toDay = new GregorianCalendar(); // 현재 날짜 받아옴
    int year;
    int month;
    int day;


    String week = "";

    Calendar cal = Calendar.getInstance();


    ListView mListEmotion;
    public CustomAdapter adapter;
    public ArrayList<ItemData> itemDatas = new ArrayList<ItemData>();
    public ItemData itemData;
    ArrayList<Bitmap> bitmap = new ArrayList<>();
    ArrayList<String> date_str = new ArrayList<>();
    ArrayList<String> emotion = new ArrayList<>();
    ArrayList<String> emotion_score = new ArrayList<>();
    File fileToUpload;
    Double angerScore;
    FileInputStream mFileInputStream;
    ImageButton addBtn;
    String path;
    TextView testText;
    public static Button t1;
    public static Button t2;
    public static Button t3;
    ViewPager mViewPager;
    Button wirteCamera, writeGallery;
    AlertDialog.Builder aDialog;
    AlertDialog ad;
    SectionsPagerAdapter mSectionsPagerAdapter;
    TextView mTextGood;
    TextView mTextBad;
    TextView mTextGoal;
    public final static int CAMERA_SHOOT = 100;
    public final static int GET_PICTURE = 200;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        EmotionRestClient.init(MainActivity.this, "5c03c0c1578340b3b1f9a74ee077f820");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        year = toDay.get(toDay.YEAR); //년
        month = toDay.get(toDay.MONTH) + 1; // 월, 0부터 시작하므로 항상 +1을 해줌
        day = toDay.get(toDay.DAY_OF_MONTH); //일
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK);

        if (day_of_week == 1) {
            week = "일요일";
        } else if (day_of_week == 2) {
            week = "월요일";
        } else if (day_of_week == 3) {
            week = "화요일";
        } else if (day_of_week == 4) {
            week = "수요일";
        } else if (day_of_week == 5) {
            week = "목요일";
        } else if (day_of_week == 6) {
            week = "금요일";
        } else if (day_of_week == 7) {
            week = "토요일";
        } else {
            week = "요일을 불러오지 못했습니다.";
        }

//        TransferUtility transferUtility = new TransferUtility(s3Client, MainActivity.this);
//        TransferObserver observer = transferUtility.upload(
//                "unithon-userfiles-mobilehub-725174249",     /* The bucket to upload to */
//                "image.jpg",    /* The key for the uploaded object */
//                fileToUpload        /* The file where the data to upload exists */
//        );

        //(Replace "MY-BUCKET" with your S3 bucket name, and "MY-OBJECT-KEY" with whatever you would            like to name the file in S3)


        t1 = (Button) findViewById(R.id.t1);
        t2 = (Button) findViewById(R.id.t2);

        t3 = (Button) findViewById(R.id.t3);
        t1.setOnClickListener(this);
        t2.setOnClickListener(this);
        t3.setOnClickListener(this);
        mSectionsPagerAdapter = new SectionsPagerAdapter(
                getApplicationContext(), getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
//        picture = (Button)findViewById(R.id.picture);
//        picture.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            startActivityForResult(cameraIntent, CAMERA_SHOOT);
//            }
//            }
//        );
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.t1:
                mViewPager.setCurrentItem(0);
                t1.setTextColor(Color.parseColor("#ffffff"));
                t2.setTextColor(Color.parseColor("#959595"));
                t3.setTextColor(Color.parseColor("#959595"));
                break;
            case R.id.t2:
                mViewPager.setCurrentItem(1);
                t2.setTextColor(Color.parseColor("#ffffff"));
                t1.setTextColor(Color.parseColor("#959595"));
                t3.setTextColor(Color.parseColor("#959595"));
                break;
            case R.id.t3:
                mViewPager.setCurrentItem(2);
                t3.setTextColor(Color.parseColor("#ffffff"));
                t2.setTextColor(Color.parseColor("#959595"));
                t1.setTextColor(Color.parseColor("#959595"));
                break;

        }

    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.mobile.group22/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.mobile.group22/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        Context mContext;

        public SectionsPagerAdapter(Context mContext, FragmentManager fm) {
            super(fm);
            this.mContext = mContext;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a DummySectionFragment (defined as a static inner class
            // below) with the page number as its lone argument.
            switch (position) {
                case 0:
                    return new Tab1(mContext);
                case 1:
                    return new Tab2(mContext);
                case 2:
                    return new Tab3(mContext);

            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);

            }
            return null;
        }
    }

    @SuppressLint("ValidFragment")
    public class Tab1 extends Fragment {
        Context mContext;

        public Tab1(Context context) {
            mContext = context;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            switch (requestCode) {
                case CAMERA_SHOOT:
                    Log.d("uniton", "==========들어옴========");
                    if (resultCode == Activity.RESULT_OK) {

                        ad.cancel();
                        Bitmap bmp = (Bitmap) data.getExtras().get("data");
//                        int dstWidth = bmp.getWidth();
//                        int dstHeight = bmp.getHeight();
//                        Bitmap resized = Bitmap.createScaledBitmap(bmp, dstWidth / 2, dstHeight / 2, true);
//                      //  Log.d("SUMUS", data.getExtras().get("data") + "----------data");
//
//                        bmp.recycle();
//                    String paths = data.getData().toString();
//                    String name_Str = getImageNameToPath(paths);

                        // writeImg.setImageBitmap(resized);
                        File fromFile = null; // 경로를 가져오기 위해 선언
                        Uri uriImages = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

                        String[] IMAGE_PROJECTION = {
                                MediaStore.Images.ImageColumns.DATA, //데이터의 파일 절대 경로(문자열)
                                MediaStore.Images.ImageColumns._ID, //데이터의 테이블상의 아이디(정수)
                        };
                        try {
                            Cursor cursorImages = getContentResolver().query(uriImages, IMAGE_PROJECTION, null, null, null);
                            if (cursorImages != null) {
                                if (cursorImages.moveToLast()) {
                                    fromFile = new File(cursorImages.getString(0)); //  경로
                                }
                                cursorImages.close(); // 커서 사용이 끝나면 꼭 닫아준다.
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Uri uri = Uri.fromFile(fromFile);
                        // String path =getImageNameToUri(uri);
                        String paths = uri.getPath();

                        String name_Str = getImageNameToPath(paths);
                        path = paths;
                        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                                MainActivity.this, // Application Context
                                "us-east-1:cf0ee8be-a39a-4a82-96b7-9dd5773508b7", // Identity Pool ID
                                Regions.US_EAST_1 // Region enum
                        );

                        AmazonS3Client s3Client = new AmazonS3Client(credentialsProvider);
                        File fileToUpload = new File(path);
                        PutObjectRequest putRequest = new PutObjectRequest("unithon-userfiles-mobilehub-725174249", "image.jpg",
                                fileToUpload);
                        PutObjectResult putResponse = s3Client.putObject(putRequest);
                        Log.d("uniton", "name_str : " + name_Str);
//                    IMG_NAME =  name_Str;
//                    sourceFile = new File(IMG_NAME);
//                    Bitmap image_bitmap = BitmapFactory.decodeFile(paths);
//                    catImg = image_bitmap;
//
//                    File filePre = new File(paths);
//                    int end = paths.lastIndexOf("/");
//                    SimpleDateFormat time_date = new SimpleDateFormat("yyyy.MM.dd-HH:mm:ss");
//
//                    File fileNow = new File(paths.substring(0, end), USER_ID+time_date.format(new Date()).toString()+".jpg");
//                    if(filePre.renameTo(fileNow)){
//                        Log.d("SUMUS", "++++++++++ 성공");
//                    }else{
//                        Log.d("SUMUS","++++++++++ 실패");
//                    }
//                    catBit =paths.substring(0, end)+ "/"+
//                            USER_ID+time_date.format(new Date()).toString()+".jpg";
//                    Log.d("SUNAH",catBit);
//
//                    ExifInterface exif = null;
//                    try {
//                        exif = new ExifInterface(paths);
//                    } catch (IOException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
                        Log.d("uniton", "path : " + path);
                        for (int i = 0; i < 10000000; i++) {

                        }

                        EmotionRestClient.getInstance().detect("https://s3.amazonaws.com/unithon-userfiles-mobilehub-725174249/image.jpg", new ResponseCallback() {
                            @Override
                            public void onError(String errorMessage) {
                                Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.error_occurred), Toast.LENGTH_LONG).show();
                            }

                            @Override
                            public void onSuccess(FaceAnalysis[] response) {
                                DecimalFormat t = new DecimalFormat("#0");


                                if (response.length == 0) {

                                    Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.no_face), Toast.LENGTH_LONG).show();

                                    return;
                                }


                                //angerScore = response[0].getScores().getHappiness() * 100.0;
                                Log.d("emotion", "happiness!!! : " + response[0].getScores().getHappiness() * 100.0);
                                Log.d("emotion", "Anger!!! : " + response[0].getScores().getAnger() * 100.0);
                                Log.d("emotion", "Contempt!!! : " + response[0].getScores().getContempt() * 100.0);
                                Log.d("emotion", "Disgust!!! : " + response[0].getScores().getDisgust() * 100.0);
                                Log.d("emotion", "Fear!!! : " + response[0].getScores().getFear() * 100.0);
                                Log.d("emotion", "Neutral!!! : " + response[0].getScores().getNeutral() * 100.0);
                                Log.d("emotion", "Surprise!!! : " + response[0].getScores().getSurprise() * 100.0);
                                Log.d("emotion", "Sadness!!! : " + response[0].getScores().getSadness() * 100.0);

                                Toast.makeText(MainActivity.this, "Perfect!!!", Toast.LENGTH_LONG).show();

                                double angerScore = response[0].getScores().getAnger() * 100.0;
                                double contemptScore = response[0].getScores().getContempt() * 100.0;
                                double disgustScore = response[0].getScores().getDisgust() * 100.0;
                                double fearScore = response[0].getScores().getFear() * 100.0;
                                double happinessScore = response[0].getScores().getHappiness() * 100.0;
                                double neutralScore = response[0].getScores().getNeutral() * 100.0;
                                double sadnessScore = response[0].getScores().getSadness() * 100.0;
                                double surpriseScore = response[0].getScores().getSurprise() * 100.0;

                                //감정 수치 배열
                                emotionArr[0] = angerScore;
                                emotionArr[1] = contemptScore;
                                emotionArr[2] = disgustScore;
                                emotionArr[3] = fearScore;
                                emotionArr[4] = happinessScore;
                                emotionArr[5] = neutralScore;
                                emotionArr[6] = sadnessScore;
                                emotionArr[7] = surpriseScore;


                                double curMax = 0.0;
                                int tmp = 0;
                                for (int i = 0; i <= 7; i++) {
                                    if (curMax < emotionArr[i]) {
                                        curMax = emotionArr[i];
                                        tmp = i;
                                    }
                                }
                                first = emotionArr[tmp];
                                str_first = IdxToStr(tmp);
                                emotionArr[tmp] *= -1.0;

                                int tmp2 = 0;
                                //감정이 2개인 경우
                                if (first < 90.0) {
                                    curMax = 0.0;
                                    for (int i = 0; i <= 7; i++) {
                                        if (curMax < emotionArr[i]) {
                                            curMax = emotionArr[i];
                                            tmp2 = i;
                                        }
                                    }
                                    second = emotionArr[tmp2];
                                    str_second = IdxToStr(tmp2);

                                }
                                Intent intent = new Intent(MainActivity.this, Finish.class);
                                intent.putExtra("first", String.valueOf(first));
                                intent.putExtra("second", String.valueOf(second));
                                intent.putExtra("str_first", str_first);
                                intent.putExtra("str_second", str_second);
                                intent.putExtra("first_index", tmp);
                                intent.putExtra("second_index", tmp2);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                    break;
                case GET_PICTURE:

                    Log.d("uniton", "==========들어옴========");
                    if (resultCode == Activity.RESULT_OK) {
                        try {

                            ad.cancel();
                            String paths = getImageNameToUri(data.getData());
                            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                                    MainActivity.this, // Application Context
                                    "us-east-1:cf0ee8be-a39a-4a82-96b7-9dd5773508b7", // Identity Pool ID
                                    Regions.US_EAST_1 // Region enum
                            );

                            AmazonS3Client s3Client = new AmazonS3Client(credentialsProvider);
                            File fileToUpload = new File(path);
                            PutObjectRequest putRequest = new PutObjectRequest("unithon-userfiles-mobilehub-725174249", "image.jpg",
                                    fileToUpload);
                            PutObjectResult putResponse = s3Client.putObject(putRequest);
                            Log.d("SUMUS", path + "----------path");

                            Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                            int dstWidth = image_bitmap.getWidth();
                            int dstHeight = image_bitmap.getHeight();
                            Bitmap resized = Bitmap.createScaledBitmap(image_bitmap, dstWidth / 2, dstHeight / 2, true);
                            //  writeImg.setImageBitmap(resized);
                            image_bitmap.recycle();
//                            CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
//                                    MainActivity.this, // Application Context
//                                    "us-east-1:cf0ee8be-a39a-4a82-96b7-9dd5773508b7", // Identity Pool ID
//                                    Regions.US_EAST_1 // Region enum
//                            );
//
//                            AmazonS3Client s3Client = new AmazonS3Client(credentialsProvider);
//                            File fileToUpload = new File(path);
//                            TransferUtility transferUtility = new TransferUtility(s3Client, MainActivity.this);
//                            TransferObserver observer = transferUtility.upload(
//                                    "unithon-userfiles-mobilehub-725174249",     /* The bucket to upload to */
//                                    "image.jpg",    /* The key for the uploaded object */
//                                    fileToUpload        /* The file where the data to upload exists */
//                            );
                            for (int i = 0; i < 10000000; i++) {

                            }

                            EmotionRestClient.getInstance().detect("https://s3.amazonaws.com/unithon-userfiles-mobilehub-725174249/image.jpg", new ResponseCallback() {
                                @Override
                                public void onError(String errorMessage) {
                                    Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.error_occurred), Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void onSuccess(FaceAnalysis[] response) {
                                    DecimalFormat t = new DecimalFormat("#0");


                                    if (response.length == 0) {

                                        Toast.makeText(MainActivity.this, MainActivity.this.getString(R.string.no_face), Toast.LENGTH_LONG).show();

                                        return;
                                    }

                                    Log.d("emotion", "happiness!!! : " + response[0].getScores().getHappiness() * 100.0);
                                    Log.d("emotion", "Anger!!! : " + response[0].getScores().getAnger() * 100.0);
                                    Log.d("emotion", "Contempt!!! : " + response[0].getScores().getContempt() * 100.0);
                                    Log.d("emotion", "Disgust!!! : " + response[0].getScores().getDisgust() * 100.0);
                                    Log.d("emotion", "Fear!!! : " + response[0].getScores().getFear() * 100.0);
                                    Log.d("emotion", "Neutral!!! : " + response[0].getScores().getNeutral() * 100.0);
                                    Log.d("emotion", "Surprise!!! : " + response[0].getScores().getSurprise() * 100.0);
                                    Log.d("emotion", "Sadness!!! : " + response[0].getScores().getSadness() * 100.0);

                                    Toast.makeText(MainActivity.this, "Perfect!!!", Toast.LENGTH_LONG).show();

                                    double angerScore = response[0].getScores().getAnger() * 100.0;
                                    double contemptScore = response[0].getScores().getContempt() * 100.0;
                                    double disgustScore = response[0].getScores().getDisgust() * 100.0;
                                    double fearScore = response[0].getScores().getFear() * 100.0;
                                    double happinessScore = response[0].getScores().getHappiness() * 100.0;
                                    double neutralScore = response[0].getScores().getNeutral() * 100.0;
                                    double sadnessScore = response[0].getScores().getSadness() * 100.0;
                                    double surpriseScore = response[0].getScores().getSurprise() * 100.0;

                                    //감정 수치 배열
                                    emotionArr[0] = angerScore;
                                    emotionArr[1] = contemptScore;
                                    emotionArr[2] = disgustScore;
                                    emotionArr[3] = fearScore;
                                    emotionArr[4] = happinessScore;
                                    emotionArr[5] = neutralScore;
                                    emotionArr[6] = sadnessScore;
                                    emotionArr[7] = surpriseScore;


                                    double curMax = 0.0;
                                    int tmp = 0;
                                    for (int i = 0; i <= 7; i++) {
                                        if (curMax < emotionArr[i]) {
                                            curMax = emotionArr[i];
                                            tmp = i;
                                        }
                                    }
                                    first = emotionArr[tmp];
                                    str_first = IdxToStr(tmp);
                                    emotionArr[tmp] *= -1.0;

                                    int tmp2 = 0;
                                    //감정이 2개인 경우
                                    if (first < 90.0) {
                                        curMax = 0.0;
                                        for (int i = 0; i <= 7; i++) {
                                            if (curMax < emotionArr[i]) {
                                                curMax = emotionArr[i];
                                                tmp2 = i;
                                            }
                                        }
                                        second = emotionArr[tmp2];
                                        str_second = IdxToStr(tmp2);

                                    }
                                    Intent intent = new Intent(MainActivity.this, Finish.class);
                                    intent.putExtra("first", String.valueOf(first));
                                    intent.putExtra("second", String.valueOf(second));
                                    intent.putExtra("str_first", str_first);
                                    intent.putExtra("str_second", str_second);
                                    intent.putExtra("first_index", tmp);
                                    intent.putExtra("second_index", tmp2);
                                    startActivity(intent);
                                    finish();
                                }
                            });

                            //Uri에서 이미지 이름을 얻어온다.
//                        String name_Str = getImageNameToUri(data.getData());
//
//                        //이미지 데이터를 비트맵으로 받아온다.
//                        Bitmap image_bitmap    = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
//                       writeImg.setImageBitmap(image_bitmap);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    break;

                default:
                    break;
            }
        }

        ;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.activity_tabs1, null);
            addBtn = (ImageButton) rootView.findViewById(R.id.addBtn);

            addBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(cameraIntent, CAMERA_SHOOT);
                    LayoutInflater inflate = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View layout = inflate.inflate(R.layout.photo_dialog, null);

                    wirteCamera = (Button) layout.findViewById(R.id.wirteCamera);
                    wirteCamera.setOnClickListener(this);
                    writeGallery = (Button) layout.findViewById(R.id.writeGallery);
                    writeGallery.setOnClickListener(this);

                    aDialog = new AlertDialog.Builder(MainActivity.this);
                    //           aDialog.setTitle("TNR 접수");
                    aDialog.setView(layout);

                    ad = aDialog.create();
                    ad.show();
                    // 카메라
                    wirteCamera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(cameraIntent, CAMERA_SHOOT);
                        }

                    });
                    // 갤러리
                    writeGallery.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(intent, GET_PICTURE);

                        }
                    });


                }
            });
//            listView = (ListView)rootView.findViewById(R.id.catList);
//            linearLayout = (LinearLayout)rootView.findViewById(R.id.emptyLayout);
//            adapter = new CustomAdapter(itemDatas, MainActivity.this,ID);
//            listView.setAdapter(adapter);
//            itemData = new ItemData();


            return rootView;
        }

    }

    @SuppressLint("ValidFragment")
    public class Tab2 extends Fragment {
        Context mContext;

        public Tab2(Context context) {
            mContext = context;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View view = inflater.inflate(R.layout.activity_tabs2, null);
//            t2.setTextColor(Color.parseColor("#ffffff"));
//            t1.setTextColor(Color.parseColor("#959595"));
//            t3.setTextColor(Color.parseColor("#959595"));
            return view;
        }

    }

    @SuppressLint("ValidFragment")
    public class Tab3 extends Fragment {
        Context mContext;

        public Tab3(Context context) {
            mContext = context;
        }
        int img[] = {R.drawable.anger, R.drawable.contempt, R.drawable.disgust, R.drawable.fear, R.drawable.happiness, R.drawable.netural, R.drawable.sadness, R.drawable.surprise};
        Bitmap bmp2;
        Date date;
        int bitmap_cnt = 0;
        int date_cnt = 0;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.activity_tabs3, null);

            mListEmotion = (ListView) view.findViewById(R.id.listEmotion);

            final ArrayList<String> items = new ArrayList<>();
            itemDatas.clear();
            bitmap_cnt = 0;
            date_cnt = 0;
            itemData = new ItemData();
            adapter = new CustomAdapter(itemDatas, MainActivity.this);
            mListEmotion.setAdapter(adapter);

            mListEmotion.setOnItemClickListener(mItemClickListener); //ListView 클릭시
            ParseQuery<ParseObject> query = ParseQuery.getQuery("history");
            query.orderByDescending("createdAt");
            query.setLimit(10);
            date_str.clear();
            bitmap.clear();
            //query.include("post");
            query.findInBackground(new FindCallback<ParseObject>() {
                @Override
                public void done(List<ParseObject> commentList, ParseException e) {

                    for (ParseObject comment : commentList) {

                        //ParseObject post = comment.getParseObject("post");
                        String str1 = comment.getString("str_first");

                        String str2 = comment.getString("first");
                        String date1 = comment.getString("date");

                        date_str.add(date1);
                        emotion.add(str1);
                        emotion_score.add(str2);
                        date_cnt++;
                        Log.d("uniton", " data_str : " + date_str);
                        int return_index = strToindex(str1);
                        Drawable drawable = getResources().getDrawable(img[return_index]);

                        adapter.addListItem(drawable, date_str.get(bitmap_cnt), emotion.get(bitmap_cnt), emotion_score.get(bitmap_cnt));
                        bitmap_cnt++;
                        adapter.notifyDataSetChanged();

                    }

                }
            });


//            query.getInBackground("RpV7qUXSlX", new GetCallback<ParseObject>() {
//                @Override
//                public void done(ParseObject object, ParseException e) {
//                    String str1 = object.getString("Emotion1");
//                    String str2 = object.getString("Emotion2");
//                    Date date1 = object.getCreatedAt();
//                    ParseFile fileObject = (ParseFile) object.get("ImageFile");
//                    fileObject.getDataInBackground(new GetDataCallback() {
//                        public void done(byte[] data,
//                                         ParseException e) {
//                            if (e == null) {
//                                Log.d("test",
//                                        "We've got data in data.");
//                                // Decode the Byte[] into
//                                // Bitmap
//                                Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
//                                // Get the ImageView from main.xml
//                                //ImageView image = (ImageView) findViewById(R.id.ad1);
//                                ImageView ad1 = (ImageView) findViewById(R.id.imageView);
//                                // Set the Bitmap into the
//                                // ImageView
//                                ad1.setImageBitmap(bmp);
//
//                            } else {
//                                Log.d("test",
//                                        "There was a problem downloading the data.");
//                            }
//                        }
//                    });
//                    items.add(""+date1);
//                    Toast.makeText(getApplicationContext(), str1 + ", " + str2 + "  date : " + date1, Toast.LENGTH_SHORT).show();
//                }
//            });

            return view;

        }

    }

    AdapterView.OnItemClickListener mItemClickListener =
            new AdapterView.OnItemClickListener(){
                public void onItemClick(AdapterView parent, View view, int position,
                                        long id){
                    // strItem = mArFile.get(position);
                    // mTextmsg.setText(strItem);
                    // mRoot += strItem;
                    // ReadTextAssets(mRoot, "KakaoTalkChats.txt");
                    //Toast.makeText(getApplicationContext(), "click " + position, Toast.LENGTH_SHORT).show();
                    Context mContext = getApplicationContext();
                    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);

                    //R.layout.dialog는 xml 파일명이고  R.id.popup은 보여줄 레이아웃 아이디
                    View layout = inflater.inflate(R.layout.activity_popup, (ViewGroup) findViewById(R.id.popup));
                    AlertDialog.Builder aDialog = new AlertDialog.Builder(MainActivity.this);
                    mTextGood = (TextView)layout.findViewById(R.id.textgood);
                    mTextBad = (TextView)layout.findViewById(R.id.textbad);
                    mTextGoal = (TextView)layout.findViewById(R.id.textgoal);

                    imageView = (ImageView)layout.findViewById(R.id.imageView);
                    // mTextGood.setText(date_str.get(position));
                    imageView.setImageBitmap(bitmap.get(position));
                    aDialog.setView(layout); //dialog.xml 파일을 뷰로 셋팅

                    Log.d("tag","date[ "+position+"]: "+date_str.get(position));
                    //  dateList = textView.getText().toString();
                    //Toast.makeText(getApplicationContext(), "click " + date_str.get(position), Toast.LENGTH_SHORT).show();


                    //그냥 닫기버튼을 위한 부분
                    aDialog.setNegativeButton("닫기", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    //팝업창 생성
                    AlertDialog ad = aDialog.create();
                    ad.show();//보여줌!

                    WindowManager.LayoutParams params = ad.getWindow().getAttributes();
                    params.width = 1000;
                    params.height = 1500;
                    ad.getWindow().setAttributes(params);

                }
            };


    public String getImageNameToUri(Uri data)   // 갤러리 선택시 이미지 이름 가져오기
    {
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        cursor.moveToFirst();

        String imgPath = cursor.getString(column_index);
        path = imgPath;
        Log.d("uniton", path);
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        return imgName;
    }

    public String getImageNameToPath(String data) // 사진 촬영시 이미지 이름 가져오기
    {
        int start = data.lastIndexOf("/");
        int end = data.length();
        String imgName = data.substring(start + 1, end);

        return imgName;
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
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
        return "";
    }
    public int strToindex(String str) {
        switch (str) {
            case "Anger":
                return 0;
            case "Contempt":
                return 1;
            case "Disgust":
                return 2;
            case "Fear":
                return 3;
            case "Happiness":
                return 4;
            case "Neutral":
                return 5;
            case "Sadness":
                return 6;
            case "Surprise":
                return 7;

        }
        return -1;
    }
}