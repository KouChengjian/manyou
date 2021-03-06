package com.acg12.lib.app;

import android.app.Application;
import android.content.Context;

import java.lang.reflect.Method;

/**
 * Created with Android Studio.
 * User: kcj
 * Date: 2019/2/21 11:38
 * Description:
 */
public final class BaseApp {

    private BaseApp() {
    }

    public static boolean isDebug() {
        return Ext.debug;
    }

    public static Application app() {
        if (Ext.app == null) {
            try {
                // 在IDE进行布局预览时使用
                Class<?> renderActionClass = Class.forName("com.android.layoutlib.bridge.impl.RenderAction");
                Method method = renderActionClass.getDeclaredMethod("getCurrentContext");
                Context context = (Context) method.invoke(null);
                Ext.app = new MockApplication(context);
            } catch (Throwable ignored) {
                throw new RuntimeException("please invoke x.Ext.init(app) on Application#onCreate()" + " and register your Application in manifest.");
            }
        }
        return Ext.app;
    }


    public static class Ext {
        private static boolean debug;
        private static Application app;

        private Ext() {
        }

        public static void init(Application app) {
            if (Ext.app == null) {
                Ext.app = app;
            }
        }

        public static void setDebug(boolean debug) {
            Ext.debug = debug;
        }

    }

    private static class MockApplication extends Application {
        public MockApplication(Context baseContext) {
            this.attachBaseContext(baseContext);
        }
    }
}
