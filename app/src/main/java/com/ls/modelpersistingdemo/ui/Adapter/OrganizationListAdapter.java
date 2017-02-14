package com.ls.modelpersistingdemo.ui.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ls.modelpersistingdemo.R;
import com.ls.modelpersistingdemo.model.DataCache;
import com.ls.modelpersistingdemo.model.vo.MemberData;
import com.ls.modelpersistingdemo.model.vo.OrganizationData;

import java.util.ArrayList;
import java.util.List;


public class OrganizationListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int ADAPTER_ITEM_ORGANIZATION = 0;
    private static final int ADAPTER_ITEM_MEMBER_SUBHEADER = 1;
    private static final int ADAPTER_ITEM_MEMBER_DATA = 2;

    private ActionListener actionListener;
    private List<BaseAdapterItem> adapterList = new ArrayList<>();

    public interface ActionListener {
        void onOrganizationItemClick(int organizationPosition);

        void onOrganizationRemoveClick(int organizationPosition);

        void onMemberAddClick(int organizationPosition);

        void onMemberItemClick(int organizationPosition, int memberPosition);

        void onMemberRemoveClick(int organizationPosition, int memberPosition);
    }


    public OrganizationListAdapter(Context context, ActionListener actionListener) {
        this.actionListener = actionListener;
    }

    public void refresh() {
        adapterList = new ArrayList<>();

        for (int i = 0; i < DataCache.getInstance().getData().size(); i++) {
            final OrganizationData organization = DataCache.getInstance().getData().get(i);
            adapterList.add(new OrganizationAdapterItem(i));
            adapterList.add(new MemberSubheaderAdapterItem(i));

            for (int j=0; j<organization.members.size(); j++) {
                adapterList.add(new MemberDataAdapterItem(i, j));
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = null;

        switch (viewType) {
            case ADAPTER_ITEM_ORGANIZATION:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.i_organization,
                        viewGroup, false);
                return new OrganizationViewHolder(view);
            case ADAPTER_ITEM_MEMBER_SUBHEADER:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.i_member_subheader,
                        viewGroup, false);
                return new MemberSubheaderViewHolder(view);
            case ADAPTER_ITEM_MEMBER_DATA:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.i_member_data,
                        viewGroup, false);
                return new MemberDataViewHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        switch (viewHolder.getItemViewType()) {
            case ADAPTER_ITEM_ORGANIZATION:
                bindOrganizationView((OrganizationViewHolder) viewHolder, position);
                break;
            case ADAPTER_ITEM_MEMBER_SUBHEADER:
                bindMemberSubheaderView((MemberSubheaderViewHolder) viewHolder, position);
                break;
            case ADAPTER_ITEM_MEMBER_DATA:
                bindMemberDataView((MemberDataViewHolder) viewHolder, position);
        }
    }

    private void bindOrganizationView(OrganizationViewHolder viewHolder, int position) {
        final OrganizationAdapterItem adapterItem = (OrganizationAdapterItem) adapterList.get(position);
        final OrganizationData data = DataCache.getInstance().getData().get(adapterItem.organizationPosition);

        viewHolder.name.setText(data.name);
        viewHolder.yearOfFoundation.setText(String.valueOf(data.yearOfFoundation));

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionListener != null) {
                    actionListener.onOrganizationItemClick(adapterItem.organizationPosition);
                }
            }
        });

        viewHolder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionListener != null) {
                    actionListener.onOrganizationRemoveClick(adapterItem.organizationPosition);
                }
            }
        });
    }

    private void bindMemberSubheaderView(MemberSubheaderViewHolder viewHolder, int position) {
        final MemberSubheaderAdapterItem adapterItem = (MemberSubheaderAdapterItem) adapterList.get(position);

        if (position != adapterList.size() - 1 && adapterList.get(position + 1) instanceof OrganizationAdapterItem) {
            viewHolder.divider.setVisibility(View.VISIBLE);
        } else {
            viewHolder.divider.setVisibility(View.INVISIBLE);
        }

        viewHolder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionListener != null) {
                    actionListener.onMemberAddClick(adapterItem.organizationPosition);
                }
            }
        });
    }

    private void bindMemberDataView(MemberDataViewHolder viewHolder, int position) {
        final MemberDataAdapterItem adapterItem = (MemberDataAdapterItem) adapterList.get(position);
        final MemberData data = DataCache.getInstance()
                .getData().get(adapterItem.organizationPosition)
                .members.get(adapterItem.memberPosition);

        viewHolder.fullName.setText(data.fullName);
        viewHolder.position.setText(data.position);

        if (position != adapterList.size() - 1 && adapterList.get(position + 1) instanceof MemberDataAdapterItem) {
            viewHolder.divider.setVisibility(View.VISIBLE);
        } else {
            viewHolder.divider.setVisibility(View.INVISIBLE);
        }

        viewHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionListener != null) {
                    actionListener.onMemberItemClick(adapterItem.organizationPosition, adapterItem.memberPosition);
                }
            }
        });

        viewHolder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (actionListener != null) {
                    actionListener.onMemberRemoveClick(adapterItem.organizationPosition, adapterItem.memberPosition);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return adapterList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return adapterList.get(position).viewType;
    }

    private class OrganizationViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        TextView name;
        TextView yearOfFoundation;
        ImageView removeButton;

        OrganizationViewHolder(View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.fl_root_view);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            yearOfFoundation = (TextView) itemView.findViewById(R.id.tv_year_of_foundation);
            removeButton = (ImageView) itemView.findViewById(R.id.bt_remove);

        }
    }

    private class MemberSubheaderViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        ImageView addButton;
        View divider;

        MemberSubheaderViewHolder(View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.fl_root_view);
            addButton = (ImageView) itemView.findViewById(R.id.bt_add);
            divider = itemView.findViewById(R.id.v_divider);
        }
    }

    private class MemberDataViewHolder extends RecyclerView.ViewHolder {
        View rootView;
        TextView fullName;
        TextView position;
        ImageView removeButton;
        View divider;

        MemberDataViewHolder(View itemView) {
            super(itemView);

            rootView = itemView.findViewById(R.id.fl_root_view);
            fullName = (TextView) itemView.findViewById(R.id.tv_full_name);
            position = (TextView) itemView.findViewById(R.id.tv_position);
            removeButton = (ImageView) itemView.findViewById(R.id.bt_remove);
            divider = itemView.findViewById(R.id.v_divider);
        }
    }

    abstract class BaseAdapterItem {
        int viewType;

        BaseAdapterItem(int viewType) {
            this.viewType = viewType;
        }
    }

    private class OrganizationAdapterItem extends BaseAdapterItem {
        int organizationPosition;

        OrganizationAdapterItem(int organizationPosition) {
            super(ADAPTER_ITEM_ORGANIZATION);
            this.organizationPosition = organizationPosition;
        }
    }

    private class MemberSubheaderAdapterItem extends BaseAdapterItem {
        int organizationPosition;

        MemberSubheaderAdapterItem(int organizationPosition) {
            super(ADAPTER_ITEM_MEMBER_SUBHEADER);
            this.organizationPosition = organizationPosition;
        }
    }

    private class MemberDataAdapterItem extends BaseAdapterItem {
        int organizationPosition;
        int memberPosition;

        MemberDataAdapterItem(int organizationPosition, int memberPosition) {
            super(ADAPTER_ITEM_MEMBER_DATA);
            this.organizationPosition = organizationPosition;
            this.memberPosition = memberPosition;
        }
    }
}
