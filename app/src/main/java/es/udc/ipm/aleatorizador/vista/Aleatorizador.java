package es.udc.ipm.aleatorizador.vista;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import es.udc.ipm.aleatorizador.R;
import es.udc.ipm.aleatorizador.StateMaintainer;
import es.udc.ipm.aleatorizador.controlador.AleatorizadorControlador;
import es.udc.ipm.aleatorizador.controlador.ProvidedControlerToViewOps;
import es.udc.ipm.aleatorizador.modelo.CategoriesModel;
import es.udc.ipm.aleatorizador.modelo.Category;
import es.udc.ipm.aleatorizador.modelo.SelectedCategoryEvent;

/**
 * Created by AguilaSamsung on 08/06/2017.
 */

public class Aleatorizador extends AppCompatActivity
        implements ProvidedViewOps, DialogListener.AlertDialogListener, DialogListener.EntryTextDialogListener {

    private static final String TAG = "Randomizer ";
    private ProvidedControlerToViewOps mPresenter;
    private final StateMaintainer mStateMaintainer =
            new StateMaintainer( getFragmentManager(), Aleatorizador.class.getName());
    private Menu menu;
    MenuItem addItem,editItem,deleteItem;
    private Category selectedCategory;
    private int selectedIndex;

    @Override
    public void showBackButton(boolean show) {
        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar!=null)
            actionBar.setDisplayHomeAsUpEnabled(show);
        else {
            Log.e(TAG,"Action bar is null");
        }
    }

    @Override
    public void clearMenu() {
        menu.clear();
        showBackButton(false);
    }

    private void setupMVP(){
        if (mStateMaintainer.firstTimeIn()) {
            AleatorizadorControlador presenter = new AleatorizadorControlador(this);
            CategoriesModel model = new CategoriesModel(presenter);
            presenter.setModel(model);

            mStateMaintainer.put(presenter);
            mStateMaintainer.put(model);
            mPresenter = presenter;
        }else{
            mPresenter = mStateMaintainer.get(AleatorizadorControlador.class.getName());
            // Updated the View in Presenter
            mPresenter.setView(this);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy(isChangingConfigurations());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aleatorizador);
        setupMVP();
        /*
        if (findViewById(R.id.activity_main) != null) {

            if (savedInstanceState != null) {
                return;
            }
            */
        CategoriesFragment frag = new CategoriesFragment();
        addFragment(frag);

        //frag.setAdapter(mPresenter.getList());

        //}
    }

    @Override
    protected void onStart() {
        super.onStart();
        //notifyCategoriesListChanged();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        setButtonAdd(true);
        return true;
    }

    public void setButtonAdd(boolean show) {
        addItem = menu.add(0, R.id.addItem, Menu.FIRST, R.string.menu_add);
        addItem.setIcon(R.drawable.ic_add);
        MenuItemCompat.setShowAsAction(addItem, MenuItem.SHOW_AS_ACTION_IF_ROOM);
        addItem.setVisible(show);
    }

    public void setButtonDeleteAndEdit(boolean show) {
        deleteItem = menu.add(0,R.id.deleteItem, Menu.FIRST, R.string.menu_delete);
        deleteItem.setIcon(R.drawable.ic_delete);
        MenuItemCompat.setShowAsAction(deleteItem, MenuItem.SHOW_AS_ACTION_IF_ROOM);

        editItem = menu.add(0, R.id.editItem, Menu.FIRST, R.string.menu_edit);
        editItem.setIcon(R.drawable.ic_edit);
        MenuItemCompat.setShowAsAction(editItem, MenuItem.SHOW_AS_ACTION_IF_ROOM);

        editItem.setVisible(show);
        deleteItem.setVisible(show);
    }

    public void addFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.activity_main, fragment, "CATEGORY_LIST_FRAG");
        transaction.commit();
    }

    public Fragment getFragment(String tag) {
        return getFragmentManager().findFragmentByTag(tag);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                mPresenter.onHomeSelected();
                break;
            case R.id.addItem:
                mPresenter.onAddSelected();
                break;
            case R.id.editItem:
                mPresenter.onEditSelected(getSelectedCategory(),getSelectedIndex());
                break;
            case R.id.deleteItem:
                mPresenter.onDeleteSelected(getSelectedCategory(), getSelectedIndex());
                break;
        }
        return true;
    }

    @Override
    public void showToast(String s){
        Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void deselectListElement() {
        CategoriesFragment cf = (CategoriesFragment) getFragmentManager().findFragmentByTag("CATEGORY_LIST_FRAG");
        int pos = cf.getListView().getCheckedItemPosition();
        cf.getListView().setChoiceMode(ListView.CHOICE_MODE_NONE);
        cf.getListView().getChildAt(pos).setActivated(false);
    }

    @Override
    public void showEditDialog(String text) {
        Bundle args = new Bundle();
        args.putString("text",text);
        args.putInt("accept_dialog_name",R.string.edit);

        DialogFragment editFragment = new EntryDialogFragment();
        editFragment.setArguments(args);
        editFragment.show(getFragmentManager(),"EDIT_DIALOG");
    }

    @Override
    public void showAddDialog() {
        Bundle args = new Bundle();
        args.putString("text","");
        args.putInt("accept_dialog_name",R.string.add);

        DialogFragment editFragment = new EntryDialogFragment();
        editFragment.setArguments(args);
        editFragment.show(getFragmentManager(),"ADD_DIALOG");
    }

    @Override
    public void showDeleteDialog() {
        Bundle args = new Bundle();
        args.putInt("accept_dialog_name",R.string.delete);

        DialogFragment deleteFragment = new DeleteDialogFragment();
        deleteFragment.setArguments(args);
        deleteFragment.show(getFragmentManager(),"DELETE_DIALOG");
    }

    @Override
    public Context getAppContext() {
        return this.getApplicationContext();
    }

    @Override
    public List<Category> getList() {
        return mPresenter.getList();
    }

    @Override
    public void notifyCategoriesListChanged() {
        ((CategoriesFragment)getFragment("CATEGORY_LIST_FRAG")).notifyDataChanged();
    }

    private Category getSelectedCategory(){
        return this.selectedCategory;
    }

    private int getSelectedIndex(){
        return this.selectedIndex;
    }

    @Subscribe
    public void onCategorySelected(SelectedCategoryEvent event){
        this.selectedCategory = event.getCategory();
        this.selectedIndex = event.getIndex();
    }

    @Override
    public void onEntryDialogPositiveClick(int action_ID, String text) {
        switch (action_ID){
            case R.string.add:
                mPresenter.addConfirmationClicked(text);

                break;
            case R.string.edit:
                mPresenter.editConfirmationClicked(text, getSelectedIndex());
                break;
        }
    }

    @Override
    public void onEntryDialogNegativeClick(int action_ID) {
    }

    @Override
    public void onEmptyInput() {
        Toast.makeText(this,"Name cannot be empty",Toast.LENGTH_LONG).show();
        showEditDialog("");
    }

    @Override
    public void onAlertDialogPositiveClick(int action_ID) {
        mPresenter.deleteConfirmationClicked(getSelectedIndex());
    }

    @Override
    public void onAlertDialogNegativeClick(int action_ID){
        showToast("Deletion canceled");
    }
}
