package com.develop_mouse.gummy_dang.post.service;

import com.develop_mouse.gummy_dang.authentication.util.SecurityContextUtil;
import com.develop_mouse.gummy_dang.common.Exception.BusinessException;
import com.develop_mouse.gummy_dang.common.domain.ResponseCode;
import com.develop_mouse.gummy_dang.common.domain.response.Response;
import com.develop_mouse.gummy_dang.member.domain.entity.Member;
import com.develop_mouse.gummy_dang.member.repository.MemberRepository;
import com.develop_mouse.gummy_dang.post.DTO.PostCoordinateDTO;
import com.develop_mouse.gummy_dang.post.DTO.PostDTO;
import com.develop_mouse.gummy_dang.post.domain.entity.Post;
import com.develop_mouse.gummy_dang.post.domain.entity.PostCoordinate;
import com.develop_mouse.gummy_dang.post.domain.request.PostRequest;
import com.develop_mouse.gummy_dang.post.domain.response.PostResponse;
import com.develop_mouse.gummy_dang.post.repository.PostCoordinateRepository;
import com.develop_mouse.gummy_dang.post.repository.PostRepository;
import jakarta.servlet.ServletContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


    /*
    게시판 주요 기능
    1) 글목록 (/post/list)
    2) 작성 (/post/create)
    3) 조회 (/post/{id})
    4) 수정 (/post/update/{id})
    5) 삭제 (/post/delete/{id})

+)

    */

