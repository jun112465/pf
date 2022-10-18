package jun.studyHelper.controller;


import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jun.studyHelper.AwsS3Config;
import jun.studyHelper.SessionConst;
import jun.studyHelper.domain.dto.MemberDTO;
import jun.studyHelper.domain.entity.Member;
import jun.studyHelper.domain.dto.LoginForm;
import jun.studyHelper.service.MemberService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Controller //컨트롤러 또한 자동으로 스프링빈에 등록된다.
public class MemberController {

    public MemberService memberService;



    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/members/new")
    public String create(MemberDTO form, RedirectAttributes redirect){
        Member member = new Member();
        member.setUid(form.getMemberId());
        member.setPw(form.getPassword());

        System.out.println("LOG : member : " + form.toString());

        if(memberService.isMemberBlank(member)){
            System.out.println("LOG: Nothing entered");
            redirect.addFlashAttribute("noTextErrorMsg", "Nothing Entered");
            return "redirect:/";
        }

        if(memberService.join(member) == null){
            // 회원가입 실패
            redirect.addFlashAttribute("joinErrorMsg", "Duplicate Id Found");
        }

        return "redirect:/";
    }

    @PostMapping("/members/login")
    public String SessionLogin(
            LoginForm form, HttpServletRequest req,
            HttpServletResponse resp, RedirectAttributes redirect){

        String uId = form.getMemberId();
        String pw = form.getPassword();

        Member loginMember = new Member();
        loginMember.setUid(uId);
        loginMember.setPw(pw);


        if(memberService.validateMemberInfo(loginMember)) {
            // 사용자의 데이터를 찾은 경우
            loginMember = memberService.findMemberByUid(loginMember).get();
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


}
