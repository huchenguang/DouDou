package com.example.chenguang.doudou.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * Created by chenguang on 2018/1/17.
 */

public class FileUtil {
    public static String readTextFromFile(InputStream in) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            String json = new String(out.toByteArray());
            in.close();
            out.close();
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
