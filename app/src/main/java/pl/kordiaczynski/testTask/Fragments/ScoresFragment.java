package pl.kordiaczynski.testTask.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import pl.kordiaczynski.testTask.DataTypes.Score;
import pl.kordiaczynski.testTask.DataTypes.ScoreListener;
import pl.kordiaczynski.testTask.DataTypes.ScoresAdapter;
import pl.kordiaczynski.testTask.R;

/**
 * Created by kordiaczynski on 27.11.2016.
 */

public class ScoresFragment extends LoadingFragment implements ScoreListener {


    List<Score> scores;
    TextView tvScoreDate;
    ListView listView;
    ScoresAdapter scoresAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mainView = inflater.inflate(R.layout.fragment_one_view, null, false);


        onLoadingStarted();
        setMessageField(getString(R.string.message_loading_one));
//        tvScoreDate = (TextView) mainView.findViewById(R.id.TVscoreDate);
//        listView = (ListView) mainView.findViewById(R.id.LVscores);
//        RSSDataSource.getInstance().listenForScoreList(this, 30);


        return mainView;
    }


    private void showChanges() {
        String text = Score.getDate();
        if (text != null) {
            tvScoreDate.setText(text);
        }

        scoresAdapter.notifyDataSetChanged();
    }

    @Override
    public void onScoreUpdated(final List<Score> scoreList) {
        Log.d("ScoreListener", "Updated");
        Activity activity = getActivity();
        if (activity != null) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (scoreList != null) {
                        if (scores == null) // Only when first time downloaded
                        {

                            scoresAdapter = new ScoresAdapter(getContext(), R.id.score_single_field, scoreList);
                            listView.setAdapter(scoresAdapter);
                            onDataLoaded();
                        }
                        else
                        {
                            scoresAdapter.clear();
                            scoresAdapter.addAll(scoreList);
                        }

                        scores = scoreList;
                        showChanges();

                    } else {
                        setMessageField(getString(R.string.message_loading_unable_to_scores));
                    }
                }
            });
        }
    }

}
