package pl.kordiaczynski.testTask.DataTypes;

import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by kordiaczynski on 27.11.2016.
 */

public class FlickrField
{
    String title,image,description,link;
    Date time;
    Bitmap bitmap;

    public FlickrField()
    {

    }
    public FlickrField(String title, String image, String description, String link, Date time) {
        this.title = title;
        this.image = image;
        this.description = description;
        this.link = link;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }




}
