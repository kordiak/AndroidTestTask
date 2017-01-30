package pl.kordiaczynski.testTask;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import java.io.Serializable;

import pl.kordiaczynski.testTask.DataTypes.RSSDataSource;
import pl.kordiaczynski.testTask.Fragments.FlickrFragment;
import pl.kordiaczynski.testTask.Fragments.ScoresFragment;

public class MainScreen extends AppCompatActivity {


    private enum CurrentView implements Serializable {
        Flickr,
        One,
        Two
    }

    private CurrentView currentView = CurrentView.Flickr;
    private final String KEY = "APP_VIEW";
    private FlickrFragment flickrFragment;
    private ScoresFragment scoresFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



    }

    @Override
    protected void onResume() {
        super.onResume();

        switch (currentView) {
            case Flickr:
                showFlickrScreen();
                break;
            case One:
                showOneScreen();
                break;
            case Two:
                showTwoScreen();
                break;
        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }


    @Override
    protected void onPause() {
        super.onPause();
        RSSDataSource.getInstance().stopAllPush();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(KEY, currentView);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        Serializable object = savedInstanceState.getSerializable(KEY);
        if (object != null) {
            currentView = (CurrentView) object;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        View view = (View) menu.findItem(R.id.action_search).getActionView();


        //Enrolling menu
        ////////////////////////////////////////////////////////////////////////////////
        final ToggleButton toggleButton = (ToggleButton) view.findViewById(R.id.showHide);
        final View linearLayoutView = findViewById(R.id.BIGBUTTON);
        linearLayoutView.setVisibility(View.VISIBLE);
        final float offScreenPoint = -R.attr.actionBarSize * 4;
        final float stoppoint = linearLayoutView.getY();
        linearLayoutView.setY(offScreenPoint);
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {

                    linearLayoutView.animate().translationY(stoppoint).setDuration(100);

                } else {

                    linearLayoutView.animate().translationY(offScreenPoint).setDuration(1000);

                }
            }
        });

        //Assign listeners to menu buttons
        ////////////////////////////////////////////////////////////////////////////////////
        linearLayoutView.findViewById(R.id.B_GotoNews).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayoutView.animate().translationY(offScreenPoint).setDuration(100);
                toggleButton.setChecked(false);
                showFlickrScreen();
            }
        });

        linearLayoutView.findViewById(R.id.B_GotoScores).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayoutView.animate().translationY(offScreenPoint).setDuration(100);
                toggleButton.setChecked(false);
                showOneScreen();
            }
        });
        linearLayoutView.findViewById(R.id.B_GotoStandings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                linearLayoutView.animate().translationY(offScreenPoint).setDuration(100);
                toggleButton.setChecked(false);
                showTwoScreen();
            }
        });


        return true;
    }


    private void showFlickrScreen() {
        currentView = CurrentView.Flickr;
        RSSDataSource.getInstance().stopAllPush();
        flickrFragment = new FlickrFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.slot, flickrFragment, KEY).commit();
        Log.d("Screen", "Flickr");
    }

    private void showOneScreen() {
        currentView = CurrentView.One;
        RSSDataSource.getInstance().stopAllPush();
        scoresFragment = new ScoresFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.slot, scoresFragment, KEY).commit();
        Log.d("Screen", "One");
    }

    private void showTwoScreen() {
        currentView = CurrentView.Two;
        RSSDataSource.getInstance().stopAllPush();

        Log.d("Screen", "Two");
    }

    @Override
    public void onBackPressed() {
        if (currentView == CurrentView.Flickr && flickrFragment.isWebViewVisible()) {
            flickrFragment.closeWebView();
            return;
        }
        super.onBackPressed();
    }
}
