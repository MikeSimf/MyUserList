package ru.mike.mylistview.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import ru.mike.mylistview.R;

public class MyAlertRemoveDialog extends DialogFragment {
    private DialogInterface.OnClickListener onPositiveClickListener;

    public void setOnPositiveClickListener(DialogInterface.OnClickListener onPositiveClickListener) {
        this.onPositiveClickListener = onPositiveClickListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.remove_dialog_title)
                .setMessage(R.string.remove_dialog_message)
                .setPositiveButton(R.string.dialog_answer_yes, onPositiveClickListener)
                .setNegativeButton(R.string.dialog_answer_no, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}
