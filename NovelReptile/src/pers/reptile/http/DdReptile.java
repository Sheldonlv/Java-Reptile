package pers.reptile.http;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

/**
 * Created by Sheldon on 2018/2/12.
 * Project Name: NovelReptile.
 * Package Name: pers.reptile.http.
 * Description:
 * 用于具体的查询小说类
 * 查询书籍有无，根据查询结果获取所有章节正文链接
 */
public class DdReptile {

    // 按关键字查询书籍
    // Search(keyword = 搜索的关键字)
    public HashMap Search(String keyword){
        // 将 keyword 转换为URL传输的UTF-8格式
        try {
            keyword = URLEncoder.encode(keyword,"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 拼接URL
        String url = "https://m.dingdiann.com/SearchBook.php?keyword=" + keyword;
        // 调用自己编写的 URL 类
        JRequest request = new JRequest();
        // 进行访问获取 html 的 strBuffer
        StringBuffer strBuffer= request.SendGET(url,"UTF-8");
        // 将 strBuffer 转化为 String 类型
        String html = strBuffer.toString();
        Document doc = Jsoup.parse(html);
        // 获取页码
        String pages = doc.select("input.page_txt").val();
        // 获取搜索结果
        Elements results = doc.select("div.hot_sale");
        //System.out.println(results);
        // 用 HashMap 变量来存储获取的信息
        HashMap hashMap = new HashMap();
        // 页面信息
        hashMap.put("pages",pages);
        // 小说相关信息
        String title;     // 标题
        String author;    // 作者
        String state;     // 状态
        String info;      // 整合信息
        String novelUrl;
        int number = 0;
        for (Element result: results) {
            title = result.select("p.title").text();
            author = result.select("p.author").first().text();
            state = result.select("p.author").last().text();
            info = (number+1) + "." + title + "\n" + author + "\n" + state + "\n";
            novelUrl = "https://m.dingdiann.com" + result.select("a").attr("href") + "all.html";
            hashMap.put("title"+number,title);
            hashMap.put("info"+number,info);
            hashMap.put("novelUrl"+number,novelUrl);
            number ++;
        }
        return hashMap;
    }

    // 获取指定小说所有章节地址
    // ChapterUrl (novelUrl = 小说预览地址)
    public HashMap ChapterUrl (String novelUrl) {
        HashMap hashMap = new HashMap();
        // 发起 Http 请求
        JRequest request = new JRequest();
        StringBuffer strBuffer = request.SendGET(novelUrl,"UTF-8");
        // 获取 html
        String html = strBuffer.toString();
        // 格式化 html
        Document doc = Jsoup.parse(html);
        // 获取指定 div 中的 p 标签
        Elements results = doc.select("div#chapterlist").select("a");
        // 所有章节信息
        String title;
        String chapterUrl;
        int number = 0;
        for (Element result: results) {
            if (number == 0) {
                number++;
                continue;
            }
            title = result.text();
            chapterUrl = "https://m.dingdiann.com" + result.attr("href");
            hashMap.put("chapterUrl"+number,chapterUrl);
            number++;
        }
        System.out.println(hashMap.size());
        return hashMap;
    }

    // 下载小说章节
    public void Noveldl(String novelUrl) {
        // 初始化 Http 请求对象
        JRequest request = new JRequest();
        // 获取 html
        StringBuffer strBuffer = request.SendGET(novelUrl,"UTF-8");
        // 转换为 String 类型
        String html = strBuffer.toString();
        // Jsoup 对 html 进行处理
        Document doc = Jsoup.parse(html);
        // 获取标题
        String title = doc.select("span.title").text();
        // 用分隔符来筛选一下 title
        String str[] = title.split("\\s+");
        // 获得文件名
        String fileName = str[0] + " " + str[1] + ".txt";
        // 小说名
        String novelTitle = str[2];
        // 筛选出 div 标签的文本内容
        String divText = doc.select("div#chaptercontent").text();
        // 整理内容（剔除杂质内容）
        divText = divText.replace("『章节错误,点此举报』"," ");
        divText = divText.replace(" ","\n\n");
        divText = divText.replace("『加入书签，方便阅读』"," ");
        // 定义文件夹
        File file = new File("novel/" + novelTitle);
        // 定义文本路径
        String pathName = "novel/" + novelTitle + "/" + fileName;
        // 若无文件夹便新建一个
        if (!file.isDirectory()) {
            file.mkdir();
        }
        // 设置文件
        file = new File(pathName);
        try {
            // 初始化“写入”对象
            FileWriter fW = new FileWriter(file,true);
            // 写入文本文件
            fW.write(divText);
            fW.close();
            System.out.println(novelTitle + "-->" + str[0] + "  下载完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 下载小说章节（重载）
    // Noveldl(novelTitle = 小说名, novelUrl = 该章节url, chapter_num = 章节序号)
    public void Noveldl(String novelTitle,String novelUrl,String chapter_num) {
        // 初始化 Http 请求对象
        JRequest request = new JRequest();
        // 获取 html
        StringBuffer strBuffer = request.SendGET(novelUrl,"UTF-8");
        // 转换为 String 类型
        String html = strBuffer.toString();
        // Jsoup 对 html 进行处理
        Document doc = Jsoup.parse(html);
        // 获取标题
        String title = doc.select("span.title").text();
        // 用分隔符来筛选一下 title
        String str[] = title.split("\\s+");
        // 获得文件名
        String fileName = chapter_num + str[0] + " " + str[1] + ".txt";
        // 筛选出 div 标签的文本内容
        String divText = doc.select("div#chaptercontent").text();
        // 整理内容
        divText = divText.replace("『章节错误,点此举报』"," ");
        divText = divText.replace(" ","\n\n");
        divText = divText.replace("『加入书签，方便阅读』"," ");
        // 定义文件夹
        File file = new File("novel/" + novelTitle);
        // 定义文本路径
        String pathName = "novel/" + novelTitle + "/" + fileName;
        // 若无文件夹便新建一个
        if (!file.isDirectory()) {
            file.mkdir();
        }
        // 设置文件
        file = new File(pathName);
        try {
            // 初始化“写入”对象
            FileWriter fW = new FileWriter(file,true);
            // 写入文本文件
            fW.write(divText);
            fW.close();
            System.out.println(novelTitle + "-->" + str[0] + "  下载完成");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
