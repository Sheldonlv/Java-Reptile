package per.reptile.bean;

/**
 * Created by Sheldon on 2018/1/31.
 * Project Name: MusicReptile.
 * Package Name: per.reptile.bean.
 * Description:
 * 装载相关歌曲信息的Bean类
 */
public class BdSongBean {
    private String songname;
    private String artistname;
    private String songid;
    private String info;
    private String songLink;
    private String crlLink;

    public String getSongLink() {
        return songLink;
    }

    public void setSongLink(String songLink) {
        this.songLink = songLink;
    }

    public String getCrlLink() {
        return crlLink;
    }

    public void setCrlLink(String crlLink) {
        this.crlLink = crlLink;
    }

    public String getSongname() {
        return songname;
    }

    public void setSongname(String songname) {
        this.songname = songname;
    }

    public String getArtistname() {
        return artistname;
    }

    public void setArtistname(String artistname) {
        this.artistname = artistname;
    }

    public String getSongid() {
        return songid;
    }

    public void setSongid(String songid) {
        this.songid = songid;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
