package com.tim.yixin.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.tim.yixin.R;
import com.tim.yixin.adapter.ListHealthAdapter;
import com.tim.yixin.model.BloodPressure;
import com.tim.yixin.model.BloodSugar;
import com.tim.yixin.model.Bmi;
import com.tim.yixin.model.Health;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;

public class HealthListActivity extends AppCompatActivity {
    private final static String TAG = HealthListActivity.class.getSimpleName();
    private RecyclerView recyclerView;
    List<Health> healthList;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_list);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        type = getIntent().getExtras().getString("type");
        initData();
        findView();
        setRecyclerView();
    }

    private void initData() {
        if (type.equals("BMI")) {
            getSupportActionBar().setTitle(getString(R.string.bmi_record));
            healthList = new ArrayList<Health>();
            Realm realm = Realm.getDefaultInstance();
            RealmResults<Bmi> bmis = realm.where(Bmi.class).findAll();
            for (Bmi b : bmis) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                String state = "";
                if (b.getBmi() < 18.5) {
                    state = "偏瘦";
                } else if (b.getBmi() >= 18.5 && b.getBmi() <= 22.9) {
                    state = "正常";
                } else if (b.getBmi() > 22.9 && b.getBmi() <= 24.9) {
                    state = "偏胖";
                } else if (b.getBmi() > 24.9 && b.getBmi() <= 29.9) {
                    state = "重度肥胖";
                } else if (b.getBmi() > 29.9) {
                    state = "极重度肥胖";
                }
                healthList.add(new Health(String.valueOf(b.getBmi()) + " (" + state + ")", String.valueOf(sdf.format(b.getCreatedAt()))));
            }
            Collections.reverse(healthList);
        } else if (type.equals("BloodPressure")) {
            getSupportActionBar().setTitle(getString(R.string.blood_pressure_record));
            healthList = new ArrayList<Health>();
            Realm realm = Realm.getDefaultInstance();
            RealmResults<BloodPressure> bps = realm.where(BloodPressure.class).findAll();


            for (BloodPressure bp : bps) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                healthList.add(new Health(bp.getHigh() + " (上压) /" + bp.getLow() + " (下压)\n" + bp.getHeart_rate() + " (心率)", String.valueOf(sdf.format(bp.getCreatedAt()))));
            }
            Collections.reverse(healthList);
        } else if (type.equals("BloodSugar")) {
            getSupportActionBar().setTitle(getString(R.string.blood_sugar_record));
            healthList = new ArrayList<Health>();
            Realm realm = Realm.getDefaultInstance();
            RealmResults<BloodSugar> bss = realm.where(BloodSugar.class).findAll();


            for (BloodSugar bs : bss) {
                String type = "";
                switch (bs.getType()) {
                    case "fbs"://空腹
                        type = "空腹";
                        break;
                    case "bm"://餐前
                        type = "餐前";
                        break;
                    case "am1"://餐后1小时
                        type = "餐后1小时";
                        break;
                    case "am2"://餐后2小时
                        type = "餐后2小时";
                        break;
                    case "am3"://餐后3小时
                        type = "餐后3小时";
                        break;
                    case "others"://其他
                        type = "其他";
                        break;
                }

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                healthList.add(new Health(String.valueOf(bs.getSugar()) + " (" + type + ")", String.valueOf(sdf.format(bs.getCreatedAt()))));
            }
            Collections.reverse(healthList);
        }
    }

    private void setRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);

        ListHealthAdapter adapter = new ListHealthAdapter(this, healthList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    private void findView() {
        recyclerView = (RecyclerView) findViewById(R.id.health_detail_recycler_view);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
