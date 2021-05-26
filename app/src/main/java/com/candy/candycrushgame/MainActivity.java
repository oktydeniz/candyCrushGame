package com.candy.candycrushgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    int[] candies = {
            R.drawable.bluecandy,
            R.drawable.greencandy,
            R.drawable.redcandy,
            R.drawable.orangecandy,
            R.drawable.yellowcandy,
            R.drawable.purplecandy,
    };
    int withOfBlock, noOfBlock = 8;
    int withOfScreen;
    ArrayList<ImageView> candy = new ArrayList<>();
    int notCandy = R.drawable.transparent;
    Handler handler;
    int interval = 100;
    int candyToBeDragged, candyToBeReplaced;
    TextView scoreTextView;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initialize() {
        scoreTextView = findViewById(R.id.scoreBoardText);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        withOfScreen = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;
        withOfBlock = withOfScreen / noOfBlock;
        handler = new Handler();
        createBoard();
        for (final ImageView imageView : candy) {
            imageView.setOnTouchListener(new OnSwipeListener(this) {

                @Override
                void onSwipeLeft() {
                    super.onSwipeLeft();
                    Toast.makeText(getApplicationContext(), "Left", Toast.LENGTH_SHORT
                    ).show();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged - noOfBlock;
                    candyInterChance();

                }

                @Override
                void onSwipeRight() {
                    super.onSwipeRight();
                    Toast.makeText(getApplicationContext(), "Right", Toast.LENGTH_SHORT
                    ).show();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged + noOfBlock;
                    candyInterChance();

                }

                @Override
                void onSwipeTop() {
                    super.onSwipeTop();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged - 1;
                    candyInterChance();
                    Toast.makeText(getApplicationContext(), "Top", Toast.LENGTH_SHORT
                    ).show();
                }

                @Override
                void onSwipeBottom() {
                    super.onSwipeBottom();
                    candyToBeDragged = imageView.getId();
                    candyToBeReplaced = candyToBeDragged + 1;
                    candyInterChance();
                    Toast.makeText(getApplicationContext(), "Bottom", Toast.LENGTH_SHORT
                    ).show();
                }
            });
        }
        startRepeat();
    }

    private void candyInterChance() {
        int background = (int) candy.get(candyToBeReplaced).getTag();
        int background1 = (int) candy.get(candyToBeDragged).getTag();
        candy.get(candyToBeDragged).setImageResource(background);
        candy.get(candyToBeReplaced).setImageResource(background1);
        candy.get(candyToBeDragged).setTag(background);
        candy.get(candyToBeReplaced).setTag(background1);
    }

    private void checkColumnsForThree() {
        for (int i = 0; i < 62; i++) {
            int chosenCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            Integer[] notValid = {6, 7, 14, 15, 22, 23, 30, 31, 38, 39, 46, 47, 54, 55};
            List<Integer> list = Arrays.asList(notValid);
            if (!list.contains(i)) {
                int x = i;
                if ((int) candy.get(x++).getTag() == chosenCandy && !isBlank && (int) candy.get(x++).getTag() == chosenCandy &&
                        (int) candy.get(x).getTag() == chosenCandy
                ) {
                    score = score + 3;
                    scoreTextView.setText(String.valueOf(score));
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);
                    x--;
                    candy.get(x).setImageResource(notCandy);
                    candy.get(x).setTag(notCandy);


                }
            }
        }
        moveDownCandies();
    }

    private void checkRowsForThree() {
        for (int i = 0; i < 47; i++) {
            int chosenCandy = (int) candy.get(i).getTag();
            boolean isBlank = (int) candy.get(i).getTag() == notCandy;
            int x = i;
            if ((int) candy.get(x).getTag() == chosenCandy && !isBlank && (int) candy.get(x + noOfBlock).getTag() == chosenCandy &&
                    (int) candy.get(x + 2 * noOfBlock).getTag() == chosenCandy
            ) {
                score = score + 3;
                scoreTextView.setText(String.valueOf(score));
                candy.get(x).setImageResource(notCandy);
                candy.get(x).setTag(notCandy);
                x = x + noOfBlock;
                candy.get(x).setImageResource(notCandy);
                candy.get(x).setTag(notCandy);
                x = x + noOfBlock;
                candy.get(x).setImageResource(notCandy);
                candy.get(x).setTag(notCandy);
            }

        }
        moveDownCandies();
    }

    private void moveDownCandies() {
        Integer[] firstRow = {0, 1, 2, 3, 4, 5, 6, 7};
        List<Integer> list = Arrays.asList(firstRow);
        for (int i = 55; i >= 0; i--) {
            if ((int) candy.get(i + noOfBlock).getTag() == notCandy) {
                candy.get(i + noOfBlock).setImageResource((int) candy.get(i).getTag());
                candy.get(i + noOfBlock).setTag((int) candy.get(i).getTag());
                candy.get(i).setImageResource(notCandy);
                candy.get(i).setTag(notCandy);
                if (list.contains(i) && (int) candy.get(i).getTag() == notCandy) {
                    int randomCandy = (int) Math.floor(Math.random() * candies.length);
                    candy.get(i).setImageResource(candies[randomCandy]);
                    candy.get(i).setTag(candies[randomCandy]);
                }
            }
        }
        for (int i = 0; i < 8; i++) {
            if ((int) candy.get(i).getTag() == notCandy) {
                int randomCandy = (int) Math.floor(Math.random() * candies.length);
                candy.get(i).setImageResource(candies[randomCandy]);
                candy.get(i).setTag(candies[randomCandy]);
            }
        }
    }

    Runnable repeatChecker = new Runnable() {
        @Override
        public void run() {
            try {
                checkColumnsForThree();
                checkRowsForThree();
                moveDownCandies();
            } finally {
                handler.postDelayed(repeatChecker, interval);
            }
        }
    };

    void startRepeat() {
        repeatChecker.run();
    }

    private void createBoard() {
        GridLayout gridLayout = findViewById(R.id.mainGridLayout);
        gridLayout.setRowCount(noOfBlock);
        gridLayout.setColumnCount(noOfBlock);

        gridLayout.getLayoutParams().width = withOfScreen;
        gridLayout.getLayoutParams().height = withOfScreen;

        for (int i = 0; i < noOfBlock * noOfBlock; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setId(i);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(withOfBlock, withOfBlock));
            imageView.setMaxHeight(withOfBlock);
            imageView.setMaxWidth(withOfBlock);
            int rndCandy = (int) Math.floor(Math.random() * candies.length);
            imageView.setImageResource(candies[rndCandy]);
            imageView.setTag(candies[rndCandy]);
            candy.add(imageView);
            gridLayout.addView(imageView);
        }
    }
}