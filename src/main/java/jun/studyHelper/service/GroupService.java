package jun.studyHelper.service;

import jun.studyHelper.domain.group.Group;
import jun.studyHelper.domain.group.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {

    GroupRepository groupRepository;

    @Autowired
    GroupService(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    public void GroupService(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
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

    public List<Group> getAllGroups(){
        return groupRepository.findAll();
    }


}
