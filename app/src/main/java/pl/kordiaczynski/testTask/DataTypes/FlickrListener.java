package pl.kordiaczynski.testTask.DataTypes;

import java.util.List;

/**
 * Created by kordiaczynski on 28.11.2016.
 */

public interface FlickrListener {
    void onNewsUpdated(List<FlickrField> flickrFieldList);
}
