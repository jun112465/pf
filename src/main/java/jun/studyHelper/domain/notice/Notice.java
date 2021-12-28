package jun.studyHelper.domain.notice;

import java.util.Date;

public class Notice {
    String content;
    Date date;
    String author;
    int id;

    public Notice() {

    }

    public String getContents() {
        return content;
    }

    public void setContents(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
