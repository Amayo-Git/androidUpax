package com.amayo.androidupax.movies.addEdit;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.amayo.androidupax.R;
import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddEditFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";


    // TODO: Rename and change types of parameters
    private String mParam1;

    private ImageView mImageView;
    final int CAMERA_REQUEST = 1100;
    private static final int GALLERY_REQUEST_CODE = 105;

    public AddEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.

     * @return A new instance of fragment AddEditFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddEditFragment newInstance(String param1) {
        AddEditFragment fragment = new AddEditFragment();
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
        View root = inflater.inflate(R.layout.fragment_add_edit, container, false);

        mImageView = root.findViewById(R.id.imageTicket);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
        return root;
    }



    private void uploadImage(){

        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.dialog_image,null);
        CardView photo = alertLayout.findViewById(R.id.camera);
        CardView image = alertLayout.findViewById(R.id.gallery);
        Button cancel = alertLayout.findViewById(R.id.bCancel);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setView(alertLayout);
        final AlertDialog show = alertDialog.show();
        photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCameraApp();
                show.dismiss();
            }
        });
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(gallery, GALLERY_REQUEST_CODE);
                show.dismiss();


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                show.dismiss();
            }
        });

        show.setCancelable(false);
    }

    private void callCameraApp(){

        final List<Intent> cameraIntents = new ArrayList<Intent>();
        final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        final PackageManager packageManager = getActivity().getPackageManager();
        final List<ResolveInfo> listCam = packageManager.queryIntentActivities(captureIntent, 0);
        for(ResolveInfo res : listCam) {
            final String packageName = res.activityInfo.packageName;
            final Intent intent = new Intent(captureIntent);
            intent.setComponent(new ComponentName(packageName, res.activityInfo.name));
            intent.setPackage(packageName);
            cameraIntents.add(intent);
        }

        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);

        Intent chooserIntent = Intent.createChooser(galleryIntent, "Select Source");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, cameraIntents.toArray(new Parcelable[cameraIntents.size()]));

        startActivityForResult(chooserIntent, CAMERA_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == CAMERA_REQUEST){
            if(resultCode == Activity.RESULT_OK){

                Bundle extras = data.getExtras();
                Bitmap bitmap = (Bitmap) extras.get("data");

                decodeString(bitmap);
                Uri tempUri = getImageUri(getActivity().getApplicationContext(), bitmap);
                //mFileName  = getRealPathFromURI(getActivity().getApplicationContext(), tempUri);
                //nombre de la foto

                Glide.with(this)
                        .load(tempUri)
                        .centerCrop()
                        .into(mImageView);


            }

        }
        else if (requestCode == GALLERY_REQUEST_CODE){
            if(resultCode == Activity.RESULT_OK){
                Uri contentUri = data.getData();

                Glide.with(this)
                        .load(contentUri)
                        .centerCrop()
                        .into(mImageView);


                //mFileName  = getRealPathFromURI(getActivity().getApplicationContext(), contentUri);
                //nombre de la imagen

                try {
                    InputStream inputStream = getActivity().getContentResolver().openInputStream(contentUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    decodeString(bitmap);
                }catch (FileNotFoundException e){
                    e.printStackTrace();
                }


            }
        }

    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    private static String getRealPathFromURI(Context context, Uri uri){

        String filePath = "";

        Cursor returnCursor =
                context.getContentResolver().query(uri, null, null, null, null);

        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);

        returnCursor.moveToFirst();

        filePath = returnCursor.getString(nameIndex);


        return filePath;
    }

    private void decodeString(Bitmap bitmap){

        ByteArrayOutputStream one = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, one);
        byte[] imageBytes = one.toByteArray();
        /*
        convertir la imagen a string
         */

        //mFileUri =  Base64.encodeToString(imageBytes, Base64.DEFAULT);

    }




    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_edit:
                //saveMovie();
                break;
            case R.id.action_delete:
                //cancelMovie();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    private void saveMovie(){
        /*
        esta funcion tendra una validacion,
        en caso de la peticion de retrofy mande mensaje sin conexion
        se guarda el registro en la base sqlite con un estatado de
        "new " ,"server " el registro viene del servidor  y "edit" el registro
        fue editado por el usuario

        Los estado new y edit sera los que se enviaran al servidor
         */
    }

    private void cancelMovie(){
        /*
        cancelar la operacion y volver al fragmento anterior

         */
    }
}