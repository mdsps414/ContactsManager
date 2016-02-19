package ru.mdsps.contacts.activity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import ru.mdsps.contacts.R;
import ru.mdsps.contacts.core.base.BaseActivity;
import ru.mdsps.contacts.core.base.BaseRecyclerFragment;
import ru.mdsps.contacts.fragments.ContactsListFragment;
import ru.mdsps.contacts.fragments.FavoritesListFragment;
import ru.mdsps.contacts.fragments.GroupsListFragment;

public class MainActivity extends BaseActivity {

    private Toolbar mToolbar;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private FloatingActionButton mFab;

    private int[] mTabIcons = new int[]{
            R.drawable.vector_ic_group_white,
            R.drawable.vector_ic_person_white,
            R.drawable.vector_ic_star_white
    };

    private int[] mFabIcons = new int[]{
            R.drawable.vector_ic_group_add_white,
            R.drawable.vector_ic_person_add_white
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mTabLayout.setupWithViewPager(mViewPager);

        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPagerChangeListener());

    }

    @Override
    public void onStart() {
        super.onStart();

        for (int i = 0; i < mTabLayout.getTabCount(); i++) {
            TabLayout.Tab mTab = mTabLayout.getTabAt(i);
            mTab.setIcon(mTabIcons[i]);
        }
        mViewPager.setCurrentItem(1);
        setupFab(1);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.action_about:
                Intent aboutIntent = new Intent(this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupFab(int fabMode) {
        switch (fabMode) {
            case 0:
                mFab.setImageResource(mFabIcons[0]);
                mFab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_amber_700)));
                mFab.setRippleColor(ContextCompat.getColor(this, R.color.md_white_1000));
                break;
            case 1:
                mFab.setImageResource(mFabIcons[1]);
                mFab.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.md_blue_700)));
                mFab.setRippleColor(ContextCompat.getColor(this, R.color.md_white_1000));
                break;
            case 2:
                mFab.setVisibility(View.GONE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private class ViewPagerChangeListener implements ViewPager.OnPageChangeListener {

        private int mPosition;

        public ViewPagerChangeListener() {
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            if (positionOffsetPixels == 0) {
                mPosition = position;
            }
        }

        @Override
        public void onPageSelected(int position) {
            mPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            if (state == 0) {
                setupFab(mPosition);
                if(mPosition < 2){
                    mFab.show();
                } else {
                    mFab.hide();
                }
            } else if (state == 2) {
                mFab.hide();
            }
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseRecyclerFragment mFragment = null;
            switch (position) {
                case 0:
                    mFragment = new GroupsListFragment();
                    mFragment.setEmptyText(R.string.fms_all_empty_groups);
                    mFragment.setShowFastScroller(false);
                    break;
                case 1:
                    mFragment = new ContactsListFragment();
                    mFragment.setEmptyText(R.string.fms_all_empty_contacts);
                    mFragment.setShowFastScroller(true);
                    break;
                case 2:
                    mFragment = new FavoritesListFragment();
                    mFragment.setEmptyText(R.string.fms_all_empty_favorites);
                    mFragment.setShowFastScroller(false);
                    break;
            }
            return mFragment;
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }


}
