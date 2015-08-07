package com.example.owner.monst;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
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
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    final String TAG = MainActivity.class.getSimpleName();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        Log.d(TAG, rtnString);
        return Integer.parseInt(rtnString);
    }

    final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText editText = (EditText) findViewById(R.id.quest_name);
            String questName = editText.getText().toString();
            HttpGet httpGet = new HttpGet(getApplicationContext());
            try {
                httpGet.setUrl(UrlData.URL_TOP + getRadioBtnNum());
                httpGet.setCallBack(httpCallBack);
                httpGet.execute();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    };

    HttpCallBack httpCallBack = new HttpCallBack() {
        @Override
        public void onEndHttpCommunication(String result) {
            List<Quest> questList = HtmlAnalysis.splitQuest(result);
            for(Quest quest : questList){
                if(quest.getContent().contains(getQuestName())){
                    startMonst(quest.getUrl());
                    return;
                }
            }
            Toast.makeText(getApplicationContext(), "クエストが見つかりませんでした", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onErrorHttpCommunication() {
            Toast.makeText(getApplicationContext(), "エラー", Toast.LENGTH_LONG).show();
        }
    };


    private void startMonst(@NonNull final String url){
        TextView textView = (TextView) findViewById(R.id.text_view);
        textView.requestFocus();
        final Uri uri = Uri.parse(url);
        final Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    @NonNull
    private String getQuestName(){
        final EditText editText = (EditText) findViewById(R.id.quest_name);
        final String questName = editText.getText().toString();
        return questName;
    }
}
