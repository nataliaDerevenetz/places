package com.example.voronezh;

import android.content.Context;
import android.graphics.PointF;
import android.os.Bundle;

import android.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKit;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.layers.ObjectEvent;
import com.yandex.mapkit.logo.Alignment;
import com.yandex.mapkit.logo.HorizontalAlignment;
import com.yandex.mapkit.logo.VerticalAlignment;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.map.IconStyle;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.mapview.MapView;
import com.yandex.mapkit.user_location.UserLocationLayer;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.yandex.runtime.image.ImageProvider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ObjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ObjectFragment extends Fragment {
    private Object object;
    TextView text;
    TextView text2;
    Button buttonBackToList;
    private final String MAPKIT_API_KEY = "f0f2e1b2-28a8-49f5-a12f-fb3164feec22";
    public MapView mapview;
    public UserLocationLayer userLocationLayer;

    interface OnFragmentSendDataObjectListener {
        void onSendDataObjectBack();
    }

    private ObjectFragment.OnFragmentSendDataObjectListener fragmentSendDataObjectListener;

    public ObjectFragment() {
        // Required empty public constructor
    }

    public void objectFragmentSetData() {
    // заполняет фрагмент объекта
        object = (Object) getArguments().getSerializable(Object.class.getSimpleName());
 //       Log.d("object",object.getDescription());
        text2.setText(object.getDescription());

        //получение координат для отрисовки на карте из Object
        String[] points = null;
        points = object.getLocation().split(",");
        Point pointObject = new Point(Double.valueOf(points[0]),Double.valueOf(points[1]));

        mapview.getMap().move(
                new CameraPosition(pointObject, 16.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        //удаление всех меток с карты
        mapview.getMap().getMapObjects().clear();
        //установка и позиционирование метки объекта относительно низа середины картинки
        IconStyle istyle= new IconStyle();
        istyle.setAnchor(new PointF(0.5f,1.0f));
        PlacemarkMapObject mark = mapview.getMap().getMapObjects().addPlacemark(pointObject, ImageProvider.fromResource(getContext(), R.drawable.lable));
        mark.setIconStyle(istyle);
        //установка логотипа яндекс в правый верхний угол
        mapview.getMap().getLogo().setAlignment(new Alignment(HorizontalAlignment.RIGHT,VerticalAlignment.TOP));

    }



    // TODO: Rename and change types and number of parameters
    public static ObjectFragment newInstance(Object obj) {

        ObjectFragment fragment = new ObjectFragment();
        Bundle args = new Bundle();
        args.putSerializable(Object.class.getSimpleName(),obj);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            object = (Object) getArguments().getSerializable(Object.class.getSimpleName());
        }

      //  MapKitFactory.initialize(this.getContext());
        MapKitFactory.setApiKey(MAPKIT_API_KEY); // Установить  ключ API
        MapKitFactory.initialize(this.getContext());
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            fragmentSendDataObjectListener = (ObjectFragment.OnFragmentSendDataObjectListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public void onStop() {
        mapview.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();

        String[] points = null;
        points = object.getLocation().split(",");
        Point pointObject = new Point(Double.valueOf(points[0]),Double.valueOf(points[1]));

        MapKitFactory.getInstance().onStart();
        mapview.onStart();
/*
        mapview.getMap().move(
                new CameraPosition(pointObject, 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
*/
        mapview.getMap().setRotateGesturesEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_object, container, false);

        mapview = (MapView) view.findViewById(R.id.mapview);

        text2 = (TextView) view.findViewById(R.id.text_bottom_sheet);

        objectFragmentSetData();

        buttonBackToList = (Button) view.findViewById(R.id.buttonBackToList);
        buttonBackToList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                fragmentSendDataObjectListener.onSendDataObjectBack();
            }
        });

        return view;
    }
}