package pers.reptile.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Sheldon on 2018/2/8.
 * Project Name: NovelReptile.
 * Package Name: pers.reptile.http.
 * Description:
 * 模拟 Http 请求的类
 * 返回 StringBuffer 类型数据
 */
public class JRequest {

    // 发送GET方法返回StringBuffer数据
    // SendGET(urlName = 网址,enConde = 编码类型)
    public StringBuffer SendGET(String urlName,String enConde){
        StringBuffer strBuffer = new StringBuffer();
        try {
            URL url = new URL(urlName);
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection con = (HttpURLConnection)urlConnection;
            // 设置连接参数
            con.setRequestProperty("accept", "*/*");
            con.setRequestProperty("connection","Keep-Alice");
            con.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            int responseCode = con.getResponseCode();
            if (responseCode == con.HTTP_OK) {
                InputStream in = con.getInputStream();
                InputStreamReader ins = new InputStreamReader(in,enConde);
                BufferedReader bufd = new BufferedReader(ins);
                String str;
                while ((str = bufd.readLine())!=null) {
                    strBuffer.append(str);
                }
                //System.out.println(strBuffer.toString());
                bufd.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("访问失败");
        }
        return strBuffer;
    }

    public static void main(String[] args) {
        JRequest request = new JRequest();
        String url = "http://www.shuqiba.com";
        String enConde = "GBK";
        request.SendGET(url,enConde);
    }
}
