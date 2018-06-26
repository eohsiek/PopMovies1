package com.example.android.popmovies1.data;

import android.os.Parcel;
import android.os.Parcelable;

public class Review  implements Parcelable
{
    private String id;
    private String content;
    private String author;
    private String url;

    public Review (){

    }
    private Review(Parcel parcel){
        id = parcel.readString();
        content = parcel.readString();
        author = parcel.readString();
        url = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(content);
        dest.writeString(author);
        dest.writeString(url);
    }
    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>(){

        @Override
        public Review createFromParcel(Parcel parcel) {
            return new Review(parcel);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[0];
        }
    };

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    public String getContent ()
    {
        content =  "Author: " + author
                + System.getProperty ("line.separator")
                + System.getProperty ("line.separator")
                + content;
        return content;
    }

    public void setContent (String content)
    {
        this.content = content;
    }

    public String getAuthor ()
    {
        return author;
    }

    public void setAuthor (String author)
    {
        this.author = author;
    }

    public String getUrl ()
    {
        return url;
    }

    public void setUrl (String url)
    {
        this.url = url;
    }

    @Override
    public String toString()
    {
        return "Review [id = "+id+", content = "+content+", author = "+author+", url = "+url+"]";
    }
}
