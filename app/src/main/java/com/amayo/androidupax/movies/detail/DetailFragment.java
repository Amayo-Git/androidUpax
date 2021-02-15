package com.amayo.androidupax.movies.detail;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amayo.androidupax.R;
import com.amayo.androidupax.movies.model.Movie;
import com.google.android.material.appbar.CollapsingToolbarLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CollapsingToolbarLayout mCollapsingView;
    private ImageView mAvatar;
    private TextView view_vote;
    private EditText title,popularity,original_language,overview;

    public DetailFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailCustomerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailFragment newInstance(String param1, String param2) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_detail_customer, container, false);

        mCollapsingView = (CollapsingToolbarLayout) getActivity().findViewById(R.id.toolbar_layout);
        mAvatar = (ImageView) getActivity().findViewById(R.id.iv_avatar);
        view_vote = root.findViewById(R.id.view_vote);
        title = root.findViewById(R.id.title);
        popularity = root.findViewById(R.id.popularity);
        original_language= root.findViewById(R.id.original_language);
        overview = root.findViewById(R.id.overview);

        //colocar la instancia de la base
        loadMovie();
        //GetMovieByIdTask() para mostrar el elemneto seleccionado ,
        return root;
    }


    private void loadMovie(){

        mCollapsingView.setTitle(mParam2);

    }



    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                showEditScreen();
                break;
            case R.id.action_delete:
                deleteMovie();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showEditScreen() {

        /*
        Intent intent = new Intent(getActivity(), AddEditActivity.class);
        intent.putExtra(MovieFragment.ARG_PARAM1, mParam1);
        startActivityForResult(intent, MovieFragment.REQUEST_UPDATE_DELETE_CUSTOMER);

            si se decea editar se va enviar a la donde se podra editar
         */
    }

    private void GetMovieByIdTask(){
        //ejectutamos la consulta de la lista seleccionada
        new GetMovieByIdTask().execute();
    }
    private void deleteMovie(){
        //borramos un registro de la base
        //new DeleteLawyerTask().execute();
    }


        private class GetMovieByIdTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            return null;// basedetos.lafuncion(parametro);
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            if (cursor != null && cursor.moveToLast()) {
                showMovie(new Movie(cursor));
            } else {
                showLoadError("Error ");
            }
        }

    }


    private void showMovie(Movie movie) {
        mCollapsingView.setTitle(movie.getTitle());
        //aqui se muestra la informacion a la vista

    }


    private class DeleteMovieTask extends AsyncTask<Void, Void, Integer> {

        @Override
        protected Integer doInBackground(Void... voids) {
            return null; //base.funcion (parametro);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            showLawyersScreen(integer > 0);
        }

    }

    private void showLawyersScreen(boolean requery) {
        if (!requery) {
            showLoadError("error delete");
        }
        /*
        de acuerdo el resultado de nuestra funcion se mandara el estado de la actividad

        getActivity().setResult(requery ? Activity.RESULT_OK : Activity.RESULT_CANCELED);
        getActivity().finish();
         */

    }

    private void showLoadError(String message) {
        Toast.makeText(getActivity(),
                message, Toast.LENGTH_SHORT).show();
    }

}