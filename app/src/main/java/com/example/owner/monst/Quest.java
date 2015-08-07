package com.example.owner.monst;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.Stack;

/**
 * Created by Owner on 2015/08/07.
 */
public class Quest {
    private final String content;
    private final String id;
    private final String url;
    private final String time;

    public static void main(String args[]){
        final Quest quest = Quest.makeQuestFromHtml(TestHtml.quest);
        System.out.println(quest.toString());
    }

    public Quest(String content, String id, String url, String time) {
        this.content = content;
        this.id = id;
        this.url = url;
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getTime() {
        return time;
    }

    public String getUrl() {
        return url;
    }

    @NonNull
    public static Quest makeQuestFromHtml(@NonNull String html){
        return new Quest(
                html,
                getIdFromHtml(html),
                getUrlFromHtml(html),
                getTimeFromHtml(html)
        );
    }

    @Nullable
    private static String getIdFromHtml(@NonNull String html){
        final String beginTag = "<span class=\"bbs-post-number\">";
        final String endTag = "</span>";
        final int beginIdx = html.indexOf(beginTag);
        if(beginIdx == -1){
            return null;
        }
        final int endIdx = html.indexOf(endTag, beginIdx);
        if(endIdx == -1){
            return null;
        }
        return html.substring(beginIdx + beginTag.length(), endIdx);
    }

    @Nullable
    private static String getUrlFromHtml(@NonNull String html){
        final int UrlLength = 61;
        final String beginString = "http://static.monster-strike.com/line/?pass_code=";
        final int beginIdx = html.indexOf(beginString);
        if(beginIdx == -1){
            return null;
        }
        return html.substring(beginIdx, beginIdx + UrlLength);
    }

    @Nullable
    private static String getTimeFromHtml(@NonNull String html){
        final String beginTag = "<span class=\"bbs-posted-time\">";
        final String endTag = "</span>";
        final int beginIdx = html.indexOf(beginTag);
        if(beginIdx == -1){
            return null;
        }
        final int endIdx = html.indexOf(endTag, beginIdx);
        if(endIdx == -1){
            return null;
        }
        return html.substring(beginIdx + beginTag.length(), endIdx);
    }

    @Override
    public String toString(){
        return  "content:" + content + "\n" +
                "id:" + id + "\n" +
                "url:" + url + "\n" +
                "time:" + time + "\n";

    }
}

