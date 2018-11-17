package opengles.xhc.android.myandroidopengles;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ChoiseWHDialog extends Dialog {

    private TextView tv1920 , tv1280 , tv480 , tv360;
    private EditText etW , etH;
    private ClickBack cb ;

    public ChoiseWHDialog(@NonNull final Context context , ClickBack cb2 ) {
        super(context , R.style.dialog);
        setContentView(getLayoutInflater().inflate(R.layout.choise_w_h_layout , null , false));
        this.cb = cb2;
        tv1920 = findViewById(R.id.tv_1920_1080);
        tv1280 = findViewById(R.id.tv_1280_720);
        tv480 = findViewById(R.id.tv_640_480);
        tv360 = findViewById(R.id.tv_640_360);
        etH = findViewById(R.id.et_height);
        etW = findViewById(R.id.et_width);

        tv1920.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 cb.clickBack(1920 , 1080);
                 dismiss();
            }
        });
        tv1280.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb.clickBack(1280 , 720);dismiss();
            }
        });
        tv480.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb.clickBack(640 , 480);dismiss();
            }
        });
        tv360.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cb.clickBack(640 , 360);dismiss();
            }
        });
        findViewById(R.id.bt_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String w = etW.getText().toString();
                String h = etH.getText().toString();
                try {
                    cb.clickBack(Integer.parseInt(w) , Integer.parseInt(h) );
                    dismiss();
                } catch (NumberFormatException e) {
                    Toast.makeText(context, "输入正确数字", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        });
    }

    public interface ClickBack{
        void clickBack(int width , int height);
    }

}
