package com.example.voronezh;

import android.content.Context;
import android.graphics.Outline;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.ViewOutlineProvider;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;


import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GridFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GridFragment extends Fragment {
    interface OnFragmentSendDataGridListener {
        void onSendDataGrid(TypeObject data);
    }

    private OnFragmentSendDataGridListener fragmentSendDataGridListener;

    ArrayList<TypeObject> objects = new ArrayList<TypeObject>();
    GridView objectsGrid;

    String[] city = { "Воронежская область", "Воронеж", "Богучар", "Борисоглебск", "Бобров"};

    private static final String TAG_LOG = "myLogs";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters

    public GridFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static GridFragment newInstance(String param1, String param2) {
        GridFragment fragment = new GridFragment();
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
            fragmentSendDataGridListener = (OnFragmentSendDataGridListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment



        // начальная инициализация списка
        setInitialData();
        View view = inflater.inflate(R.layout.fragment_grid, container, false);



        Spinner spinnerCity = (Spinner) view.findViewById(R.id.spinnerCity);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, city);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinnerCity.setAdapter(adapter);

//****************************
        ImageView imgBanner = (ImageView) view.findViewById(R.id.imageBanner1);

        ViewOutlineProvider provider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int curveRadius = 24;
                outline.setRoundRect(0, 0, view.getWidth(), (view.getHeight()), curveRadius);
            }
        };

        imgBanner.setOutlineProvider(provider);
        imgBanner.setClipToOutline(true);

        
        objectsGrid = (GridView) view.findViewById(R.id.gridviewTypeObject);

        // создаем адаптер
        ObjectTypeAdapter objectAdapter = new ObjectTypeAdapter(getContext(), R.layout.cell_grid, objects);
        // устанавливаем адаптер
        objectsGrid.setAdapter(objectAdapter);


        objectsGrid.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                // получаем выбранный элемент
                TypeObject selectedObject = (TypeObject) parent.getItemAtPosition(position);
                Log.d(TAG_LOG, selectedObject.getName());

                fragmentSendDataGridListener.onSendDataGrid(selectedObject);
                // Посылаем данные Activity
                //fragmentSendDataListener.onSendData(selectedItem);
            }
        });

        return view;
        //return inflater.inflate(R.layout.fragment_grid, container, false);
    }

    private void setInitialData(){

        objects.add(new TypeObject ("Музеи Воронежа", 1, R.drawable.type1));
        objects.add(new TypeObject ("Музеи области ", 2, R.drawable.type2));
        objects.add(new TypeObject ("Театры",3, R.drawable.type3));
        objects.add(new TypeObject ("Храмы",4, R.drawable.type4));
        objects.add(new TypeObject ("Парки",5, R.drawable.type5));
        objects.add(new TypeObject ("Памятники природы",6, R.drawable.type6));
        objects.add(new TypeObject ("Санатории",7, R.drawable.type7));
        objects.add(new TypeObject ("Активный отдых", 8, R.drawable.type8));
        objects.add(new TypeObject ("Памятники", 9, R.drawable.type9));
        objects.add(new TypeObject ("Достопримечательности", 10, R.drawable.type10));
        objects.add(new TypeObject ("Спорт", 11, R.drawable.type11));
        objects.add(new TypeObject ("Развлечения", 12, R.drawable.type12));

    }
}