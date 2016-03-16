package chris.zhang.nanjinginhand.loading;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import java.io.IOException;
import java.io.InputStream;

import chris.zhang.nanjinginhand.R;
import chris.zhang.nanjinginhand.nanjing.MainActivity;
import chris.zhang.nanjinginhand.util.Constants;
import chris.zhang.nanjinginhand.customized.WaitSurfaceView;
import chris.zhang.nanjinginhand.util.ZipUtil;

public class LoadingActivity extends Activity {

    private final int WAIT_DIALOG = 0;

    private WaitSurfaceView waitSurfaceView;
    private Dialog waitDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        Log.i(Constants.TAG, "LoadingActivity starts.");


        showDialog(WAIT_DIALOG);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        Dialog dialog = null;
        switch (id) {
            case WAIT_DIALOG:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setItems(null, null);
                builder.setCancelable(false);
                waitDialog = builder.create();
                dialog = waitDialog;
                break;
            default:
                break;
        }
        return dialog;
    }

    @Override
    protected void onPrepareDialog(int id, Dialog dialog) {
        switch (id) {
            case WAIT_DIALOG:
                prepareWaitingDialog(dialog);
                break;
        }
    }

    private void prepareWaitingDialog(Dialog dialog) {
        dialog.setContentView(R.layout.activity_loading);
        waitSurfaceView = (WaitSurfaceView) findViewById(R.id.loading_wait);

        try {
            Log.i(Constants.TAG, "try to unzip " + Constants.APP_DATA_FILE_NAME);

            InputStream inputStream = getAssets().open(Constants.APP_DATA_FILE_NAME);
            ZipUtil.unZip(inputStream, Constants.APP_SD_ROOT_PATH);
        } catch (IOException e) {
            Log.e(Constants.TAG, "IO Exception when unzipping data file!");
            return;
        }

        Log.i(Constants.TAG, "unzipped " + Constants.APP_DATA_FILE_NAME + " successfully! Go to main activity.");

        Intent intent = new Intent();
        intent.setClass(LoadingActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
