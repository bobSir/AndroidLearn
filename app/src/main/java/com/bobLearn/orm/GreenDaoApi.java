package com.bobLearn.orm;

import android.content.Context;

import com.bobLearn.orm.greendao.DaoMaster;
import com.bobLearn.orm.greendao.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * created by cly on 2019-05-02
 */
public class GreenDaoApi {
    private static final String DB = "papa.db";
    private static DaoSession mDaoSession;

    public void setUpDataBase(Context context) {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(context, DB);
        Database database = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(database);
        mDaoSession = daoMaster.newSession();
    }

    public static DaoSession getmDaoSession() {
        return mDaoSession;
    }

    public static void insertUser(User user) {
        try {
            mDaoSession.getUserDao().insertOrReplace(user);
        }catch (Exception e){

        }
    }

    public static void updateUser(User user) {
        mDaoSession.getUserDao().update(user);
    }

    public static User getUser() {
        User user = mDaoSession.getUserDao().loadByRowId(1);
        return user;
    }
}
