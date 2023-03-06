package com.example.voronezh;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GridFragment.OnFragmentSendDataGridListener, ListFragment.OnFragmentSendDataListListener, ObjectFragment.OnFragmentSendDataObjectListener {

    FragmentManager myFragmentManager;
    GridFragment myGridFragment;
    ListFragment myListFragment;
    ObjectFragment myObjectFragment;

    final static String TAG_GRID = "FRAGMENT_GRID";
    final static String TAG_LIST = "FRAGMENT_LIST";

    final static String TAG_OBJECT = "FRAGMENT_OBJECT";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //скрывает название программы
        getSupportActionBar().hide();
        setContentView(R.layout.activity_main);

        myFragmentManager = getFragmentManager();
        myGridFragment = new GridFragment();
        myListFragment = new ListFragment();
        myObjectFragment = new ObjectFragment();

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
        //создает или показывает фрагмент со списком объектов одного типа, типа музеи

        Log.d("TAG_LOG", selectedObjectType.getName());

        Bundle bundle = new Bundle();
        bundle.putSerializable(TypeObject.class.getSimpleName(), selectedObjectType);
        myListFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
        Fragment listToShowFragment = myFragmentManager.findFragmentByTag(TAG_LIST);

        if (listToShowFragment == null) {
            fragmentTransaction.add(R.id.container, (Fragment) myListFragment, TAG_LIST);
        } else {
            myListFragment.listFragmentSetData();
        }
        fragmentTransaction.hide(myGridFragment);

        fragmentTransaction.show(myListFragment).commit();

    }

    @Override
    public void onSendDataListBack() {
        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
        fragmentTransaction.hide(myListFragment);
        fragmentTransaction.show(myGridFragment).commit();
    }

    @Override
    public void onSendDataObjectBack() {
        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
        fragmentTransaction.hide(myObjectFragment);
        fragmentTransaction.show(myListFragment).commit();
    }

    @Override
    public void onSendDataListObject(Object selectedObject) {
        // создает или показывает фрагмент с данными конкретного объекта
        Bundle bundle = new Bundle();
        bundle.putSerializable(Object.class.getSimpleName(), selectedObject);
        myObjectFragment.setArguments(bundle);

        FragmentTransaction fragmentTransaction = myFragmentManager.beginTransaction();
        Fragment listToObjectFragment = myFragmentManager.findFragmentByTag(TAG_OBJECT);

        if (listToObjectFragment == null) {
            fragmentTransaction.add(R.id.container, (Fragment) myObjectFragment, TAG_OBJECT);
        } else {
            myObjectFragment.objectFragmentSetData();
        }
        fragmentTransaction.hide(myListFragment);

        fragmentTransaction.show(myObjectFragment).commit();

    }



}