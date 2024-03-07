package com.gamecodeschool.hw03_minesweeperpart1_maxwellhanson;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class NewGameVerifyDialog extends DialogFragment {

    private NewGameListenerInterface listener ;

    public NewGameVerifyDialog(NewGameListenerInterface listener){
        this.listener = listener;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder =
                new AlertDialog.Builder(getActivity());

        builder.setMessage("New Game?")
        // An OK button that does nothing
                .setPositiveButton("OK", new DialogInterface.OnClickListener() { //-----------OK BUTTON ------------------stop timer and pressedNewGame
                    public void onClick(DialogInterface dialog, int id) {
                        listener.pressedNewGame();listener.stopTimer();
                    }
                })
            // A "Cancel" button that does nothing
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id) {
                // Nothing happening here either
                    }
                });


        return builder.create();

    }



}
