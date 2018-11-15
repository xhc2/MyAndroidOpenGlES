package opengles.xhc.android.myandroidopengles.javaopengl;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES20.*;
import android.util.Log;

import static android.opengl.GLES20.GL_LINEAR;
import static android.opengl.GLES20.GL_LINEAR_MIPMAP_LINEAR;
import static android.opengl.GLES20.GL_TEXTURE_2D;
import static android.opengl.GLES20.GL_TEXTURE_MAG_FILTER;
import static android.opengl.GLES20.GL_TEXTURE_MIN_FILTER;
import static android.opengl.GLES20.glBindTexture;
import static android.opengl.GLES20.glDeleteTextures;
import static android.opengl.GLES20.glGenTextures;
import static android.opengl.GLES20.glGenerateMipmap;
import static android.opengl.GLES20.glTexImage2D;
import static android.opengl.GLES20.*;
import static android.opengl.GLUtils.texImage2D;
public class TextureHelper {

    public static int loadTexture(Context context , int resourceId){
        final int[] textureObjectIds = new int[1];
        glGenTextures(1,textureObjectIds , 0);
        if(textureObjectIds[0] == 0){
            Log.e("xhc" , "cant open opengl texture object ！");
            return 0;
        }
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;
        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources() , resourceId , options);
        if(bitmap == null){
            Log.e("xhc" , "bitmap create faild ");
            glDeleteTextures(1, textureObjectIds , 0);
            return 0;
        }
        //绑定纹理
        glBindTexture(GL_TEXTURE_2D , textureObjectIds[0]);
        /**
         * 设置过滤器GL_TEXTURE_MIN_FILTER指缩小的情况选择GL_LINEAR_MIPMAP_LINEAR说明缩小使用三线性过滤
         * GL_TEXTURE_MAG_FILTER 指放大的情况GL_LINEAR使用双线性过滤
         */
        glTexParameteri(GL_TEXTURE_2D , GL_TEXTURE_MIN_FILTER , GL_LINEAR_MIPMAP_LINEAR);
        glTexParameteri(GL_TEXTURE_2D , GL_TEXTURE_MAG_FILTER , GL_LINEAR);
        //加载位图数据到opengl中
        texImage2D(GL_TEXTURE_2D , 0 , bitmap,0);

        bitmap.recycle();
        //生成mip贴图
        glGenerateMipmap(GL_TEXTURE_2D);
        //解除纹理绑定
        glBindTexture(GL_TEXTURE_2D , 0);
        return textureObjectIds[0];
    }

    public static int[] initYuvTexture(int outWidth , int outHeight){

        int[] textureObjectIds = new int[3];
        glGenTextures(3 ,textureObjectIds , 0); //3是创建三个纹理对象的意思
        if(textureObjectIds[0] == 0 || textureObjectIds[1] == 0 || textureObjectIds[2] == 0){
            Log.e("xhc" , "cant open opengl texture object ！");
            return null;
        }

        glBindTexture(GL_TEXTURE_2D , textureObjectIds[0]);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
//        glTexParameteri(GL_TEXTURE_2D , GL_TEXTURE_MIN_FILTER , GL_LINEAR);
//        glTexParameteri(GL_TEXTURE_2D , GL_TEXTURE_MAG_FILTER , GL_LINEAR);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

//        glTexImage2D(
//                GL_TEXTURE_2D,  //target 指定目标纹理，这个值必须是GL_TEXTURE_2D。
//                0,           //level 执行细节级别。0是最基本的图像级别，n表示第N级贴图细化级别。
//                GL_LUMINANCE,//指定纹理中的颜色组件。可选的值有GL_ALPHA,GL_RGB,GL_RGBA,GL_LUMINANCE, GL_LUMINANCE_ALPHA 等几种。
//                outWidth , //指定纹理图像的宽度，必须是2的n次方。纹理图片至少要支持64个材质元素的宽度
//                outHeight, // 指定纹理图像的高度，必须是2的m次方。纹理图片至少要支持64个材质元素的高度
//                0,             // 指定边框的宽度。必须为0
//                GL_LUMINANCE,//像素数据的颜色格式, 不需要和internalformatt取值必须相同。可选的值参考internalformat。
//                GL_UNSIGNED_BYTE, //ype 指定像素数据的数据类型。可以使用的值有GL_UNSIGNED_BYTE,GL_UNSIGNED_SHORT_5_6_5,GL_UNSIGNED_SHORT_4_4_4_4,GL_UNSIGNED_SHORT_5_5_5_1
//                null                    //pixels 指定内存中指向图像数据的指针
//        );

        glBindTexture(GL_TEXTURE_2D, textureObjectIds[1]);
        //缩小的过滤器
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        //设置纹理的格式和大小
//        glTexImage2D(GL_TEXTURE_2D,
//                0,
//                GL_LUMINANCE,
//                outWidth / 2,
//                outHeight / 2,
//                0,
//                GL_LUMINANCE,
//                GL_UNSIGNED_BYTE,
//                null
//        );

        //设置纹理属性
        glBindTexture(GL_TEXTURE_2D, textureObjectIds[2]);
        //缩小的过滤器
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_REPEAT);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
//        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);

        //设置纹理的格式和大小
//        glTexImage2D(GL_TEXTURE_2D,
//                0,
//                GL_LUMINANCE,
//                outWidth / 2, outHeight / 2,
//                0,
//                GL_LUMINANCE,
//                GL_UNSIGNED_BYTE,
//                null
//        );

        return textureObjectIds;
    }


}
