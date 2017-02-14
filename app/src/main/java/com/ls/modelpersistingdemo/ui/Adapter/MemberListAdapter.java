package com.ls.modelpersistingdemo.ui.Adapter;


public class MemberListAdapter {/* extends BaseExpandableListAdapter {
    private Context context;
    private List<MemberVO> memberList;

    public MemberListAdapter(Context context, List<MemberVO> memberList) {
        this.context = context;
        this.memberList = memberList;
    }

    @Override
    public Object getChild(int listPosition, int expandedListPosition) {
        return memberList.get(expandedListPosition);
    }

    @Override
    public long getChildId(int listPosition, int expandedListPosition) {
        return expandedListPosition;
    }

    @Override
    public View getChildView(int listPosition, final int expandedListPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.i_member_data, null);
        }

        MemberVO item = (MemberVO) getChild(listPosition, expandedListPosition);

        TextView fullNameTextView = (TextView) convertView.findViewById(R.id.tv_full_name);
        fullNameTextView.setText(item.fullName);

        TextView positionTextView = (TextView) convertView.findViewById(R.id.tv_position);
        positionTextView.setText(item.position);

        ImageView removeButton = (ImageView) convertView.findViewById(R.id.bt_remove);
        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO
            }
        });

        return convertView;
    }

    @Override
    public int getChildrenCount(int listPosition) {
        return memberList.size();
    }

    @Override
    public Object getGroup(int listPosition) {
        return context.getString(R.string.members);
    }

    @Override
    public int getGroupCount() {
        return 1;
    }

    @Override
    public long getGroupId(int listPosition) {
        return listPosition;
    }

    @Override
    public View getGroupView(int listPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.i_member_subheader, null);
        }

        String listTitle = (String) getGroup(listPosition);

        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.tv_list_title);
        listTitleTextView.setText(listTitle);

        ImageView addButton = (ImageView) convertView.findViewById(R.id.bt_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, MemberDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString(
                        MemberDetailsActivity.EXTRA_MEMBER_DETAILS_ACTION,
                        MemberDetailsActivity.ACTION_ADD);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });


        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int listPosition, int expandedListPosition) {
        return false;
    }*/
}
