package com.example.paul.villefutee_android;

import android.app.ListActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.CheckedTextView;
import android.widget.Toast;
import android.view.View;


/**
 * Created by sylvinho on 04/04/2017.
 */
public class ProductListActivity extends ListActivity {

    private String[] produits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listviewproducts);
        Toast.makeText(this, "ICIIIII", Toast.LENGTH_LONG);
        ListView listview = getListView();
        String[] produits = getResources().getStringArray(R.array.produits);

        setListAdapter(new ArrayAdapter<String>(this, R.layout.products, produits));



    }


    public void onListItemClick(ListView parent, View v, int position, long id) {
        //CheckedTextView item = (CheckedTextView) v;
        Toast.makeText(this, /*produits[position] +*/ " checked : "
               /*item.isChecked()*/, Toast.LENGTH_SHORT).show();
    }
}