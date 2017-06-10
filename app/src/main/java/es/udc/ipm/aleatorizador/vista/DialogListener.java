package es.udc.ipm.aleatorizador.vista;

/**
 * Created by AguilaSamsung on 08/06/2017.
 */

public interface DialogListener {

    interface EntryTextDialogListener {

        void onEntryDialogPositiveClick(int action_ID, String entry_text);

        void onEntryDialogNegativeClick(int action_ID);

        void onEmptyInput();
    }

    interface AlertDialogListener {

        void onAlertDialogPositiveClick(int action_ID);

        void onAlertDialogNegativeClick(int action_ID);
    }
}
