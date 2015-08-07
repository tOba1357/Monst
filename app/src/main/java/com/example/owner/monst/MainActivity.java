package com.example.owner.monst;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.monst.HttpConectionTools.HttpCallBack;
import com.example.owner.monst.HttpConectionTools.HttpGet;
import com.example.owner.monst.HttpConectionTools.UrlData;

import java.net.MalformedURLException;
import java.util.List;


public class MainActivity extends Activity {
    final String TAG = MainActivity.class.getSimpleName();
    private Handler handler;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = null;
        setViews();
        setBtn();
    }

    private void setViews() {
        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.setFocusableInTouchMode(true);
    }

    private void setBtn() {
        Button button = (Button) findViewById(R.id.start_btn);
        button.setOnClickListener(clickListener);
    }


    private int getRadioBtnNum() {
        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radio_group);
        final int checkRadioNum = radioGroup.getCheckedRadioButtonId();
        final RadioButton radioButton = (RadioButton) findViewById(checkRadioNum);
        final String boarTitle = radioButton.getText().toString();
        final String rtnString = boarTitle.substring(0, boarTitle.indexOf("."));
        return Integer.parseInt(rtnString);
    }

    private void startSearchMonstQuest(final int boardNum) {
        final HttpGet httpGet = new HttpGet(getApplicationContext());
        try {
            httpGet.setUrl(UrlData.URL_TOP + boardNum);
            httpGet.setCallBack(httpCallBack);
            httpGet.execute();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            stopHandler();
            startSearchMonstQuest(getRadioBtnNum());
        }
    };

    HttpCallBack httpCallBack = new HttpCallBack() {
        @Override
        public void onEndHttpCommunication(String result) {
            Log.d(TAG, "Search");
            List<Quest> questList = HtmlAnalysis.splitQuest(result);
            for (Quest quest : questList) {
                if (quest.getContent().contains(getQuestName())) {
                    if ("数秒前".equals(quest.getTime())) {
                        stopHandler();
                        startMonst(quest.getUrl());
                        return;
                    }
                    break;
                }
            }
            startHandler();
        }

        @Override
        public void onErrorHttpCommunication() {
            Toast.makeText(getApplicationContext(), "エラー", Toast.LENGTH_LONG).show();
        }
    };


    private void startMonst(@NonNull final String url) {
        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.requestFocus();
        final Uri uri = Uri.parse(url);
        final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @NonNull
    private String getQuestName() {
        final EditText editText = (EditText) findViewById(R.id.quest_name);
        final String questName = editText.getText().toString();
        return questName;
    }

    private void startHandler() {
        handler = new Handler();
        handler.postDelayed(handlerRunnable, 1000);
    }

    private void stopHandler(){
        if(handler != null){
            handler.removeCallbacks(null);
        }
    }

    private Runnable handlerRunnable = new Runnable() {
        @Override
        public void run() {
            startSearchMonstQuest(getRadioBtnNum());
        }
    };
}
