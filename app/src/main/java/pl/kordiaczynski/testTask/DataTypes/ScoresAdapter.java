package pl.kordiaczynski.testTask.DataTypes;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import pl.kordiaczynski.testTask.R;

/**
 * Created by kordiaczynski on 29.11.2016.
 */

public class ScoresAdapter extends ArrayAdapter<Score> {
    public ScoresAdapter(Context context, int resource, List<Score> objects) {
        super(context, resource, objects);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.score_single_field, null);
        TextView tvHost = (TextView) view.findViewById(R.id.TVhostTeam);
        TextView tvGuest = (TextView) view.findViewById(R.id.TVguestTeam);
        TextView tvResult = (TextView) view.findViewById(R.id.TVresult);

        Score score = getItem(position);
        String result = score.fs_A + " - " + score.fs_B;
        tvResult.setText(result);
        tvHost.setText(score.getTeam_A_name());
        tvGuest.setText(score.getTeam_B_name());


        if(position%2==0)
        {
            view.setBackgroundColor(Color.parseColor("#ECECEC"));
        }
        return view;
    }
}
