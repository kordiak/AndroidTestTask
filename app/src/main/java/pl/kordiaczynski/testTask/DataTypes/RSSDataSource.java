package pl.kordiaczynski.testTask.DataTypes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;


import org.xmlpull.v1.XmlPullParser;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import pl.kordiaczynski.testTask.AdditionalTools.BackgroundTask;
import pl.kordiaczynski.testTask.AdditionalTools.XmlParser;

/**
 * Created by kordiaczynski on 28.11.2016.
 */

public class RSSDataSource implements DataSource {

    private static final String ERROR_LOG = "RSSDataSource Error:";
    private static final String NEWS_URL = "https://api.flickr.com/services/feeds/photos_public.gne?format=rss_200_enc";
    private static final String SCORE_URL = "some path";
    private static RSSDataSource source;
    private BackgroundTask task = BackgroundTask.getInstance();
    private ScoreListener scoreListener;
    private int interval;
    private Handler backgroundHandler;
    private Handler mainHandler;
    private XmlPullParser xmlPullParser;
    private boolean isTimerRunning = true;

    public static RSSDataSource getInstance() {
        if (null == source) {
            source = new RSSDataSource();
        }
        return source;
    }

    private RSSDataSource() {
        backgroundHandler = task.getBackgroundHandler();
        mainHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void infateImageViewWithImage(final FlickrField flickrField, final ImageView view) {
        BackgroundTask backgroundTask = BackgroundTask.getInstance();

        backgroundTask.getBackgroundHandler().post(new Runnable() {
            @Override
            public void run() {

                BitmapFactory.Options options = new BitmapFactory.Options();
                Bitmap bmp = null;


                try {

                    String path = flickrField.getImage();
                    URL url = new URL(path);
                   bmp=BitmapFactory.decodeStream((InputStream)url.getContent(), null, options);

                } catch (MalformedURLException ex) {

                    Log.e(ERROR_LOG, "Image url error");
                } catch (IOException ex) {
                    Log.d(ERROR_LOG, "Connection error");
                }
                if (null != bmp) {
                    //todo What if view is not visible

                    flickrField.setBitmap(Bitmap.createScaledBitmap(bmp, view.getMaxWidth(), view.getMaxHeight(), false));
                    mainHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            view.setImageBitmap(flickrField.getBitmap());
                        }
                    });

                }

            }
        });
    }

    @Override
    public void listenForNewsList(final FlickrListener flickrListener) {
        backgroundHandler.post(new Runnable() {
            @Override
            public void run() {
                List<FlickrField> flickrFieldList = XmlParser.parseNewsListFromXml(NEWS_URL);
                flickrListener.onNewsUpdated(flickrFieldList);
            }
        });
    }


    private void listenForScoreListCyclically() {
        if (scoreListener != null) {
            backgroundHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (isTimerRunning) {
                        List<Score> newsList = XmlParser.parseScoreListFromXml(SCORE_URL);
                        scoreListener.onScoreUpdated(newsList);
                        backgroundHandler.postDelayed(this, interval);
                    }
                }
            });
        }
    }

    @Override
    public void listenForScoreList(final ScoreListener scoreListener, int seconds) {
        if (seconds <= 0) {
            backgroundHandler.post(new Runnable() {
                @Override
                public void run() {
                    List<Score> newsList = XmlParser.parseScoreListFromXml(SCORE_URL);
                    scoreListener.onScoreUpdated(newsList);
                }
            });

            return;
        }
        isTimerRunning = true;
        this.scoreListener = scoreListener;
        interval = seconds * 1000;
        listenForScoreListCyclically();

    }



    @Override
    public void stopAllPush() {

        isTimerRunning = false;
        backgroundHandler.removeCallbacksAndMessages(null);
    }
}
