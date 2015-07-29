package com.example.owner.monst;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.owner.monst.HttpConectionTools.HttpCallBack;
import com.example.owner.monst.HttpConectionTools.HttpGet;
import com.example.owner.monst.HttpConectionTools.UrlData;

import java.net.MalformedURLException;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setBtn();
    }

    private void setBtn(){
        Button button = (Button) findViewById(R.id.start_btn);
        button.setOnClickListener(clickListener);
    }

    View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EditText editText = (EditText) findViewById(R.id.quest_name);
            String questName = editText.getText().toString();
            HttpGet httpGet = new HttpGet(getApplicationContext());
            try {
                httpGet.setUrl(UrlData.URL_TOP + "1");
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
            TextView textView = (TextView)findViewById(R.id.result);
            textView.setText(result);
        }

        @Override
        public void onErrorHttpCommunication() {
            Toast.makeText(getApplicationContext(), "エラー", Toast.LENGTH_LONG).show();
        }
    };


}
