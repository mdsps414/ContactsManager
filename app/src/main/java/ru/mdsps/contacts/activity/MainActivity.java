package ru.mdsps.contacts.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import ru.mdsps.contacts.R;
import ru.mdsps.contacts.contacts.CategoryListFragment;
import ru.mdsps.contacts.contacts.ContactListFragment;
import ru.mdsps.contacts.contacts.FavoriteListFragment;
import ru.mdsps.contacts.core.base.BaseActivity;
import ru.mdsps.contacts.settings.SettingsActivity;

public class MainActivity extends BaseActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Fragment[] mFragments = new Fragment[]{
            new CategoryListFragment(),
            new ContactListFragment(),
            new FavoriteListFragment()
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id){
            case R.id.action_settings:
                Intent settings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(settings);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment mFragment = mFragments[position];
            if(mFragment instanceof ContactListFragment){
                ContactListFragment mFr = (ContactListFragment) mFragment;
                mFr.showFastScroller(true);
                mFr.setEmptyText(R.string.form_main_recycler_fragment_empty_contacts);
            } else if(mFragment instanceof FavoriteListFragment){
                FavoriteListFragment mFr = (FavoriteListFragment) mFragment;
                mFr.showFastScroller(false);
                mFr.setEmptyText(R.string.form_main_recycler_fragment_empty_favorites);
            } else {
                CategoryListFragment mFr = (CategoryListFragment) mFragment;
                mFr.showFastScroller(false);
                mFr.setEmptyText(R.string.form_main_recycler_fragment_empty_categories);
            }
            return mFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "SECTION 1";
                case 1:
                    return "SECTION 2";
                case 2:
                    return "SECTION 3";
            }
            return null;
        }
    }
}
