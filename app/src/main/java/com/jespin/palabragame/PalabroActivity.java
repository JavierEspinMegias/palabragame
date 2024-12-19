package com.jespin.palabragame;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowMetrics;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.ColorKt;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.libraries.ads.mobile.sdk.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.Random;

public class PalabroActivity extends AppCompatActivity {


    private String selectedWord;
    private String inputWord;
    private int round = 0;
    private ArrayList<Integer> aList = new ArrayList<>();
    private ArrayList<Integer> bList = new ArrayList<>();
    private ArrayList<Integer> cList = new ArrayList<>();
    private ArrayList<Integer> dList = new ArrayList<>();
    private ArrayList<Integer> eList = new ArrayList<>();
    private ArrayList<Integer> fList = new ArrayList<>();

    private ArrayList<ArrayList<Integer>> allLists = new ArrayList<>();
    private ArrayList<Integer> actualList;
    private ArrayList<Integer> correctLetterPositions;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_palabro);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ArrayList<String> wordList = new ArrayList<>();
        wordList.add("saaaa");
        wordList.add("saaaa");
        wordList.add("saaaa");
        wordList.add("saaaa");
        wordList.add("saaaa");

        Random r = new Random();
        int result = r.nextInt(wordList.size());
        selectedWord = wordList.get(result);

        aList.add(R.id.a1);
        aList.add(R.id.a2);
        aList.add(R.id.a3);
        aList.add(R.id.a4);
        aList.add(R.id.a5);

        bList.add(R.id.b1);
        bList.add(R.id.b2);
        bList.add(R.id.b3);
        bList.add(R.id.b4);
        bList.add(R.id.b5);

        cList.add(R.id.c1);
        cList.add(R.id.c2);
        cList.add(R.id.c3);
        cList.add(R.id.c4);
        cList.add(R.id.c5);

        dList.add(R.id.d1);
        dList.add(R.id.d2);
        dList.add(R.id.d3);
        dList.add(R.id.d4);
        dList.add(R.id.d5);

        eList.add(R.id.e1);
        eList.add(R.id.e2);
        eList.add(R.id.e3);
        eList.add(R.id.e4);
        eList.add(R.id.e5);

        fList.add(R.id.f1);
        fList.add(R.id.f2);
        fList.add(R.id.f3);
        fList.add(R.id.f4);
        fList.add(R.id.f5);

        allLists.add(aList);
        allLists.add(bList);
        allLists.add(cList);
        allLists.add(dList);
        allLists.add(eList);
        allLists.add(fList);


        correctLetterPositions = new ArrayList<>();
        correctLetterPositions.clear();

        setAutoMoveNextChar();

        Toast.makeText(this, "" + selectedWord, Toast.LENGTH_SHORT).show();


        // Initialize the Mobile Ads SDK
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
                Toast.makeText(this, " successful ", Toast.LENGTH_SHORT).show();
            }
        });

        AdView mAdView;
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void sendResult(View v){
        actualList = getRow();
        inputWord = convertToWord(actualList);
        checkWord(inputWord);
    }

    private ArrayList<Integer> getRow(){
        switch (round){
            case 0:
                return aList;
            case 1:
                return bList;
            case 2:
                return cList;
            case 3:
                return dList;
            case 4:
                return eList;
            case 5:
                return fList;
        }
        return null;
    }

    private String convertToWord(ArrayList<Integer> letterList){
        String singleWord = "";
        for(Integer letterId:letterList){
            EditText newEdit = findViewById(letterId);
            singleWord += newEdit.getText();
        }
        return singleWord;
    }
    private void checkWord(String inputWord){
        if(inputWord.length()>4){
            for (int i = 0; i < inputWord.length(); i++) {
                EditText thisEdit = findViewById(actualList.get(i));
                if(inputWord.toCharArray()[i]==selectedWord.toCharArray()[i]) {
                    correctLetterPositions.add(i);
                    thisEdit.setBackgroundColor(Color.GREEN);
                }else if(selectedWord.contains(inputWord.toCharArray()[i]+"")){
                    thisEdit.setBackgroundColor(Color.LTGRAY);
                }else{
                    thisEdit.setBackgroundColor(Color.RED);
                }
            }
            closeEditTexts(actualList);
            if(openEditTexts()){
//                Win game


            }
        }else{
            Toast.makeText(this, "Not 5 characters filled", Toast.LENGTH_SHORT).show();
        }
    }

    private void closeEditTexts(ArrayList<Integer> closeList){
        for(Integer position:closeList){
            EditText thisEdit = findViewById(position);
            thisEdit.setFocusable(false);
            thisEdit.setClickable(false);
        }
        round +=1;
    }
    private boolean openEditTexts(){
        int rightLetters = 0;
        ArrayList<Integer> rightPositions = new ArrayList<>();
        for (int i = 0; i < selectedWord.length(); i++) {
            EditText thisEdit = findViewById(getRow().get(i));
            if((inputWord.toCharArray()[i]+"").equals(selectedWord.toCharArray()[i]+"")){
                rightPositions.add(i);
                rightLetters+=1;
            }else{
                thisEdit.setFocusable(true);
                thisEdit.setClickable(true);
            }
        }
        if(rightLetters==5){
            onButtonShowPopupWindowClick(true);
            Toast.makeText(this, "Win game at round "+round, Toast.LENGTH_SHORT).show();
            return true;
        }else{
            for (int i = 0; i < rightPositions.size(); i++) {
                EditText thisEdit = findViewById(getRow().get(rightPositions.get(i)));
                thisEdit.setBackgroundColor(Color.GREEN);
                thisEdit.setText(selectedWord.toCharArray()[rightPositions.get(i)]+"");
            }
        }
        return false;
    }

    private void setAutoMoveNextChar(){
        for(ArrayList<Integer> list:allLists) {
            for (int i = 0; i < list.size()-1; i++) {
                if(i<list.size()){
                    EditText thisEdit = findViewById(list.get(i));
                    EditText nextEdit = findViewById(list.get(i+1));

                    thisEdit.addTextChangedListener(new TextWatcher() {

                        public void onTextChanged(CharSequence s, int start,int before, int count)
                        {
                            // TODO Auto-generated method stub
                            if(thisEdit.getText().toString().length()==1)     //size as per your requirement
                            {
                                nextEdit.requestFocus();
                            }
                        }
                        public void beforeTextChanged(CharSequence s, int start,
                                                      int count, int after) {
                            // TODO Auto-generated method stub

                        }

                        public void afterTextChanged(Editable s) {
                            // TODO Auto-generated method stub
                        }

                    });
                }
            }
        }
    }


    public void onButtonShowPopupWindowClick(boolean isWin) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);


        TextView textViewIsWin = popupView.findViewById(R.id.popup_win);
        if(!isWin){
            textViewIsWin.setText(R.string.is_lose);
        }
        TextView textViewWord = popupView.findViewById(R.id.popup_word);
        textViewWord.setText(textViewWord.getText().toString()+" "+selectedWord);

        Button buttonNewGame = popupView.findViewById(R.id.popup_button_new);
        buttonNewGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(PalabroActivity.this, PalabroActivity.class);
                startActivity(k);
            }
        });

        Button buttonBack = popupView.findViewById(R.id.popup_button_other);
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(PalabroActivity.this, MainActivity3.class);
                startActivity(k);
            }
        });

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }
}