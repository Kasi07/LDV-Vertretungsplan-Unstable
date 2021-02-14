package com.example.com;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableRow;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    String title;
    ArrayList<String> array1 = new ArrayList<>();
    TextView text1;
    TableRow ersteStunde;
    TableRow zweiteStunde;
    TableRow dritteStunde;
    TableRow vierteStunde;
    TableRow fuenfteStunde;
    TableRow sechsteStunde;
    TableRow siebteStunde;
    TableRow achteStunde;
    TableRow neunteStunde;
    TableRow zehnteStunde;
    TableRow elfteStunde;
    TableRow zwoelfteStunde;
    String kinder;
    ArrayList<String> doppelstunden = new ArrayList<>();
    public int zellen;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text1 = findViewById(R.id.ersteStundeMo);
        ersteStunde = findViewById(R.id.ersteStunde);
        zweiteStunde = findViewById(R.id.zweiteStunde);
        dritteStunde = findViewById(R.id.dritteStunde);
        vierteStunde = findViewById(R.id.vierteStunde);
        fuenfteStunde = findViewById(R.id.fuenfteStunde);
        sechsteStunde = findViewById(R.id.sechsteStunde);
        siebteStunde = findViewById(R.id.siebteStunde);
        achteStunde = findViewById(R.id.achteStunde);
        neunteStunde = findViewById(R.id.neunteStunde);
        zehnteStunde = findViewById(R.id.zehnteStunde);
        elfteStunde = findViewById(R.id.elfteStunde);
        zwoelfteStunde = findViewById(R.id.zwoelfteStunde);

        kinder = Integer.toString(ersteStunde.getChildCount());
        text1.setText(kinder);
        WEBSITE();

    }


    public void WEBSITE() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();
                try {
                    Document doc = Jsoup.connect("https://web.gymnasium-nippes.de/~autolog/schueler/03/s/s00062.htm")
                            .header("Authorization", "Basic aG9sbGFuZDpmYXJmcm9taG9tZQ==")
                            .get();
                    Element table = doc.select("table").get(0);
                    Elements cells = table.select("td[colspan=12]");
                    String gay = table.text();
                    zellen = cells.size();
                    for (int i = 5; i < cells.size(); i++) {
                        String fett = cells.get(i).text();
                        array1.add(fett);
                        if ( cells.attr("rowspan").equals(Integer.toString(4)));
                        {
                            doppelstunden.add(fett);
                        }
                    }


                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        for (int i = 1; i < zellen-5; i++) {
                            TextView view = null;
                            //StringBuilder sb = new StringBuilder(array1.get(i - 1));
                            /*int b = 0;


                            
                            while ((b = sb.indexOf(" ", b + 5)) != -1) {
                                sb.replace(b, b + 1, "\n");
                            }*/

                            if (i < 6) {
                                view = (TextView) ersteStunde.getChildAt(i);
                                view.setText(array1.get(i-1));
                            }
                            if (i< 12  && i>6){

                                view = (TextView) zweiteStunde.getChildAt(i-6);
                                view.setText(array1.get(i-1));
                            }

                        }

                    }
                });
            }
        }).start();




    }

}