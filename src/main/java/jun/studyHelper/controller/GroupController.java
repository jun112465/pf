package jun.studyHelper.controller;

import jun.studyHelper.entity.Group;
import jun.studyHelper.entity.Member;
import jun.studyHelper.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class GroupController {

    GroupService groupService;

    @Autowired
    GroupController(GroupService groupService){
        this.groupService = groupService;
    }


    @PostMapping("/group/add-group")
    public String addGroup(HttpServletRequest req, @RequestParam String groupName){

        for(Cookie c : req.getCookies())
            if (c.getName().equals("memberId"))
                groupService.createGroup(
                        new Member(c.getValue()), new Group(Group.createGroupId(), groupName)
                );

        return "redirect:/";
    }

    @ResponseBody
    @PostMapping("/group/find-group")
    public List<Group> findGroup(@RequestBody Group searchGroup){
        return groupService.searchGroups(searchGroup);
    }
}
