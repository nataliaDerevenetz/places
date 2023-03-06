package com.example.voronezh;

import android.content.Context;
import android.view.LayoutInflater;
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

        TypeObject object = objects.get(position);

        imgView.setImageResource(object.getImgResource());
        nameView.setText(object.getName());

        return view;
    }
}
