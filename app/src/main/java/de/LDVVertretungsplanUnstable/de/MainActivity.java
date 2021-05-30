package de.LDVVertretungsplanUnstable.de;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    String title;
    ArrayList<Stunde> array1 = new ArrayList<>();
    TextView text1;
    TableLayout layout;
    String kinder;
    ArrayList<String> doppelstunden = new ArrayList<>();
    public int zellen;
    private Context context;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        layout = findViewById(R.id.PLAN);
        // kinder(layout);
        context = this;
        WEBSITE();

    }

    public void kinder(TableLayout layout) {
        System.out.println(layout.getChildCount());
    }

    public void WEBSITE() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                try {//https://web.gymnasium-nippes.de/~autolog/schueler/03/s/s00062.htm Jonas Dunkel
                    //https://web.gymnasium-nippes.de/~autolog/schueler/21/s/s00302.htm Kasimir Weilandt
                    Document doc = Jsoup.connect("https://web.gymnasium-nippes.de/~autolog/schueler/21/s/s00061.htm")
                            .header("Authorization", "Basic aG9sbGFuZDpmYXJmcm9taG9tZQ==")
                            .get();
                    Element table = doc.select("table").get(0);
                    Elements cells = table.select("td[rowspan]");
                    for (int i = 0; i < cells.size(); i++) {
                        boolean zustand = false;
                        boolean entfall = false;
                        if(cells.get(i).select("td[bgcolor]").size() != 0) {
                            zustand =true;
                        }
                        if(cells.get(i).select("strike").size() != 0)
                        {
                            entfall = true;
                        }
                        Stunde jetztigeStunde = new Stunde(cells.get(i).text(), (Integer.parseInt(cells.get(i).attr("rowspan")) / 2), zustand, entfall);
                        array1.add(jetztigeStunde);

                    }


                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<TableRow> tableRows = new ArrayList<TableRow>();
                        for (int i = 0; i < layout.getChildCount(); i++) {
                            tableRows.add((TableRow) layout.getChildAt(i));

                        }

                        int reihenanzahl = 0;
                        for (int j = 0; j < array1.size(); j++) {
                            Stunde jetzigeStunde = array1.get(j);
                            System.out.println(jetzigeStunde.isZustand());
                            if (jetzigeStunde.getStunde().length() == 1 || jetzigeStunde.getStunde().length() == 2) {
                                reihenanzahl++;
                            } else {
                                TableRow reihe = tableRows.get(reihenanzahl);
                                int spaltenzahl = 0;
                                boolean gefunden = false;
                                while (!gefunden) {
                                    TextView kind = (TextView) reihe.getChildAt(spaltenzahl);
                                    if (kind.getText().toString().equals(" ")) {
                                        gefunden = true;
                                        kind.setText(jetzigeStunde.getStunde());
                                        System.out.println(jetzigeStunde.isZustand());
                                        if(jetzigeStunde.isZustand())
                                        {

                                            kind.setTextColor(Color.RED);
                                        }
                                        if (jetzigeStunde.isEntfall())
                                        {
                                            kind.setPaintFlags(kind.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                                        }

                                    }
                                    else {
                                        spaltenzahl++;
                                    }
                                }

                                int reihenanzahlcopy = reihenanzahl;
                                if (jetzigeStunde.getLaenge() > 1) {
                                    int laenge = jetzigeStunde.getLaenge();
                                    for (int i = 0; i < laenge-1; i++) {

                                        reihenanzahlcopy++;
                                        TableRow reihencopy = tableRows.get(reihenanzahlcopy);
                                        TextView kind = (TextView) reihencopy.getChildAt(spaltenzahl);
                                        kind.setText(jetzigeStunde.getStunde());
                                    }
                                }
                            }

                            }



                        }


                });
            }
        }).start();


    }

}