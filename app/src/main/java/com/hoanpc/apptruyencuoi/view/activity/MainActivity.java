package com.hoanpc.apptruyencuoi.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hoanpc.apptruyencuoi.R;
import com.hoanpc.apptruyencuoi.Story;
import com.hoanpc.apptruyencuoi.Topic;
import com.hoanpc.apptruyencuoi.TopicAdapter;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String KEY_LIST_STORY = "KEY_LIST_STORY";
    public static final String KEY_STORY_SELECTED = "KEY_STORY_SELECTED";
    public static final int REQUEST_CODE = 1;
    private static final int LEVEL_CLOSE = 0;
    private static final int LEVEL_OPEN = 1;
    private static final String KEY_NAME = "KEY_NAME";
    private static final String TAG = MainActivity.class.getName();
    private DrawerLayout mDrawer;
    private ImageView iv_menu;
    private TextView tv_menu;
    private List<Topic> listTopic;
    private RecyclerView rvTopic;
    private List<Story> mListData;
    private String dataName;
    private Story oldStory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initTopic();
    }

    private void initData() {
        rvTopic.setAnimation(AnimationUtils.loadAnimation(this, R.anim.alpha_anim));
        try {
            mListData = new ArrayList<>();
            InputStream inputStream = getAssets().open("data/" + dataName + ".txt");

            //  InputStream inputStream1 = ClassLoader.class.getClassLoader().getResourceAsStream("assets/text/" + dataName + ".txt");
            BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            String name = null;
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (name == null) {
                    name = line;
                } else if (line.equals("','0');")) {
                    mListData.add(new Story(name, content.toString()));
                    name = null;
                    content = new StringBuilder();
                } else {
                    content.append(line).append("\n");
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        initAdapter();
    }

    private void initAdapter() {
        TopicAdapter adapter = new TopicAdapter(mListData, this);
        rvTopic.setLayoutManager(new LinearLayoutManager(this));
        rvTopic.setAdapter(adapter);
        adapter.setOnTopicCallBack(new TopicAdapter.onTopicCallBack() {
            @Override
            public void onCallBack(Story story) {
                oldStory = story;
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra(KEY_LIST_STORY, (Serializable) mListData);
                intent.putExtra(KEY_STORY_SELECTED, story);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    private void initViews() {
        rvTopic = findViewById(R.id.rv_topic);
        iv_menu = findViewById(R.id.iv_menu);
        tv_menu = findViewById(R.id.tv_menu);
        iv_menu.setOnClickListener(this);
        mDrawer = findViewById(R.id.drawer);

        mDrawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                iv_menu.getDrawable().setLevel(LEVEL_OPEN);
                findViewById(R.id.tv_welcome).setVisibility(View.GONE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                iv_menu.getDrawable().setLevel(LEVEL_CLOSE);
            }
        });
    }

    private void initTopic() {
        try {
            String[] listPath = getAssets().list("image");
            listTopic = new ArrayList<>();
            LinearLayout lnTopic = findViewById(R.id.ln_topic);
            lnTopic.removeAllViews();
            for (String fileName : listPath) {
                final String name = fileName.replaceFirst("[.][^.]+$", "");
                listTopic.add(new Topic("image/" + fileName, fileName));

                final View viewTopic = LayoutInflater.from(this)
                        .inflate(R.layout.item_topic, lnTopic, false);
                ImageView ivTopic = viewTopic.findViewById(R.id.iv_topic);
                TextView tvTopic = viewTopic.findViewById(R.id.tv_topic);
                ivTopic.setImageBitmap(BitmapFactory
                        .decodeStream(getAssets().open("image/" + fileName)));
                tvTopic.setText(name);
                lnTopic.addView(viewTopic);
                viewTopic.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataName = name;
                        initData();
                        mDrawer.closeDrawers();
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_menu) {
            mDrawer.openDrawer(Gravity.LEFT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                Story in = (Story) data.getSerializableExtra(DetailActivity.KEY_RETURN);
                int newIndex = mListData.indexOf(in);
               // int oldIndex = mListData.indexOf(oldStory);
                rvTopic.scrollToPosition(newIndex);
//                if (newIndex != oldIndex && rvTopic.findViewHolderForLayoutPosition(newIndex) != null ) {
//                  rvTopic.findViewHolderForLayoutPosition(newIndex).itemView.setBackgroundResource(R.color.color_Violet);
//                  rvTopic.findViewHolderForLayoutPosition(oldIndex).itemView.setBackgroundResource(R.color.colorPrimaryWhite);
//               }
            }
        }
    }

}
