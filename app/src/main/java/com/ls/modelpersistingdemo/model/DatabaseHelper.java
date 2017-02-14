package com.ls.modelpersistingdemo.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ls.modelpersistingdemo.MyApplication;
import com.ls.modelpersistingdemo.R;
import com.ls.modelpersistingdemo.model.vo.ormlite.MemberVO;
import com.ls.modelpersistingdemo.model.vo.ormlite.OrganizationVO;

import java.sql.SQLException;


public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "ormlite.db";
    private static final int DATABASE_VERSION = 1;

    private static DatabaseHelper instance;

    private Dao<OrganizationVO, Integer> organizationDao;
    private Dao<MemberVO, Integer> memberDao;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION, R.raw.database_configuration);
    }

    public static DatabaseHelper getInstance() {
        if (instance == null) {
            instance = OpenHelperManager.getHelper(MyApplication.getSharedContext(), DatabaseHelper.class);
        }

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, OrganizationVO.class);
            TableUtils.createTable(connectionSource, MemberVO.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqliteDatabase, ConnectionSource connectionSource, int oldVer, int newVer) {
        try {
            TableUtils.dropTable(connectionSource, OrganizationVO.class, true);
            onCreate(sqliteDatabase, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<OrganizationVO, Integer> getOrganizationDao() throws SQLException {
        if (organizationDao == null) {
            organizationDao = getDao(OrganizationVO.class);
        }
        return organizationDao;
    }

    public Dao<MemberVO, Integer> getMemberDao() throws SQLException {
        if (memberDao == null) {
            memberDao = getDao(MemberVO.class);
        }
        return memberDao;
    }
}
