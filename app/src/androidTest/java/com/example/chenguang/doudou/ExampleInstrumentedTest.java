package com.example.chenguang.doudou;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.chenguang.doudou.bean.Province;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        try {
            Context appContext = InstrumentationRegistry.getTargetContext();

            InputStream in = appContext.getAssets().open("city.txt");
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = in.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
            String json = new String(out.toByteArray());

            in.close();
            out.close();

            System.out.println(json);
            Gson gson = new Gson();
            List<Province> provinces = gson.fromJson(json, new TypeToken<List<Province>>() {
            }.getType());
            System.out.println(provinces);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
