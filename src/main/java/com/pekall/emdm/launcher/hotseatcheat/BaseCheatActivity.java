package com.pekall.emdm.launcher.hotseatcheat;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class BaseCheatActivity extends Activity {

    private static final String TAG = "CheatActivity";

    public static final String TYPE_PHONE = "Dialtacts";
    public static final String TYPE_CONTACTS = "Contacts";
    public static final String TYPE_MESSAGE = "Mms";

    private static final String MESSAGE_CATEGORY = "android.intent.category.APP_MESSAGING";

    private static final List<Intent> sMessageIntent = new ArrayList<Intent>();

    //适配进入短消息列表的intent
    static {
        sMessageIntent.add(new Intent(Intent.ACTION_MAIN).addCategory(MESSAGE_CATEGORY));
        sMessageIntent.add(new Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_DEFAULT).setType("vnd.android-dir/mms-sms"));
        sMessageIntent.add(new Intent(Intent.ACTION_VIEW).setData(Uri.parse("sms:")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void cheatAndLaunch(String type) {
        Intent intent = null;
        if (TYPE_PHONE.equals(type)) {
            intent = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("tel:"));
        } else if (TYPE_CONTACTS.equals(type)) {
            intent = new Intent(Intent.ACTION_VIEW).setData(ContactsContract.Contacts.CONTENT_URI);
        }

        if (TYPE_MESSAGE.equals(type)) {
            loopLaunchMessageIntent();
        } else {
            launchIntent(intent);
        }

        finish();
    }

    private boolean launchIntent(Intent intent) {
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        boolean result = false;
        try {
            startActivity(intent);
            result = true;
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void loopLaunchMessageIntent() {
        for (Intent intent : sMessageIntent) {
            Log.e(TAG, "message launch intent:" + intent);
            if (launchIntent(intent)) {
                break;
            }
        }
    }
}
