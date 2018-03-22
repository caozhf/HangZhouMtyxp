package com.mtyxp.hangzhoumtyxp.controller.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mtyxp.hangzhoumtyxp.R;
import com.mtyxp.hangzhoumtyxp.controller.fragment.CustomerServiceFragment;
import com.mtyxp.hangzhoumtyxp.controller.fragment.ExpressFragment;
import com.mtyxp.hangzhoumtyxp.controller.fragment.PersonalInfoFragment;
import com.mtyxp.hangzhoumtyxp.controller.fragment.StatisticFragment;
import com.mtyxp.hangzhoumtyxp.utils.GetVersionBroadcast;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class OperateActivity extends AppCompatActivity implements OnMenuItemClickListener, OnMenuItemLongClickListener {

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private static Activity operate;
    private FragmentTransaction transaction;
    private Fragment mfragment = null;
    private long mkeyTime;

    private int operate_local_version_num;
    private int operate_serve_version_num;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operate);

        CompareVersionNum();

        operate = this;
        fragmentManager = getSupportFragmentManager();
        initToolbar();
        initMenuFragment();
        addFragment(new ExpressFragment(), true, R.id.containers);
    }

    private void CompareVersionNum() {
        operate_local_version_num = GetVersionBroadcast.local_version_num;
        operate_serve_version_num = GetVersionBroadcast.serve_version_num;
        System.out.println("ver"+operate_local_version_num+"vert"+operate_serve_version_num);
    }

    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        menuParams.setAnimationDelay(30);
        menuParams.setAnimationDuration(30);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    private List<MenuObject> getMenuObjects() {
        // You can use any [resource, bitmap, drawable, color] as image:
        // item.setResource(...)
        // item.setBitmap(...)
        // item.setDrawable(...)
        // item.setColor(...)
        // You can set image ScaleType:
        // item.setScaleType(ScaleType.FIT_XY)
        // You can use any [resource, drawable, color] as background:
        // item.setBgResource(...)
        // item.setBgDrawable(...)
        // item.setBgColor(...)
        // You can use any [color] as text color:
        // item.setTextColor(...)
        // You can set any [color] as divider color:
        // item.setDividerColor(...)

        List<MenuObject> menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.icn_close);

        MenuObject send = new MenuObject("配送鲜奶");
        send.setResource(R.drawable.icn_1);

        MenuObject like = new MenuObject("数据统计");
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.icn_2);
        like.setBitmap(b);

        MenuObject addFr = new MenuObject("个人信息");
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.icn_3));
        addFr.setDrawable(bd);

        MenuObject addFav = new MenuObject("客服联系");
        addFav.setResource(R.drawable.icn_4);

        MenuObject block = new MenuObject("退出登录");
        block.setResource(R.drawable.icn_5);

        menuObjects.add(close);
        menuObjects.add(send);
        menuObjects.add(like);
        menuObjects.add(addFr);
        menuObjects.add(addFav);
        menuObjects.add(block);
        return menuObjects;
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setHomeButtonEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        mToolbar.setNavigationIcon(R.drawable.btn_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BmobUser.logOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
            }
        });
        mToolBarTextView.setText("美天优鲜配配送服务");
    }

    protected void addFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        mfragment = fragment;
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            transaction = fragmentManager.beginTransaction();
            transaction.replace(containerId, fragment, backStackName)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null) {
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mMenuDialogFragment != null && mMenuDialogFragment.isAdded()) {
            mMenuDialogFragment.dismiss();
        } else {
            finish();
        }
    }

    @Override
    public void onMenuItemClick(View clickedView, int position) {

        switch (position){
            case 1:
                addFragment(new ExpressFragment(), true, R.id.containers);
                break;
            case 2:
                addFragment(new StatisticFragment(), true, R.id.containers);
                break;
            case 3:
                addFragment(new PersonalInfoFragment(),true,R.id.containers);
                break;
            case 4:
                addFragment(new CustomerServiceFragment(),true,R.id.containers);
                break;
            case 5:
                BmobUser.logOut();
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                finish();
                break;
        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {
        Toast.makeText(this, "Long clicked on position: " + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        System.out.println("operate_onDestroy");
        transaction.remove(mfragment);
        BmobUser.logOut();
        finish();


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (operate_local_version_num<operate_serve_version_num){
            BmobUser.logOut();
            finish();
        }
    }


        public boolean onKeyDown(int keyCode, KeyEvent event) {

        if(keyCode == KeyEvent.KEYCODE_BACK){
            if((System.currentTimeMillis() - mkeyTime)>2000){
                mkeyTime = System.currentTimeMillis();
                Toast.makeText(this,"Press the exit program again!",Toast.LENGTH_SHORT).show();
            }else {
                BmobUser.logOut();
                finish();
            }
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

}
