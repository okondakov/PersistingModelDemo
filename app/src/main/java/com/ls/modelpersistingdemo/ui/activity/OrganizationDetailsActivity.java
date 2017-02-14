package com.ls.modelpersistingdemo.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ls.modelpersistingdemo.R;
import com.ls.modelpersistingdemo.model.DataCache;
import com.ls.modelpersistingdemo.model.DataStorage;
import com.ls.modelpersistingdemo.model.vo.OrganizationData;


public class OrganizationDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_ORGANIZATION_DETAILS_ACTION = "organization_details_action";
    public static final String EXTRA_ORGANIZATION_POSITION = "organization_position";
    public static final String ACTION_ADD = "action_add";
    public static final String ACTION_UPDATE = "action_update";

    private EditText nameEditText;
    private EditText yearOfFoundationEditText;

    private int organizationPosition;
    private OrganizationData organization;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.a_organization_details);

        nameEditText = (EditText) findViewById(R.id.et_name);
        yearOfFoundationEditText = (EditText) findViewById(R.id.et_year_of_foundation);

        final String action = getIntent().getStringExtra(EXTRA_ORGANIZATION_DETAILS_ACTION);
        if (getIntent().hasExtra(EXTRA_ORGANIZATION_POSITION)) {

            organizationPosition = getIntent().getIntExtra(EXTRA_ORGANIZATION_POSITION, 0);
            organization = DataCache.getInstance().getData().get(organizationPosition);

            nameEditText.setText(organization.name);
            yearOfFoundationEditText.setText(String.valueOf(organization.yearOfFoundation));
        }

        Button confirmButton = (Button) findViewById(R.id.bt_confirm);
        if (action.equals(ACTION_ADD)) {
            setTitle(getString(R.string.add_organization));
            confirmButton.setText(getString(R.string.add));
        } else if (action.equals(ACTION_UPDATE)) {
            setTitle(getString(R.string.update_organization));
            confirmButton.setText(getString(R.string.update));
        }

        findViewById(R.id.bt_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = nameEditText.getText().toString();
                final int yearOfFoundation;

                try {
                    yearOfFoundation = Integer.valueOf(yearOfFoundationEditText.getText().toString());
                } catch (NumberFormatException e) {
                    Toast.makeText(
                            OrganizationDetailsActivity.this,
                            getString(R.string.year_of_foundation_exception),
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                if (action.equals(ACTION_ADD)) {
                    DataCache.getInstance().getData().add(new OrganizationData(name, yearOfFoundation));
                } else if (action.equals(ACTION_UPDATE)) {
                    organization.name = name;
                    organization.yearOfFoundation = yearOfFoundation;

                    DataCache.getInstance().getData().set(organizationPosition, organization);
                }

                DataStorage.getInstance().save(DataCache.getInstance().getData());

                finish();
            }
        });
    }
}
