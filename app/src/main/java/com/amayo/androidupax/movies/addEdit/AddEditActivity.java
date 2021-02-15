package com.amayo.androidupax.movies.addEdit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;

import com.amayo.androidupax.R;
import com.amayo.androidupax.movies.MovieFragment;

public class AddEditActivity extends AppCompatActivity {
    public static final int REQUEST_ADD_CUSTOMER = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_customer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String movieId = getIntent().getStringExtra(MovieFragment.EXTRA_MOVIE_ID);
        setTitle(movieId == null ? getResources().getString(R.string.action_add): getResources().getString(R.string.action_edit));

        AddEditFragment addEditFragment = (AddEditFragment)
                getSupportFragmentManager().findFragmentById(R.id.movie_add_container);
        if (addEditFragment == null) {
            addEditFragment = AddEditFragment.newInstance(movieId);
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.movie_add_container, addEditFragment)
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}