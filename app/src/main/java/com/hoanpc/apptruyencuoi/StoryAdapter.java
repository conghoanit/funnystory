package com.hoanpc.apptruyencuoi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public class StoryAdapter extends PagerAdapter {
    private List<Story> mListData;
    private Context mContext;

    public StoryAdapter(List<Story> mListData, Context mContext) {
        this.mListData = mListData;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_detail_story, container, false);

        TextView tvName = view.findViewById(R.id.tv_name);
        TextView tvContent = view.findViewById(R.id.tv_content);

        Story story = mListData.get(position);
        tvName.setText(story.getName());
        tvContent.setText(story.getContent());
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
