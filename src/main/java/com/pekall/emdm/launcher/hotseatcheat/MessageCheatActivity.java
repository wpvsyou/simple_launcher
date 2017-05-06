package com.pekall.emdm.launcher.hotseatcheat;


import android.os.Bundle;

public class MessageCheatActivity extends BaseCheatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cheatAndLaunch(TYPE_MESSAGE);
    }
}
