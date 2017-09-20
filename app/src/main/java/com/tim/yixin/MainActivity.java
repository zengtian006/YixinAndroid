package com.tim.yixin;

import android.app.SearchManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.appkefu.lib.interfaces.KFAPIs;
import com.appkefu.lib.service.KFXmppManager;
import com.appkefu.lib.utils.KFConstants;
import com.appkefu.smack.util.StringUtils;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;
import com.tim.yixin.activity.WebBrowserActivity;
import com.tim.yixin.fragment.FacilitiesFragment;
import com.tim.yixin.fragment.HealthFragment;
import com.tim.yixin.fragment.HomeFragment;
import com.tim.yixin.fragment.NewsFragment;
import com.tim.yixin.fragment.ProductFragment;
import com.tim.yixin.utils.LocaleUtil;

import static com.tim.yixin.app.AppConfig.web_url;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static FragmentManager fragmentManager;
    public static BottomBar bottomBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LocaleUtil.initialize(getApplicationContext(), LocaleUtil.SIMP_CHINESE);
        LocaleUtil.setLocale(getApplicationContext(), LocaleUtil.SIMP_CHINESE);
//        Log.v(TAG, "test: " + getResources().getIdentifier("Yixin", null, this.getPackageName()));

        bottomBar = (BottomBar) findViewById(R.id.bottomBar);
        bottomBar.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelected(@IdRes int tabId) {
                if (tabId == R.id.home_page) {
                    displayView(0);
                } else if (tabId == R.id.news) {
                    displayView(1);
                } else if (tabId == R.id.product) {
                    displayView(2);
//                    startActivityForResult(new Intent(MainActivity.this, AddItemActivity.class), INTENT_REQUEST_ADD_ITEM);
                } else if (tabId == R.id.health) {
                    displayView(3);
                } else if (tabId == R.id.facilities) {
                    displayView(4);
                }

            }
        });
    }

    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                break;
            case 1:
                title = getString(R.string.bt_news);
                fragment = new NewsFragment();
                break;
            case 2:
                title = getString(R.string.bt_product);
                fragment = new ProductFragment();
                break;
            case 3:
                title = getString(R.string.bt_health);
                fragment = new HealthFragment();
                break;
            case 4:
                title = getString(R.string.bt_facilities);
                fragment = new FacilitiesFragment();
                break;
            default:
                break;
        }

        if (fragment != null) {
            fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();
            // set the toolbar title
            getSupportActionBar().setTitle(title);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setLogo(R.drawable.ic_top_logo);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.v(TAG, query);
                Intent intent = new Intent(MainActivity.this, WebBrowserActivity.class);
                String url = web_url + "?s=" + query;
                intent.putExtra("url", url);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);

        switch (item.getItemId()) {
//            case R.id.lang_en:
//                startActivity(intent);
//                LocaleUtil.setLocale(getApplicationContext(), LocaleUtil.ENGLISH);
//                return true;
//
//            case R.id.lang_cn:
//                startActivity(intent);
//                LocaleUtil.setLocale(getApplicationContext(), LocaleUtil.SIMP_CHINESE);
//                return true;
            case R.id.contact_doctor:
                KFAPIs.startChat(this,
                        "19871010", // 1. 客服工作组ID(请务必保证大小写一致)，请在管理后台分配
                        "在线医生", // 2. 会话界面标题，可自定义
                        null, // 3. 附加信息，在成功对接客服之后，会自动将此信息发送给客服;
                        // 如果不想发送此信息，可以将此信息设置为""或者null
                        false, // 4. 是否显示自定义菜单,如果设置为显示,请务必首先在管理后台设置自定义菜单,
                        // 请务必至少分配三个且只分配三个自定义菜单,多于三个的暂时将不予显示
                        // 显示:true, 不显示:false
                        0, // 5. 默认显示消息数量
                        //修改SDK自带的头像有两种方式，1.直接替换appkefu_message_toitem和appkefu_message_fromitem.xml里面的头像，2.传递网络图片自定义
                        "http://47.90.33.185/PHP/XMPP/gyfd/chat/web/img/user-avatar.png",//6. 修改默认客服头像，如果不想修改默认头像，设置此参数为null
                        "http://47.90.33.185/PHP/XMPP/gyfd/chat/web/img/kefu-avatar.png",      //7. 修改默认用户头像, 如果不想修改默认头像，设置此参数为null
                        false, // 8. 默认机器人应答
                        false,  //9. 是否强制用户在关闭会话的时候 进行“满意度”评价， true:是， false:否
                        null
                );
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private BroadcastReceiver mXmppreceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //监听：连接状态
            if (action.equals(KFConstants.ACTION_XMPP_CONNECTION_CHANGED)) {//监听链接状态
                updateStatus(intent.getIntExtra("new_state", 0));
            }
            //监听：即时通讯消息
            else if (action.equals(KFConstants.ACTION_XMPP_MESSAGE_RECEIVED))//监听消息

            {
                String body = intent.getStringExtra("body");
                String from = StringUtils.parseName(intent.getStringExtra("from"));
            } else if (action.equals(KFConstants.ACTION_XMPP_WORKGROUP_ONLINESTATUS))

            {
                String onlineStatus = intent.getStringExtra("onlinestatus");
//        KFLog.d("客服工作组:" + onlineStatus);//online：在线；offline: 离线
                if (onlineStatus.equals("online")) {
                } else {
                }
            }
        }
    };

    private void updateStatus(int status) {
        switch (status) {
            case KFXmppManager.CONNECTED:
//                mTitle.setText("微客服(客服Demo)");
                //在成功建立连接之后, 查询客服工作组在线状态，返回结果在BroadcastReceiver中返回
                KFAPIs.checkKeFuIsOnlineAsync("19871010", this);
                break;
            case KFXmppManager.DISCONNECTED:
//                mTitle.setText("微客服(客服Demo)(未连接)");
                break;
            case KFXmppManager.CONNECTING:
//                mTitle.setText("微客服(客服Demo)(登录中...)");
                break;
            case KFXmppManager.DISCONNECTING:
//                mTitle.setText("微客服(客服Demo)(登出中...)");
                break;
            case KFXmppManager.WAITING_TO_CONNECT:
            case KFXmppManager.WAITING_FOR_NETWORK:
//                mTitle.setText("微客服(客服Demo)(登录中)");
                break;
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter = new IntentFilter();
        //监听网络连接变化情况
        intentFilter.addAction(KFConstants.ACTION_XMPP_CONNECTION_CHANGED);
        //监听消息
        intentFilter.addAction(KFConstants.ACTION_XMPP_MESSAGE_RECEIVED);
        //工作组在线状态
        intentFilter.addAction(KFConstants.ACTION_XMPP_WORKGROUP_ONLINESTATUS);
        registerReceiver(mXmppreceiver, intentFilter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // 登录方式
        KFAPIs.visitorLogin(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mXmppreceiver != null) {
            unregisterReceiver(mXmppreceiver);
            mXmppreceiver = null;
        }
    }
}