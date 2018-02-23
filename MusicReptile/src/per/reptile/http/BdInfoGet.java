package per.reptile.http;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sheldon on 2018/1/31.
 * Project Name: MusicReptile.
 * Package Name: per.reptile.http.
 * Description:
 */
public class BdInfoGet {

    // 按关键词查找歌曲
    public JSONArray Find(String word){
        String url = "http://sug.qianqian.com/info/suggestion?format=json&word=" + word +
                "&version=2&from=0&callback=window.baidu.sug&third_type=0&client_type=0&_=1517384784169";
        String str;
        JRequest jRequest = new JRequest();
        StringBuffer strBuffer = new StringBuffer();
        strBuffer = jRequest.SendGET(url);

        // 正则表达式
        String rgex = "window\\.baidu\\.sug\\((.*?)\\);";
        Pattern pattern = Pattern.compile(rgex);
        Matcher matcher = pattern.matcher(strBuffer.toString());
        if (matcher.find()){
            str = matcher.group(1);
            strBuffer = new StringBuffer(str);
        }
        // toString 将其转化为字符串格式,并存在json当中
        JSONObject jsonObject = JSONObject.fromObject(strBuffer.toString());
        // 创建 json 数列对象
        JSONArray jsonArray = new JSONArray();
        // 提取出 data 项数据
        jsonObject = jsonObject.getJSONObject("data");
        // 因为 song 是一个 json 数列，所以要用 jsonArray
        jsonArray = jsonObject.getJSONArray("song");
        return jsonArray;
    }

    public static void main(String[] args) {
        BdInfoGet bdInfoGet = new BdInfoGet();
        bdInfoGet.Find("一眼");
    }
}