@Service
@Transactional
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final SecurityContextUtil securityContextUtil; // 현 사용자 불러오기 위해 주입
    private final PostCoordinateRepository postCoordinateRepository;

    // +) 이미지에서 사용
    private ServletContext servletContext; // ServletContext 주입


    // 글 생성
    @Override
    public Response<PostResponse> createPost(PostRequest postRequest){
        Member user = memberRepository.findById(securityContextUtil.getContextMemberInfo().getMemberId()).stream()
            .findAny()
            .orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

      Post post = Post.builder()
              .member(user)
              .title(postRequest.getTitle())
              .description(postRequest.getDescription())
              .build();

        if (postRequest.getPostCoordinates().size() > 6) { // 좌표 6개 초과 들어오면 오류
            throw new BusinessException(ResponseCode.OVER_NUM_COORDINATES);
        }

        Post savedPost = postRepository.save(post);

        postRequest.getPostCoordinates().forEach(postCoordinateDTO -> {
            PostCoordinate postCoordinate = PostCoordinate.builder()
                .latitude(postCoordinateDTO.getLatitude())
                .longitude(postCoordinateDTO.getLongitude())
                .post(post)
                .build();
            postCoordinateRepository.save(postCoordinate);
            post.addCoordinate(postCoordinate);
        });

        PostResponse response = PostResponse.builder()
            .postId(savedPost.getId())
            .title(savedPost.getTitle())
            .description(savedPost.getDescription())
            .postCoordinates(savedPost.getPostCoordinates().stream()
                .map(PostCoordinateDTO::fromEntity)
                .toList())
            .build();

        return Response.ok(response);
    }

    // 글 수정 *좌표값...으어억*
    @Override
    public Response<PostResponse> updatePost(PostRequest postRequest) {

        Optional<Post> existingPost = postRepository.findById(postRequest.getPostId());

        if (existingPost.isEmpty()){
            throw new BusinessException(ResponseCode.POST_NOT_FOUND);
        }
        Post post = existingPost.get();

        Member user = memberRepository.findById(securityContextUtil.getContextMemberInfo().getMemberId()).get();
        if (!post.getMember().equals(user)){ // 작성자 != 사용자 이면 예외 처리
            throw new BusinessException(ResponseCode.POST_WRITER_DIFFERENCE);
        }

        post.updateTitle(postRequest.getTitle());
        post.updateDescription(postRequest.getDescription());

        Set<PostCoordinate> existingCoordinates = post.getPostCoordinates();
        Set<PostCoordinate> updatedCoordinates = new HashSet<>();

        long newCoordinatesCount = postRequest.getPostCoordinates().stream()
                .filter(coordinateDTO -> coordinateDTO.getId() == null)
                .count();

        if (existingCoordinates.size() + newCoordinatesCount > 6){
            throw new BusinessException(ResponseCode.OVER_NUM_COORDINATES);
        }

        for(PostCoordinateDTO coordinateDTO : postRequest.getPostCoordinates()){
            if(coordinateDTO.getId() != null){ // 기존 좌표 업데이트
                updateCoordinate(existingCoordinates, updatedCoordinates, coordinateDTO);
            }
            else { // 새로운 좌표 추가
                PostCoordinate postCoordinate = PostCoordinate.builder()
                        .latitude(coordinateDTO.getLatitude())
                        .longitude(coordinateDTO.getLongitude())
                        .post(post).build();
                updatedCoordinates.add(postCoordinate);
            }
        }

        existingCoordinates.clear();
        existingCoordinates.addAll(updatedCoordinates);

        Post savedPost = postRepository.save(post);

        PostResponse response = PostResponse.builder()
            .postId(savedPost.getId())
            .title(savedPost.getTitle())
            .description(savedPost.getDescription())
            .postCoordinates(savedPost.getPostCoordinates().stream()
                .map(PostCoordinateDTO::fromEntity)
                .toList())
            .build();

        return Response.ok(response);
    }

    // 기존 Post 에서 수정할 Coordinate 찾아서 수정하는 메서드
    private void updateCoordinate(Set<PostCoordinate> existingCoordinates, Set<PostCoordinate> updatedCoordinates, PostCoordinateDTO coordinateDTO) {
        boolean found = false;
        for (PostCoordinate existingCoordinate : existingCoordinates) {
            if (existingCoordinate.getId().equals(coordinateDTO.getId())) {
                existingCoordinate.updateLongitude(coordinateDTO.getLongitude());
                existingCoordinate.updateLatitude(coordinateDTO.getLatitude());
                updatedCoordinates.add(existingCoordinate);
                found = true;
                break;
            }
        }
        if (!found) {
            throw new BusinessException(ResponseCode.COORDINATE_NOT_FOUND);
        }
    }


    @Override
    public Response<Void> deletePost(Long id) {

        Post post = postRepository.findById(id).stream()
            .findAny()
            .orElseThrow(() -> new BusinessException(ResponseCode.POST_NOT_FOUND));

        Member user = memberRepository.findById(securityContextUtil.getContextMemberInfo().getMemberId()).stream()
            .findAny()
            .orElseThrow(() -> new BusinessException(ResponseCode.MEMBER_NOT_FOUND));

        if (!post.getMember().equals(user)){
            throw new BusinessException(ResponseCode.POST_WRITER_DIFFERENCE);
        }

        postRepository.deleteById(id);
        return Response.ok();
    }

    // 글 하나 보기
    @Override
    public Response<PostResponse> detailPost(Long id) {
        Post post = postRepository.findById(id).stream()
            .findAny()
            .orElseThrow(() -> new BusinessException(ResponseCode.POST_NOT_FOUND));

        PostResponse postResponse = PostResponse.builder()
                .postId(post.getId())
                .title(post.getTitle())
                .description(post.getDescription())
                .imageUrl(post.getImageUrl())
                .postCoordinates(post.getPostCoordinates().stream()
                    .map(PostCoordinateDTO::fromEntity)
                    .toList())
                .build();

        return Response.ok(postResponse);
    }

    // 글 목록
    @Override
    public Response<List<PostResponse>> postList() {
        List<Post> posts = postRepository.findAll();

        List<PostResponse> postResponses = posts.stream()
            .map(post -> PostResponse.builder()
				.postId(post.getId())
				.title(post.getTitle())
				.description(post.getDescription())
				.imageUrl(post.getImageUrl())
				.postCoordinates(post.getPostCoordinates().stream()
                    .map(PostCoordinateDTO::fromEntity)
                    .toList())
				.build())
            .toList();

        return Response.ok(postResponses);
    }

}
