package pl.kordiaczynski.testTask.Fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import pl.kordiaczynski.testTask.DataTypes.FlickrField;
import pl.kordiaczynski.testTask.DataTypes.FlickrAdapter;
import pl.kordiaczynski.testTask.DataTypes.FlickrListener;
import pl.kordiaczynski.testTask.DataTypes.RSSDataSource;
import pl.kordiaczynski.testTask.R;

import static android.view.View.GONE;

/**
 * Created by kordiaczynski on 26.11.2016.
 */

public class FlickrFragment extends LoadingFragment implements FlickrListener, WebViewSupportAble {


    private FlickrAdapter flickrAdapter;
    private List<FlickrField> flickrFieldList = new ArrayList<>();
    private WebView webView;
    private boolean isWebViewVisible = false;
    private ListView listView;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mainView = inflater.inflate(R.layout.fragment_flickr_view, null, false);
        onLoadingStarted();
        webView = (WebView) mainView.findViewById(R.id.WVbrowser);
        webView.setWebViewClient(new WebViewClient());
        listView = (ListView) mainView.findViewById(R.id.LVnews);
        RSSDataSource.getInstance().listenForNewsList(this);
        setMessageField(getString(R.string.message_loading_flickr));
        return mainView;
    }


    @Override
    public void onNewsUpdated(final List<FlickrField> flickrFieldList) {
        this.flickrFieldList = flickrFieldList;
        Log.d("FlickrListener", "Updated");

        Activity activity = getActivity();
        if (activity != null)
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    if (flickrFieldList == null) {
                        setMessageField(getString(R.string.message_loading_unable_to_download_news));
                    } else {
                        flickrAdapter = new FlickrAdapter(FlickrFragment.this, getContext(), R.id.news_single_field, FlickrFragment.this.flickrFieldList);

                        listView.setAdapter(flickrAdapter);
                        onDataLoaded();
                    }
                }
            });

    }

    public boolean isWebViewVisible() {
        return isWebViewVisible;
    }

    @Override
    public void openSiteInWebView(String url) {
        isWebViewVisible = true;
        webView.setVisibility(View.VISIBLE);
        listView.setVisibility(GONE);
        webView.loadUrl(url);

    }

    @Override
    public void closeWebView() {
        isWebViewVisible = false;
        webView.clearHistory();
        webView.setVisibility(GONE);
        listView.setVisibility(View.VISIBLE);
    }
}

