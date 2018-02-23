package per.reptile.http;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sheldon on 2018/1/30.
 * Project Name: MusicReptile.
 * Package Name: per.reptile.http.
 * Description:
 * 用于发送 htpp 请求，从不同的API中所需信息
 */
public class JRequest {

    // 发送GET方法返回StringBuffer数据
    public StringBuffer SendGET(String urlname){
        StringBuffer strBuffer = new StringBuffer();
        try {
            URL url = new URL(urlname);
            // 打开 URL 连接
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = (HttpURLConnection)urlConnection;
            // 设置连接参数
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection","Keep-Alice");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            int responseCode = connection.getResponseCode();
            if (responseCode == connection.HTTP_OK) {
                System.out.println("访问成功");
                InputStream in = connection.getInputStream();
                InputStreamReader ins = new InputStreamReader(in,"UTF-8");
                BufferedReader bufd = new BufferedReader(ins);
                String str;
                while ((str = bufd.readLine())!=null) {
                    strBuffer.append(str);
                    //System.out.println(strBuffer.toString());
                }
                bufd.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e){
            System.out.println("访问失败");
        }
        return strBuffer;
    }

}
