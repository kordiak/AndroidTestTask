package pl.kordiaczynski.testTask.AdditionalTools;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import pl.kordiaczynski.testTask.DataTypes.FlickrField;
import pl.kordiaczynski.testTask.DataTypes.Score;

/**
 * Created by kordiaczynski on 28.11.2016.
 */

public class XmlParser {

    private static final String ERROR_LOG = "XmlParser Error:";

    private static XmlPullParser getParserToURL(String path) {

        XmlPullParser xmlPullParser = null;
        HttpURLConnection connection = null;
        try {
            xmlPullParser = XmlPullParserFactory.newInstance().newPullParser();
            URL url = new URL(path);
            connection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = connection.getInputStream();
            InputStream stream = new BufferedInputStream(connection.getInputStream());
            xmlPullParser.setInput(new InputStreamReader(stream));
        } catch (MalformedURLException ex) {
            Log.e(ERROR_LOG, path + " url error");

        } catch (IOException ex) {
            Log.e(ERROR_LOG, path + " connection error");

        } catch (XmlPullParserException ex) {
            Log.e(ERROR_LOG, path + " parse exception");

        }
        return xmlPullParser;
    }

    private static FlickrField readItemBody(XmlPullParser parser) throws XmlPullParserException, IOException {

        FlickrField flickrField = new FlickrField();
        String title = null;
        String pubDate = null;
        String url = null;
        String link = null;
        int currentEvent = parser.getEventType();
        while (XmlPullParser.END_DOCUMENT != currentEvent) {
            String name = parser.getName();
            switch (currentEvent) {
                case XmlPullParser.END_TAG: {
                    if (name.equalsIgnoreCase("enclosure")) {
                        flickrField.setImage(parser.getAttributeValue(null, "url"));
                    } else if (name.equalsIgnoreCase("item")) {
                        return flickrField;
                    }
                    break;
                }
                case XmlPullParser.START_TAG: {
                    if (name.equalsIgnoreCase("title")) {
                        currentEvent = parser.next();
                        if (currentEvent == XmlPullParser.TEXT) {
                            flickrField.setTitle(parser.getText());
                        }
                    } else if (name.equalsIgnoreCase("pubDate")) {
                        currentEvent = parser.next();
                        if (currentEvent == XmlPullParser.TEXT) {
                            flickrField.setDescription(parser.getText());
                        }
                    } else if (name.equalsIgnoreCase("link")) {
                        currentEvent = parser.next();
                        if (currentEvent == XmlPullParser.TEXT) {
                            flickrField.setLink(parser.getText());
                        }
                    }
                    break;
                }

            }
            currentEvent = parser.next();
        }

        return flickrField;
    }

    public static List<FlickrField> parseNewsListFromXml(String path) {
        XmlPullParser parser = getParserToURL(path);
        List<FlickrField> flickrFieldList = new ArrayList<>();
        FlickrField flickrField = null;
        try {
            int currentEvent = parser.getEventType();
            while (XmlPullParser.END_DOCUMENT != currentEvent) {
                String name = parser.getName();
                if (name != null)
                    if (name.equalsIgnoreCase("item")) {
                        if (XmlPullParser.END_TAG == currentEvent) {
                            if (flickrField != null) {
                                flickrFieldList.add(flickrField);
                            }

                        } else if (XmlPullParser.START_TAG == currentEvent) {
                            flickrField = readItemBody(parser);
                            currentEvent = parser.getEventType();
                            continue;
                        }
                    }
                currentEvent = parser.next();
            }
        } catch (XmlPullParserException ex) {
            Log.e(ERROR_LOG, "Unable to get list of the flickrField");
            return null;
        } catch (IOException ex) {
            return null;
        }

        return flickrFieldList;
    }

    public static List<Score> parseScoreListFromXml(String path) {
        XmlPullParser parser = getParserToURL(path);


        List<Score> scoreList = new ArrayList<>();
        try {
            int currentEvent = parser.getEventType();
            while (XmlPullParser.END_DOCUMENT != currentEvent) {
                if (XmlPullParser.START_TAG != currentEvent) {
                    currentEvent = parser.next();
                    continue;
                }
                String name = parser.getName();
                if (name.equalsIgnoreCase("parameter")) {
                    String value = parser.getAttributeValue(null, "name");
                    if(null != value && value.equalsIgnoreCase("date"))
                    {
                        String date = parser.getAttributeValue(null,"value");
                        if (date != null) {
                            Score.setDate(date);
                        }

                    }

                } else if (name.equalsIgnoreCase("match")) {
                    String team_A = parser.getAttributeValue(null, "team_A_name");
                    String team_B = parser.getAttributeValue(null, "team_B_name");
                    String fs_A = parser.getAttributeValue(null, "fs_A");
                    String fs_B = parser.getAttributeValue(null, "fs_B");

                    if (null != team_A && null != team_B && null != fs_A && null != fs_B) {
                        scoreList.add(new Score(team_A,team_B,fs_A,fs_B));
                    }
                }

                currentEvent = parser.next();

            }
        } catch (XmlPullParserException ex) {
            Log.e(ERROR_LOG, "Unable to get list of the scores");
            return null;
        } catch (IOException ex) {
            return null;
        }
        return scoreList;
    }

}
