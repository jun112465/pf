package jun.studyHelper.service;

import jun.studyHelper.domain.member.FriendRepository;
import jun.studyHelper.domain.entity.Member;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendService {

    FriendRepository friendRepository;

    public FriendService(FriendRepository friendRepository){
        this.friendRepository = friendRepository;
    }

    public void addFriend(Member me, Member friend){
        friendRepository.add(me, friend);
    }

    public void deleteFriend(Member me, Member friend){
        friendRepository.delete(me, friend);
    }

    public List<Member> getFriendList(Member me){
        return friendRepository.findAll(me);
    }
}
