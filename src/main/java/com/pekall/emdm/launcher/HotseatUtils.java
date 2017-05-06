package com.pekall.emdm.launcher;


import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.pekall.emdm.launcher.hotseatcheat.ContactsCheatActivity;
import com.pekall.emdm.launcher.hotseatcheat.MessageCheatActivity;
import com.pekall.emdm.launcher.hotseatcheat.PhoneCheatActivity;

import java.util.HashMap;

/**
 * hotseat 详细对应的 activity
 */
public final class HotseatUtils {

    public static final String TAG = "HotseatUtils";

    public static final String TYPE_PHONE = "Dialtacts";
    public static final String TYPE_CONTACTS = "Contacts";
    public static final String TYPE_MESSAGE = "Mms";
    public static final String TYPE_BROWSER = "Browser";

    private static final HashMap<String, Class> sHotseatAcClassMap = new HashMap<String, Class>();

    static {
        sHotseatAcClassMap.put(TYPE_PHONE, PhoneCheatActivity.class);
        sHotseatAcClassMap.put(TYPE_CONTACTS, ContactsCheatActivity.class);
        sHotseatAcClassMap.put(TYPE_MESSAGE, MessageCheatActivity.class);
    }

    public static ResolveInfo getHotseatResolveInfo(Context context, String type) {
        if (sHotseatAcClassMap.containsKey(type)) {
            Intent intent = new Intent();
            intent.setClass(context, sHotseatAcClassMap.get(type));
            final PackageManager packageManager = context.getPackageManager();
            return packageManager.resolveActivity(intent, 0);
        }
        return null;
    }

}
