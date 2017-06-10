package es.udc.ipm.aleatorizador.modelo;

import java.util.List;

/**
 * Created by AguilaSamsung on 08/06/2017.
 */

public interface ProvidedModelOps {

    void insertCategory(String name);

    List<Category> getList();

    void loadData();

    void deleteCategory(int index);

    void editCategory(String text, int index);

    void onDestroy(boolean isChangingConfiguration);
}