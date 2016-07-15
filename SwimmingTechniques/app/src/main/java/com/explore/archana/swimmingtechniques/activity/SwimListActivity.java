package com.explore.archana.swimmingtechniques.activity;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.explore.archana.swimmingtechniques.R;
import com.explore.archana.swimmingtechniques.fragment.SwimListFragment;
import com.explore.archana.swimmingtechniques.sync.SwimTechniqueSyncAdapter;

/**
 * Created by archana on 7/8/2016.
 */
public class SwimListActivity extends AppCompatActivity {

    public static final String TAG = "SwimListActivity";
    public static final String LISTFRAGMENT = "MLTAG";
    public static final String DETAILFRAGMENT = "DFTAG";
    private SwimListFragment listFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.swim_list_layout);
        listFragment = new SwimListFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.swim_list_container, listFragment, LISTFRAGMENT).commit();
        listFragment = (SwimListFragment) getSupportFragmentManager().findFragmentByTag(LISTFRAGMENT);

        SwimTechniqueSyncAdapter.initializeSyncAdapter(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        getSupportFragmentManager().putFragment(outState, LISTFRAGMENT, listFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            listFragment = (SwimListFragment) getSupportFragmentManager().getFragment(savedInstanceState, LISTFRAGMENT);
        }
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() == 0)
            finish();
        else {
            removeCurrentFragment();
        }
    }

    public void removeCurrentFragment() {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        android.app.Fragment currentFrag = getFragmentManager().findFragmentById(R.id.swim_detail_container);
        Log.i("Current fragment", "current fragment" + currentFrag.getClass().getSimpleName());
        final boolean b = getFragmentManager().popBackStackImmediate(currentFrag.getClass().getSimpleName(), 0);
        Log.d("stack", "stack" + getFragmentManager().popBackStackImmediate());
        if (!b && getFragmentManager().findFragmentByTag(DETAILFRAGMENT) == null) {
            transaction.replace(R.id.swim_detail_container, currentFrag, DETAILFRAGMENT);
            transaction.commit();
        }
    }

}
