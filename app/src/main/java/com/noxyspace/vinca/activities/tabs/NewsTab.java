package com.noxyspace.vinca.activities.tabs;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.noxyspace.vinca.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class NewsTab extends ListFragment implements AdapterView.OnItemClickListener, View.OnClickListener {

    private ArrayList<SingleRow> newsList = new ArrayList<SingleRow>();
    private ArrayList<String> titleList = new ArrayList<String>();
    private ArrayList<String> descriptionList = new ArrayList<String>();
    private ArrayList<String> imageUrl = new ArrayList<String>();

//    private FloatingActionButton fab_btn_news;

    private CustomAdapter adapter;
    private int id = 1;

    // Disclaimer : does not support image replacement functionality yet - 13:05 10-11-16

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_tab_fragment, container, false);

//        fab_btn_news = (FloatingActionButton) view.findViewById(R.id.fab_plus_news);
//        fab_btn_news.setOnClickListener(this);

        if (newsList.isEmpty()) {

            String Title1, Title2, Title3;
            String Description1, Description2, Description3;
            String imageUrl1, imageUrl2, imageUrl3;

            // DUMMY TEXT

            Title1 = "VINCA News Title 1";
            Title2 = "VINCA News Title 2";
            Title3 = "VINCA News Title 3";

            Description1 = "I 1860, da Abraham var præsidentkandidat, blev han af en journalist bedt om at beskrive" +
                    " sin barndom. Med et citat fra Thomas Grays Elegy sagde han: ”Den korte og enkle historie om fattigdom. " +
                    "Det er mit liv, og det er alt De, eller nogen anden, kan få ud af det”. Hans far, Thomas, som var " +
                    "tømrer, var ædruelig og hårdtarbejdende. Abrahams mor, Nancy (f. Hanks), var 25 år, da hun fødte " +
                    "Abraham. Hun kunne hverken læse eller skrive, men hendes fætter Dennis Hanks beskrev hende som en uskolet, " +
                    "men naturlig begavelse – ”hurtigt og klart opfattende”, ”udspekuleret”, ”intellektuel af natur” med en god " +
                    "hukommelse, sund dømmekraft og meget kærlig.";

            Description2 = "I 1816 flyttede familien til Indiana. De flyttede sammen med familien Sparrow – Nancys tante og onkel samt " +
                    "Dennis Hanks, deres adoptivsøn. De flyttede til et skovområde i nærheden af Ohiofloden. Efter to års slid og " +
                    "nybyggeri indtraf tragedie på tragedie i 1818. Først døde onklen, siden tanten og Abrahams mor. Abraham og Dennis " +
                    "hjalp Thomas med at lave kisterne til dem. Thomas Lincoln giftede sig med en ungdomskæreste, Sarah Bush, der i " +
                    "mellemtiden også var blevet alene. Lincoln lærte hurtigt at elske sin nye mor, og han skrev senere om hende: " +
                    "”Alt hvad jeg er, eller håber at blive, skylder jeg min kære moder”.";

            Description3 = "Den unge Abraham var en læsehest, der nedskrev alt vigtigt, han havde læst. Hvis han ikke havde papir , hvad " +
                    "han sjældent havde, skrev han på sin kridttavle og skrev ordene på papir, når han fik fat på noget. Han læste " +
                    "foruden Bibelen Æsops fabler, Robinson Crusoe samt historie. Han udviklede her den vane, der fulgte ham hele " +
                    "livet: Hvis der var noget han ikke forstod, læste han det igen og igen og funderede over det, indtil det gav " +
                    "mening. Som syttenårig var Abraham allerede 1,87 m høj, men vejede blot 55 kg. Han arbejdede en del for de lokale, " +
                    "men de huskede ham mest som ”doven, altid læsende og tænkende”.";

            imageUrl1 = "http://icons.iconarchive.com/icons/ph03nyx/super-mario/256/Mushroom-1UP-icon.png";
            imageUrl2 = "http://icons.iconarchive.com/icons/ph03nyx/super-mario/256/Mushroom-Mini-icon.png";
            imageUrl3 = "http://icons.iconarchive.com/icons/ph03nyx/super-mario/256/Mushroom-Super-icon.png";

            // END DUMMY TEXT

            titleList.add(Title1);
            titleList.add(Title2);
            titleList.add(Title3);

            descriptionList.add(Description1);
            descriptionList.add(Description2);
            descriptionList.add(Description3);

            imageUrl.add(imageUrl1);
            imageUrl.add(imageUrl2);
            imageUrl.add(imageUrl3);

            // new DownloadImageTask().execute(imageUrl.get(i)))

            for (int i = 0; i < titleList.size(); i++) {
                newsList.add(new SingleRow(titleList.get(i), descriptionList.get(i)));
            }
        }
        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        return view;
    }

    public void onTabSelected() {

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new CustomAdapter();
        setListAdapter(adapter);
        getListView().setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();

        // Position is position in the list, 0 is first
    }

    @Override
    public void onClick(View v) {

        NewsFull newsFull = new NewsFull();
        getFragmentManager().beginTransaction()
                .replace(R.id.pager, newsFull)
                .commit();
    }

    private class SingleRow {

        private String title, description;
        private Bitmap bitmap;

        SingleRow(String title, String description) {
            this.title = title;
            this.description = description;
            // this.bitmap = bitmap;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

       // public Bitmap getBitmap() {
       //     return bitmap;
       // }
    }

    private class singleNews {
        private String fullTitle, fullDescription;

        singleNews(String fullTitle, String fullDescription) {
            this.fullTitle = fullTitle;
            this.fullDescription = fullDescription;
        }

        public String getFullTitle() {
            return fullTitle;
        }

        public String getFullDescription() {
            return fullDescription;
        }
    }

    public class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return newsList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            int type = getItemViewType(position);
            if (view == null) {
                view = getActivity().getLayoutInflater().inflate(R.layout.news_element_fragment, null);
            }
            ImageView img = (ImageView) view.findViewById(R.id.newsImage);
            TextView title = (TextView) view.findViewById(R.id.newsTitle);
            title.setText(newsList.get(position).getTitle());
            TextView description = (TextView) view.findViewById(R.id.newsDescription);
            description.setText(newsList.get(position).getDescription());

            return view;
        }
    }

    // HTTP REQUEST

    public static String executePost(String targetURL, String urlParameters) {
        HttpURLConnection connection = null;

        try {
            //Create connection
            URL url = new URL(targetURL);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            //Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            //Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private class DownloadImageTask extends AsyncTask<String, Integer, Bitmap> {
        protected Bitmap doInBackground(String... params) {

            try {

                System.out.println("Url for bitmap = " + params[0]);

                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                // Log exception
                return null;
            }

        }

    }
}







