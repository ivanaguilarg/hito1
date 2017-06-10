package es.udc.ipm.aleatorizador.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import es.udc.ipm.aleatorizador.controlador.AleatorizadorControlador;

/**
 * Created by AguilaSamsung on 08/06/2017.
 */

public class CategoriesModel implements ProvidedModelOps{

    private DAO mDAO;
    private List<Category> categoryList;
    private AleatorizadorControlador mPresenter;

    public CategoriesModel(AleatorizadorControlador mPresenter){
        this.mPresenter = mPresenter;
        this.mDAO = new DAO(mPresenter.getAppContext());
        this.categoryList = new ArrayList<>();
    }

    @Override
    public void onDestroy(boolean isChangingConfiguration) {
        if (!isChangingConfiguration) {
            mPresenter = null;
            mDAO = null;
            categoryList = null;
        }
    }

    @Override
    public void insertCategory(String name) {

        Category category = mDAO.insertCategory(new Category(name));
        this.categoryList.add(category);
        Collections.sort(categoryList);
        mPresenter.notifyCategoriesListChanged();
    }

    @Override
    public void deleteCategory(int index) {
        mDAO.deleteCategory(this.categoryList.get(index));
        categoryList.remove(index);
        mPresenter.notifyCategoriesListChanged();
    }

    @Override
    public void editCategory(String text, int index) {
        Category category = this.categoryList.get(index);
        category.setName(text);
        mDAO.updateCategory(category);
        Collections.sort(categoryList);
        mPresenter.notifyCategoriesListChanged();
    }

    @Override
    public List<Category> getList() {
        return this.categoryList;
    }

    @Override
    public void loadData() {
        categoryList = mDAO.getAllCategories();
    }
}

