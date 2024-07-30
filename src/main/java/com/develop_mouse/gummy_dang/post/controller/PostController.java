package com.develop_mouse.gummy_dang.post.controller;

import com.develop_mouse.gummy_dang.authentication.util.SecurityContextUtil;
import com.develop_mouse.gummy_dang.common.Exception.BusinessException;
import com.develop_mouse.gummy_dang.common.domain.ResponseCode;
import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import com.develop_mouse.gummy_dang.member.repository.MemberRepository;
import com.develop_mouse.gummy_dang.post.domain.entity.Post;
import com.develop_mouse.gummy_dang.post.domain.entity.PostCoordinate;
import com.develop_mouse.gummy_dang.post.repository.PostCoordinateRepository;
import org.springframework.ui.Model;
import com.develop_mouse.gummy_dang.post.service.PostService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

// +)
import jakarta.servlet.ServletContext;

    /*
    게시판 주요 기능
    1) 글목록 (/post/list)
    2) 작성 (/post/create)
        - 지도 좌표값 받아와야 ..!
    3) 조회 (/post/{id})
    4) 수정 (/post/update/{id})
    5) 삭제 (/post/delete/{id})
    */

    // 추가 해야할 것: 좌표값 관련 부분


@Controller
public class PostController {


    private PostService postService;

    private SecurityContextUtil securityContextUtil; // 현 사용자 불러오기 위해 주입

    // +) 이미지에서 사용
    private ServletContext servletContext; // ServletContext 주입

    private MemberRepository memberRepository;
    private PostCoordinateRepository postCoordinateRepository;

    // 글 목록
    @GetMapping("/post/list")
    public Response postList(Model model){
        model.addAttribute("post", postService.findAll());
        return Response.ok();
    }

    // 글 생성 화면 form
    @GetMapping("/post/createForm")
    public Response createForm(Model model){
        model.addAttribute("post", Post.builder().createdAt(LocalDateTime.now()).build());
        return Response.ok();
    }

    // 글 작성
    @PostMapping("/post/create")
    public Response createPost(@ModelAttribute Post post,
                             @RequestParam("image") MultipartFile image) throws IOException {

        Member user = memberRepository.findById(securityContextUtil.getContextMemberInfo().getMemberId()).get();
        post.builder().member(user).build();

        if (!image.isEmpty()) { // image 객체 있으면 (비어있지 않으면) 저장
            String imagePath = saveImage(image);
            post.builder().imageUrl(imagePath).build();
        }
        postService.save(post);
        return Response.ok();
    }

    // 글 수정 화면 form
    @PostMapping("/post/updateForm/{id}")
    public Response updatePostForm(@PathVariable Long id,
                                 Model model){
        Post post = postService.findById(id);
        if (post == null){
            new BusinessException(ResponseCode.POST_NOT_FOUND);
        }
        model.addAttribute("post", post.builder().updatedAt(LocalDateTime.now()).build());
        return Response.ok();
    }

    // 글 수정
    @PostMapping("/post/update/{id}")
    public Response updatePost(@PathVariable long id,
                             @ModelAttribute Post post,
                             @RequestParam("image") MultipartFile image) throws  IOException{
        Post existingPost = postService.findById(id);

        if (existingPost != null){ // 게시글 존재하면 수정

            Member user = memberRepository.findById(securityContextUtil.getContextMemberInfo().getMemberId()).get();
            if (!existingPost.getMember().equals(user)){ // 작성자 != 사용자 이면 예외 처리
                new BusinessException(ResponseCode.POST_AUTHOR_DIFFERENCE);
            }

            existingPost.builder().title(post.getTitle())
                    .description(post.getDescription()).build();

            if(!image.isEmpty()){ // 수정할 때 넘어오는 image 있으면 그 이미지로 다시 저장
                String imagePath = saveImage(image);
                existingPost.builder().imageUrl(imagePath).build();
            }
        }
        postService.save(existingPost);

        return Response.ok();
    }


    // 특정 id의 게시글 삭제
    @GetMapping("/post/delete/{id}")
    public Response deletePost(@PathVariable Long id){

        Post post = postService.findById(id);
        Member user = memberRepository.findById(securityContextUtil.getContextMemberInfo().getMemberId()).get();
        if (!post.getMember().equals(user)){
            new BusinessException(ResponseCode.POST_AUTHOR_DIFFERENCE);
        }

        postService.deleteById(id);
        return Response.ok();
    }

    // 글 조회
    @GetMapping("/post/{id}")
    public Response viewPost(@PathVariable Long id, Model model) {
        model.addAttribute("post", postService.findById(id));
        return Response.ok();
    }


    // 이미지 불러오는 부분은 GPT의 도움을 많이 받아서 ...
    // 혹 잘못됐다면 알려주세용...
    private String saveImage(MultipartFile image) throws IOException {
        // 애플리케이션의 루트 경로 가져오기
        String realPath = servletContext.getRealPath("/");

        // 이미지 저장 경로 설정
        String imagePath = realPath + "uploads/";
        File uploadDir = new File(imagePath);

        // 디렉토리가 없으면 생성
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 파일 이름 생성
        String fileName = UUID.randomUUID().toString() + "_" + image.getOriginalFilename();
        File dest = new File(uploadDir, fileName);

        // 파일 저장
        image.transferTo(dest);

        // 저장된 파일의 경로 반환
        return "/uploads/" + fileName;
    }

}
