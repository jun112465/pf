package jun.studyHelper.controller;


import jun.studyHelper.domain.member.Member;
import jun.studyHelper.domain.member.MemberForm;
import jun.studyHelper.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import java.util.Map;

@Controller //컨트롤러 또한 자동으로 스프링빈에 등록된다.
public class MemberController {

    public MemberService memberService;

    @Autowired //이 어노테이션이 붙은 생성자는 연관된 객체를 스프링 컨테이너에서 자동으로 넣어준다.
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setMemberId(form.getMemberId());
        member.setPassword(form.getPassword());
        memberService.join(member);
        
        return "redirect:/";
    }

    @PostMapping("/members/login")
    public String login(MemberForm form, HttpServletResponse resp){
        String id = form.getMemberId();
        String pw = form.getPassword();

        Member m = new Member();
        m.setMemberId(id);
        m.setPassword(pw);

        if(memberService.validateMemberInfo(m)){
            Cookie idCookie = new Cookie("memberId", id);
            idCookie.setPath("/");
            resp.addCookie(idCookie);
        }else{
            System.out.println("No member founded");
        }

        return "redirect:/";
    }


//    @PostMapping(value = "/members/add-friend",  produces = "application/json")
//    @ResponseBody
//    public Map<Integer,String> addFriend( MemberForm form, @CookieValue(name="memberId", required = false)String memberId){
//
//        System.out.println("Member Controller : " + form.getMemberId());
//        try{
//            Member me = memberService.findOne(memberId);
//            Member friend = memberService.findOne(form.getMemberId());
//            memberService.addFriend(me, friend);
//            Map<Integer, String> friendList = memberService.getFriends(me);
//
//            return friendList;
////            JSONObject json = new JSONObject();
////            Iterator<Integer> it = friendList.keySet().iterator();
////            while(it.hasNext()){
////                int key = it.next();
////                json.put(String.valueOf(key), friendList.get(key));
////            }
////            return json.toString();
//        }catch(Exception e){
//            e.printStackTrace();
//            System.out.println("MemberController : no friend member found");
//            return null;
//        }
//    }

    @GetMapping("/members/setting")
    public String setting(Model model, @CookieValue(name="memberId", required = false)String memberId){
        Member member = memberService.findOne(memberId);
        model.addAttribute("MemberId", memberId);
        model.addAttribute("Name", member.getName());
//        model.addAttribute("Status", feijwj, eijfw, "eifje", )
//        model.addAttribute("Status)
        return "infoSetting";
    }

    @PostMapping("/members/profile-update")
    public String profileUpdate(MemberForm form, @CookieValue(name="memberId", required = false)String memberId){
        System.out.println("MemberController executed");

        MultipartFile imageFile = null;
        String message = null;
//        Integer memberId = null;

        if(! (form.getImageFile().getSize() == 0))
            imageFile = form.getImageFile();
//        if(form.getMemberId() != null)
//            memberId = form.getMemberId();
        if(!form.getMessage().equals(""))
            message = form.getMessage();

        memberService.updateMemberInfo(imageFile, message, Integer.parseInt(memberId));

//
        System.out.println(imageFile);
        System.out.println(message);
//        System.out.println(memberId);
        return "redirect:/";
    }


//    @PostMapping(value = "/members/deleteFriend", produces = "application/json")
//    @ResponseBody
//    public Map<Integer, String> deleteFriend(MemberForm form, @CookieValue(name="memberId", required=false)String memberId){
//        int friendId = form.getMemberId();
//        return memberService.deleteFriend(memberId, friendId);
//    }
}
