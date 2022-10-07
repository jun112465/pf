package jun.studyHelper.controller;


import jun.studyHelper.SessionConst;
import jun.studyHelper.dto.MemberDTO;
import jun.studyHelper.entity.Member;
import jun.studyHelper.dto.LoginForm;
import jun.studyHelper.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;


@Controller //컨트롤러 또한 자동으로 스프링빈에 등록된다.
public class MemberController {

    public MemberService memberService;

    @Autowired //이 어노테이션이 붙은 생성자는 연관된 객체를 스프링 컨테이너에서 자동으로 넣어준다.
    public MemberController(MemberService memberService){
        this.memberService = memberService;
    }

    @PostMapping("/members/new")
    public String create(MemberDTO form, RedirectAttributes redirect){
        Member member = new Member();
        member.setId(form.getMemberId());
        member.setPw(form.getPassword());

        System.out.println("LOG : member : " + form.toString());

        if(!memberService.isMemberNull(member)){
            System.out.println("LOG: Nothing entered");
            redirect.addFlashAttribute("noTextErrorMsg", "Nothing Entered");
            return "redirect:/";
        }

        if(!memberService.join(member)){
            // 회원가입 실패
            redirect.addFlashAttribute("joinErrorMsg", "Duplicate Id Found");
        }

        return "redirect:/";
    }

//    @PostMapping("/members/login");
//    public String CookieLogin(LoginForm form, HttpServletResponse resp, RedirectAttributes redirectAttributes){
//        String id = form.getMemberId();
//        String pw = form.getPassword();
//
//        Member m = new Member();
//        m.setId(id);
//        m.setPw(pw);
//
//        if(memberService.validateMemberInfo(m)){
//            Cookie idCookie = new Cookie("memberId", id);
//            idCookie.setPath("/");
//            resp.addCookie(idCookie);
//        }else{
//            redirectAttributes.addAttribute("error-msg", "No Member Found");
//            System.out.println("No member founded");
//        }
//
//        return "redirect:/";
//    }

    @PostMapping("/members/login")
    public String SessionLogin(
            LoginForm form, HttpServletRequest req,
            HttpServletResponse resp, RedirectAttributes redirect){

        String id = form.getMemberId();
        String pw = form.getPassword();

        Member loginMember = new Member(id, pw);

        if(memberService.validateMemberInfo(loginMember) != null) {
            // 사용자의 데이터를 찾은 경우
            HttpSession session = req.getSession();
            session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);

            // 세션 유지 시간을 최대 하루로 잡음
            session.setMaxInactiveInterval(24*60*60);
        }else{
            // 사용자의 데이터를 찾지 못한 경우
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("LoginError", "No Member Found, Try Again");

            String msg = "No Member Found, Try Again";

            // addFlashAttribute 을 통해 세션이 생성된다.
            redirect.addFlashAttribute("loginErrorMsg", msg);
        }

        return "redirect:/";
    }

    @GetMapping ("/members/logout")
    public String logout(HttpServletRequest req, HttpServletResponse resp){

        HttpSession session = req.getSession();

        // 세션은 유지하되 세션과 연결된 object 삭제
        session.removeAttribute(SessionConst.LOGIN_MEMBER);

        // jsession 쿠키 삭제
        Cookie cookie = new Cookie("JSESSIONID", null);
        cookie.setMaxAge(-1);
        resp.addCookie(cookie);

        // 세션을 풀로 돌려보내기 (세션 제거)
        session.invalidate();

        return "redirect:/";
    }

//    @GetMapping("/members/setting")
//    public String setting(Model model, @CookieValue(name="memberId", required = false)String memberId){
//        Member member = memberService.findOne(memberId);
//        model.addAttribute("MemberId", memberId);
////        model.addAttribute("Name", member.getName());
////        model.addAttribute("Status", feijwj, eijfw, "eifje", )
////        model.addAttribute("Status)
//        return "infoSetting";
//    }

////    @PostMapping("/members/profile-update")
////    public String profileUpdate(MemberForm form, @CookieValue(name="memberId", required = false)String memberId){
////        System.out.println("MemberController executed");
////
////        MultipartFile imageFile = null;
////        String message = null;
//////        Integer memberId = null;
////
////        if(! (form.getImageFile().getSize() == 0))
////            imageFile = form.getImageFile();
//////        if(form.getMemberId() != null)
//////            memberId = form.getMemberId();
////        if(!form.getMessage().equals(""))
////            message = form.getMessage();
////
////        memberService.updateMemberInfo(imageFile, message, Integer.parseInt(memberId));
////
//////
////        System.out.println(imageFile);
////        System.out.println(message);
//////        System.out.println(memberId);
////        return "redirect:/";
////    }
//
}
