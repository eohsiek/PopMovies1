package com.example.android.popmovies1.data;

import android.databinding.BindingAdapter;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageView;

import com.example.android.popmovies1.R;
import com.squareup.picasso.Picasso;

public class Trailer implements Parcelable {
    private String source;
    private String name;
    private String type;
    private String size;

    public Trailer (){

    }

    private Trailer(Parcel parcel){
        source = parcel.readString();
        name = parcel.readString();
        type = parcel.readString();
        size = parcel.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(source);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(size);
    }
    public static final Parcelable.Creator<Trailer> CREATOR = new Parcelable.Creator<Trailer>(){

        @Override
        public Trailer createFromParcel(Parcel parcel) {
            return new Trailer(parcel);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[0];
        }
    };

    public String getYouTubeURL() {
        String url =  "https://www.youtube.com/watch?v=" + source;
        return url;
    }

    public String getYouTubeThumbnail() {
        String url = "https://img.youtube.com/vi/" + source + "/default.jpg";
        return url;
    }

    public String getSource ()
    {
        return source;
    }

    public void setSource (String source)
    {
        this.source = source;
    }

    public String getName ()
    {
        return name;
    }

    public void setName (String name)
    {
        this.name = name;
    }

    public String getType ()
    {
        return type;
    }

    public void setType (String type)
    {
        this.type = type;
    }

    public String getSize ()
    {
        return size;
    }

    public void setSize (String size)
    {
        this.size = size;
    }

    public String getImageUrl() {
        String url = "https://img.youtube.com/vi/" + source + "/default.jpg";
        return url;
    }

    @Override
    public String toString()
    {
        return "Trailer [source = "+source+", name = "+name+", type = "+type+", size = "+size+"]";
    }



    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView view, String imageUrl) {
        Picasso.with(view.getContext())
                .load(imageUrl)
                .error(R.drawable.notfound)
                .placeholder(R.drawable.placeholder)
                .into(view);
    }


}
