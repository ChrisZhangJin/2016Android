package com.example.kickofftest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.*;

public class AccountActivity extends BaseActivity {
	
	private TextView textAccount;
	private Button buttonKickoff;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_account);
		
		Intent intent = getIntent();
		String accountName = intent.getStringExtra("ACCOUNT_NAME");
		
		textAccount = (TextView)findViewById(R.id.text_account);
		textAccount.setText(accountName);
		
		buttonKickoff = (Button)findViewById(R.id.button_logout);
		buttonKickoff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.example.kickofftest.FORCE_OFFLINE");
				sendBroadcast(intent);
			}
		});
	}
}
