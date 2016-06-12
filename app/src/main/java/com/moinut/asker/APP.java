package com.moinut.asker;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.moinut.asker.config.Const;
import com.moinut.asker.model.bean.User;
import com.moinut.asker.utils.SPUtils;

public class APP extends Application {

    private static Context mContext;
    private static User mUser;
    private static boolean mLogin;

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {

        }
        mContext = getApplicationContext();
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setUser(Context context, User user) {
        String userJson;
        mUser = user;
        if (user == null) {
            setLogin(false);
            userJson = "";
        } else {
            userJson = new Gson().toJson(user);
            setLogin(true);
        }
        SPUtils.put(context, Const.SP_KEY_USER, userJson);
    }

    public static void exitUser(Context context) {
        setLogin(false);
        mUser = null;
        SPUtils.remove(context, Const.SP_KEY_USER);
    }

    public static User getUser(Context context) {
        if (mUser == null) {
            String json = (String) SPUtils.get(context, Const.SP_KEY_USER, "");
            mUser = new Gson().fromJson(json, User.class);
        }
        return mUser;
    }

    public static boolean isLogin() {
        if (!mLogin) {
            String json = (String) SPUtils.get(mContext, Const.SP_KEY_USER, "");
            User user = new Gson().fromJson(json, User.class);
            mLogin = user != null;
        }
        return mLogin;
    }

    public static void setLogin(boolean login) {
        APP.mLogin = login;
    }
}
