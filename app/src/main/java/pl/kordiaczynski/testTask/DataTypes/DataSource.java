package pl.kordiaczynski.testTask.DataTypes;

import android.widget.ImageView;

/**
 * Created by kordiaczynski on 28.11.2016.
 */

public interface DataSource
{
    public void infateImageViewWithImage(FlickrField flickrField, ImageView view);

    public void listenForNewsList(FlickrListener flickrListener);
    //If secondsInterval == 0 then call only once
    public void listenForScoreList(ScoreListener scoreListener,int seconds);
    public void stopAllPush();



    //public void listenForScores()
}
