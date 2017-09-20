package com.tim.yixin.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.tim.yixin.R;
import com.tim.yixin.adapter.ListFeedAdapter;
import com.tim.yixin.model.Feed;
import com.tim.yixin.utils.LocaleUtil;

import java.util.ArrayList;
import java.util.List;

import static com.tim.yixin.utils.CommonUtil.containerHeight;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends Fragment {
    private final static String TAG = NewsFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    View rootView;
    List<Feed> feedList;

    public NewsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_news, container, false);
        findView(rootView);
        buildDataSet();
        setView();
        return rootView;
    }

    private void buildDataSet() {
        Integer[] imageList = new Integer[]{R.drawable.health_tips, R.drawable.travel_warning};
//        String[] titleList = new String[]{"Health tips", "Travel warning"};
        String[] titleList = getResources().getStringArray(R.array.NewsArray);
        String[] aliasList = LocaleUtil.getStringArrayByLocal(getActivity(), R.array.NewsArray, LocaleUtil.ENGLISH);

        feedList = new ArrayList<Feed>();
        for (int i = 0; i < imageList.length; i++) {
            feedList.add(new Feed(aliasList[i], titleList[i], imageList[i]));
        }
    }

    private void setView() {
        setRecyclerView();
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        View header = LayoutInflater.from(rootView.getContext()).inflate(R.layout.header, recyclerView, false);
        int proportionalHeight = containerHeight(getActivity(), 3);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, proportionalHeight); // (width, height)
        header.setLayoutParams(params);
        KenBurnsView headerImage = (KenBurnsView) header.findViewById(R.id.header_image_view);
        headerImage.setImageResource(R.drawable.bg_news);

        ListFeedAdapter adapter = new ListFeedAdapter(getContext(), feedList);
        adapter.setHeaderView(header);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void findView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.news_recycler_view);
    }

}
