package pl.kordiaczynski.testTask.DataTypes;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import pl.kordiaczynski.testTask.Fragments.WebViewSupportAble;
import pl.kordiaczynski.testTask.R;

/**
 * Created by kordiaczynski on 27.11.2016.
 */

public class FlickrAdapter extends ArrayAdapter<FlickrField> {


    RSSDataSource dataSource;
    WebViewSupportAble webViewSupportAble;


    public FlickrAdapter(WebViewSupportAble webViewSupportAble, Context context, int resource, List<FlickrField> objects) {
        super(context, resource, objects);
        dataSource = RSSDataSource.getInstance();
        this.webViewSupportAble = webViewSupportAble;
    }

    private void loadImageFromUrl(final String path, int width, int height, final ImageView view) {
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.news_single_field, null);
        final FlickrField flickrField = getItem(position);

        ImageView imageView = (ImageView) view.findViewById(R.id.IVimage);
        if (flickrField.getBitmap() == null) {
            dataSource.infateImageViewWithImage(flickrField, imageView);
        } else
            imageView.setImageBitmap(flickrField.getBitmap());

        TextView tvTitle = (TextView) view.findViewById(R.id.TVtitle);
        tvTitle.setText(flickrField.title);
        TextView tvDate = (TextView) view.findViewById(R.id.TVdate);
        tvDate.setText(flickrField.description);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (webViewSupportAble != null) {
                    webViewSupportAble.openSiteInWebView(flickrField.link);
                }
            }
        });
        return view;
    }
}
