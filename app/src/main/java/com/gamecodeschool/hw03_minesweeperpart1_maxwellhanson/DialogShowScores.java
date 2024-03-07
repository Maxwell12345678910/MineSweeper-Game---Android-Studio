package com.gamecodeschool.hw03_minesweeperpart1_maxwellhanson;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class DialogShowScores extends DialogFragment {

    public UserSession user;
    public UserSession[] top5List;

    public DialogShowScores(UserSession user, UserSession[] top5List) {
        this.user = user;
        this.top5List = top5List;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.score_board, null);
        TextView rank1 = dialogView.findViewById(R.id.nameDisp1);
        TextView rank2 = dialogView.findViewById(R.id.nameDisp2);
        TextView rank3 = dialogView.findViewById(R.id.nameDisp3);
        TextView rank4 = dialogView.findViewById(R.id.nameDisp4);
        TextView rank5 = dialogView.findViewById(R.id.nameDisp5);
        TextView score1 = dialogView.findViewById(R.id.scoreDisp1);
        TextView score2 = dialogView.findViewById(R.id.scoreDisp2);
        TextView score3 = dialogView.findViewById(R.id.scoreDisp3);
        TextView score4 = dialogView.findViewById(R.id.scoreDisp4);
        TextView score5 = dialogView.findViewById(R.id.scoreDisp5);
        TextView userPromptRed = dialogView.findViewById(R.id.userPromptRed);

        updateTop5List(score1,score2,score3,score4,score5,rank1, rank2, rank3, rank4, rank5);





        EditText nameInput = dialogView.findViewById(R.id.nameInput);
        if(getUserRankInTop5List(user) == -1) { //if the user didnt make the top 5, hide the input and prompt
            nameInput.setVisibility(View.GONE); userPromptRed.setVisibility(View.GONE);
        }        else
        {
            nameInput.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    String userName = s.toString();
                    user.setUsername(userName);
                    int userRank = getUserRankInTop5List(user);
                    if (userRank != -1) {
                        switch (userRank) {
                            case 0:
                                rank1.setText(s.toString());
                                rank1.setTextColor(Color.RED);
                                break;
                            case 1:
                                rank2.setText(s.toString());
                                rank2.setTextColor(Color.RED);
                                break;
                            case 2:
                                rank3.setText(s.toString());
                                rank3.setTextColor(Color.RED);
                                break;
                            case 3:
                                rank4.setText(s.toString());
                                rank4.setTextColor(Color.RED);
                                break;
                            case 4:
                                rank5.setText(s.toString());
                                rank5.setTextColor(Color.RED);
                                break;
                        }
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }


        Button btnOK = dialogView.findViewById(R.id.btnOK);
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Start a new game or perform any other actions upon clicking OK
                // For now, let's assume you have a method to start a new game called startNewGame()
                ((MainActivity) requireActivity()).pressedNewGame();

                dismiss(); // Close the dialog after performing the action
            }
        });


        builder.setView(dialogView);

        return builder.create();



    }




    private void updateTop5List(TextView score1, TextView score2, TextView score3, TextView score4, TextView score5, TextView rank1, TextView rank2, TextView rank3, TextView rank4, TextView rank5) {
        // Iterate through top5List to find the correct position to insert the new user
        int indexToInsert = -1;
        for (int i = 0; i < top5List.length; i++) {
            if (top5List[i] == null || user.getScore() > top5List[i].getScore()) {
                indexToInsert = i;
                break;
            }
        }

        // Shift the elements to make space for the new user
        if (indexToInsert != -1) {
            for (int i = top5List.length - 1; i > indexToInsert; i--) {
                top5List[i] = top5List[i - 1];
            }
            top5List[indexToInsert] = user;
        }

        // Set the scores to TextViews
        score1.setText(top5List[0] != null ? String.valueOf(top5List[0].getScore()) : "000");
        score2.setText(top5List[1] != null ? String.valueOf(top5List[1].getScore()) : "000");
        score3.setText(top5List[2] != null ? String.valueOf(top5List[2].getScore()) : "000");
        score4.setText(top5List[3] != null ? String.valueOf(top5List[3].getScore()) : "000");
        score5.setText(top5List[4] != null ? String.valueOf(top5List[4].getScore()) : "000");
        // Set the text color of all rank TextViews to black
        rank1.setTextColor(Color.BLACK);
        rank2.setTextColor(Color.BLACK);
        rank3.setTextColor(Color.BLACK);
        rank4.setTextColor(Color.BLACK);
        rank5.setTextColor(Color.BLACK);



        for (int i = 0; i < top5List.length; i++) {
            if (top5List[i] != null) {
                switch (i) {
                    case 0:
                        rank1.setText(top5List[i].getUsername());
                        if (user.equals(top5List[i])) rank1.setTextColor(Color.RED);
                        break;
                    case 1:
                        rank2.setText(top5List[i].getUsername());
                        if (user.equals(top5List[i])) rank2.setTextColor(Color.RED);
                        break;
                    case 2:
                        rank3.setText(top5List[i].getUsername());
                        if (user.equals(top5List[i])) rank3.setTextColor(Color.RED);
                        break;
                    case 3:
                        rank4.setText(top5List[i].getUsername());
                        if (user.equals(top5List[i])) rank4.setTextColor(Color.RED);
                        break;
                    case 4:
                        rank5.setText(top5List[i].getUsername());
                        if (user.equals(top5List[i])) rank5.setTextColor(Color.RED);
                        break;
                }
            } else {
                // Set the rank TextViews to "???" if the corresponding user is null
                switch (i) {
                    case 0:
                        rank1.setText("???");
                        break;
                    case 1:
                        rank2.setText("???");
                        break;
                    case 2:
                        rank3.setText("???");
                        break;
                    case 3:
                        rank4.setText("???");
                        break;
                    case 4:
                        rank5.setText("???");
                        break;
                }
            }
        }


    }

    private int getUserRankInTop5List(UserSession user) {
        for (int i = 0; i < top5List.length; i++) {
            if (top5List[i] == user) {
                return i; // Return the index where the user is found in the top5List
            }
        }
        return -1; // Return -1 if the user is not found in the top5List
    }

}//class bracket
