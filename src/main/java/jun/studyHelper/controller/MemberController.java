package jun.studyHelper.controller;


import jun.studyHelper.SessionConst;
import jun.studyHelper.model.dto.CategoryDTO;
import jun.studyHelper.model.dto.MemberDTO;
import jun.studyHelper.model.dto.NewMemberDTO;
import jun.studyHelper.model.dto.NoticeDTO;
import jun.studyHelper.model.entity.Category;
import jun.studyHelper.model.entity.Member;
import jun.studyHelper.model.entity.Notice;
import jun.studyHelper.service.CategoryService;
import jun.studyHelper.service.FileService;
import jun.studyHelper.service.MemberService;
import jun.studyHelper.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public FileService fileService;
    public NoticeService noticeService;
    public CategoryService categoryService;



    @Autowired
    public MemberController(
            MemberService memberService,
//            FileService fileService,
            NoticeService noticeService,
            CategoryService categoryService
    ) {
        this.memberService = memberService;
//        this.fileService = fileService;
        this.noticeService = noticeService;
        this.categoryService = categoryService;
    }

    @PostMapping("/members/new")
    @ResponseBody
    public String create(@RequestBody MemberDTO memberDTO){

        System.out.println(memberDTO);
//        System.out.println(newMemberDTO.toString());
////        System.out.println(map.toString());
//        MemberDTO memberDTO = MemberDTO.builder()
//                .uid(newMemberDTO.getUid())
//                .password(newMemberDTO.getPwd())
//                .build();

        if(memberService.findMember(memberDTO).isPresent())
            return "DUPLICATED UID";
//
//        if(memberService.isMemberBlank(memberDTO))
//            return "PLEASE ENTER TEXT";

        Member member = memberService.join(memberDTO);
        // 초기 카테고리 & 노트 생성

        // add member
        System.out.println("멤버 등록 후 " + member.toString());


        // add first category
        CategoryDTO categoryDTO = CategoryDTO.builder()
                .memberId(member.getId())
                .name("Set Category Name")
                .build();
        Category category = categoryService.addCategory(categoryDTO);

        // add first note
        NoticeDTO noticeDTO = NoticeDTO.builder()
                .memberId(member.getId())
                .categoryId(category.getId())
                .content("first note")
                .date(Notice.getCurrentDate())
                .build();
        noticeService.add(noticeDTO);

        return "new member added";
    }

    @PostMapping("/members/login")
    public String SessionLogin(
            MemberDTO memberDTO,
            HttpServletRequest req,
            RedirectAttributes redirect){

        System.out.println("SessionLogin");
        System.out.println(memberDTO);

        System.out.println("조건문 직전");
        if(memberService.validateMemberInfo(memberDTO)) {
            // 사용자의 데이터를 찾은 경우
            System.out.println("조건문 통과");
            System.out.println(memberService.findMember(memberDTO));
            Member loginMember = memberService.findMember(memberDTO).orElse(null);
            System.out.println("founded member");
            System.out.println(loginMember);

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


    @PostMapping("/members/update")
    public String updateProfile(
            @RequestParam("profile") MultipartFile profileImg,
            @RequestParam("id") String id,
            @RequestParam("pw") String pw,
            HttpServletRequest req
    ){
        Member member = (Member)req.getSession().getAttribute(SessionConst.LOGIN_MEMBER);


        if(!profileImg.isEmpty()){
            String PATH = "profiles/";
            String newFileName = PATH + member.getId();
            System.out.println("newFileName : " + newFileName);
            try {
                fileService.uploadToS3(profileImg, newFileName);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println(id);
        if(!id.isEmpty()){
            member.setUid(id);

        }

        System.out.println(pw);
        if(!pw.isEmpty()){
            member.setPw(pw);
        }

        return "redirect:/";
    }

}
