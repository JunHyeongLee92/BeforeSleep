package CustomList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.mobile.group22.R;


public class CustomAdapter extends BaseAdapter {

    public ArrayList<ItemData> itemDatas = null;
    public LayoutInflater layoutInflater = null;
    public Context contexts=null;



    public CustomAdapter(ArrayList<ItemData> itemDatas, Context ctx){

        contexts = ctx;
        this.itemDatas = itemDatas;
        layoutInflater = (LayoutInflater)ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    public void setItemData(ArrayList<ItemData> itemDatas){
        this.itemDatas = itemDatas;
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return (itemDatas != null) ? itemDatas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return (itemDatas != null && (0 <= position && position < itemDatas.size()) ? itemDatas.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return (itemDatas != null && (0 <= position && position < itemDatas.size()) ? position : 0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {



        /** 이전코드 **/

        ViewHolder viewHolder = new ViewHolder();

        if(convertView == null){
            convertView = layoutInflater.inflate(R.layout.activity_listview, parent ,false);

            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.imageView);
            viewHolder.textView = (TextView)convertView.findViewById(R.id.textView);
            viewHolder.txt_emotion = (TextView)convertView.findViewById(R.id.txt_emotion);
            viewHolder.txt_score = (TextView)convertView.findViewById(R.id.txt_score);
            convertView.setTag(viewHolder);
        }

        else{
            viewHolder=(ViewHolder)convertView.getTag();
        }

        final ItemData itemData = itemDatas.get(position);
        viewHolder.imageView.setImageDrawable(itemData.image_bit);
        viewHolder.textView .setText(itemData.date.toString());
        viewHolder.txt_emotion .setText(itemData.emotion.toString());
        viewHolder.txt_score .setText(itemData.score.toString());
        return convertView;
    }


    public void addListItem(Drawable pro,String date, String emotion, String score)
    {
        ItemData addinfo = null;
        addinfo = new ItemData();

        addinfo.image_bit = pro;
        addinfo.date = date;
        addinfo.emotion = emotion;
        addinfo.score = score;
        itemDatas.add(addinfo);
    }

}
