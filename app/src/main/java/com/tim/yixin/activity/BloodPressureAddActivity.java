package com.tim.yixin.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tim.yixin.R;
import com.tim.yixin.model.BloodPressure;
import com.tim.yixin.utils.ToastUtil;

import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class BloodPressureAddActivity extends AppCompatActivity {
    private final static String TAG = BloodPressureAddActivity.class.getSimpleName();

    Button btnSavePressure;
    EditText inputSBP, inputDBP, inputHR;

    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_pressure_add);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");

        realm = Realm.getDefaultInstance();
        findView();
        setListner();
    }

    private void setListner() {
        btnSavePressure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inputSBP.getText().toString().isEmpty() && !inputDBP.getText().toString().isEmpty() && !inputHR.getText().toString().isEmpty()) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
//                            realm.delete(Bmi.class);
                            double high = Double.valueOf(inputSBP.getText().toString());
                            double low = Double.valueOf(inputDBP.getText().toString());
                            double heart_rate = Double.valueOf(inputHR.getText().toString());

                            BloodPressure bp = realm.createObject(BloodPressure.class, UUID.randomUUID().toString());
                            bp.setHigh(high);
                            bp.setLow(low);
                            bp.setHeart_rate(heart_rate);
                            ToastUtil.show(BloodPressureAddActivity.this, "保存成功");
                        }
                    });
                } else {
                    ToastUtil.show(BloodPressureAddActivity.this, "Please check your input data");
                }
            }
        });
    }

    private void findView() {
        btnSavePressure = (Button) findViewById(R.id.btnSavePressure);
        inputSBP = (EditText) findViewById(R.id.etSBP);
        inputDBP = (EditText) findViewById(R.id.etDBP);
        inputHR = (EditText) findViewById(R.id.etHR);
        RealmResults<BloodPressure> bps = realm.where(BloodPressure.class).findAll();
        for (BloodPressure bp : bps) {
            Log.v(TAG, "BP: " + bp.getHeart_rate());
            Log.v(TAG, "BP: " + bp.getHigh());
            Log.v(TAG, "BP: " + bp.getLow());
        }
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
