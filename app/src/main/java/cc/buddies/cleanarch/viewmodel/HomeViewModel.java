package cc.buddies.cleanarch.viewmodel;

import android.content.Context;
import android.util.Pair;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import cc.buddies.cleanarch.R;
import cc.buddies.cleanarch.base.BaseViewModel;

public class HomeViewModel extends BaseViewModel {

    private MutableLiveData<List<Pair<String, String>>> newsTitlesLiveData = new MutableLiveData<>();

    public LiveData<List<Pair<String, String>>> getNewsTitles(Context context) {
        String[] stringTitles = context.getResources().getStringArray(R.array.news_titles);
        String[] stringKeys = context.getResources().getStringArray(R.array.news_keys);

        List<Pair<String, String>> newsTitles = new ArrayList<>();
        Pair<String, String> pair;

        for (int i = 0, size = stringTitles.length; i < size; i++) {
            pair = new Pair<>(stringTitles[i], stringKeys[i]);
            newsTitles.add(pair);
        }

        newsTitlesLiveData.setValue(newsTitles);
        return newsTitlesLiveData;
    }

}
