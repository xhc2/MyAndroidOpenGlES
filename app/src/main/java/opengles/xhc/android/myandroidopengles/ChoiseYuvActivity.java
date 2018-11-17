package opengles.xhc.android.myandroidopengles;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 只去FFmpeg目录下寻找yuv文件。
 */

public class ChoiseYuvActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private static final String pathRoot = "sdcard/FFmpeg/";
    private ChoiseYuvAdapter adapter;
    private List<String> list = new ArrayList<>();
    private ChoiseWHDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choise_yuv);
        recyclerView = findViewById(R.id.recyleview);
        adapter = new ChoiseYuvAdapter( list , this );
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        requestPermisstion();
        adapter.setOnRecyleItemClick(new MyBaseAdapter.OnRecyleItemClick() {
            @Override
            public void onItemClick(View v, Object o, final int position) {
                //跳转

                int[] wh = parseWidthHeight(list.get(position));
                int width = 0 ;
                int height = 0;
                if(wh == null || wh[0] == 0 || wh[1] == 0){
                    //开启对话框
                    showDialog(new ChoiseWHDialog.ClickBack(){
                        @Override
                        public void clickBack(int width, int height) {
                            Intent intent = new Intent(ChoiseYuvActivity.this , MainActivity.class);
                            intent.putExtra("path" , list.get(position));
                            intent.putExtra("w" , width);
                            intent.putExtra("h" , height);
                            startActivity(intent);
                        }
                    });
                }
                else{
                    Intent intent = new Intent(ChoiseYuvActivity.this , MainActivity.class);
                    width = wh[0];
                    height = wh[1];
                    intent.putExtra("path" , list.get(position));
                    intent.putExtra("w" , width);
                    intent.putExtra("h" , height);
                    startActivity(intent);
                }

            }
        });
    }

    private void showDialog(ChoiseWHDialog.ClickBack cb2 ){
        if(dialog == null){
            dialog = new ChoiseWHDialog(this ,cb2 );
        }
        dialog.show();
    }

    private void dismissDialog(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }

    private int[] parseWidthHeight(String path){
        String[] strs = path.split("_");
        int [] results = new int[2];
        if(strs != null){
            for (String s : strs){
                try {
                    int temp = Integer.parseInt(s);
                    if(results[0] == 0){
                        results[0] = temp;
                    }
                    if(results[1] == 0){
                        results[1] = temp;
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        return results;
    }

    private void requestPermisstion(){
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(checkCallPhonePermission != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},100);
            }else{
                //上面已经写好的拨号方法
                findYuvFile();
            }
        } else {
            //上面已经写好的拨号方法
            findYuvFile();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                if (requestCode == 100) {
                    if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // 权限被允许
                        findYuvFile();
                    } else if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        showToast( "请开启权限！");
                    } else {
                        // 还是没开权限
                        showToast(  "请开启权限！");
                    }
                }
                break;
        }
    }

    protected void showToast(String msg){
        Toast.makeText(this , msg, Toast.LENGTH_LONG).show();
    }

    private List<String> findYuvFile(){
        File files = new File(pathRoot);
        if(files.exists()){
            File[] fs = files.listFiles();
            for(File f : fs){
                if(f.getAbsolutePath().endsWith("yuv")){
                    list.add(f.getAbsolutePath());
                }
            }
            adapter.refreshAllData(list);
        }
        else{
            showToast("我只在sdcard/FFmpeg 下寻找文件！");
        }
        return null;
    }



}
