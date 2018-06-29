package com.example.android.popmovies1;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.android.popmovies1.data.Favorite;
import com.example.android.popmovies1.data.FavoriteDatabase;

import java.util.List;

public class MainViewModel extends AndroidViewModel{
    private LiveData<List<Favorite>> favorites;

    public MainViewModel(Application application) {
        super(application);
        FavoriteDatabase database = FavoriteDatabase.getDatabase(this.getApplication());
        favorites = database.daoAccess().fetchAllFavorites();
    }

    public LiveData<List<Favorite>> getFavorites() {
        return favorites;
    }
}
