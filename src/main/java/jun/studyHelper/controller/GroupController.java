package jun.studyHelper.controller;

import jun.studyHelper.domain.group.Group;
import jun.studyHelper.domain.group.GroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GroupController {

    GroupRepository groupRepository;

    @Autowired
    GroupController(GroupRepository groupRepository){
        this.groupRepository = groupRepository;
    }


    @PostMapping
    public String addGroup(String name){
        groupRepository.create(new Group(name));
        return "redirect:/";
    }

}
