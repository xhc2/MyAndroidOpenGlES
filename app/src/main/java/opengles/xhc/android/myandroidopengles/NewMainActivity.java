package opengles.xhc.android.myandroidopengles;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NewMainActivity extends Activity {

    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_main_layout);

        findViewById(R.id.tv_java_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(NewMainActivity.this , JavaShowTrangleActivity.class));
            }
        });

    }
}
