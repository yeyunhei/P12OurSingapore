package sg.edu.rp.c346.id20011806.p12oursingapore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends ArrayAdapter {

    Context parent_context;
    int layout_id;
    ArrayList<Song> songList;

    public CustomAdapter(Context context, int resource, ArrayList<Song> songs) {
        super(context, resource, songs);

        parent_context = context;
        layout_id = resource;
        songList = songs;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtain the LayoutInflater object
        LayoutInflater inflater = (LayoutInflater)
                parent_context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // "Inflate" the View for each row
        View rowView = inflater.inflate(layout_id, parent, false);

        // Obtain the UI components and do the necessary binding
        TextView tvName = rowView.findViewById(R.id.textViewTitle);
        TextView tvYear = rowView.findViewById(R.id.textViewYear);
        TextView tvSinger = rowView.findViewById(R.id.textViewSinger);
//        TextView tvStars = rowView.findViewById(R.id.textViewStar);
        RatingBar ratingBarRow = rowView.findViewById(R.id.ratingBarRow);
        ImageView ivNew = rowView.findViewById(R.id.imageViewNew);

        // Obtain the Android Version information based on the position
        Song currentSong = songList.get(position);

        // Set values to the TextView to display the corresponding information
        tvName.setText(currentSong.getTitle());
        tvYear.setText(String.valueOf(currentSong.getYear()));
        /*String numstars = "";
        if (currentSong.getStars() == 1) {
            numstars = "*";
        } else if (currentSong.getStars() == 2) {
            numstars = "* *";
        }else if (currentSong.getStars() == 3) {
            numstars = "* * *";
        }else if (currentSong.getStars() == 4) {
            numstars = "* * * *";
        }else if (currentSong.getStars() == 5) {
            numstars = "* * * * *";
        }
        tvStars.setText(numstars);*/
        ratingBarRow.setRating(currentSong.getStars());
        tvSinger.setText(currentSong.getSingers());
        if (currentSong.getYear() < 2019) {
            ivNew.setVisibility(View.INVISIBLE);
        } else {
            ivNew.setImageResource(R.drawable.newsong);
        }

        return rowView;
    }
}

