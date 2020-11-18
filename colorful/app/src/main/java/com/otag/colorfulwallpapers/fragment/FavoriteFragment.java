package com.otag.colorfulwallpapers.fragment;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.otag.colorfulwallpapers.R;
import com.otag.colorfulwallpapers.adabter.FavoriteAdabter;
import com.otag.colorfulwallpapers.database.FavoriteRoomDatabase;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {
    FavoriteRoomDatabase  database;
    RecyclerView recyclerViewFavorite;
    LinearLayout layoutNoFavorite;


    List<String> listFavorite;

    public FavoriteFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        recyclerViewFavorite = view.findViewById(R.id.recycler_view_favorite);
        recyclerViewFavorite.setHasFixedSize(true);
        recyclerViewFavorite.setLayoutManager(new LinearLayoutManager(getContext()));
        layoutNoFavorite = view.findViewById(R.id.layout_no_favorite);
        database = FavoriteRoomDatabase.getDatabse(getContext());
        new LoadFavorite().execute();

        return view;
    }
    class LoadFavorite extends AsyncTask<Void, List<String>, List<String>> {


        @Override
        protected List<String> doInBackground(Void... voids) {
            return database.dao().getAllFavorite();
        }

        @Override
        protected void onPostExecute(List<String> list) {

            super.onPostExecute(list);
            listFavorite = list;
            if (listFavorite.size() > 0 ){


                layoutNoFavorite.setVisibility(View.GONE);

                FavoriteAdabter adabter = new FavoriteAdabter(getContext(),listFavorite);
                recyclerViewFavorite.setAdapter(adabter);
            }else{
                recyclerViewFavorite.setVisibility(View.GONE);
                layoutNoFavorite.setVisibility(View.VISIBLE);

            }
        }
    }
}
