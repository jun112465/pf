package jun.studyHelper.service;

import jun.studyHelper.domain.group.Group;
import jun.studyHelper.domain.group.GroupRepository;

public class GroupService {

    GroupRepository groupRepository;

    public void GroupService(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }

    public void createGroup(Group group){

        //중복 처리 아직 안함

        //그룹 생성
        groupRepository.create(group);
    }


}
