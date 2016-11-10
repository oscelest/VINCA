package com.noxyspace.vinca;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class NewsTab extends ListFragment implements AdapterView.OnItemClickListener {

    private ArrayList<SingleRow> newsList = new ArrayList<SingleRow>();
    private CustomAdapter adapter;
    private int id = 1;

    // Disclaimer : does not support image replacement functionality yet - 13:05 10-11-16

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.news_tab_fragment, container, false);

        String Title1, Title2, Title3;
        String Description1, Description2, Description3;

        // DUMMY TEXT

        Title1 = "VINCA News Title 1";
        Title2 = "VINCA News Title 2";
        Title3 = "VINCA News Title 3";

        Description1 =  "I 1860, da Abraham var præsidentkandidat, blev han af en journalist bedt om at beskrive" +
                        " sin barndom. Med et citat fra Thomas Grays Elegy sagde han: ”Den korte og enkle historie om fattigdom. " +
                        "Det er mit liv, og det er alt De, eller nogen anden, kan få ud af det”. Hans far, Thomas, som var " +
                        "tømrer, var ædruelig og hårdtarbejdende. Abrahams mor, Nancy (f. Hanks), var 25 år, da hun fødte " +
                        "Abraham. Hun kunne hverken læse eller skrive, men hendes fætter Dennis Hanks beskrev hende som en uskolet, " +
                        "men naturlig begavelse – ”hurtigt og klart opfattende”, ”udspekuleret”, ”intellektuel af natur” med en god " +
                        "hukommelse, sund dømmekraft og meget kærlig.";

        Description2 =  "I 1816 flyttede familien til Indiana. De flyttede sammen med familien Sparrow – Nancys tante og onkel samt " +
                        "Dennis Hanks, deres adoptivsøn. De flyttede til et skovområde i nærheden af Ohiofloden. Efter to års slid og " +
                        "nybyggeri indtraf tragedie på tragedie i 1818. Først døde onklen, siden tanten og Abrahams mor. Abraham og Dennis " +
                        "hjalp Thomas med at lave kisterne til dem. Thomas Lincoln giftede sig med en ungdomskæreste, Sarah Bush, der i " +
                        "mellemtiden også var blevet alene. Lincoln lærte hurtigt at elske sin nye mor, og han skrev senere om hende: " +
                        "”Alt hvad jeg er, eller håber at blive, skylder jeg min kære moder”.";

        Description3 =  "Den unge Abraham var en læsehest, der nedskrev alt vigtigt, han havde læst. Hvis han ikke havde papir , hvad " +
                        "han sjældent havde, skrev han på sin kridttavle og skrev ordene på papir, når han fik fat på noget. Han læste " +
                        "foruden Bibelen Æsops fabler, Robinson Crusoe samt historie. Han udviklede her den vane, der fulgte ham hele " +
                        "livet: Hvis der var noget han ikke forstod, læste han det igen og igen og funderede over det, indtil det gav " +
                        "mening. Som syttenårig var Abraham allerede 1,87 m høj, men vejede blot 55 kg. Han arbejdede en del for de lokale, " +
                        "men de huskede ham mest som ”doven, altid læsende og tænkende”.";

        // END DUMMY TEXT

        // Inserting text into Single Row Objects

        newsList.add(new SingleRow(Title1, Description1));
        newsList.add(new SingleRow(Title2, Description2));
        newsList.add(new SingleRow(Title3, Description3));

        return view;
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

    class SingleRow {

        private String title, description;
        private int image;

        SingleRow(String title, String description) {
            this.title = title;
            this.description = description;
            //this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
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
                    view = getActivity().getLayoutInflater().inflate(R.layout.news_element, null);
            }
                ImageView img = (ImageView) view.findViewById(R.id.newsImage);
                TextView title = (TextView) view.findViewById(R.id.newsTitle);
                title.setText(newsList.get(position).getTitle());
                TextView description = (TextView) view.findViewById(R.id.newsDescription);
                description.setText(newsList.get(position).getDescription());

            return view;
        }
    }
}