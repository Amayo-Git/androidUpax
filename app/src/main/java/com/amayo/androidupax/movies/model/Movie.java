package com.amayo.androidupax.movies.model;

import android.content.ContentValues;
import android.database.Cursor;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Movie {

    public static final String MOVIE    = "customer";
    public static final String COLUMN_ID           = "_id";
    public static final String COLUMN_ID_MOVIE     = "movie_id";
    public static final String COLUMN_TITLE        = "title";
    public static final String COLUMN_POSTER_PATH  = "posterPath";
    public static final String COLUMN_STATE        = "state";


    /*
    ESTRUCTURA DE LA BASE DE DATOS SQLITE
     */
    public static final  String CREATE_TABLE =
            "CREATE TABLE "+ MOVIE +"("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_ID_MOVIE + " INTEGER,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_POSTER_PATH + " TEXT,"
                    + COLUMN_STATE + " TEXT,"
                    + "UNIQUE ("+ COLUMN_ID +"))";



    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("adult")
    private boolean adult;
    @SerializedName("overview")
    private String overview;
    @SerializedName("release_date")
    private String releaseDate;
    @SerializedName("genre_ids")
    private List<Integer> genreIds = new ArrayList<Integer>();
    @SerializedName("id")
    private Integer id;
    private long movie_id;
    @SerializedName("original_title")
    private String originalTitle;
    @SerializedName("original_language")
    private String originalLanguage;
    @SerializedName("title")
    private String title;
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("popularity")
    private Double popularity;
    @SerializedName("vote_count")
    private Integer voteCount;
    @SerializedName("video")
    private Boolean video;
    @SerializedName("vote_average")
    private Double voteAverage;
    private String state;

    /*
    public Movie(String posterPath, boolean adult, String overview, String releaseDate, List<Integer> genreIds, Integer id,
                 String originalTitle, String originalLanguage, String title, String backdropPath, Double popularity,
                 Integer voteCount, Boolean video, Double voteAverage) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
    }

     */

    public Movie (long movie_id, String title, String posterPath, String state){
        this.movie_id = movie_id;
        this.title = title;
        this.posterPath = posterPath;
        this.state = state;

    }

    public Movie(){

    }


    /*
     ELEMENTOS QUE SERAN MOSTRADOS EN LA VISTA
     */
    public Movie(Cursor cursor){

        movie_id = Long.parseLong(cursor.getString(cursor.getColumnIndex(COLUMN_ID_MOVIE)));
        title = cursor.getString(cursor.getColumnIndex(COLUMN_TITLE));
        posterPath = cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH));
        state = cursor.getString(cursor.getColumnIndex(COLUMN_STATE));

    }

    /*
    LOS VALORES QUE SE VAN A REGISTRAS EN LA BASE DE DATOS
     */
    public ContentValues toContentValues(){
        ContentValues  values = new ContentValues();

        values.put(COLUMN_ID_MOVIE, id);
        values.put(COLUMN_TITLE, title);
        values.put(COLUMN_POSTER_PATH, posterPath);
        values.put(COLUMN_STATE, state);
        return values;
    }


    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public long getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(long movie_id) {
        this.movie_id = movie_id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
