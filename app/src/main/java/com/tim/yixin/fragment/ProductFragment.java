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
public class ProductFragment extends Fragment {
    private final static String TAG = NewsFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    View rootView;
    List<Feed> feedList;

    public ProductFragment() {
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
        rootView = inflater.inflate(R.layout.fragment_product, container, false);
        findView(rootView);
        buildDataSet();
        setView();
        return rootView;
    }

    private void buildDataSet() {
        Integer[] imageList = new Integer[]{R.drawable.common_medication, R.drawable.vaccine, R.drawable.medical_instrument, R.drawable.health_care, R.drawable.physical_exam};
//        String[] titleList = new String[]{"Common medications", "Vaccine", "Medical instruments", "Health Care", "Physical examination"};
        String[] titleList = getResources().getStringArray(R.array.ProductArray);
        String[] aliasList = LocaleUtil.getStringArrayByLocal(getActivity(), R.array.ProductArray, LocaleUtil.ENGLISH);

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
        headerImage.setImageResource(R.drawable.bg_product);

        ListFeedAdapter adapter = new ListFeedAdapter(getContext(), feedList);
        adapter.setHeaderView(header);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void findView(View rootView) {
        recyclerView = (RecyclerView) rootView.findViewById(R.id.product_recycler_view);
    }

}
