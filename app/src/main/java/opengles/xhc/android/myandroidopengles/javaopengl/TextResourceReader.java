package opengles.xhc.android.myandroidopengles.javaopengl;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TextResourceReader {

    public static String readTextFileFromResource(Context context , int resId){

        StringBuilder body = new StringBuilder();
        try{
            InputStream inputStream = context.getResources().openRawResource(resId);
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String nextLine ;
            while((nextLine = bufferedReader.readLine()) != null){
                body.append(nextLine);
                body.append("\n");
            }
        }catch (Exception e){
            Log.e("xhc" , "read source faild ");
        }
        return body.toString();
    }

}
