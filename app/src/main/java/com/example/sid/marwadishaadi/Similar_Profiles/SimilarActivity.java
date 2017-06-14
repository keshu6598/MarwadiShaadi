package com.example.sid.marwadishaadi.Similar_Profiles;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;

import com.example.sid.marwadishaadi.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lawrence Dalmet on 13-06-2017.
 */

public class SimilarActivity extends AppCompatActivity{

    private SimilarAdapter similarAdapter;
    private List<SimilarModel> similarModelList = new ArrayList<>();;
    private RecyclerView recyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_similar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.similar_toolbar);
        toolbar.setTitle("Similar Profiles");
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        similarAdapter = new SimilarAdapter(similarModelList,getApplicationContext());
        recyclerView = (RecyclerView)findViewById(R.id.recycler);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        recyclerView.setAdapter(similarAdapter);
        recyclerView.setHasFixedSize(true);

        similarData();

    }

    private void similarData() {

        SimilarModel similarModel = new SimilarModel("Mervin Dalmet","Vasai","B. Tech","https://avatars2.githubusercontent.com/u/13920107?v=3&s=460",19);
        similarModelList.add(similarModel);

        similarModel = new SimilarModel("Mervin Dalmet","Vasai","B. Tech","https://avatars2.githubusercontent.com/u/13920107?v=3&s=460",19);
        similarModelList.add(similarModel);

        similarModel = new SimilarModel("Mervin Dalmet","Vasai","B. Tech","https://avatars2.githubusercontent.com/u/13920107?v=3&s=460",19);
        similarModelList.add(similarModel);

        similarModel = new SimilarModel("Mervin Dalmet","Vasai","B. Tech","https://avatars2.githubusercontent.com/u/13920107?v=3&s=460",19);
        similarModelList.add(similarModel);

        similarModel = new SimilarModel("Mervin Dalmet","Vasai","B. Tech","https://avatars2.githubusercontent.com/u/13920107?v=3&s=460",19);
        similarModelList.add(similarModel);

        similarModel = new SimilarModel("Mervin Dalmet","Vasai","B. Tech","https://avatars2.githubusercontent.com/u/13920107?v=3&s=460",19);
        similarModelList.add(similarModel);

        similarAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish();
        return true;
    }
}
