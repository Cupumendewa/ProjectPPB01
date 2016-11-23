package id.sch.smktelkom_mlg.project.xirpl207162534.digitalbulletin;

/**
 * Created by nerdywoffy on 11/22/16.
 */

public class Post {
    public String id;
    public String Title;
    public String Date;
    public String channel;
    public Post(String channel, String id, String Title, String Date){
        this.Title = Title;
        this.id = id;
        this.Date = Date;
        this.channel = channel;
    }
}
