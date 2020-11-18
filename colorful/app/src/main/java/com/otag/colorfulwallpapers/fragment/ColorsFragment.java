package com.otag.colorfulwallpapers.fragment;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.otag.colorfulwallpapers.R;
import com.otag.colorfulwallpapers.adabter.ColorsAdabter;
import com.otag.colorfulwallpapers.adabter.PicturesAdabter;
import com.otag.colorfulwallpapers.model.Colors;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment implements ColorsAdabter.ColorsAdabterListern {

    RecyclerView recyclerView_colors,recyclerViewPictures;
    DatabaseReference reference, referencePictures;
    PicturesAdabter picturesAdabter;
    List<Colors> colorsList = new ArrayList<>();
    List<String> pictureList = new ArrayList<>();
    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_colors, container, false);
        loadColor(view);
        loadPictures(view);
        return view;
    }

    private void loadPictures(View view) {

        recyclerViewPictures = view.findViewById(R.id.recycler_view_pictures);
        recyclerViewPictures.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        recyclerViewPictures.setLayoutManager(gridLayoutManager);
        picturesAdabter = new PicturesAdabter(getContext(),pictureList);
        recyclerViewPictures.setAdapter(picturesAdabter);


    }

    private void loadColor(View view) {
        recyclerView_colors = view.findViewById(R.id.recycler_view_colors);
        recyclerView_colors.setHasFixedSize(true);
        recyclerView_colors.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        final ColorsAdabter colorsAdabter = new ColorsAdabter(getContext(),colorsList,this);
        recyclerView_colors.setAdapter(colorsAdabter);
        reference = FirebaseDatabase.getInstance().getReference("Colors");
        reference.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                colorsList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    Colors colors = snapshot.getValue(Colors.class);
                    colorsList.add(colors);

                }
                colorsAdabter.notifyDataSetChanged();
                reference.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void showPic(String colorName) {

        referencePictures= FirebaseDatabase.getInstance().getReference("Wallpaper/"+ colorName +"/");
        referencePictures.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pictureList.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    pictureList.add(snapshot.getValue(String.class));


                }

                picturesAdabter.notifyDataSetChanged();
                referencePictures.removeEventListener(this);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getContext(), ""+databaseError.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}