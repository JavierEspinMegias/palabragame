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
import android.view.WindowManager;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Set;

public class    PalabroActivity extends AppCompatActivity {

    private String selectedWord;
    private String inputWord;
    private int round = 0;
    private ArrayList<Integer> aList = new ArrayList<>();
    private ArrayList<Integer> bList = new ArrayList<>();
    private ArrayList<Integer> cList = new ArrayList<>();
    private ArrayList<Integer> dList = new ArrayList<>();
    private ArrayList<Integer> eList = new ArrayList<>();
    private ArrayList<Integer> fList = new ArrayList<>();
    private String[] wordsList = new String[0];
    private ArrayList<ArrayList<Integer>> allLists = new ArrayList<>();
    private ArrayList<Integer> actualList;
    private HashMap<Integer, String> correctLetterPositions;
    private HashMap<String, Integer> letters = new HashMap<String, Integer>();
    private int selectedSpotId;

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


        correctLetterPositions = new HashMap<>();

        // Prepare keyboard behaviour
        createKeyboardMap();
        overrideKeyboardButtons();
        // Prepare spots behaviour
        overrideSpotsOnClick();
        selectCharSpot(R.id.a1, false);

        // Prepare ad
        AdView adView = findViewById(R.id.adView_game1);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);

        // Select word list by locale languaje
        wordsList = getResources().getStringArray(getWordsListByLocale());
        int randomPosition = new Random().nextInt(wordsList.length);
        selectedWord = wordsList[randomPosition];

        Toast.makeText(this, ""+selectedWord, Toast.LENGTH_SHORT).show();
    }

    private void createKeyboardMap(){
        letters.put("a", R.id.button_a);
        letters.put("b", R.id.button_b);
        letters.put("c", R.id.button_c);
        letters.put("d", R.id.button_d);
        letters.put("e", R.id.button_e);
        letters.put("f", R.id.button_f);
        letters.put("g", R.id.button_g);
        letters.put("h", R.id.button_h);
        letters.put("i", R.id.button_i);
        letters.put("j", R.id.button_j);
        letters.put("k", R.id.button_k);
        letters.put("l", R.id.button_l);
        letters.put("m", R.id.button_m);
        letters.put("n", R.id.button_n);
        letters.put("o", R.id.button_o);
        letters.put("p", R.id.button_p);
        letters.put("q", R.id.button_q);
        letters.put("r", R.id.button_r);
        letters.put("s", R.id.button_s);
        letters.put("t", R.id.button_t);
        letters.put("u", R.id.button_u);
        letters.put("v", R.id.button_v);
        letters.put("w", R.id.button_w);
        letters.put("x", R.id.button_x);
        letters.put("y", R.id.button_y);
        letters.put("z", R.id.button_z);
        letters.put("delete", R.id.button_delete);
        letters.put("send", R.id.button_send);
    }

    private void overrideSpotsOnClick() {
        TextView singleSpot;
        for (int i = 0; i < allLists.size(); i++) {
            for (int j = 0; j < allLists.get(i).size(); j++) {
                final int thisRow = i;
                final int positionId = allLists.get(i).get(j);
                singleSpot = findViewById(positionId);
                singleSpot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(thisRow == round){
                            selectCharSpot(selectedSpotId, true);
                            selectCharSpot(positionId, false);
                        }
                    }
                });
            }

        }
    }

    private void selectCharSpot(int positionId, boolean isDeselect){
        TextView textViewSpot = findViewById(positionId);
        if(isDeselect){
            textViewSpot.setBackground(getResources().getDrawable(R.drawable.round_box));
        }else{
            selectedSpotId = positionId;
            textViewSpot.setBackground(getResources().getDrawable(R.drawable.selected_spot));
        }
    }
    private void selectNextSpot(){
        TextView selectTextView;
        for (int i = 0; i < allLists.get(round).size()-1; i++) {
            selectTextView = findViewById(allLists.get(round).get(i+1));
            if(allLists.get(round).get(i)==selectedSpotId && selectTextView.getCurrentTextColor() != getResources().getColor(R.color.grey)){
                selectCharSpot(allLists.get(round).get(i+1), false);
                selectCharSpot(allLists.get(round).get(i), true);
                break;
            }
        }
    }
    private void removeSpot(){
        for (int i = 0; i < allLists.get(round).size(); i++) {
            if(allLists.get(round).get(i) == selectedSpotId){
                TextView textViewType = findViewById(getRow().get(i));
                textViewType.setText("");
                selectCharSpot(getRow().get(i), true);

                if(i > 0 ){
                    for (int j = i-1; j >= 0; j--) {
                        TextView previousTextView = findViewById(getRow().get(j));
                        if(previousTextView.getCurrentTextColor() != getResources().getColor(R.color.grey)){
                            selectCharSpot(getRow().get(j), false);
                            break;
                        }
                    }
                }
            }
        }
    }
    private void overrideKeyboardButtons(){
        for (int i = 0; i < letters.values().size(); i++) {
            TextView key_text = findViewById((Integer) letters.values().toArray()[i]);
            key_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(key_text.getId() == R.id.button_delete){
                        removeSpot();
                    }else if(key_text.getId() == R.id.button_send){
                        sendResult();
                    }else{
                        TextView textViewType = findViewById(selectedSpotId);
                        textViewType.setText(key_text.getText().toString());
                        selectNextSpot();
                    }
                }
            });
        }
    }
    private int getWordsListByLocale(){
        String locale = getResources().getConfiguration().locale.toString().toLowerCase();
        if (locale.contains("es")) {
            return R.array.esp_5_words;
        } else if (locale.contains("fr")) {
            return R.array.fra_5_words;
        } else if (locale.contains("it")) {
            return R.array.ita_5_words;
        } else if (locale.contains("de")) {
            return R.array.deu_5_words;
        }
        return R.array.eng_5_words;
    }
    public void sendResult(){
        actualList = getRow();
        inputWord = convertToWord(actualList);
        if(inputWord.length()<5){
            Toast.makeText(this, getResources().getString(R.string.not_5_characters_filled), Toast.LENGTH_SHORT).show();
        }else if(word_exists()){
            checkWord();
        }else{
            Toast.makeText(this, getResources().getString(R.string.word_does_not_exists)+""+convertToWord(actualList), Toast.LENGTH_SHORT).show();
        }
    }
    private boolean word_exists(){
        for(String singleWord:wordsList){
            if(convertToWord(actualList).equals(singleWord.toUpperCase())){
                return true;
            }
        }
        return false;
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
            default:
                return null;
        }
    }
    private String convertToWord(ArrayList<Integer> letterList){
        String singleWord = "";
        for(Integer letterId:letterList){
            TextView newEdit = findViewById(letterId);
            singleWord += newEdit.getText();
        }
        return singleWord.toUpperCase();
    }
    private boolean checkWord(){
        int incorrectSpotId = 10;
        TextView textViewKeyboardLetter;
        for (int i = 0; i < inputWord.length(); i++) {
            TextView thisSpotTextView = findViewById(actualList.get(i));
            textViewKeyboardLetter = findViewById(letters.get(inputWord.toLowerCase().toCharArray()[i]+""));
            if(inputWord.toLowerCase().toCharArray()[i] == selectedWord.toLowerCase().toCharArray()[i]) {

                correctLetterPositions.put(i, inputWord.toLowerCase().toCharArray()[i]+"");
                thisSpotTextView.setBackgroundColor(getResources().getColor(R.color.right));
                thisSpotTextView.setTextColor(getResources().getColor(R.color.grey));
                textViewKeyboardLetter.setBackgroundColor(getResources().getColor(R.color.right));

            }else if(selectedWord.toLowerCase().contains((inputWord.toCharArray()[i]+"").toLowerCase())){

                thisSpotTextView.setBackgroundColor(getResources().getColor(R.color.orange));
                textViewKeyboardLetter.setBackgroundColor(getResources().getColor(R.color.orange));

            }else{
                if(incorrectSpotId==10){
                    incorrectSpotId = i;
                }
                thisSpotTextView.setBackgroundColor(getResources().getColor(R.color.bad));
                textViewKeyboardLetter.setBackgroundColor(getResources().getColor(R.color.bad));
                textViewKeyboardLetter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        }

        if(round<5 && correctLetterPositions.size()<5 && correctLetterPositions.size()>0){
            Set<Integer> data;
            if(correctLetterPositions.keySet().size()>0){
                data = correctLetterPositions.keySet();
                for (int index:data){
                    TextView nextSpotTextView = findViewById(allLists.get(round+1).get(index));
                    nextSpotTextView.setBackgroundColor(getResources().getColor(R.color.right));
                    nextSpotTextView.setTextColor(getResources().getColor(R.color.grey));
                    nextSpotTextView.setText(selectedWord.toUpperCase().toCharArray()[index]+"");
                }
            }
        }else if(correctLetterPositions.size()==5){
//            Game win
            onButtonShowPopupWindowClick(true);
            return true;
        }
        passRound(incorrectSpotId);
        return false;
    }
    private boolean passRound(int incorrectSpotId){
        if(round<5){
            round+=1;
            actualList = getRow();
            if(incorrectSpotId!=10){
                selectCharSpot(allLists.get(round).get(incorrectSpotId), false);
            }
            makeTextViewsUnclickable();
        }else if(round == 5){
//            Game lost
            onButtonShowPopupWindowClick(false);
            return false;
        }
        return false;
    }

    private void makeTextViewsUnclickable(){
        for (int i = 0; i < correctLetterPositions.size(); i++) {
            TextView correctText = findViewById(allLists.get(round).get((Integer) correctLetterPositions.keySet().toArray()[0]));
            correctText.setClickable(false);
            correctText.setActivated(false);
            correctText.setFocusable(false);
            correctText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });
        }
    }
    public void onButtonShowPopupWindowClick(boolean isWin) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_window, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(this.getWindow().getDecorView().getRootView(), Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });

        TextView isWinText = popupView.findViewById(R.id.popup_win);
        if(!isWin){
            isWinText.setText(getResources().getString(R.string.is_lose));
        }
        TextView theWordWas = popupView.findViewById(R.id.popup_word);
        theWordWas.setText(theWordWas.getText().toString()+" "+selectedWord);

        Button newGameButton = popupView.findViewById(R.id.popup_button_new);
        newGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

        Button backGameButton = popupView.findViewById(R.id.popup_button_other);
        backGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}