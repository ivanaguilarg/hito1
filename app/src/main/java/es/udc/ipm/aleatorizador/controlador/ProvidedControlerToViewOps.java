package es.udc.ipm.aleatorizador.controlador;

import java.util.List;

import es.udc.ipm.aleatorizador.modelo.Category;
import es.udc.ipm.aleatorizador.vista.ProvidedViewOps;

/**
 * Created by AguilaSamsung on 08/06/2017.
 */

public interface ProvidedControlerToViewOps {

    void setView(ProvidedViewOps view);

    void onHomeSelected();

    void onAddSelected();

    void onEditSelected(Category category, int index);

    void onDeleteSelected(Category category, int index);

    void addConfirmationClicked(String text);

    void editConfirmationClicked(String text, int selectedIndex);

    List<Category> getList();

    void deleteConfirmationClicked(int selectedIndex);

    void onDestroy(boolean onConfigurationChanged);
}
