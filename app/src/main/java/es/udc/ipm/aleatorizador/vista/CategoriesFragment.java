package es.udc.ipm.aleatorizador.vista;


import android.app.ListFragment;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import es.udc.ipm.aleatorizador.R;
import es.udc.ipm.aleatorizador.modelo.Category;
import es.udc.ipm.aleatorizador.modelo.SelectedCategoryEvent;

/**
 * Created by AguilaSamsung on 08/06/2017.
 */

public class CategoriesFragment extends ListFragment implements CategoryListView{

    public CategoriesFragment() {
    }

    private CategoriesAdapter<Category> categoriesAdapter;
    private ProvidedViewOps providedViewOps;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            providedViewOps = (ProvidedViewOps) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement MenuView");
        }
    }

    public void setAdapter(List<Category> list){
        this.categoriesAdapter = new CategoriesAdapter<>(getActivity(),R.layout.mylistrow,list);
        setListAdapter(categoriesAdapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        setAdapter(providedViewOps.getList());

        getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int index, long l) {
                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                getListView().setItemChecked(index, true);
                providedViewOps.clearMenu();
                providedViewOps.showBackButton(true);
                providedViewOps.setButtonDeleteAndEdit(true);
                sendCategorySelected(view,index);
                return true;
            }
        });

        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                sendCategorySelected(view,index);
            }
        });
    }

    private void sendCategorySelected(View view, int index){
        TextView tv = (TextView) view;
        String categoryName = tv.getText().toString();
        Category category = new Category(categoryName);
        EventBus.getDefault().post(new SelectedCategoryEvent(category, index));
    }

    public void notifyDataChanged() {

        this.categoriesAdapter.notifyDataSetChanged();
    }


}
