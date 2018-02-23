package per.reptile.http;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Sheldon on 2018/1/31.
 * Project Name: MusicReptile.
 * Package Name: per.reptile.http.
 * Description:
 * 提供了获取歌曲信息，下载歌曲的功能的类
 */
public class BdSongGet {

    // 获取歌曲信息
    public JSONArray SongInfo(String songs[]){
        JSONArray jsonArray = new JSONArray();
        int size = songs.length;
        String songIds;
        StringBuffer strbuffer = new StringBuffer();
        for (int i = 0; i < size; i++) {
            if (i < size - 1){
                strbuffer.append(songs[i] + ",");
            }else {
                strbuffer.append(songs[i]);
            }
        }
        songIds = strbuffer.toString();
        String url = "http://play.baidu.com/data/music/songlink?songIds=" + songIds +
                "&hq=0&type=m4a%2Cmp3&rate=&pt=0&flag=-1&s2p=-1&prerate=-1&bwt=-1&dur=-1&bat=-1&bp=-1&pos=-1&auto=-1";
        JRequest jRequest = new JRequest();
        StringBuffer songBuffer = new StringBuffer();
        songBuffer = jRequest.SendGET(url);
        JSONObject jsonObject = JSONObject.fromObject(songBuffer.toString());
        jsonObject = jsonObject.getJSONObject("data");
        jsonArray = jsonObject.getJSONArray("songList");
        return jsonArray;
    }

    // 用于执行下载动作
    public void Download(String urlname,String songname,String artist){
        URL url = null;
        try {
            url = new URL(urlname);
            // 打开 URL 连接
            URLConnection urlConnection = url.openConnection();
            HttpURLConnection connection = (HttpURLConnection)urlConnection;
            // 设置连接参数
            connection.setRequestMethod("GET");
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection","Keep-Alice");
            connection.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            int responseCode = connection.getResponseCode();
            if (responseCode == connection.HTTP_OK) {
                System.out.println("访问成功,开始下载");
                InputStream in = connection.getInputStream();
                // 查看是否有music文件夹
                File dir = new File("music");
                // 没有就新建该文件夹
                if (!dir.exists()){
                    dir.mkdirs();
                }
                String path = "music/" + songname + "(" + artist + ").mp3";
                FileOutputStream file = new FileOutputStream(new File(path));
                byte[] buffer = new byte[1024 * 1024];
                int len = -1;
                while ((len = in.read(buffer)) > 0){
                    file.write(buffer,0,len);
                }
                System.out.println("下载成功");
                file.close();
                in.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("下载失败");
        }
    }

    public static void main(String[] args) {
        BdSongGet bdSongGet = new BdSongGet();
        String songs[] = new String[1];
        songs[0] = "2496522";
        bdSongGet.SongInfo(songs);
    }
}
