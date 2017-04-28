package com.example.mohamedabdelaziz.popmovie_stage1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
public class PopMovie extends AppCompatActivity {
GridView gridView ;
    String view_as = "popular" ;
    ArrayList<mydata>arrayList ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_movie);
        gridView=(GridView)findViewById(R.id.gridView);
        arrayList=new ArrayList<>();
        new getdata().execute();
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent  = new Intent(getApplicationContext(),detail_activity.class);
                intent.putExtra("title", arrayList.get(position).title);
                intent.putExtra("poster_path", arrayList.get(position).movie_poster);
                intent.putExtra("release_date", arrayList.get(position).release_date);
                intent.putExtra("vote_average", arrayList.get(position).vote_average);
                intent.putExtra("plot_synopsis", arrayList.get(position).plot_synopsis);
                startActivity(intent);
            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.polular)
        {
            view_as="popular";
            new getdata().execute();
        }
        else  if(item.getItemId()==R.id.top_rated)
        {
            view_as="top_rated";
            new getdata().execute();
        }
        return super.onOptionsItemSelected(item);
    }
    class getdata extends AsyncTask<Void,Void,Void>
    {
        URL url = null ;
        HttpURLConnection httpURLConnection = null ;
        BufferedReader bufferedReader=null;
        StringBuffer stringBuffer=new StringBuffer() ;
        String Json ;
        InputStream inputStream=null ;

        @Override
        protected void onPreExecute() {
           arrayList.clear();
            try {
                url=new URL("https://api.themoviedb.org/3/movie/"+view_as+"?api_key=********************");

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("GET");
                httpURLConnection.connect();

            } catch (IOException e) {
                e.printStackTrace();

            }
            try {
                inputStream = httpURLConnection.getInputStream();
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream)) ;
                String temp;

                while((temp=bufferedReader.readLine())!=null)
                    stringBuffer.append(temp+"\n");

            } catch (IOException e) {
                e.printStackTrace();
               }

            Json =stringBuffer.toString();
            /************************************************************************/
            try {
                JSONObject object1 = new JSONObject(Json);
                JSONArray jsonArray  =object1.getJSONArray("results") ;

                for (int i = 0; i < jsonArray.length(); i++) {
                   String temp = jsonArray.getString(i);
                    JSONObject object  = new JSONObject(temp) ;
                    arrayList.add(new mydata(object.getString("original_title"),object.getString("release_date"),object.getString("poster_path"),
                            object.getString("vote_average"),object.getString("overview")));
                }

            } catch (JSONException e) {
                e.printStackTrace();

            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            gridView.setAdapter(new custom_adapter(getApplicationContext(),arrayList));
       }

    }
}
