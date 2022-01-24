package jun.studyHelper.domain.notice;

import org.apache.commons.text.StringEscapeUtils;

public class NoticeForm {
    String content;
    int memberId;
    String title;
    int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMemberId() {
        return memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = StringEscapeUtils.escapeHtml4(content);
    }
}
