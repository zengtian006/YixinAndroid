package com.tim.yixin.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tim.yixin.R;
import com.tim.yixin.activity.BMIActivity;
import com.tim.yixin.activity.BloodPressureActivity;
import com.tim.yixin.activity.BloodSugarActivity;
import com.tim.yixin.activity.WebBrowserActivity;
import com.tim.yixin.model.Feed;

import java.util.List;

import static com.tim.yixin.app.AppConfig.web_url;
import static com.tim.yixin.utils.CommonUtil.containerHeight;

/**
 * Created by Zeng on 2017/8/14.
 */

public class ListFeedAdapter extends RecyclerView.Adapter<ListFeedAdapter.ItemRowHolder> {
    private final static String TAG = ListFeedAdapter.class.getSimpleName();
    private List<Feed> dataList;
    private Context mContext;
    private View mHeaderView;
    public static final int TYPE_HEADER = 0;  //说明是带有Header的
    public static final int TYPE_NORMAL = 1;  //说明是不带有header和footer的

    public ListFeedAdapter(Context context, List<Feed> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }


    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            //第一个item应该加载Header
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ItemRowHolder(mHeaderView);
        }

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.feed_list, null);
        ItemRowHolder rowHolder = new ItemRowHolder(v);
        return rowHolder;
    }

    @Override
    public void onBindViewHolder(ItemRowHolder itemRowHolder, int i) {
        if (getItemViewType(i) == TYPE_HEADER) {
            return;
        }
        Feed feed = dataList.get(i - 1);

        itemRowHolder.title.setText(feed.getTitle());
        itemRowHolder.thumbnail.setImageResource(feed.getImage());
        itemRowHolder.alias.setText(feed.getAlias());

        int proportionalHeight = containerHeight((Activity) mContext, 6);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, proportionalHeight); // (width, height)
        itemRowHolder.layout.setLayoutParams(params);
    }

    @Override
    public int getItemCount() {
        if (mHeaderView != null) {
            return dataList.size() + 1;
        }

        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        public TextView title, alias;
        public ImageView thumbnail;
        public RelativeLayout layout;

        public ItemRowHolder(View view) {
            super(view);
            if (view == mHeaderView) {
                return;
            }
            layout = (RelativeLayout) view.findViewById(R.id.item_layout);
            title = (TextView) view.findViewById(R.id.item_title);
            thumbnail = (ImageView) view.findViewById(R.id.item_thumbnail);
            alias = (TextView) view.findViewById(R.id.item_alias);

            View.OnClickListener itemClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.v(TAG, "ALIAS: " + alias.getText().toString());
                    if (title.getText().equals(view.getResources().getString(R.string.bmi))) {
                        Intent intent = new Intent(view.getContext(), BMIActivity.class);
                        view.getContext().startActivity(intent);
                        return;
                    }
                    if (title.getText().equals(view.getResources().getString(R.string.blood_sugar))) {
                        Intent intent = new Intent(view.getContext(), BloodSugarActivity.class);
                        view.getContext().startActivity(intent);
                        return;
                    }
                    if (title.getText().equals(view.getResources().getString(R.string.blood_pressure))) {
                        Intent intent = new Intent(view.getContext(), BloodPressureActivity.class);
                        view.getContext().startActivity(intent);
                        return;
                    }
                    /////////////////////////////////////////////////////////////////////////////////////
                    Intent intent = new Intent(view.getContext(), WebBrowserActivity.class);
                    String cate = alias.getText().toString().replace(" ", "_");
                    String url = web_url + cate;
                    intent.putExtra("url", url);
                    view.getContext().startActivity(intent);

//                    Intent intent = new Intent(view.getContext(), WebBrowserActivity.class);
//                    view.getContext().startActivity(intent);

                }
            };
            view.setOnClickListener(itemClickListener);
            thumbnail.setOnClickListener(itemClickListener);
        }

    }
}
