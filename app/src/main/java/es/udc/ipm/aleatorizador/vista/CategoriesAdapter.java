package es.udc.ipm.aleatorizador.vista;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import es.udc.ipm.aleatorizador.R;
import es.udc.ipm.aleatorizador.modelo.Category;



/**
 * Created by AguilaSamsung on 08/06/2017.
 */


public class CategoriesAdapter<T extends Category> extends ArrayAdapter<T> {

    private class ViewHolder{
        private TextView itemView;
    }

    public CategoriesAdapter(Context context, int resource, List objects) {
        super(context, resource, objects);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.mylistrow, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.itemView = (TextView) convertView.findViewById(R.id.list_item);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Category category = getItem(position);
        if (category!= null) {
            viewHolder.itemView.setText(category.getName());
        }
        return convertView;
    }
}

