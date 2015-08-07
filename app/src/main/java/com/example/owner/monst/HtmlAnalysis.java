package com.example.owner.monst;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Owner on 2015/08/07.
 */
public class HtmlAnalysis {
    private static final String TAG = HtmlAnalysis.class.getSimpleName();

    public static void main(String args[]) {
        List<String> quests = splitQuestString(TestHtml.html);
        for(String quest : quests){
            System.out.println(quest);
        }
    }

    public static List<Quest> splitQuest(@NonNull final String html){
        final List<String> stringList = splitQuestString(html);
        final List<Quest> questList = new ArrayList<>();
        for(String questStr : stringList){
            questList.add(Quest.makeQuestFromHtml(questStr));
        }
        return questList;
    }

    @NonNull
    public static List<String> splitQuestString(@NonNull final String html) {
        final String beginTag = "<div class=\"bbs-post bbs-content\"  >";
        final List<String> result = new ArrayList<>();
        String tmp = html;

        while (true) {
            final int questsIdx = tmp.indexOf(beginTag);
            if (questsIdx == -1) {
                break;
            }
            tmp = tmp.substring(questsIdx);
            final int endQuestIdx = HtmlAnalysis.searchEndTagIndex(tmp);
            result.add(tmp.substring(0, endQuestIdx));
            tmp  = tmp.substring(endQuestIdx);
        }

        return result;
    }

    public static int searchEndTagIndex(@NonNull final String html) {
        int idx = 1, depth = 1;
        while (depth > 0) {
            idx = html.indexOf("<", idx);
            if (idx == -1) {
                return -1;
            }
            final String tag = getTag(html.substring(idx));
            if (!tag.startsWith("br")) {
                if (tag.charAt(0) == '/') {
                    depth--;
                } else {
                    depth++;
                }
            }
            idx++;
        }

        return html.indexOf(">", idx) + 1;
    }


    private static String getTag(@NonNull final String html) {
        int lastIndex = Math.min(html.indexOf(" "), html.indexOf(">"));
        return html.substring(1, lastIndex);
    }
}
