package com.example.voronezh;

import android.content.Context;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListFragment extends Fragment {

    DatabaseHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    TypeObject typeObject;
    TextView text;
    ListView objectsList;
    ArrayAdapter<Object> arrayAdapter;

    interface OnFragmentSendDataListListener {
        void onSendDataList();
    }

    private ListFragment.OnFragmentSendDataListListener fragmentSendDataListListener;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListFragment() {
        // Required empty public constructor
    }

    public void listFragmentSetData() {
        typeObject = (TypeObject) getArguments().getSerializable(TypeObject.class.getSimpleName());
        text.setText(typeObject.getName());
        Log.d("TAG_LIST", typeObject.getName());

        DatabaseAdapter adapter = new DatabaseAdapter(getContext());
        adapter.open();

        List<Object> objects = adapter.getObjects(typeObject.getIdType());

        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, objects);
        objectsList.setAdapter(arrayAdapter);

        adapter.close();
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(String param1, String param2) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentSendDataListListener = (ListFragment.OnFragmentSendDataListListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            typeObject = (TypeObject) getArguments().getSerializable(TypeObject.class.getSimpleName());

            Log.d("TAG_LIST", typeObject.getName());

            //     mParam1 = getArguments().getString(ARG_PARAM1);
            //     mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        /*
        DatabaseAdapter adapter = new DatabaseAdapter(getContext());
        adapter.open();

        List<Object> objects = adapter.getObjects(typeObject.getIdType());

        arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, objects);
        objectsList.setAdapter(arrayAdapter);

        adapter.close();

         */
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_list, container, false);

        View view = inflater.inflate(R.layout.fragment_list, container, false);


        text = (TextView) view.findViewById(R.id.textFragment);

        objectsList = (ListView) view.findViewById(R.id.objectsList);
        listFragmentSetData();
        //text.setText(typeObject.getName());



        Button buttonBack = (Button) view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                fragmentSendDataListListener.onSendDataList();
            }
        });




        return view;
    }


}