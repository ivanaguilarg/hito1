package es.udc.ipm.aleatorizador.controlador;

import android.content.Context;

/**
 * Created by AguilaSamsung on 08/06/2017.
 */

public interface ProvidedControlerToModelOps {
    Context getAppContext();

    void notifyCategoriesListChanged();
}
