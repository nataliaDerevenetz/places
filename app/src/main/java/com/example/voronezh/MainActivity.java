package com.example.voronezh;



import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.GridView;
import java.util.ArrayList;

public class MainActivity extends Activity {

    String[] city = { "Воронежская область", "Воронеж", "Богучар", "Борисоглебск", "Бобров"};

    ArrayList<TypeObject> objects = new ArrayList<TypeObject>();
    GridView objectsGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinnerCity = findViewById(R.id.spinnerCity);
        // Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, city);
        // Определяем разметку для использования при выборе элемента
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Применяем адаптер к элементу spinner
        spinnerCity.setAdapter(adapter);




        // начальная инициализация списка
        setInitialData();

        // получаем элемент GridView
        objectsGrid = findViewById(R.id.gridviewTypeObject);
        // создаем адаптер
        ObjectTypeAdapter objectAdapter = new ObjectTypeAdapter(this, R.layout.cell_grid, objects);
        // устанавливаем адаптер
        objectsGrid.setAdapter(objectAdapter);




        // получаем элемент GridView
        //GridView typeObjectList = findViewById(R.id.gridviewTypeObject);
        // создаем адаптер
        //ArrayAdapter<String> adapterTypeObject = new ArrayAdapter(this, android.R.layout.simple_list_item_1, city);
        //typeObjectList.setAdapter(adapterTypeObject);
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