/**
 class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

 private Exception exception;

 protected Bitmap doInBackground(String... urls) {
 try {

 URL url = new URL(urls[0]);

 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
 connection.setDoInput(true);
 connection.connect();
 InputStream input = connection.getInputStream();
 Bitmap myBitmap = BitmapFactory.decodeStream(input);
 return myBitmap;
 } catch (Exception e) {
 this.exception = e;

 return null;
 }
 }

 protected void onPostExecute(Bitmap feed) {
 // TODO: check this.exception
 // TODO: do something with the feed
 }
 }

    URL url = new URL("http://....");
    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());



  try {
URL url = new URL("http://....");
    Bitmap image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
} catch(IOException e) {
        System.out.println(e);
        }


 try {

 System.out.println("Url for bitmap = " + params[0]);

 URL url = new URL(params[0]);
 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
 connection.setDoInput(true);
 connection.connect();
 InputStream input = connection.getInputStream();
 Bitmap myBitmap = BitmapFactory.decodeStream(input);
 return myBitmap;
 } catch (IOException e) {
 // Log exception
 return null;
 }


 System.out.println("URL = " + params[0]);

 URL url = new URL(params[0]);

 HttpURLConnection connection = (HttpURLConnection) url.openConnection();
 connection.setDoInput(true);
 connection.connect();
 InputStream input = connection.getInputStream();
 Bitmap myBitmap = BitmapFactory.decodeStream(input);

 System.out.println("END URL");

 return myBitmap;




 **/