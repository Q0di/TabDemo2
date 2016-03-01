package com.coda.tabdemo2;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import java.util.HashMap;


public class MainActivity extends FragmentActivity implements TabHost.OnTabChangeListener{

    private TabHost mTabHost;
    private HashMap mapTabInfo;
    private TabInfo mLastTab;

    private class TabInfo{

        private String tag;
        private Class clss;
        private Bundle args;
        private Fragment fragment;


        public TabInfo(String tag, Class clss, Bundle args) {
            this.clss = clss;
            this.tag = tag;
            this.args = args;
        }
    }

    public class TabFactory implements TabHost.TabContentFactory{
        private final Context mContext;

        public TabFactory (Context context){

            mContext = context;
        }

        public View createTabContent (String tag){

            View v = new View(mContext);
            v.setMinimumWidth(0);
            v.setMinimumHeight(0);

            return v;
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeTabHost(savedInstanceState);
        if(savedInstanceState != null){

            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab"));
        }

    }

    public void initializeTabHost(Bundle args){

        mTabHost = (TabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup();
        TabInfo tabInfo =  null;

        TabSpec photoSpec= mTabHost.newTabSpec("Photos");
        photoSpec.setIndicator("Photos");
        tabInfo = new TabInfo("Photos", PhotosFragment.class,args);
        mTabHost.addTab(photoSpec);
        this.mapTabInfo.put(tabInfo.tag, tabInfo);

        TabSpec songSpec= mTabHost.newTabSpec("Song");
        photoSpec.setIndicator("Song");
        tabInfo = new TabInfo("Song", SongFragment.class,args);
        mTabHost.addTab(songSpec);
        this.mapTabInfo.put(tabInfo.tag, tabInfo);

        TabSpec videoSpec= mTabHost.newTabSpec("Video");
        photoSpec.setIndicator("Video");
        tabInfo = new TabInfo("Video", VideoFragment.class,args);
        mTabHost.addTab(videoSpec);
        this.mapTabInfo.put(tabInfo.tag, tabInfo);

        //default to first tab
        this.onTabChanged("Photos");
        mTabHost.setOnTabChangedListener(this);
    }

    public void onTabChanged (String tag){

        TabInfo newTab = (TabInfo) this.mapTabInfo.get(tag);
        if(mLastTab != newTab){

            FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
            if(mLastTab != null){
                if(mLastTab.fragment != null){
                    ft.detach(mLastTab.fragment);

                }

                if(newTab != null){
                    if(newTab.fragment == null){
                        newTab.fragment = Fragment.instantiate(this,newTab.clss.getName(),newTab.args);
                        ft.add(android.R.id.tabcontent, newTab.fragment, newTab.tag);

                    }else{
                        ft.attach(newTab.fragment);

                    }

                }

                mLastTab = newTab;
                ft.commit();
                this.getSupportFragmentManager().executePendingTransactions();
            }
        }
    }
}
