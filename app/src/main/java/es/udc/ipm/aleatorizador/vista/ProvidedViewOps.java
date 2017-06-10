package es.udc.ipm.aleatorizador.vista;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import java.util.List;
import es.udc.ipm.aleatorizador.modelo.Category;

/**
 * Created by AguilaSamsung on 08/06/2017.
 */

public interface ProvidedViewOps{

    void setButtonAdd(boolean show);

    void setButtonDeleteAndEdit(boolean show);

    void showBackButton(boolean show);

    void clearMenu();

    void showToast(String s);

    void deselectListElement();

    void showEditDialog(String text);

    void showAddDialog();

    void showDeleteDialog();

    Context getAppContext();

    List<Category> getList();

    void notifyCategoriesListChanged();
}

