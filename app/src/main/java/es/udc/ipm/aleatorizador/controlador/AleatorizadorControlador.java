package es.udc.ipm.aleatorizador.controlador;

import android.content.Context;

import java.lang.ref.WeakReference;
import java.util.List;

import es.udc.ipm.aleatorizador.modelo.Category;
import es.udc.ipm.aleatorizador.modelo.ProvidedModelOps;
import es.udc.ipm.aleatorizador.vista.ProvidedViewOps;

/**
 * Created by AguilaSamsung on 08/06/2017.
 */

public class AleatorizadorControlador implements ProvidedControlerToViewOps, ProvidedControlerToModelOps{

    private WeakReference<ProvidedViewOps> mView;
    // Model reference
    private ProvidedModelOps mModel;

    public AleatorizadorControlador(ProvidedViewOps view){

        mView = new WeakReference<>(view);

    }

    @Override
    public void setView(ProvidedViewOps view) {
        mView = new WeakReference<>(view);
    }

    private ProvidedViewOps getView() throws NullPointerException{
        if ( mView != null )
            return mView.get();
        else
            throw new NullPointerException("View is unavailable");
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {

        mView = null;
        mModel.onDestroy(isChangingConfiguration);
        // Activity destroyed
        if ( !isChangingConfiguration ) {
            mModel = null;
        }
    }

    public void setModel(ProvidedModelOps model) {
        mModel = model;
        loadData();
    }

    private void loadData() {
        mModel.loadData();
    }

    @Override
    public void onHomeSelected() {
        getView().clearMenu();
        getView().setButtonAdd(true);
        getView().deselectListElement();
    }

    @Override
    public void onAddSelected() {
        getView().showAddDialog();
    }

    @Override
    public void onEditSelected(Category category, int index) {
        getView().showEditDialog(category.getName());
    }

    @Override
    public void onDeleteSelected(Category category, int index) {
        getView().showDeleteDialog();
    }

    @Override
    public void addConfirmationClicked(String text) {
        mModel.insertCategory(text);
        getView().showToast(text + " added");
    }

    @Override
    public void editConfirmationClicked(String text, int index) {
        mModel.editCategory(text, index);
        getView().showToast("Successfully edited");
        onHomeSelected();
    }

    @Override
    public void deleteConfirmationClicked(int index) {
        mModel.deleteCategory(index);
        getView().showToast("Successfully deleted");
        onHomeSelected();
    }

    @Override
    public Context getAppContext() {
        return getView().getAppContext();
    }

    @Override
    public List<Category> getList() {
        return mModel.getList();
    }

    @Override
    public void notifyCategoriesListChanged(){
        getView().notifyCategoriesListChanged();
    }
}

