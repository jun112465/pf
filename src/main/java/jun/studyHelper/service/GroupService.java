package jun.studyHelper.service;

import jun.studyHelper.domain.group.Group;
import jun.studyHelper.domain.group.GroupMemberRepository;
import jun.studyHelper.domain.group.GroupRepository;
import jun.studyHelper.domain.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {

    GroupRepository groupRepository;
    GroupMemberRepository groupMemberRepository;

    @Autowired
    public GroupService(GroupRepository groupRepository, GroupMemberRepository groupMemberRepository){
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
    }


    public void createGroup(Member member, Group group){

        //중복 처리 아직 안함

        //그룹 생성
        groupRepository.create(group);

        // 생성자를 그룹 멤버로 추가
        groupMemberRepository.add(member, group);
    }

    public void deleteGroup(Group group){
        groupRepository.delete(group);
    }

    public void updateGroupName(Group group){
        groupRepository.updateName(group);
    }

    public List<Group> searchGroups(String search){
        return groupRepository.findAll(search);
    }
    public List<Group> getMemberGroups(Member member){

        List<Group> groupList = new ArrayList<>();

        for(Group g : groupMemberRepository.findAllGroups(member)){
            groupList.add(groupRepository.findById(g.getId()));
        }

        return groupList;
    }

    public List<Group> getAllGroups(){
        return groupRepository.findAll();
    }



}
