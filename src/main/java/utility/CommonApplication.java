package utility;

import android.app.Application;

/**
 * 公共全局的application，解决使用context不方便的问题
 * Created by Rocky on 2015/6/23.
 */
public class CommonApplication {
    private static Application mApplication;

    /**
     * 设置Application
     * @param pApplication Application
     */
    public static void setCommonApplication(Application pApplication) {
        mApplication = pApplication;
    }

    /**
     * 得到Application
     * @return Application
     */
    public static Application getCommonApplication() {
        return mApplication;
    }
}
