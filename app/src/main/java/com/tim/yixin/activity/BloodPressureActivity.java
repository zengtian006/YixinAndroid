package com.tim.yixin.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;
import com.tim.yixin.R;
import com.tim.yixin.model.BloodPressure;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class BloodPressureActivity extends AppCompatActivity {
    private final static String TAG = BloodSugarActivity.class.getSimpleName();
    private final static int INTENT_RECORD = 0;

    private PieChart mChart;
    protected Typeface mTfRegular;
    protected Typeface mTfLight;
    Button btnAddNewBP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initChart();
        findView();
        setListner();
    }

    private void initChart() {
        mChart = (PieChart) findViewById(R.id.chartPressure);
        mChart.setUsePercentValues(true);
        mChart.getDescription().setEnabled(false);
        mChart.setExtraOffsets(5, 10, 5, 5);

        mChart.setDragDecelerationFrictionCoef(0.95f);

        mChart.setCenterTextTypeface(mTfLight);
        mChart.setCenterText(generateCenterSpannableText());

        mChart.setDrawHoleEnabled(true);
        mChart.setHoleColor(Color.WHITE);

        mChart.setTransparentCircleColor(Color.WHITE);
        mChart.setTransparentCircleAlpha(110);

        mChart.setHoleRadius(58f);
        mChart.setTransparentCircleRadius(61f);

        mChart.setDrawCenterText(true);

        mChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mChart.setRotationEnabled(true);
        mChart.setHighlightPerTapEnabled(true);

        setData();

        mChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = mChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        mChart.setEntryLabelColor(Color.WHITE);
        mChart.setEntryLabelTypeface(mTfRegular);
        mChart.setEntryLabelTextSize(12f);
    }

    private void setData() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, -3);
        Date threeMonthAgo = cal.getTime();
        Log.v(TAG, "Three Month Ago: " + cal.getTime());
        Realm realm = Realm.getDefaultInstance();
        RealmResults<BloodPressure> bps = realm.where(BloodPressure.class).findAll();
        int qualified = 0;
        for (BloodPressure bp : bps) {
            if (!bp.getCreatedAt().before(threeMonthAgo)) { //Date within recent three months
                if ((bp.getHigh() >= 90 && bp.getHigh() <= 140) && (bp.getLow() >= 60 && bp.getLow() <= 90)) {
                    qualified += 1;
                }
            }
        }
        float range = bps.size();

        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        entries.add(new PieEntry((float) (qualified / range), "良好控制", null)); //qualified
        entries.add(new PieEntry((float) ((range - qualified) / range), "还未达标", null)); //unqualified

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        ArrayList<Integer> colors = new ArrayList<Integer>();

        colors.add(Color.rgb(0, 176, 80));//qualified
        colors.add(Color.rgb(255, 0, 0)); //unqualified

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        mChart.setData(data);
        // undo all highlights
        mChart.highlightValues(null);
        mChart.invalidate();
    }

    private SpannableString generateCenterSpannableText() {
        SpannableString s = new SpannableString("Blood Pressure");
        s.setSpan(new RelativeSizeSpan(1.7f), 0, 14, 0);
        return s;
    }

    private void setListner() {
        btnAddNewBP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(BloodPressureActivity.this, BloodPressureAddActivity.class), INTENT_RECORD);
            }
        });
    }

    private void findView() {
        btnAddNewBP = (Button) findViewById(R.id.btnNewBP);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            case R.id.heath_list:
                Intent intent = new Intent(BloodPressureActivity.this, HealthListActivity.class);
                intent.putExtra("type", "BloodPressure");
                startActivity(intent);

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.health_list_menu, menu);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_RECORD) {
            if (resultCode == RESULT_OK) {
                setData();
            }
        }
    }
}
