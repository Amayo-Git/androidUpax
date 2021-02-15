package com.amayo.androidupax.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.amayo.androidupax.movies.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME    = "demo";
    public static final int    DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Movie.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Movie.MOVIE);

    }

    /*
      guardar datos a la base
     */

    public long saveMovie(Movie movie) {
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();

        return sqLiteDatabase.insert(
                Movie.MOVIE,
                null,
                movie.toContentValues());

    }
    /*
    consulta de un solo registro
     */
    public Cursor getMovieById(String lawyerId) {
        Cursor c = getReadableDatabase().query(
                Movie.MOVIE,
                null,
                Movie.COLUMN_ID_MOVIE  + " LIKE ?",
                new String[]{lawyerId},
                null,
                null,
                null);
        return c;
    }
    /*
    eliminacion de un registro
     */

    public int deleteMovie(String lawyerId) {
        return getWritableDatabase().delete(
                Movie.MOVIE,
                Movie.COLUMN_ID_MOVIE  + " LIKE ?",
                new String[]{lawyerId});
    }

    /*
    una actualizacion
     */
    public int updateMovie(Movie movie, String lawyerId) {
        return getWritableDatabase().update(
                Movie.MOVIE,
                movie.toContentValues(),
                Movie.COLUMN_ID_MOVIE + " LIKE ?",
                new String[]{lawyerId}
        );
    }


    /*
     una lista para llenar el adapter
     */
    public List<Movie> getAllMovie(){
        List<Movie> movieArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {
                Movie.COLUMN_ID,
                Movie.COLUMN_ID_MOVIE,
                Movie.COLUMN_TITLE,
                Movie.COLUMN_POSTER_PATH,
                Movie.COLUMN_STATE
        };
        Cursor c = db.query(Movie.MOVIE,
                columns,
                null,
                null,
                null,
                null,
                null);
        c.moveToFirst();
        while (!c.isAfterLast()){
            Movie movie = new Movie();
            movie.setId((int) Long.parseLong(c.getString(c.getColumnIndex(Movie.COLUMN_ID))));
            movie.setTitle(c.getString(c.getColumnIndex(Movie.COLUMN_TITLE)));
            movie.setPosterPath(c.getString(c.getColumnIndex(Movie.COLUMN_POSTER_PATH)));
            movie.setState(c.getString(c.getColumnIndex(Movie.COLUMN_STATE)));
            movieArrayList.add(movie);
            c.moveToNext();
        }
        c.close();
        db.close();
        return movieArrayList;
    }


}
