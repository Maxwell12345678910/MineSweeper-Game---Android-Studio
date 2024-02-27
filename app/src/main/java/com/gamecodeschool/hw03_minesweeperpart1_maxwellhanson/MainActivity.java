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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    public TextView gridSizeDisp = null;
    public TextView mineCountDisp = null;
    public TextView userCountDisplay = null;
    public int rowSize = 10;
    public int colSize = 10;
    public int mineCount = 20;
    public int numCells = 100;
    public int userCount =20;
    public boolean ratioGood = false;

    private HashMap<String, MineCell> mineField = new HashMap<>();//used to keep track of data for every button in the grid

    private HashSet<String> activeMines = new HashSet<>(); //used to keep track of the positions of active mines only

    private List <Button> btnList = new ArrayList<>();
    private HashMap <String,Button> flaggedBtnList = new HashMap<>();



    //if we switch to the main activity layout we need to call this method again to have the id's of the
    //text views and set them
    public void setInitVars(){
        userCount = mineCount;
        //connecting textview displays to their ID's and setting them to default text
        gridSizeDisp = (TextView) findViewById(R.id.gridSizeDisp);
        mineCountDisp = (TextView) findViewById(R.id.mineCountDisp);
        refreshDisplays();
        initializeMineField();
        Log.d("positions",mineField.toString());
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setInitVars();
    }

    //used by other methods to interact with buttons at a particular position
    private String getPosition(int row, int col) {
        return String.format("%d_%d", row, col);
    }

    //creates mine cell objects, their positions, and adds them to a hashmap
    private void initializeMineField() {
        mineField.clear();//clear the hashmap incase the size changed or its reset game
        //create a number of rows to add to the hashmap
        for (int i = 0; i < rowSize; i++) {
            //create the same number of columns
            for (int j = 0; j < colSize; j++) {
                //similar to C programming, each int is passed to the first param as secondary params.
                //Example Cell positions: 0_0, 0_1, 0_2.....9_9
                String position = getPosition(i,j);
                MineCell addMine = new MineCell();
                addMine.setPosition(position);
                mineField.put(position, addMine);
            }
        }
        insertMinesIntoMap();
    }

    //triggered when the user clicks the play button
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
        //we need to initialize the mineField (which also inserts mines) in this method, otherwise it only happens in onCrate but we need it for new games as well
        initializeMineField();
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


    //BUTTONS ADDED HERE--------------------------------------------------onClick METHODS HERE-----------------------------------
    private void addButtonsGrid(LinearLayout gridLayout) {
        //This loop creates each row of the grid
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

            //this loop populates each row in the grid, column by column with buttons
            for (int j = 0; j <= rowSize - 1; j++) {
                int buttonRow = i;
                int buttonCol= j;
                Button button = new Button(this);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.weight = 1; // Set equal weight for each button to distribute them evenly
                button.setLayoutParams(params); // Apply layout parameters to button

               //its necesarry to have all this onClick code, but for the logic I moved that to separate methods
                //called setButtonClickListeners which receive the button as well as its row and col
                //for some reason though, i had to set these up outside  the onClick method it self, after the
                //setOnClickListener command
                button.setOnClickListener(new View.OnClickListener() { //-----------------------------------CEll Button SHORT click
                    @Override
                    public void onClick(View v) {
                        revealCell(buttonRow,buttonCol);

                        MineCell cell = mineField.get(getPosition(buttonRow,buttonCol));//get the armed int from revealCell, set the background of the button to that.
                        int armedCount = cell.getArmedNeighborsCount();
                        button.setText(String.valueOf(armedCount));
                        if(cell.hasMine()) { // IF USER ENDS THE GAME ---------------------------------------------------------------------
                            button.setBackgroundColor(Color.RED);
                            for(Button btn : btnList){ /// loop sets all buttons to disabled
                                btn.setEnabled(false);
                            }
                            Log.d("Flagged were:", flaggedBtnList.toString());
                        }
                        else button.setBackgroundColor(Color.GRAY);
                    }

                });

                button.setOnLongClickListener(new View.OnLongClickListener() {//-----------------------------------CEll Button LONG click------
                    //DONT FORGET TO MAKE IT SO IF CELL IS REVEALED IT CANT BE FLAGGED
                    @Override
                    public boolean onLongClick(View v) {
                        MineCell cell = mineField.get(getPosition(buttonRow,buttonCol));
                        if (cell.isFlagged())//if flagged, set it back to unflagged, otherwise make it yellow and flag the cell
                        {
                            cell.setFlagged(false);
                            button.setBackgroundColor(Color.LTGRAY);
                            button.setActivated(false);
                            userCount++;userCountDisplay.setText(String.valueOf(userCount)); //raise userCount + update its display
                            flaggedBtnList.remove(cell.getPosition());
                        }
                        else {
                            cell.setFlagged(true);
                            button.setBackgroundColor(Color.YELLOW);
                            userCount--;    userCountDisplay.setText(String.valueOf(userCount));    //lower userCount + update its display
                            flaggedBtnList.put(cell.getPosition(),button);
                        }
                        return true;
                    }
                });
                horizontalLayout.addView(button); // Add button to horizontal layout
                btnList.add(button);
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

        //Create a display for the userCount of mines
        userCountDisplay = new TextView(this);
        userCountDisplay.setText(String.valueOf(userCount));
        horizontalLayoutLast.addView(userCountDisplay);


        // Create a new game button
        Button newGameButton = new Button(this);
        newGameButton.setText("NEW GAME");
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        newGameButton.setLayoutParams(params);
        newGameButton.setOnClickListener(new View.OnClickListener() { // -------------NEW GAME BUTTON
            @Override
            public void onClick(View v) {
                pressedNewGame();
            }
        }
        );//hook up the method to the button
        horizontalLayoutLast.addView(newGameButton);
        gridLayout.addView(horizontalLayoutLast);
    }

    public void refreshDisplays(){
        String rowSizeStr = String.valueOf(rowSize); //These lines update the starting page displays
        String colSizeStr = String.valueOf(colSize);
        String mineCountStr = String.valueOf(mineCount);
        gridSizeDisp.setText(rowSizeStr + " X " + colSizeStr);
        mineCountDisp.setText(mineCountStr);
        //update the count of mines (not including those flagged by the user) display in the game grid screen
        userCountDisplay = new TextView(this);
        userCountDisplay.setText(String.valueOf(userCount));
    }


    public void increaseGrid(View v){
        if(rowSize > 14) {
            v.setClickable(false);
            return;
        }
        rowSize++;
        colSize++;
        numCells=rowSize*colSize;
        //everytime the user updates the grid size, we need to remake the mineField Hashmap
        initializeMineField();
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
        initializeMineField();
        refreshDisplays();
    }

    //still have to add mines in grid generation method, and add a line using that method here
    public void increaseMines(View v){
        mineCount++;
        userCount = mineCount;
        refreshDisplays();
        insertMinesIntoMap();
    }
    //still have to add mines in grid generation method, and add a line using that method here

    public void decreaseMines(View v){
        mineCount--;
        userCount = mineCount;
        refreshDisplays();
        insertMinesIntoMap();
    }



    public void pressedNewGame(){
        setContentView(R.layout.activity_main);
        setInitVars();
        initializeMineField();
    }

    public void insertMinesIntoMap(){
        activeMines.clear();//reset all mines so we can add new ones easier
        for(int i=0;i<mineCount;i++){
            int randomSideIndex1 = (int) (Math.random() * rowSize) ;int randomSideIndex2 = (int) (Math.random() * rowSize) ;
            String randomPosition = randomSideIndex1 + "_" + randomSideIndex2;//generate random num for col and row and get a concat a position string from that, then access this as a random
            while(activeMines.contains(randomPosition)){ // this loop handles duplicate positions. So while the set contains the generated postions, create a new one
                 randomSideIndex1 = (int) (Math.random() * rowSize) ; randomSideIndex2 = (int) (Math.random() * rowSize) ;
                 randomPosition = randomSideIndex1 + "_" + randomSideIndex2;
            }
            Log.d("Insert Random Mine: ", "Inserting a mine into position: " + randomPosition);
            activeMines.add(randomPosition); //add it to active mines so we keep track of all the mines in one set
            MineCell mineAtPosition = mineField.get(randomPosition); //fetch the button in the grid at that position
            mineAtPosition.setMine(true); //insert the mine into that button in the grid
        }
    }

    public List<String> findNeighbors(String position) {
        List<String> neighbors = new ArrayList<>();

        // Split the position string into row and column numbers
        String[] parts = position.split("_");
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);

        // Define offsets for neighboring cells
        int[][] offsets = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        // Loop through offsets to find neighboring cells
        for (int[] offset : offsets) {
            int newRow = row + offset[0]; // row plus the first dimension's offset iterated item
            int newCol = col + offset[1];//column plus the second dimension's iterated item value

            // Check bounds - if its x/y position is 0-max then we're good
            if (newRow >= 0 && newRow < rowSize && newCol >= 0 && newCol < colSize) {
                neighbors.add(newRow + "_" + newCol);                // Add the neighboring cell to the list
            }
        }
        return neighbors;
    } //

    public List<String> findArmedNeighbors(String position){
        List<String> armedNeighbors = new ArrayList<>();

        // Split the position string into row and column numbers
        String[] parts = position.split("_");
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);

        // Define offsets for neighboring cells
        int[][] offsets = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1},           {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };

        // Loop through offsets to find neighboring cells
        for (int[] offset : offsets) {
            int newRow = row + offset[0]; // row plus the first dimension's offset iterated item
            int newCol = col + offset[1];//column plus the second dimension's iterated item value
            String neighborPosition = newRow + "_" + newCol;
            MineCell neighborCell = mineField.get(neighborPosition); //get the cell from the map to see if its armed
            // Check bounds - if its x/y position is 0-max then we're good
            if (newRow >= 0 && newRow < rowSize && newCol >= 0 && newCol < colSize && neighborCell.hasMine()) {

                armedNeighbors.add(newRow + "_" + newCol);                // Add the neighboring cell to the list
            }
        }
        return armedNeighbors;
    }

    public void revealCell(int row, int col) {
        String position = getPosition(row, col);//get the format we set for positions from earlier for processing in the map
        MineCell cell = mineField.get(position);//find the cell associated with that string key
        char alphabet = (char) ('A' + (row)); //get the alphabetical value of the row for the log
        Log.d("User Revealed.....:","ROW was :" + alphabet +". COl number was: " + (col+1)
                +". And cell data's position was: " + cell.getPosition() + ". MINE STATUS: " + cell.hasMine() );

        List<String> revealedCellNeighbors = findNeighbors(position);
        cell.setNeighbors(revealedCellNeighbors);
        Log.d("THIS CELL NEIGHBORS:",cell.getNeighbors().toString() + " ----Remember to add 1 to all coordinates");

        List<String> armed = findArmedNeighbors(position);
        cell.setArmedNeighbors(armed);
        Log.d("ARMED NEIGHBORS:",cell.getArmedNeighbors().toString() + " ----Remember to add 1 to all coordinates");
        Log.d("ARMED COUNT:", String.valueOf(cell.getArmedNeighborsCount()));

        cell.setRevealed(true);
    }// Method to reveal a cell
}
