package com.ls.modelpersistingdemo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ls.modelpersistingdemo.R;
import com.ls.modelpersistingdemo.model.DataCache;
import com.ls.modelpersistingdemo.model.DataStorage;
import com.ls.modelpersistingdemo.model.vo.MemberData;
import com.ls.modelpersistingdemo.model.vo.OrganizationData;


public class MemberDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_MEMBER_DETAILS_ACTION = "member_details_action";
    public static final String EXTRA_ORGANIZATION_POSITION = "organization_position";
    public static final String EXTRA_MEMBER_POSITION = "member_position";
    public static final String ACTION_ADD = "action_add";
    public static final String ACTION_UPDATE = "action_update";

    private EditText fullNameEditText;
    private EditText positionEditText;

    private OrganizationData organization;
    private int memberPosition;
    private MemberData member;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_member_details);

        fullNameEditText = (EditText) findViewById(R.id.et_full_name);
        positionEditText = (EditText) findViewById(R.id.et_position);

        final int organizationPosition = getIntent().getIntExtra(EXTRA_ORGANIZATION_POSITION, 0);
        organization = DataCache.getInstance().getData().get(organizationPosition);

        final String action = getIntent().getStringExtra(EXTRA_MEMBER_DETAILS_ACTION);
        if (getIntent().hasExtra(EXTRA_MEMBER_POSITION)) {
            memberPosition = getIntent().getIntExtra(EXTRA_MEMBER_POSITION, 0);
            member = organization.members.get(memberPosition);

            fullNameEditText.setText(member.fullName);
            positionEditText.setText(String.valueOf(member.position));
        }

        Button confirmButton = (Button) findViewById(R.id.bt_confirm);
        if (action.equals(ACTION_ADD)) {
            setTitle(getString(R.string.add_member));
            confirmButton.setText(getString(R.string.add));
        } else if (action.equals(ACTION_UPDATE)) {
            setTitle(getString(R.string.update_member));
            confirmButton.setText(getString(R.string.update));
        }

        findViewById(R.id.bt_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String fullName = fullNameEditText.getText().toString();
                final String position = positionEditText.getText().toString();

                if (action.equals(ACTION_ADD)) {
                    organization.members.add(new MemberData(fullName, position));
                } else if (action.equals(ACTION_UPDATE)) {
                    member.fullName = fullName;
                    member.position = position;

                    organization.members.set(memberPosition, member);
                }

                DataStorage.getInstance().save(DataCache.getInstance().getData());

                finish();
            }
        });

    }

}
