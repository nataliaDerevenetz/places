package com.example.voronezh;

import android.content.Context;
import android.graphics.drawable.Drawable;
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

import java.io.IOException;
import java.io.InputStream;
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
    Button buttonBack;
    ArrayAdapter<Object> arrayAdapter;

    interface OnFragmentSendDataListListener {
        void onSendDataListBack();
        void onSendDataListObject(Object data);
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

        for(Object object : objects){

            String filename = String.valueOf(object.getId()) + ".png";
            try(InputStream inputStream = getContext().getAssets().open(filename)){
                object.setImgUrl(filename);
                //Drawable drawable = Drawable.createFromStream(inputStream, null);
                //object.setImg(drawable);
               // Log.d("filename",filename);
            }
            catch (IOException e){
                filename = String.valueOf(object.getId()) + ".jpg";
                try(InputStream inputStream = getContext().getAssets().open(filename)){
                    object.setImgUrl(filename);
                    //Drawable drawable = Drawable.createFromStream(inputStream, null);
                    //object.setImg(drawable);
                   // Log.d("filename",filename);
                } catch (IOException e_jpg) {e_jpg.printStackTrace();}
               // e.printStackTrace();
            }
        }

        ObjectAdapter objectAdapter = new ObjectAdapter(getContext(), R.layout.item_list, objects);
        // устанавливаем адаптер
        objectsList.setAdapter(objectAdapter);

        objectsList.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // получаем выбранный элемент
                Object selectedObject = (Object) parent.getItemAtPosition(position);
                Log.d("objectsListClick", selectedObject.getName());

                fragmentSendDataListListener.onSendDataListObject(selectedObject);

                // Посылаем данные Activity
                //fragmentSendDataGridListener.onSendDataGrid(selectedObject);
                // Посылаем данные Activity
                //fragmentSendDataListener.onSendData(selectedItem);
            }
        });

        //закрытие адаптера базы
        adapter.close();
    }


    // TODO: Rename and change types and number of parameters
    public static ListFragment newInstance(TypeObject typeObj) {
        ListFragment fragment = new ListFragment();
        Bundle args = new Bundle();
        args.putSerializable(TypeObject.class.getSimpleName(),typeObj);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_list, container, false);

        text = (TextView) view.findViewById(R.id.textFragment);
        objectsList = (ListView) view.findViewById(R.id.objectsList);

        listFragmentSetData();

        buttonBack = (Button) view.findViewById(R.id.buttonBack);
        buttonBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                fragmentSendDataListListener.onSendDataListBack();
            }
        });

        return view;
    }


}