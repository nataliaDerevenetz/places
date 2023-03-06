package com.example.voronezh;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import java.util.List;

public class MainActivity extends Activity implements GridFragment.OnFragmentSendDataGridListener, ListFragment.OnFragmentSendDataListListener {

    FragmentManager myFragmentManager;
    GridFragment myGridFragment;
    ListFragment myListFragment;

    final static String TAG_GRID = "FRAGMENT_GRID";
    final static String TAG_LIST = "FRAGMENT_LIST";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myFragmentManager = getFragmentManager();
        myGridFragment = new GridFragment();
        myListFragment = new ListFragment();

        if (savedInstanceState == null) {
            // при первом запуске программы
            FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
            // добавляем в контейнер при помощи метода add()
            fragmentTransaction.add(R.id.container, (Fragment)myGridFragment, TAG_GRID);
            fragmentTransaction.commit();
        }

    }

    @Override
    public void onSendDataGrid(TypeObject selectedObjectType) {

        Log.d("TAG_LOG", selectedObjectType.getName());

        Bundle bundle = new Bundle();
        bundle.putSerializable(TypeObject.class.getSimpleName(), selectedObjectType);
        myListFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
       // fragmentTransaction.replace(R.id.container, (Fragment)myListFragment, TAG_LIST);

        Fragment listToShowFragment = myFragmentManager.findFragmentByTag(TAG_LIST);

        if (listToShowFragment == null) {
            fragmentTransaction.add(R.id.container, (Fragment) myListFragment, TAG_LIST);
        } else {
            myListFragment.listFragmentSetData();
        }
        fragmentTransaction.hide(myGridFragment);

        fragmentTransaction.show(myListFragment).commit();

        //fragmentTransaction.commit();

    }

    @Override
    public void onSendDataList() {
        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
        fragmentTransaction.hide(myListFragment);
        fragmentTransaction.show(myGridFragment).commit();
    }


    }