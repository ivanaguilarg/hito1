package es.udc.ipm.aleatorizador.vista;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import es.udc.ipm.aleatorizador.R;
import es.udc.ipm.aleatorizador.vista.DialogListener.EntryTextDialogListener;

/**
 * Created by AguilaSamsung on 08/06/2017.
 */


public class EntryDialogFragment extends DialogFragment {

    private EntryTextDialogListener mListener;

    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (EntryTextDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement DialogListener");
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_edit, null);

        final EditText editText = (EditText) view.findViewById(R.id.entry_dialog_edit_text);
        Bundle b = getArguments();
        String text = b.getString("text");
        final int accept_dialog_name_ID = b.getInt("accept_dialog_name");
        editText.setText(text);

        builder.setView(view);
        // Add action buttons
        builder.setPositiveButton(accept_dialog_name_ID, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String input = editText.getText().toString();
                if (!input.equals("")) {
                    mListener.onEntryDialogPositiveClick(accept_dialog_name_ID, input);
                }else{
                    mListener.onEmptyInput();
                }
            }
        })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onEntryDialogNegativeClick(accept_dialog_name_ID);
                    }
                });

        return builder.create();
    }
}

