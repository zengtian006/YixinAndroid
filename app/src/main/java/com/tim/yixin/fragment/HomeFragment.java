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
import android.widget.ViewSwitcher;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.flaviofaria.kenburnsview.Transition;
import com.tim.yixin.R;
import com.tim.yixin.adapter.ListFeedAdapter;
import com.tim.yixin.model.Feed;
import com.tim.yixin.utils.LocaleUtil;

import java.util.ArrayList;
import java.util.List;

import static com.tim.yixin.utils.CommonUtil.containerHeight;

public class HomeFragment extends Fragment implements KenBurnsView.TransitionListener {
    private final static String TAG = HomeFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    View rootView;
    List<Feed> feedList;

    private static final int TRANSITIONS_TO_SWITCH = 3;
    private ViewSwitcher mViewSwitcher;
    private int mTransitionsCount = 0;

    public HomeFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_home, container, false);
        findView(rootView);
        buildDataSet();
        setView();
        return rootView;
    }

    private void buildDataSet() {
        Integer[] imageList = new Integer[]{R.drawable.endocrine, R.drawable.cardiology, R.drawable.ophthalmology, R.drawable.psychiatry, R.drawable.hepatology, R.drawable.gastroenterostomy};
//        String[] titleList = new String[]{"Endocrine", "Cardiology", "Ophthalmology", "Psychiatry", "Hepatology", "Gastroenterostomy"};
        String[] titleList = getResources().getStringArray(R.array.YixinArray);
        String[] aliasList = LocaleUtil.getStringArrayByLocal(getActivity(), R.array.YixinArray, LocaleUtil.ENGLISH);
//        Log.v(TAG, "Number: " + imageList.length);
//        Log.v(TAG, "title: " + titleList[2]);
//        Log.v(TAG, "Image: " + imageList[2]);
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

        ListFeedAdapter adapter = new ListFeedAdapter(getContext(), feedList);
        adapter.setHeaderView(header);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void findView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.home_recycler_view);
    }

    @Override
    public void onTransitionStart(Transition transition) {

    }

    @Override
    public void onTransitionEnd(Transition transition) {

    }
}
