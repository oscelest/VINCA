package com.noxyspace.vinca;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import java.util.ArrayList;

import static com.noxyspace.vinca.R.id.NewsListWiev;

public class NewsTab extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener{

    ListView newsList;

    public NewsTab() {
        newsList = (ListView) newsList.findViewById(NewsListWiev);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.news_tab_fragment, container, false);

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}

class singleRow{

    String title, description;
    int image;

    singleRow(String title, String description, int image){
        this.title = title;
        this.description = description;
        this.image = image;
    }
}

class NewsAdapter extends BaseAdapter{

    ArrayList<singleRow> list;

    Context context;

    NewsAdapter(Context c){

        context = c;

        list = new ArrayList<>();
        Resources res = c.getResources();
        String[] titles = res.getStringArray(R.array.NewsTitle);
        String[] descriptions = res.getStringArray(R.array.NewsDescription);
        int[] imiges = {    R.drawable.news_Icon,R.drawable.news_Icon,R.drawable.news_Icon,
                            R.drawable.news_Icon,R.drawable.news_Icon,R.drawable.news_Icon,
                            R.drawable.news_Icon,R.drawable.news_Icon,R.drawable.news_Icon,
                            R.drawable.news_Icon};

        for (int i = 0 ; i < titles.length ; i++){

            list.add(new singleRow(titles[i], descriptions[i], imiges[i]));

        }

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.news_element, parent,false);
        TextView title = (TextView) row.findViewById(R.id.newsTitle);
        TextView description = (TextView) row.findViewById(R.id.newsDescription);
        ImageView image = (ImageView) row.findViewById(R.id.newsImage);

        singleRow temp = list.get(position);

        title.setText(temp.title);
        description.setText(temp.description);
        image.setImageResource(temp.image);

        return row;
    }
}