package com.tim.yixin.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tim.yixin.R;
import com.tim.yixin.model.Bmi;
import com.tim.yixin.utils.ToastUtil;

import java.text.DecimalFormat;
import java.util.UUID;

import io.realm.Realm;
import io.realm.RealmResults;

public class BMICalcActivity extends AppCompatActivity {
    final private static String TAG = BMICalcActivity.class.getSimpleName();

    EditText inputHeight, inputWeight;
    Button btnCalcBMI, btnResetBMI, btnSaveBMI;
    TextView tvBMILevel, tvBMIRange, tvBMIDesc, tvBMINum;
    private Realm realm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalc);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        realm = Realm.getDefaultInstance();

        findView();
        setView();
        setlistner();
    }

    private void setView() {
        btnSaveBMI.setVisibility(View.GONE);
    }

    private void setlistner() {
        btnCalcBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double height;
                double weight;
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                try {
                    height = Double.valueOf(inputHeight.getText().toString());
                    weight = Double.valueOf(inputWeight.getText().toString());
                    double bmi = weight / ((height / 100) * (height / 100));
                    DecimalFormat df = new DecimalFormat("0.00");

                    tvBMINum.setText("你的BMI指数: " + String.valueOf(df.format(bmi)));
                    tvBMILevel.setTextColor(ContextCompat.getColor(BMICalcActivity.this, R.color.red));
                    tvBMIRange.setTextColor(ContextCompat.getColor(BMICalcActivity.this, R.color.red));
                    tvBMIDesc.setTextColor(ContextCompat.getColor(BMICalcActivity.this, R.color.red));
                    if (bmi < 18.5) {
                        tvBMILevel.setText("偏瘦");
                        tvBMIRange.setText("< 18.5");
                        tvBMIDesc.setText("您的BMI显示您的体重有些过轻喔!要注意均衡饮食了!");
                    } else if (bmi >= 18.5 && bmi <= 22.9) {
                        tvBMILevel.setTextColor(ContextCompat.getColor(BMICalcActivity.this, R.color.green));
                        tvBMIRange.setTextColor(ContextCompat.getColor(BMICalcActivity.this, R.color.green));
                        tvBMIDesc.setTextColor(ContextCompat.getColor(BMICalcActivity.this, R.color.green));
                        tvBMILevel.setText("正常");
                        tvBMIRange.setText("18.5 - 22.9");
                        tvBMIDesc.setText("喔！您的身材不错，真是令人羡慕呢，请持续保持如此状态吧！");
                    } else if (bmi > 22.9 && bmi <= 24.9) {
                        tvBMILevel.setText("偏胖");
                        tvBMIRange.setText("23 - 24.9");
                        tvBMIDesc.setText("有点稍稍小过重了些喔，要开始多注意饮食起居和适度运动了！");
                    } else if (bmi > 24.9 && bmi <= 29.9) {
                        tvBMILevel.setText("重度肥胖");
                        tvBMIRange.setText("25 - 29.9");
                        tvBMIDesc.setText("您已经算是圆胖的身材喽!要开始想办法不要再过胖了");
                    } else if (bmi > 29.9) {
                        tvBMILevel.setText("极重度肥胖");
                        tvBMIRange.setText(">= 30");
                        tvBMIDesc.setText("您的体重已属过度肥胖喔!该注意一下日常是否有暴饮暴食的行为呢？");
                    }
                    btnSaveBMI.setVisibility(View.VISIBLE);
//                    ToastUtil.show(BMICalcActivity.this, String.valueOf(bmi));
                } catch (Exception e) {
                    ToastUtil.show(BMICalcActivity.this, "Please check your input data");
                }

            }
        });

        btnResetBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSaveBMI.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                tvBMINum.setText("你的BMI指数");
                tvBMILevel.setText("");
                tvBMIRange.setText("");
                tvBMIDesc.setText("");
                inputHeight.setText("");
                inputWeight.setText("");
                inputHeight.requestFocus();
            }
        });
        btnSaveBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!inputWeight.getText().toString().isEmpty() && !inputHeight.getText().toString().isEmpty()) {
                    realm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
//                            realm.delete(Bmi.class);
                            double height = Double.valueOf(inputHeight.getText().toString());
                            double weight = Double.valueOf(inputWeight.getText().toString());
                            double bmi = weight / ((height / 100) * (height / 100));
                            DecimalFormat df = new DecimalFormat("0.00");

                            Bmi b = realm.createObject(Bmi.class, UUID.randomUUID().toString());
                            b.setBmi(Double.valueOf(df.format(bmi)));
                            ToastUtil.show(BMICalcActivity.this, "保存成功");
                        }
                    });
                }
            }
        });
    }

    private void findView() {
//        realm.delete(Bmi.class);
        RealmResults<Bmi> bmis = realm.where(Bmi.class).findAll();
        for (Bmi b : bmis) {
            Log.v(TAG, "BMI ID:" + b.getId());
            Log.v(TAG, "BMI:" + b.getBmi());
            Log.v(TAG, "BMI Created:" + b.getCreatedAt());
        }

        inputHeight = (EditText) findViewById(R.id.input_height);
        inputWeight = (EditText) findViewById(R.id.input_weight);
        btnCalcBMI = (Button) findViewById(R.id.btn_calc_bmi);
        btnResetBMI = (Button) findViewById(R.id.btn_reset_bmi);
        tvBMIDesc = (TextView) findViewById(R.id.textBMIDesc);
        tvBMINum = (TextView) findViewById(R.id.textBMINum);
        tvBMILevel = (TextView) findViewById(R.id.textBMILevel);
        tvBMIRange = (TextView) findViewById(R.id.textBMIRange);
        btnSaveBMI = (Button) findViewById(R.id.btnSaveBMI);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close(); // Remember to close Realm when done.
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
