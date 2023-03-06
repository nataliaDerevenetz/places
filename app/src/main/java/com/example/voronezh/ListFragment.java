package com.example.voronezh;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import android.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.widget.SwitchCompat;

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
    EditText userFilter;
    SwitchCompat switchAccessibility;
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

    public void listFragmentSetDataFilter(String filter) {
        Log.d("filter",filter);
        DatabaseAdapter adapter = new DatabaseAdapter(getContext());
        adapter.open();

        List<Object> objects = adapter.getObjectsFilter(typeObject.getIdType(),filter);

        for(Object object : objects){

            String filename = String.valueOf(object.getId()) + ".png";
            try(InputStream inputStream = getContext().getAssets().open(filename)){
                object.setImgUrl(filename);
            }
            catch (IOException e){
                filename = String.valueOf(object.getId()) + ".jpg";
                try(InputStream inputStream = getContext().getAssets().open(filename)){
                    object.setImgUrl(filename);
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
                // Посылаем данные Activity
                fragmentSendDataListListener.onSendDataListObject(selectedObject);
          }
        });
        //закрытие адаптера базы
        adapter.close();
    }
    public void listFragmentSetData() {
        userFilter.setText("");
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
            }
            catch (IOException e){
                filename = String.valueOf(object.getId()) + ".jpg";
                try(InputStream inputStream = getContext().getAssets().open(filename)){
                    object.setImgUrl(filename);
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
        userFilter = (EditText)view.findViewById(R.id.objectFilter);
        switchAccessibility = (SwitchCompat)view.findViewById(R.id.switchAccessibility);
        switchAccessibility.setText("Доступная среда");
        // установка слушателя изменения текста
        userFilter.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) { }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }
            // при изменении текста выполняем фильтрацию
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                listFragmentSetDataFilter(s.toString());
                //Log.d("sss","sss");
                //userAdapter.getFilter().filter(s.toString());
            }
        });

        //скрытие клавиатуры после нажатия enter
        userFilter.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT) {
                    InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                }
                return false;
            }
        });

        //скрытие клавиатуры еcли TextEdit теряет фокус
        userFilter.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager inputMethodManager =(InputMethodManager)getActivity().getSystemService(getActivity().INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
                }
            }
        });



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