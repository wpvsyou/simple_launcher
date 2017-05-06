package utility;

import android.content.Intent;

/**
 * 按钮模拟工具类
 * Created by Rocky on 2015/6/23.
 */
public class ButtonImitate {
    /**
     * 模拟点击home键，拉起launcher
     */
    public static void imitateHomeButton() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        CommonApplication.getCommonApplication().startActivity(intent);
    }
}
