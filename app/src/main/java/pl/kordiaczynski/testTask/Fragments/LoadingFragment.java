package pl.kordiaczynski.testTask.Fragments;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

import pl.kordiaczynski.testTask.R;

/**
 * Created by kordiaczynski on 27.11.2016.
 */

public class LoadingFragment extends Fragment {
    protected View mainView;
    private TextView messageField;


    protected void onDataLoaded() {

        mainView.findViewById(R.id.Lloading_view).setVisibility(View.GONE);
    }

    protected void onLoadingStarted() {
        View view = mainView.findViewById(R.id.Lloading_view);
        view.setVisibility(View.VISIBLE);


    }

    protected void setMessageField(String text) {
        if (null == messageField) {
            messageField =(TextView) mainView.findViewById(R.id.TVloadingMessage);
        }
        messageField.setText(text);

    }

}
