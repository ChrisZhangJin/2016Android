package chris.zhang.nanjinginhand.nanjing;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import chris.zhang.nanjinginhand.R;

public class MainActivity extends NJActivityGroupBase implements View.OnClickListener {
    private RadioButton[] radioButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioButtons = new  RadioButton[3];
        radioButtons[0] = (RadioButton) findViewById(R.id.nanjing_btn_search);
        radioButtons[1] = (RadioButton) findViewById(R.id.nanjing_btn_home);
        radioButtons[2] = (RadioButton) findViewById(R.id.nanjing_btn_set);

        radioButtons[0].setOnClickListener(this);
        radioButtons[0].setOnCheckedChangeListener(this);
        radioButtons[1].setOnClickListener(this);
        radioButtons[1].setOnCheckedChangeListener(this);
        radioButtons[2].setOnClickListener(this);
        radioButtons[2].setOnCheckedChangeListener(this);
    }

    @Override
    protected ViewGroup getContainer() {
        return (ViewGroup)findViewById(R.id.nanjing_layout_container);
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.nanjing_btn_home:
                radioButtons[0].setBackgroundResource(R.drawable.bt_search);
                radioButtons[1].setBackgroundResource(R.drawable.bt_home2);
                radioButtons[2].setBackgroundResource(R.drawable.bt_set);
                break;
            case R.id.nanjing_btn_search:
                radioButtons[0].setBackgroundResource(R.drawable.bt_search2);
                radioButtons[1].setBackgroundResource(R.drawable.bt_home);
                radioButtons[2].setBackgroundResource(R.drawable.bt_set);
                break;
            case R.id.nanjing_btn_set:
                radioButtons[0].setBackgroundResource(R.drawable.bt_search);
                radioButtons[1].setBackgroundResource(R.drawable.bt_home);
                radioButtons[2].setBackgroundResource(R.drawable.bt_set2);
                break;
            default:
                radioButtons[0].setBackgroundResource(R.drawable.bt_search);
                radioButtons[1].setBackgroundResource(R.drawable.bt_home);
                radioButtons[2].setBackgroundResource(R.drawable.bt_set);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0)
        {
            quitConfirm();
        }

        return false;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(!isChecked) return;

        switch (buttonView.getId()){
            case R.id.nanjing_btn_search:
                setContainerView(CONTENT_ACTIVITY_SEARCH, SearchActivity.class);
                break;
            case R.id.nanjing_btn_home:
                setContainerView(CONTENT_ACTIVITY_HOME, HomeActivity.class);
                break;
            case R.id.nanjing_btn_set:
                setContainerView(CONTENT_ACTIVITY_SETTINGS, SettingsActivity.class);
                break;
            default:
                break;
        }
    }

    protected void quitConfirm(){
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("您确定要退出吗?");
        builder.setTitle(" 提示");
        builder.setPositiveButton("确认",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        MainActivity.this.finish();
                    }
                });
        builder.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.create().show();

    }
}
