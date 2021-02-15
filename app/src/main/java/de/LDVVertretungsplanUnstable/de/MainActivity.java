package de.LDVVertretungsplanUnstable.de;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;

import org.jsoup.*;
import java.io.IOException;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    ArrayList<String> array = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayAdapter ad = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item1,namearray);
        list1.setAdapter(ad);
    }

    public static void main(String[] args) throws IOException {
        org.jsoup.nodes.Document doc = Jsoup.connect("https://web.gymnasium-nippes.de/~autolog/schueler/07/s/s00002.htm")
                .header("Authorization", "aG9sbGFuZDpmYXJmcm9taG9tZQ==")
                .get();
        org.jsoup.select.Elements rows = doc.select("tr");
        for(org.jsoup.nodes.Element row :rows)
        {
            org.jsoup.select.Elements columns = row.select("td");
            for (org.jsoup.nodes.Element column:columns)
            {
                System.out.print(column.text());
            }
            System.out.println();
        }

    }
}