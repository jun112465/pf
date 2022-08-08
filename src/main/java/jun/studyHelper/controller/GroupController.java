package jun.studyHelper.controller;

import jun.studyHelper.domain.group.Group;
import jun.studyHelper.domain.group.GroupRepository;
import jun.studyHelper.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GroupController {

    GroupService groupService;

    @Autowired
    GroupController(GroupService groupService){
        this.groupService = groupService;
    }


    @PostMapping("/group/add-group")
    public String addGroup(@RequestParam String groupName){

        groupService.createGroup(new Group(Group.createGroupId(), groupName));
        return "redirect:/";
    }

}
