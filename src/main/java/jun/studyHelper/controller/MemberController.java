package jun.studyHelper.controller;


import jun.studyHelper.domain.member.Member;
import jun.studyHelper.domain.member.MemberForm;
import jun.studyHelper.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

@Controller //컨트롤러 또한 자동으로 스프링빈에 등록된다.
public class MemberController {

    public MemberService memberService;

    @Autowired //이 어노테이션이 붙은 생성자는 연관된 객체를 스프링 컨테이너에서 자동으로 넣어준다.
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }
    
    @PostMapping("members/new")
    public String create(MemberForm form){
//        Member member = new Member(app.noticeService());
        Member member = new Member();
        member.setName(form.getName());
        member.setMemberId(form.getMemberId());
        memberService.join(member);
        
        return "redirect:/";
    }

    @PostMapping("/members/login")
    public String login(MemberForm form, HttpServletResponse resp){
        int id = form.getMemberId();

        if(memberService.findOne(id) != null) {
            System.out.println(id);
            Cookie idCookie = new Cookie("memberId", String.valueOf(id));
            idCookie.setPath("/");
            resp.addCookie(idCookie);
            System.out.println(idCookie.getName() +  " : " + idCookie.getValue());
        }else{
            System.out.println("MemberController : No member found");
        }

        return "redirect:/";
    }


    @PostMapping(value = "/members/add-friend",  produces = "application/json")
    @ResponseBody
    public Map<Integer,String> addFriend( MemberForm form, @CookieValue(name="memberId", required = false)String memberId){

        System.out.println("Member Controller : " + form.getMemberId());
        try{
            Member me = memberService.findOne(Integer.valueOf(memberId));
            Member friend = memberService.findOne(form.getMemberId());
            memberService.addFriend(me, friend);
            Map<Integer, String> friendList = memberService.getFriends(me);

            return friendList;
//            JSONObject json = new JSONObject();
//            Iterator<Integer> it = friendList.keySet().iterator();
//            while(it.hasNext()){
//                int key = it.next();
//                json.put(String.valueOf(key), friendList.get(key));
//            }
//            return json.toString();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("MemberController : no friend member found");
            return null;
        }
    }

}
