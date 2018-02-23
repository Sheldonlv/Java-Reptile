import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import per.reptile.bean.BdSongBean;
import per.reptile.http.BdInfoGet;
import per.reptile.http.BdSongGet;

import java.util.Scanner;

/**
 * Created by Sheldon on 2018/1/31.
 * Project Name: MusicReptile.
 * Package Name: PACKAGE_NAME.
 * Description:
 */
public class Demo {
    public static void main(String[] args) {
        // 1.查找
        System.out.println("请输入你要查找的歌曲：");
        Scanner scanner = new Scanner(System.in);
        //String word = "红昭愿";
        String word = scanner.nextLine();

        // 初始化查找用的对象
        BdInfoGet bdInfoGet = new BdInfoGet();
        // 代入关键字进行查找
        JSONArray findInfo = bdInfoGet.Find(word);

        // 定义 size 变量，记录 jsonArray 条数
        int size = findInfo.size();
        // 初始化该 Bean 类数组
        BdSongBean songBean[] = new BdSongBean[size];
        String songs[] = new String[size];
        JSONObject jsonObject = new JSONObject();
        for (int i = 0;i < size; i++) {
            jsonObject = findInfo.getJSONObject(i);
            // 初始化该 Bean 对象
            songBean[i] = new BdSongBean();
            // 设置歌名
            songBean[i].setSongname(jsonObject.get("songname").toString());
            // 设置歌手姓名
            songBean[i].setArtistname(jsonObject.get("artistname").toString());
            // 设置歌曲 id
            songBean[i].setSongid(jsonObject.get("songid").toString());
            songs[i] = songBean[i].getSongid();
            // 设置歌曲信息
            songBean[i].setInfo(jsonObject.get("info").toString());
        }

        // 初始化获取歌曲具体信息的对象
        BdSongGet songGet = new BdSongGet();
        JSONArray songInfo = new JSONArray();
        songInfo = songGet.SongInfo(songs);
        for (int i = 0; i < size; i++) {
            jsonObject = songInfo.getJSONObject(i);
            songBean[i].setSongLink(jsonObject.get("songLink").toString());
            songBean[i].setCrlLink(jsonObject.get("lrcLink").toString());
        }

        System.out.println("查找到如下歌曲信息：");
        // 2.展示
        int i = 1;
        for (BdSongBean bean:songBean){
            System.out.println( i + "." + bean.getArtistname() + "-->" + bean.getSongname());
            i++;
        }

        // 3.下载
        System.out.println("请输入你要下载的序号：");
        int id = Integer.valueOf(scanner.nextLine()) - 1;
        String songLink = songBean[id].getSongLink();
        String songName = songBean[id].getSongname();
        String artisName = songBean[id].getArtistname();
        songGet.Download(songLink,songName,artisName);
    }
}
 
