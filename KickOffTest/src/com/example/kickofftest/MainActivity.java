package com.example.kickofftest;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends BaseActivity {

	private EditText inputAccount;
	private EditText inputPassword;
	private Button buttonLogin;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		inputAccount = (EditText)findViewById(R.id.input_account);
		inputPassword = (EditText)findViewById(R.id.input_password);
		buttonLogin = (Button)findViewById(R.id.button_login);
		buttonLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String account = inputAccount.getText().toString();
				String password = inputPassword.getText().toString();
				
				if(account.isEmpty() || password.isEmpty())
				{
					Toast.makeText(MainActivity.this, "account or password is empty!", Toast.LENGTH_SHORT).show();
					return;
				}
				
				Intent intent = new Intent(MainActivity.this, AccountActivity.class);
				intent.putExtra("ACCOUNT_NAME", account);
				startActivity(intent);
				finish();
			}
		});
	}
}
