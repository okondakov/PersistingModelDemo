package com.ls.modelpersistingdemo.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ls.modelpersistingdemo.model.IDataStorage;
import com.ls.modelpersistingdemo.model.vo.OrganizationData;
import com.ls.modelpersistingdemo.model.vo.firebase.OrganizationVO;

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
        List<OrganizationVO> result = new ArrayList<>();
        for (OrganizationData organizationData : list) {
            result.add(new OrganizationVO(organizationData));
        }

        DatabaseReference rootNode = FirebaseDatabase.getInstance().getReference("organizations");

        rootNode.removeValue();

        for (OrganizationVO organization : result) {
            String organizationKey = rootNode.push().getKey();
            DatabaseReference organizationNode = rootNode.child(organizationKey);
            organizationNode.setValue(organization);
        }
    }

    @Override
    public void load(@NonNull final DataLoadListener listener) {
        DatabaseReference organizationListNode = FirebaseDatabase.getInstance().getReference("organizations");

        Query query = organizationListNode.orderByChild("name");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                List<OrganizationVO> list = new ArrayList<OrganizationVO>();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    OrganizationVO organization = snapshot.getValue(OrganizationVO.class);
                    list.add(organization);
                }

                List<OrganizationData> result = new ArrayList<>();
                for (OrganizationVO organizationVo : list) {
                    result.add(organizationVo.convertToOrganizationData());
                }

                listener.onDataLoaded(result);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}
