package pl.kordiaczynski.testTask.DataTypes;

import java.util.List;

/**
 * Created by kordiaczynski on 29.11.2016.
 */

public interface ScoreListener {

    void onScoreUpdated(List<Score> scoreList);
}
