package com.example.voronezh;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.ViewOutlineProvider;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ObjectAdapter extends ArrayAdapter<Object>{
    private LayoutInflater inflater;
    private int layout;
    private List<Object> objects;

    public ObjectAdapter(Context context, int resource, List<Object> objects) {
        super(context, resource, objects);
        this.objects = objects;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View view=inflater.inflate(this.layout, parent, false);

        ImageView imgView = view.findViewById(R.id.imgObj);
        TextView nameView = view.findViewById(R.id.name);

        Object object = objects.get(position);

        try (InputStream inputStream = getContext().getAssets().open(object.getImgUrl())) {
            Drawable drawable = Drawable.createFromStream(inputStream, null);
            imgView.setImageDrawable(drawable);

            ViewOutlineProvider provider = new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    int curveRadius = 24;
                    outline.setRoundRect(0, 0, view.getWidth(), (view.getHeight()), curveRadius);
                }
            };

            imgView.setOutlineProvider(provider);
            imgView.setClipToOutline(true);


            //imgView.setClipToOutline(true);
        } catch (IOException e){e.printStackTrace();}
        nameView.setText(object.getName());

        return view;
    }
}
