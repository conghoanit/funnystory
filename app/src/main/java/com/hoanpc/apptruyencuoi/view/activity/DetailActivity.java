package com.hoanpc.apptruyencuoi.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.hoanpc.apptruyencuoi.R;
import com.hoanpc.apptruyencuoi.Story;
import com.hoanpc.apptruyencuoi.StoryAdapter;

import java.util.List;

public class DetailActivity extends AppCompatActivity {
    public static final String KEY_RETURN = "KEY_RETURN";
    private static final String TAG = DetailActivity.class.getName();
    private Story currentItem;
    private ViewPager viewPager;
    private List<Story> listData;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        initViews();
    }

    private void initViews() {
        viewPager = findViewById(R.id.vp_story);
        Intent intent = getIntent();
        if (intent != null) {
            listData = (List<Story>) intent.getSerializableExtra(MainActivity.KEY_LIST_STORY);
            StoryAdapter storyAdapter = new StoryAdapter(listData, this);
            viewPager.setAdapter(storyAdapter);
            Story story = (Story) intent.getSerializableExtra(MainActivity.KEY_STORY_SELECTED);
            int index = listData.indexOf(story);
            viewPager.setCurrentItem(index, true);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        int id = viewPager.getCurrentItem();
        currentItem = listData.get(id);
        intent.putExtra(KEY_RETURN, currentItem);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }
}
