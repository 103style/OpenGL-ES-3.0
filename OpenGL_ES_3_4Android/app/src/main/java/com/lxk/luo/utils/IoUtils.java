package com.lxk.luo.utils;

import android.content.res.Resources;

import com.lxk.luo.main.MainApplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * @author https://github.com/103style
 * @date 2019/6/3 15:35
 */
public class IoUtils {

    /**
     * 读取资源
     *
     * @param resourceId 资源id
     * @return 资源文本内容
     */
    public static String readShaderResource(int resourceId) {
        StringBuilder builder = new StringBuilder();
        try {
            InputStream inputStream = MainApplication.getApplication().getResources().openRawResource(resourceId);
            InputStreamReader streamReader = new InputStreamReader(inputStream);

            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String textLine;
            while ((textLine = bufferedReader.readLine()) != null) {
                builder.append(textLine);
                builder.append("\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return builder.toString();
    }

}