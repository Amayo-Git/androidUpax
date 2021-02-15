package com.amayo.androidupax.movies;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.amayo.androidupax.R;
import com.amayo.androidupax.apiClient.Client;
import com.amayo.androidupax.apiClient.Services;
import com.amayo.androidupax.movies.adapter.movieAdapter;
import com.amayo.androidupax.movies.addEdit.AddEditActivity;
import com.amayo.androidupax.movies.model.Movie;
import com.amayo.androidupax.movies.model.MovieResponse;
import com.amayo.androidupax.db.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MovieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    public static final String EXTRA_MOVIE_ID = "movie_id";
    public static final String EXTRA_TITLE = "title";
    public static final String EXTRA_VOTE ="vote";
    public static final String EXTRA_OVERVIEW = "overview";
    public static final String EXTRA_IMG = "img";
    public static final String EXTRA_POPULARITY = "popularity";
    public static final String EXTRA_ORIGINAL = "original";

    public static final int REQUEST_UPDATE_DELETE_CUSTOMER = 2;
    private final static String API_KEY = "240fc5a15fc7c07f4de855c0891fd67d";


    // TODO: Rename and change types of parameters
    private String mParam1;
    private DatabaseHelper mDatabaseHelper;
    private RecyclerView   mRecyclerCustomer;
    private movieAdapter mAdapter;
    private ArrayList<Movie> mArrayList;
    private FloatingActionButton mFloating;

    private String mMovieId; //por de faul es nulo para inserta



    public MovieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment CustomerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieFragment newInstance(String param1) {
        MovieFragment fragment = new MovieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_customer, container, false);
        mFloating = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFloating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCustomer();
            }
        });
        mRecyclerCustomer = root.findViewById(R.id.listCustomer);
        mRecyclerCustomer.setLayoutManager(new LinearLayoutManager(getActivity()));
        mArrayList = new ArrayList<>();
        mAdapter = new movieAdapter(mArrayList,getActivity());
        mRecyclerCustomer.setAdapter(mAdapter);

        //mDatabaseHelper = new DatabaseHelper(getActivity());

        //loadMovie();
        clientService();
        return root;
    }

    private void clientService(){
        Services apiService =
                Client.getClient().create(Services.class);

        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY);
        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse>call, Response<MovieResponse> response) {
                List<Movie> movies = response.body().getResults();
               /*
                se recoreria la lista para inserta los datos
                for (int i=0; i<movies.size(); i++){
                    long id = movies.get(i).getId();
                    String title = movies.get(i).getTitle();
                    String posterPath = movies.get(i).getPosterPath();
                    String state ="server";


                    aqui iria una compraracion de datos
                    con la sqlite y la respuesta de retrofy

                    if (mArrayList.isEmpty()){
                            Movie movie =  new Movie(id,title,posterPath,state);
                            new AddMovieTask().execute(movie);
                        }else {

                            boolean searchP = false;
                            for (int x = 0; x < mArrayList.size(); x++) {
                                Movie m = mArrayList.get(x);
                                if (m.getTitle().equals(title)) {
                                    searchP = true;
                                    break;
                                }
                            }

                            if (searchP) {

                            }else {
                                Movie movie = new Movie(id, title, posterPath, state);
                                new AddMovieTask().execute(movie);
                            }
                    }

                }
                */



                mRecyclerCustomer.setAdapter(new movieAdapter(movies,getActivity()));

            }

            @Override
            public void onFailure(Call<MovieResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e("error", t.toString());

                /*
                encaso no tener internet ejecutamos la funcion a la consulta de sqlite
                 */
            }
        });
    }


    /*

     */

    private class AddMovieTask extends AsyncTask<Movie, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Movie... movies) {
            if (mMovieId != null) {
                return mDatabaseHelper.updateMovie(movies[0], mMovieId) > 0;

            } else {
                return mDatabaseHelper.saveMovie(movies[0]) > 0;
            }

        }

        @Override
        protected void onPostExecute(Boolean result) {
            showLawyersScreen(result);
        }

    }

    private void showLawyersScreen(Boolean requery) {
        if (!requery) {
            System.out.println("error de creacion");
        } else {
            //registro correcto
            System.out.println("Creacion correcta");
        }


    }


    private void loadMovie(){
        new getAllTask().execute();

    }

    private class getAllTask extends AsyncTask<Void, Void, ArrayList<Movie> >{

        @Override
        protected ArrayList<Movie> doInBackground(Void... voids) {
            return new  ArrayList<>(mDatabaseHelper.getAllMovie());
        }
        @Override
        protected void onPostExecute(ArrayList arrayList) {
            mArrayList = arrayList;
            if (mArrayList.size() > 0){
                mAdapter = new movieAdapter(mArrayList,getActivity());
                mRecyclerCustomer.setAdapter(mAdapter);
            }else {
                showMessage(getResources().getString(R.string.no_data));
            }

        }
    }

    private void showMessage(String message){
        Toast.makeText(getActivity(),
                message, Toast.LENGTH_SHORT).show();
    }


    private void addCustomer(){

        Intent intent = new Intent(getActivity(), AddEditActivity.class);
        startActivityForResult(intent, AddEditActivity.REQUEST_ADD_CUSTOMER);
    }

}