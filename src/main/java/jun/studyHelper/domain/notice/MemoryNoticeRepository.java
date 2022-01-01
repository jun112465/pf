package jun.studyHelper.domain.notice;

import jun.studyHelper.domain.member.Member;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MemoryNoticeRepository implements NoticeRepository{

    LinkedList<Notice> noticeLinkedList = new LinkedList<>();

    @Override
    public void save(Notice notice) {
        noticeLinkedList.add(notice);
    }

    @Override
    public void remove(int idx) {
        noticeLinkedList.remove(idx);
    }

    @Override
    public List<Notice> findAll(int memberId) {
        return noticeLinkedList;
    }
}
