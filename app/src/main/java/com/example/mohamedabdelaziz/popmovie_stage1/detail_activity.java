package com.example.mohamedabdelaziz.popmovie_stage1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
public class detail_activity extends AppCompatActivity {
TextView text_title  , date_vote , plot ;
    ImageView image_poster ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_activity);
        text_title = (TextView)findViewById(R.id.ttitle);
        image_poster=(ImageView)findViewById(R.id.image_poster);
        date_vote = (TextView)findViewById(R.id.date_and_vote);
        plot = (TextView)findViewById(R.id.plot);
        Intent intent = getIntent() ;
        String  title=  intent.getStringExtra("title"), release_date = intent.getStringExtra("release_date"),
                movie_poster = intent.getStringExtra("poster_path"), vote_average = intent.getStringExtra("vote_average")
                ,plot_synopsis=intent.getStringExtra("plot_synopsis") ;
         text_title.setText(title);
        Picasso.with(getApplicationContext()).load("http://image.tmdb.org/t/p/w185/"+movie_poster).into(image_poster);
        date_vote.setText("Date : "+release_date+"\n\n"+"Vote : "+vote_average);
        plot.setText(plot_synopsis);
    }

}
