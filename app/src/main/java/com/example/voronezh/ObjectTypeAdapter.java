package com.example.voronezh;

import android.content.Context;
import android.graphics.Outline;
import android.view.LayoutInflater;
import android.view.ViewOutlineProvider;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

public class ObjectTypeAdapter extends ArrayAdapter<TypeObject> {

    private LayoutInflater inflater;
    private int layout;
    private List<TypeObject> objects;

    public ObjectTypeAdapter(Context context, int resource, List<TypeObject> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        ImageView imgView = view.findViewById(R.id.imageObject);
        TextView nameView = view.findViewById(R.id.textObject);

        //Получаем данные о объекте в TypeObject
        TypeObject object = objects.get(position);

        //Устанавливаем картинку для объекта
        imgView.setImageResource(object.getImgResource());

        // Устанавливаем округлые углы у картинки
        ViewOutlineProvider provider = new ViewOutlineProvider() {
            @Override
            public void getOutline(View view, Outline outline) {
                int curveRadius = 24;
                outline.setRoundRect(0, 0, view.getWidth(), (view.getHeight()), curveRadius);
            }
        };
        imgView.setOutlineProvider(provider);
        imgView.setClipToOutline(true);
        //Устанавливаем текст - название объекта (например музеи)
        nameView.setText(object.getName());

        return view;
    }
}
