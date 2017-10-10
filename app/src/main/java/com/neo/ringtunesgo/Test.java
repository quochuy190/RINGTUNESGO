package com.neo.ringtunesgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.neo.ringtunesgo.untils.BaseActivity;

/**
 * Created by QQ on 8/9/2017.
 */

public class Test extends BaseActivity {
    Button button;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        button = (Button) findViewById(R.id.test);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.neo.testgetdatavina");
                launchIntent.putExtra("vinaphone", "0942148362");
                if (launchIntent != null) {
                    startActivity(launchIntent);
                    //null pointer check in case package name was not found
                }
            }
        });
    }

    @Override
    public int setContentViewId() {
        return 0;
    }

    @Override
    public void initData() {

    }
}
