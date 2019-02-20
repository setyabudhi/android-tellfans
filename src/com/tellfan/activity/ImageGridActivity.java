/*
 * To change this template, choose Tools | Templates
 * && open the template in the editor.
 */
package com.tellfan.activity;

import com.tellfan.R;
import com.tellfan.R.drawable;
import com.tellfan.R.id;
import com.tellfan.R.layout;
import com.tellfan.adapter.ImageAdapter;
import com.tellfan.util.ImageItem;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.GridView;



public class ImageGridActivity extends Activity {

    @Override
    protected void onCreate(final Bundle istate) {
        super.onCreate(istate);
        setContentView(R.layout.media_grid);

        final GridView view = (GridView) findViewById(R.id.mediagrid);
        
        view.setAdapter(new ImageAdapter(
                new ImageItem("Apple", R.drawable.apple),
                new ImageItem("Banana", R.drawable.banana),
                new ImageItem("Black Berries", R.drawable.blackberry),
                new ImageItem("Cherries", R.drawable.cherries),
                new ImageItem("Coconut", R.drawable.coconut),
                new ImageItem("Grapes", R.drawable.grapes),
                new ImageItem("Kiwi", R.drawable.kiwi)               
               ));

    }

}
