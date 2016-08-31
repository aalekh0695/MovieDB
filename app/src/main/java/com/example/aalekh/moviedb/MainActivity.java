package com.example.aalekh.moviedb;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {
    private ArrayAdapter<String> myAdapter;
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        String[] movie = {"movie1","movie2","movie3","movie4","movie5","movie6","movie7"};
        List<String> movieData = new ArrayList<String>(Arrays.asList(movie));
        myAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,movieData);
        lv = (ListView)findViewById(R.id.listviewid);
        lv.setAdapter(myAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String movieTitle = myAdapter.getItem(position);
                Toast.makeText(MainActivity.this,movieTitle,Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(MainActivity.this,DetailActivity.class);
                intent.putExtra("MovieTitle",movieTitle);
                startActivity(intent);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_refresh){
            Async1 obj = new Async1();
            obj.execute("https://api.cinemalytics.com/v1/movie/releasedthisweek?auth_token=7FC796088CDF529C9942283F020E4FB0");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private class Async1 extends FetchMovieData{

        @Override
        protected void onPostExecute(String[] strings) {
            if(strings!=null){
                myAdapter.clear();
                for (String movieTitle:strings){
                    myAdapter.add(movieTitle);
                }
            }
        }

    }

}
