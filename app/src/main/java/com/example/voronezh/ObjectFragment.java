package com.example.voronezh;

import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;

import android.app.Fragment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.core.widget.NestedScrollView;

import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

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
    BottomSheetBehavior bottomSheetBehavior;
    LinearLayout llBottomSheet;
    NestedScrollView nestedScroll;
    ImageButton imageButtonCall;
    ImageButton imageButtonWebsite;
    ImageButton imageButtonEmail;
    TextView textName;
    TextView textAddress;
    TextView textDescription;
    ImageView imageObject;
    LinearLayout llBottomObjects;
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
       // Log.d("object",object.getDescription());
        nestedScroll.fullScroll(NestedScrollView.FOCUS_UP);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
/*
        Log.d("LOG::",object.getDescription());
        String s1 = object.getDescription();
        char[] chars = new char[s1.length()];
        for (int i = 0; i < s1.length(); i++) {
            chars[i] = s1.charAt(i);
            int n = Character.getNumericValue(chars[i]);
            Log.d("Character:",String.valueOf((int)chars[i]));
            Log.d("Character:",String.valueOf(chars[i]));
        }
*/


        textDescription.setText(object.getDescription());
        textName.setText(object.getName());
        textAddress.setText(object.getAddress());

        int weightSum = 3;
        if(object.getPhone().isEmpty()) {
            weightSum--;
        }
        if(object.getEmail().isEmpty()) {
            weightSum--;
        }
        if(object.getWebsite().isEmpty()) {
            weightSum--;
        }

        llBottomObjects.setWeightSum(weightSum);

        if(object.getPhone().isEmpty()) {
            imageButtonCall.setVisibility(View.GONE);
        } else {
            if (imageButtonCall.getVisibility() == View.GONE) {
                imageButtonCall.setVisibility(View.VISIBLE);
            }
        }

        if(object.getWebsite().isEmpty()) {
            imageButtonWebsite.setVisibility(View.GONE);
        } else {
            if (imageButtonWebsite.getVisibility() == View.GONE) {
                imageButtonWebsite.setVisibility(View.VISIBLE);
            }
        }

        if(object.getEmail().isEmpty()) {
            imageButtonEmail.setVisibility(View.GONE);
        } else {
            if (imageButtonEmail.getVisibility() == View.GONE) {
                imageButtonEmail.setVisibility(View.VISIBLE);
            }
        }


        try (InputStream inputStream = getContext().getAssets().open(object.getImgUrl())) {
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            imageObject.setImageDrawable(drawable);

            ViewOutlineProvider provider = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    int curveRadius = 24;
                    outline.setRoundRect(0, 0, view.getWidth(), (view.getHeight()), curveRadius);
                }
            };

            imageObject.setOutlineProvider(provider);
            imageObject.setClipToOutline(true);


            //imgView.setClipToOutline(true);
        } catch (IOException e){e.printStackTrace();}




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

        //String[] points = null;
        //points = object.getLocation().split(",");
       // Point pointObject = new Point(Double.valueOf(points[0]),Double.valueOf(points[1]));

        MapKitFactory.getInstance().onStart();
        mapview.onStart();
        mapview.getMap().setRotateGesturesEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_object, container, false);

        mapview = (MapView) view.findViewById(R.id.mapview);

        nestedScroll = (NestedScrollView) view.findViewById(R.id.bottom_sheet_scroll);

        llBottomObjects = (LinearLayout) view.findViewById(R.id.button_objects);

        llBottomSheet = (LinearLayout) view.findViewById(R.id.bottom_sheet);
        bottomSheetBehavior = BottomSheetBehavior.from(llBottomSheet);
        //получаем высоту экрана
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        int height = displayMetrics.heightPixels;

        //устанавливаем высоту нижнего экрана
        int maxHeight = (int) (height*0.80);
        bottomSheetBehavior.setMaxHeight(maxHeight);
        llBottomSheet.setMinimumHeight(maxHeight);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

        imageObject = (ImageView) view.findViewById(R.id.imageObject);

        textName = (TextView) view.findViewById(R.id.textName);
        textAddress = (TextView) view.findViewById(R.id.textAddress);
        textDescription = (TextView) view.findViewById(R.id.textDescription);


        buttonBackToList = (Button) view.findViewById(R.id.buttonBackToList);
        buttonBackToList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                fragmentSendDataObjectListener.onSendDataObjectBack();
            }
        });


        imageButtonCall = (ImageButton) view.findViewById(R.id.imageButtonCall);
        imageButtonCall.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String[] phones = null;
                phones = object.getPhone().split("\n");

                String[] phone_first = phones[0].split("\\D+");
                String phoneNumber =  String.join("", phone_first);

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + phoneNumber));
                getActivity().startActivity(intent);

            }
        });

        imageButtonWebsite = (ImageButton) view.findViewById(R.id.imageButtonWebsite);
        imageButtonEmail = (ImageButton) view.findViewById(R.id.imageButtonEmail);
        objectFragmentSetData();

        return view;
    }
}