import java.util.HashMap;
import java.util.Scanner;

import pers.reptile.http.*;

/**
 * Created by Sheldon on 2018/2/9.
 * Project Name: NovelReptile.
 * Package Name: pers.reptile.http.
 * Description:
 * 下载主程序
 */
public class DdAutoGetDemo {

    // 下载整本小说
    public void Start() {
        System.out.println("请输入要查找的小说:");
        Scanner scanner = new Scanner(System.in);
        String novel = scanner.nextLine();
        DdReptile reptile = new DdReptile();
        // 获取查找书籍结果
        HashMap search =  reptile.Search(novel);
        System.out.println("查找到的结果如下：");
        // 查找到的小说数目
        int size = (search.size() - 1) / 3;
        for (int i = 0; i < size; i++) {
            System.out.println(search.get("info"+i));
        }
        System.out.println("请选择要下载的书籍序号");
        int chose = scanner.nextInt() - 1;
        String novelUrl = search.get("novelUrl"+chose).toString();
        // 获取具体书籍信息
        HashMap info = reptile.ChapterUrl(novelUrl);
        // hashMap 数目
        size = info.size();
        // 书名
        String title = search.get("title"+chose).toString();
        // 章节数目
        int chapterNum;
        // 章节URL
        String chapterUrl;
        // 章节序列号
        String chapter_num;
        for (int i = 0; i < size; i++) {
            chapterNum = i+1;
            chapterUrl = info.get("chapterUrl"+chapterNum).toString();
            chapter_num = i + 1 + ".";
            // 下载
            try {
                reptile.Noveldl(title,chapterUrl,chapter_num);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                continue;
            }
        }
    }

    public static void main(String[] args) {
        DdAutoGetDemo ddAutoGet = new DdAutoGetDemo();
        ddAutoGet.Start();
    }
}
 
