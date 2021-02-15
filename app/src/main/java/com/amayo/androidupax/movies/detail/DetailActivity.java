package com.amayo.androidupax.movies.detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;


import com.amayo.androidupax.R;
import com.amayo.androidupax.movies.MovieFragment;

public class DetailActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String id = getIntent().getStringExtra(MovieFragment.EXTRA_MOVIE_ID);
        String title = getIntent().getStringExtra(MovieFragment.EXTRA_TITLE);



        DetailFragment fragment = (DetailFragment)
                getSupportFragmentManager().findFragmentById(R.id.movie_detail_container);
        if (fragment == null) {
            fragment = DetailFragment.newInstance(id,title);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}