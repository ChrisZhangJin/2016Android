package chris.zhang.nanjinginhand.loading;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import java.io.File;

import chris.zhang.nanjinginhand.R;
import chris.zhang.nanjinginhand.nanjing.MainActivity;
import chris.zhang.nanjinginhand.util.Constants;

public class LoginActivity extends Activity {

    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        Log.i(Constants.TAG, "LoginActivity starts.");

        file = new File(Constants.APP_SD_FILE_PATH);

        Log.i(Constants.TAG, "Open File"+Constants.APP_SD_FILE_PATH);

        Thread thread = new Thread()
        {
            @Override
            public void run()
            {
                try
                {
                    sleep(2000);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
                finish();
                changeFaces();
            }
        };
        thread.start();
    }

    private void changeFaces() {
        Intent intent = new Intent();
        if (!file.exists()) {
            Log.i(Constants.TAG, "file exists!");

            intent.setClass(LoginActivity.this, LoadingActivity.class);
        } else {
            Log.i(Constants.TAG, "file does not exist!");

            intent.setClass(LoginActivity.this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}
