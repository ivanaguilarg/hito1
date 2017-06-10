package es.udc.ipm.aleatorizador.vista;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import es.udc.ipm.aleatorizador.R;
import es.udc.ipm.aleatorizador.vista.DialogListener.AlertDialogListener;
/**
 * Created by AguilaSamsung on 08/06/2017.
 */


public class DeleteDialogFragment extends DialogFragment {

    private AlertDialogListener mListener;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (AlertDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction

        Bundle b = getArguments();
        final int accept_dialog_name_ID = b.getInt("accept_dialog_name");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.dialog_category_delete)
                .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onAlertDialogPositiveClick(accept_dialog_name_ID);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onAlertDialogNegativeClick(accept_dialog_name_ID);
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
}


