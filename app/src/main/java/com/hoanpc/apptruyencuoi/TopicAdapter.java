package com.hoanpc.apptruyencuoi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileOutputStream;
import java.util.List;

public class TopicAdapter extends RecyclerView.Adapter<TopicAdapter.TopicHolder> {
    private List<Story> mListData;
    private Context mContext;
    private Story selectedData;
    private onTopicCallBack onTopicCallBack;

    public TopicAdapter(List<Story> mListData, Context mContext) {
        this.mListData = mListData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TopicHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.item_menu2, parent, false);
        return new TopicHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopicHolder holder, int position) {
        Story data = mListData.get(position);
        holder.tv_name.setText(data.getName());
        holder.tv_content.setText(data.getContent());
        holder.story = data;
//
//        if (selectedData == data) {
//            holder.lnTopic.setBackgroundResource(R.color.color_Violet);
//        } else {
//            holder.lnTopic.setBackgroundResource(R.color.colorPrimaryWhite);
//        }
    }

    @Override
    public int getItemCount() {
        return mListData.size();
    }

    public interface onTopicCallBack {
        void onCallBack(Story story);
    }
    public void setOnTopicCallBack(onTopicCallBack ontopicCallBack) {
        this.onTopicCallBack = ontopicCallBack;
    }

    public class TopicHolder extends RecyclerView.ViewHolder {
        private TextView tv_name;
        private TextView tv_content;
        private LinearLayout lnTopic;
        private Story story;

        public TopicHolder(@NonNull View itemView) {
            super(itemView);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_content = itemView.findViewById(R.id.tv_content);
            lnTopic = itemView.findViewById(R.id.ln_rv_topic);
            lnTopic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //selectedData = story;
                    notifyDataSetChanged();
                    onTopicCallBack.onCallBack(story);
                }
            });
        }
    }

}
