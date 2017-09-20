package com.tim.yixin.activity;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.tim.yixin.R;
import com.tim.yixin.model.BloodSugar;
import com.tim.yixin.utils.ToastUtil;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class BloodSugarAddActivity extends AppCompatActivity {
    private final static String TAG = BloodSugarAddActivity.class.getSimpleName();

    RadioGroup radioGroup;
    TextView tvSugarType;
    String sugarType;
    EditText inputBloodSugar;
    Button btnSaveSugar;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_sugar_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        realm = Realm.getDefaultInstance();

        findView();
        setView();
        setListner();
    }

    private void setView() {
        sugarType = "";
    }

    private void setListner() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                Log.v(TAG, "Click: " + i);
                switch (i) {
                    case R.id.fbs://空腹
                        tvSugarType.setText("空腹");
                        sugarType = "fbs";
                        break;
                    case R.id.bm://餐前
                        tvSugarType.setText("餐前");
                        sugarType = "bm";
                        break;
                    case R.id.am1://餐后1小时
                        tvSugarType.setText("餐后1小时");
                        sugarType = "am1";
                        break;
                    case R.id.am2://餐后2小时
                        tvSugarType.setText("餐后2小时");
                        sugarType = "am2";
                        break;
                    case R.id.am3://餐后3小时
                        tvSugarType.setText("餐后3小时");
                        sugarType = "am3";
                        break;
                    case R.id.others://其他
                        tvSugarType.setText("其他");
                        sugarType = "others";
                        break;
                }
            }
        });

        btnSaveSugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inputBloodSugar.getText().toString().isEmpty() && !sugarType.isEmpty()) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
//                            realm.delete(Bmi.class);
                            double sugar = Double.valueOf(inputBloodSugar.getText().toString());

                            BloodSugar bs = realm.createObject(BloodSugar.class, UUID.randomUUID().toString());
                            bs.setSugar(sugar);
                            bs.setType(sugarType);
                            ToastUtil.show(BloodSugarAddActivity.this, "保存成功");
                        }
                    });
                } else {
                    ToastUtil.show(BloodSugarAddActivity.this, "Please check your input data");
                }
            }
        });

    }

    private void findView() {
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        tvSugarType = (TextView) findViewById(R.id.TextSugarType);
        inputBloodSugar = (EditText) findViewById(R.id.etBloodSugar);
        btnSaveSugar = (Button) findViewById(R.id.btnSaveSugar);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                setResult(RESULT_OK);
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_OK);
        super.onBackPressed();
    }
}
