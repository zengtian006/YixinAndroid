package com.tim.yixin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tim.yixin.R;
import com.tim.yixin.model.Health;

import java.util.List;

/**
 * Created by Zeng on 2017/8/17.
 */

public class ListHealthAdapter extends RecyclerView.Adapter<ListHealthAdapter.ItemRowHolder> {

    private final static String TAG = ListHealthAdapter.class.getSimpleName();
    private List<Health> dataList;
    private Context mContext;

    public ListHealthAdapter(Context context, List<Health> dataList) {
        this.dataList = dataList;
        this.mContext = context;
    }

    @Override
    public ListHealthAdapter.ItemRowHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.health_list, null);
        ListHealthAdapter.ItemRowHolder rowHolder = new ListHealthAdapter.ItemRowHolder(v);
        return rowHolder;
    }

    @Override
    public void onBindViewHolder(ListHealthAdapter.ItemRowHolder itemRowHolder, int position) {
        Health health = dataList.get(position);
        itemRowHolder.healthData.setText(health.getData());
        itemRowHolder.healthTime.setText(health.getTime());
    }

    @Override
    public int getItemCount() {
        return (null != dataList ? dataList.size() : 0);
    }

    public class ItemRowHolder extends RecyclerView.ViewHolder {

        public TextView healthData, healthTime;
        public RelativeLayout layout;

        public ItemRowHolder(View view) {
            super(view);
            layout = (RelativeLayout) view.findViewById(R.id.health_layout);
            healthData = (TextView) view.findViewById(R.id.healthListData);
            healthTime = (TextView) view.findViewById(R.id.healthListTime);
        }

    }
}
