package com.example.silentproto;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class LugaresAdaptador extends ArrayAdapter<Lugares> {
    private Context context;
    private int layout;
    private Lugares[] cLugares;

    public LugaresAdaptador(@NonNull Context context, int resource, @NonNull Lugares[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.layout = resource;
        this.cLugares = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //si no existe la fila
        if (convertView == null){//La primera vez que se muestra la lista aún no existe
            convertView = ((Activity)context).getLayoutInflater().inflate(layout,parent,false);
        }else{

        }
        TextView txtVwLugar, txtVwDesc;
        txtVwLugar = convertView.findViewById(R.id.txtVwLugar);
        txtVwDesc = convertView.findViewById(R.id.txtVwDesc);

        txtVwLugar.setText(cLugares[position].getLugar());
        txtVwDesc.setText(cLugares[position].getDesc());

        return convertView;
    }
}