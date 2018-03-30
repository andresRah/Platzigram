package com.andresarevalo.platzigram.post.view;


import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andresarevalo.platzigram.R;
import com.andresarevalo.platzigram.adapter.PictureAdapterRecyclerView;
import com.andresarevalo.platzigram.model.Picture;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {


    private static final int REQUEST_CAMERA = 1;
    private FloatingActionButton fabCamera;
    private String photoPathTemp = "file:/storage/emulated/0/Android/data/com.andresarevalo.platzigram/files/Pictures/JPEG_20180329_19-54-34_-2058414868.jpg";

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        showToolbar(getResources().getString(R.string.tab_home),false, view);
        RecyclerView picturesRecycler = (RecyclerView) view.findViewById(R.id.pictureRecycler);

        fabCamera = (FloatingActionButton) view.findViewById(R.id.fabCamera);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        picturesRecycler.setLayoutManager(linearLayoutManager);

        PictureAdapterRecyclerView pictureAdapterRecyclerView =
                new PictureAdapterRecyclerView(buidPictures(), R.layout.cardview_picture, getActivity());

        picturesRecycler.setAdapter(pictureAdapterRecyclerView);


        fabCamera.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view){
                takePicture();
            }
        });

        return view;
    }

    // Metodo para abrir la camara
    private void takePicture() {
        Intent intentTakePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // Verificamos si el dispositivo tiene camara
        if(intentTakePicture.resolveActivity(getActivity().getPackageManager()) != null){

            File photoFile = null;

            // Se debe crear previamente el archivo antes de capturar la foto
            try{
                photoFile = createImageFile();
            }
            catch(Exception e){
                e.printStackTrace();
            }

            // Procedemos a abrir la camara
            if(photoFile != null){
                //Uri photoUri = FileProvider.getUriForFile(getActivity(), "com.andresarevalo.platzigram", photoFile);
                //intentTakePicture.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                if (Build.VERSION.SDK_INT >= 24){
                    Uri photo = FileProvider.getUriForFile(getContext(), "com.andresarevalo.platzigram", photoFile);
                    intentTakePicture.putExtra(MediaStore.EXTRA_OUTPUT, photo);
                } else {
                    intentTakePicture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.parse(photoPathTemp));
                }
                startActivityForResult(intentTakePicture, REQUEST_CAMERA);
            }
        }
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HH-mm-ss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        File photo = File.createTempFile(imageFileName, ".jpg", storageDir);

        photoPathTemp = "file:" + photo.getAbsolutePath();

        return photo;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CAMERA && resultCode == getActivity().RESULT_OK){
            Log.d("HomeFRagment", "CAMERA OK !!! :)");

            Intent i = new Intent(getActivity(), NewPostActivity.class);
            i.putExtra("PHOTO_PATH_TEMP", this.photoPathTemp);
            startActivity(i);
        }
    }

    public ArrayList<Picture> buidPictures(){
        ArrayList<Picture> pictures = new ArrayList<>();

        pictures.add(new Picture("http://www.novalandtours.com/images/guide/guilin.jpg", "Uriel Ramírez", "4 días", "3 Me Gusta"));
        pictures.add(new Picture("http://www.enjoyart.com/library/landscapes/tuscanlandscapes/large/Tuscan-Bridge--by-Art-Fronckowiak-.jpg", "Juan Pablo", "3 días", "10 Me Gusta"));
        pictures.add(new Picture("http://www.educationquizzes.com/library/KS3-Geography/river-1-1.jpg", "Anahi Salgado", "2 días", "9 Me Gusta"));
        pictures.add(new Picture("https://media.metrolatam.com/2018/03/13/dxdbaj4vaaa7ej5large-da29f11d4f2f73814bb67d491df4fe4e-1200x600.jpg", "Goku", "2 días", "9 Me Gusta"));
        pictures.add(new Picture("https://image.tmdb.org/t/p/w300/fZ2cGj7gTII3xdyTKZsyqDHOfv9.jpg", "Super sayayin", "2 días", "9 Me Gusta"));
        pictures.add(new Picture("http://3.bp.blogspot.com/-cVn4hSwGiOQ/Wo5lhZpCt5I/AAAAAAAAAAg/hkQjft83jd0OBQuyx1EXGHBxF_dAL-F3gCLcBGAs/s1600/129.jpg", "Migate", "2 días", "9 Me Gusta"));
        pictures.add(new Picture("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSZigwLxEeqoPfVqCa-6fXPlBwyG1c6WYKvR-lWjfqdw91WZW26", "Batman", "2 días", "9 Me Gusta"));

        return pictures;
    }

    // Colocar Toolbar a Fragment
    public void showToolbar(String title, boolean upButton, View view){
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
    }

}
