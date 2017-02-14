package com.ls.modelpersistingdemo.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.ls.modelpersistingdemo.R;
import com.ls.modelpersistingdemo.model.DataCache;
import com.ls.modelpersistingdemo.model.DataStorage;
import com.ls.modelpersistingdemo.model.IDataStorage;
import com.ls.modelpersistingdemo.model.vo.OrganizationData;
import com.ls.modelpersistingdemo.ui.Adapter.OrganizationListAdapter;

import java.util.List;


public class OrganizationListActivity extends AppCompatActivity implements OrganizationListAdapter.ActionListener, IDataStorage.DataLoadListener {

    private RecyclerView recyclerView;
    private OrganizationListAdapter organizationListAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_organization_list);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        organizationListAdapter = new OrganizationListAdapter(this, this);

        recyclerView.setAdapter(organizationListAdapter);

        refreshList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_add_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                Intent intent = new Intent(this, OrganizationDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(
                        OrganizationDetailsActivity.EXTRA_ORGANIZATION_DETAILS_ACTION,
                        OrganizationDetailsActivity.ACTION_ADD);
                intent.putExtras(bundle);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshList();
    }

    private void refreshList() {
        DataStorage.getInstance().load(this);
    }

    @Override
    public void onDataLoaded(List<OrganizationData> data) {
        DataCache.getInstance().setData(data);
        organizationListAdapter.refresh();
    }

    @Override
    public void onOrganizationItemClick(int organizationPosition) {
        Intent intent = new Intent(this, OrganizationDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(
                OrganizationDetailsActivity.EXTRA_ORGANIZATION_DETAILS_ACTION,
                OrganizationDetailsActivity.ACTION_UPDATE);
        bundle.putInt(
                OrganizationDetailsActivity.EXTRA_ORGANIZATION_POSITION,
                organizationPosition);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onOrganizationRemoveClick(int organizationPosition) {
        DataCache.getInstance().getData().remove(organizationPosition);
        DataStorage.getInstance().save(DataCache.getInstance().getData());
        refreshList();
    }

    @Override
    public void onMemberAddClick(int organizationPosition) {
        Intent intent = new Intent(this, MemberDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(
                MemberDetailsActivity.EXTRA_MEMBER_DETAILS_ACTION,
                MemberDetailsActivity.ACTION_ADD);
        bundle.putInt(
                MemberDetailsActivity.EXTRA_ORGANIZATION_POSITION,
                organizationPosition);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onMemberItemClick(int organizationPosition, int memberPosition) {
        Intent intent = new Intent(this, MemberDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString(
                MemberDetailsActivity.EXTRA_MEMBER_DETAILS_ACTION,
                MemberDetailsActivity.ACTION_UPDATE);
        bundle.putInt(
                MemberDetailsActivity.EXTRA_ORGANIZATION_POSITION,
                organizationPosition);
        bundle.putInt(
                MemberDetailsActivity.EXTRA_MEMBER_POSITION,
                memberPosition);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onMemberRemoveClick(int organizationPosition, int memberPosition) {
        DataCache.getInstance()
                .getData().get(organizationPosition)
                .members.remove(memberPosition);
        DataStorage.getInstance().save(DataCache.getInstance().getData());
        refreshList();
    }

}
