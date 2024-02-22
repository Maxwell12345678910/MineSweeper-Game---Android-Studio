package com.gamecodeschool.hw03_minesweeperpart1_maxwellhanson;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.Context;
import android.widget.Toast;
import java.util.HashMap;



public class MainActivity extends AppCompatActivity {
    public TextView gridSizeDisp = null;
    public TextView mineCountDisp = null;
    public int rowSize = 10;
    public int colSize = 10;
    public int mineCount = 20;
    public int numCells = 100;
    public boolean ratioGood = false;

    private HashMap<String, MineCell> mineField = new HashMap<>();










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connecting textview displays to their ID's and setting them to default text
        gridSizeDisp = (TextView) findViewById(R.id.gridSizeDisp);
        mineCountDisp = (TextView) findViewById(R.id.mineCountDisp);
        refreshDisplays();

        initializeMineField();
        Log.d("positions",mineField.toString());

    }


    private void initializeMineField() {
        //create a number of rows to add to the hashmap
        for (int i = 0; i < rowSize; i++) {
            //create the same number of columns
            for (int j = 0; j < colSize; j++) {
                //similar to C programming, each int is passed to the first param as secondary params.
                //Example Cell positions: 0_0, 0_1, 0_2.....9_9
                String position = String.format("%d_%d", i, j);
                mineField.put(position, new MineCell());
            }
        }
    }

    public void displayPlayBoard(View v) {
        // first do ratio check
        double ratio = (double) mineCount / numCells;
        if (ratio >= .1 && ratio <= .3)
            ratioGood = true;
        else ratioGood = false;
        if (!ratioGood) {
            if (ratio < .1) {
                long requiredIncreaseMines = (long) Math.ceil(numCells * 0.1); // Smallest whole number mineCount for ratio >= 0.1
                String message = "To achieve a good ratio, set mineCount to at least " + requiredIncreaseMines;
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            } else if (ratio > .3) {
                long requiredDecreaseMines = (long) Math.floor(numCells * 0.3); // Smallest whole number mineCount for ratio >= 0.1
                String message2 = "To achieve a good ratio, set mineCount to at least " + requiredDecreaseMines;
                Toast.makeText(this, message2, Toast.LENGTH_SHORT).show();
            }
            return;
        }

        LinearLayout gridLayout = new LinearLayout(this);
        gridLayout.setOrientation(LinearLayout.VERTICAL);

        // Create and add text labels row
        addTextLabelsRow(gridLayout);

        // Create and add buttons grid
        addButtonsGrid(gridLayout);

        // Create and add the last row for the new game button
        addLastRow(gridLayout);

        setContentView(gridLayout); // load the display
    }

    private void addTextLabelsRow(LinearLayout gridLayout) {
        // Create a horizontal LinearLayout for the text for the columns
        LinearLayout horizontalLayout2 = new LinearLayout(this);
        horizontalLayout2.setOrientation(LinearLayout.HORIZONTAL);
        horizontalLayout2.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        // Add numbers 0 through rowSize to the horizontalLayout2 TextView
        for (int i = 0; i <= rowSize; i++) {
            TextView columnTextView = new TextView(this);
            columnTextView.setText(String.valueOf(i)); // Set column number
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0, // width
                    LinearLayout.LayoutParams.WRAP_CONTENT // height
            );
            params.weight = 1; // Set equal weight for each column TextView to distribute them evenly
            columnTextView.setLayoutParams(params); // Apply layout parameters to TextView
            columnTextView.setGravity(Gravity.CENTER); // Center align the text
            horizontalLayout2.addView(columnTextView); // Add TextView to horizontalLayout2
        }

        gridLayout.addView(horizontalLayout2); // Add horizontalLayout2 to the gridLayout
    }

    private void addButtonsGrid(LinearLayout gridLayout) {
        for (int i = 0; i <= rowSize - 1; i++) {
            // Create a horizontal LinearLayout
            LinearLayout horizontalLayout = new LinearLayout(this);
            horizontalLayout.setOrientation(LinearLayout.HORIZONTAL);
            horizontalLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            // Create and add the TextView with alphabetical chars before each row of buttons
            TextView columnTextView = new TextView(this);
            char numValToChar = (char) ('A' + i);
            String alphabet = String.valueOf(numValToChar);
            columnTextView.setText(" " + alphabet + "   ");
            horizontalLayout.addView(columnTextView);

            for (int j = 0; j <= rowSize - 1; j++) {
                Button button = new Button(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.weight = 1; // Set equal weight for each button to distribute them evenly
                button.setLayoutParams(params); // Apply layout parameters to button
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        button.setBackgroundColor(Color.GREEN);
                    }
                });
                button.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        button.setBackgroundColor(Color.RED);
                        return true;
                    }
                });
                horizontalLayout.addView(button); // Add button to horizontal layout
            }

            gridLayout.addView(horizontalLayout);
        }
    }

    private void addLastRow(LinearLayout gridLayout) {
        //creating the last row for the new game button, timer, and unflagged mine count
        LinearLayout horizontalLayoutLast = new LinearLayout(this);
        horizontalLayoutLast.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));
        horizontalLayoutLast.setGravity(Gravity.CENTER);
        // Create a new game button
        Button newGameButton = new Button(this);
        newGameButton.setText("NEW GAME");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        newGameButton.setLayoutParams(params);
        horizontalLayoutLast.addView(newGameButton);
        gridLayout.addView(horizontalLayoutLast);
    }

    public void refreshDisplays(){
        String rowSizeStr = String.valueOf(rowSize);
        String colSizeStr = String.valueOf(colSize);
        String mineCountStr = String.valueOf(mineCount);
        gridSizeDisp.setText(rowSizeStr + " X " + colSizeStr);
        mineCountDisp.setText(mineCountStr);
    }

    public void increaseGrid(View v){
        if(rowSize > 14) {
            v.setClickable(false);
            return;
        }
        rowSize++;
        colSize++;
        numCells=rowSize*colSize;
        refreshDisplays();
    }
    public void decreaseGrid(View v){
        if(rowSize < 6) {
            v.setClickable(false);
            return;
        }
        rowSize--;
        colSize--;
        numCells=rowSize*colSize;
        refreshDisplays();
    }
    public void increaseMines(View v){
        mineCount++;
        refreshDisplays();
    }
    public void decreaseMines(View v){
        mineCount--;
        refreshDisplays();
    }


}
