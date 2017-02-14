package com.ls.modelpersistingdemo.model;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;
import com.ls.modelpersistingdemo.model.DatabaseHelper;
import com.ls.modelpersistingdemo.model.IDataStorage;
import com.ls.modelpersistingdemo.model.vo.MemberData;
import com.ls.modelpersistingdemo.model.vo.OrganizationData;
import com.ls.modelpersistingdemo.model.vo.ormlite.MemberVO;
import com.ls.modelpersistingdemo.model.vo.ormlite.OrganizationVO;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class DataStorage implements IDataStorage {
    private static DataStorage instance;

    public static IDataStorage getInstance() {
        if (instance == null) {
            instance = new DataStorage();
        }
        return instance;
    }

    @Override
    public void save(List<OrganizationData> list) {
        List<OrganizationVO> organizationList = new ArrayList<>();
        List<MemberVO> memberList = new ArrayList<>();

        for (OrganizationData organizationData : list) {
            OrganizationVO organizationVO = new OrganizationVO(organizationData);
            organizationList.add(organizationVO);

            for (MemberData memberData : organizationData.members) {
                memberList.add(new MemberVO(memberData, organizationVO));
            }
        }

        try {
            final Dao<OrganizationVO, Integer> organizationDAO = DatabaseHelper.getInstance().getOrganizationDao();
            final Dao<MemberVO, Integer> memberDAO = DatabaseHelper.getInstance().getMemberDao();

            TableUtils.dropTable(DatabaseHelper.getInstance().getConnectionSource(), OrganizationVO.class, false);
            TableUtils.createTable(DatabaseHelper.getInstance().getConnectionSource(),  OrganizationVO.class);
            TableUtils.dropTable(DatabaseHelper.getInstance().getConnectionSource(), MemberVO.class, false);
            TableUtils.createTable(DatabaseHelper.getInstance().getConnectionSource(),  MemberVO.class);

            for (OrganizationVO organization : organizationList) {
                organizationDAO.create(organization);
            }

            for (MemberVO member : memberList) {
                memberDAO.create(member);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void load(DataLoadListener listener) {
        try {
            ArrayList<OrganizationData> result = new ArrayList<>();

            final Dao<MemberVO, Integer> memberDAO = DatabaseHelper.getInstance().getMemberDao();
            final Dao<OrganizationVO, Integer> organizationDAO = DatabaseHelper.getInstance().getOrganizationDao();

            List<OrganizationVO> organizationList = organizationDAO.queryForAll();

            for (OrganizationVO organization : organizationList) {
                QueryBuilder<OrganizationVO, Integer> organizationsQueryBuilder = organizationDAO.queryBuilder();
                organizationsQueryBuilder.where().eq("id", organization.id);
                QueryBuilder<MemberVO, Integer> membersQueryBuilder = memberDAO.queryBuilder();

                List<MemberVO> memberList = membersQueryBuilder.join(organizationsQueryBuilder).query();

                result.add(organization.convertToOrganizationData(memberList));
            }

            listener.onDataLoaded(result);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
