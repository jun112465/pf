package jun.studyHelper.service;

import jun.studyHelper.domain.group.Group;
import jun.studyHelper.domain.group.GroupMemberRepository;
import jun.studyHelper.domain.group.GroupRepository;
import jun.studyHelper.domain.member.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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


    public void createGroup(Group group){

        //중복 처리 아직 안함

        //그룹 생성
        groupRepository.create(group);
    }

    public void deleteGroup(Group group){
        groupRepository.delete(group);
    }

    public void updateGroupName(Group group){
        groupRepository.updateName(group);
    }

    public List<Group> getMemberGroups(Member member){
        return groupMemberRepository.findAllGroups(member);
    }

    public List<Group> getAllGroups(){
        return groupRepository.findAll();
    }



}
