/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pekall.emdm.launcher;

import android.app.Application;
import android.content.*;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
//
//import com.pekall.emdm.pcpchild.PcpConstants;
//import com.pekall.emdm.pcpchild.PolicyDeployReceiver;

import java.lang.ref.WeakReference;

public class LauncherApplication extends Application{
    public LauncherModel mModel;
    public IconCache mIconCache;
    private static boolean sIsScreenLarge;
    private static float sScreenDensity;
    private static int sLongPressTimeout = 300;
    private static final String sSharedPreferencesKey = "com.android.launcher2.prefs";
    WeakReference<LauncherProvider> mLauncherProvider;
    private static Context mContext;
    private static LauncherApplication mInstance;
    private LocalBroadcastManager mManager;
    /*public LauncherApplication(Context ctx){
        super(ctx);
        mContext = ctx;
        mInstance = this;
    }*/

    public static LauncherApplication getInstance(){
        return mInstance;
    }

    public static Context getmContext(){
        return mContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        mInstance = this;
        LauncherProvider.setLaucherProvier();

        // set sIsScreenXLarge and sScreenDensity *before* creating icon cache
        sIsScreenLarge = mContext.getResources().getBoolean(R.bool.is_large_screen);
        sScreenDensity = mContext.getResources().getDisplayMetrics().density;
        mManager = LocalBroadcastManager.getInstance(mContext);

        mIconCache = new IconCache(mContext);
        mModel = new LauncherModel(mContext, mIconCache);

        // Register intent receivers
        /*IntentFilter filter = new IntentFilter(PolicyDeployReceiver.ACTION_POLICY_DEPLOY);
//        filter.addDataScheme("package");
//        mContext.registerReceiver(mModel, filter);
//        mManager.registerReceiver(mModel, filter);
//        filter = new IntentFilter();
        filter.addAction(Intent.ACTION_LOCALE_CHANGED);
        filter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
        filter.addAction(PcpConstants.ACTION_HIDE_APPS);*/
//        mContext.registerReceiver(mModel, filter);
//        mManager.registerReceiver(mModel, filter);
        /*filter = new IntentFilter();
        filter.addAction(SearchManager.INTENT_GLOBAL_SEARCH_ACTIVITY_CHANGED);
        mContext.registerReceiver(mModel, filter);
        filter = new IntentFilter();
        filter.addAction(SearchManager.INTENT_ACTION_SEARCHABLES_CHANGED);
        mContext.registerReceiver(mModel, filter);*/
        // Register for changes to the favorites
        ContentResolver resolver = mContext.getContentResolver();
        resolver.registerContentObserver(LauncherSettings.Favorites.CONTENT_URI, true,
                mFavoritesObserver);
    }

   /* *//**
     * There's no guarantee that this function is ever called.
     *//*
    @Override
    public void onTerminate() {
        mContext.unregisterReceiver(mModel);

        ContentResolver resolver = getContentResolver();
        resolver.unregisterContentObserver(mFavoritesObserver);
    }

    @Override
    public void onConfigurationChanged(Configuration configuration) {

    }

    @Override
    public void onLowMemory() {

    }

    @Override
    public void onTrimMemory(int i) {

    }

    @Override
    public void registerComponentCallbacks(ComponentCallbacks componentCallbacks) {

    }

    @Override
    public void unregisterComponentCallbacks(ComponentCallbacks componentCallbacks) {

    }

    @Override
    public void registerActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {

    }

    @Override
    public void unregisterActivityLifecycleCallbacks(Application.ActivityLifecycleCallbacks activityLifecycleCallbacks) {

    }*/

    /**
     * Receives notifications whenever the user favorites have changed.
     */
    private final ContentObserver mFavoritesObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            // If the database has ever changed, then we really need to force a reload of the
            // workspace on the next load
            mModel.resetLoadedState(false, true);
            mModel.startLoaderFromBackground();
        }
    };

    LauncherModel setLauncher(Launcher launcher) {
        mModel.initialize(launcher);
        return mModel;
    }

    IconCache getIconCache() {
        return mIconCache;
    }

    LauncherModel getModel() {
        return mModel;
    }

    void setLauncherProvider(LauncherProvider provider) {
        mLauncherProvider = new WeakReference<LauncherProvider>(provider);
    }

    LauncherProvider getLauncherProvider() {
        return mLauncherProvider.get();
    }

    public static String getSharedPreferencesKey() {
        return sSharedPreferencesKey;
    }

    public static boolean isScreenLarge() {
        return sIsScreenLarge;
    }

    public static boolean isScreenLandscape(Context context) {
        return context.getResources().getConfiguration().orientation ==
            Configuration.ORIENTATION_LANDSCAPE;
    }

    public static float getScreenDensity() {
        return sScreenDensity;
    }

    public static int getLongPressTimeout() {
        return sLongPressTimeout;
    }
}
