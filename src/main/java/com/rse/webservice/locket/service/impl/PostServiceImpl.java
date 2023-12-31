package com.rse.webservice.locket.service.impl;

import com.rse.webservice.locket.exception.ApiRequestException;
import com.rse.webservice.locket.model.Post;
import com.rse.webservice.locket.payload.request.account.AccountSelfRequest;
import com.rse.webservice.locket.payload.request.post.*;
import com.rse.webservice.locket.payload.response.post.PostCreateResponse;
import com.rse.webservice.locket.payload.response.post.PostSearchResponse;
import com.rse.webservice.locket.payload.response.post.PostSelfResponse;
import com.rse.webservice.locket.payload.response.post.PostUpdateResponse;
import com.rse.webservice.locket.repository.PostRepository;
import com.rse.webservice.locket.service.AccountService;
import com.rse.webservice.locket.service.PostService;
import com.rse.webservice.locket.service.common.CommonService;
import com.rse.webservice.locket.utils.DataUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final CommonService commonService;

    private final AccountService accountService;


    @Override
    public PostCreateResponse create(PostCreateRequest request) {
        var newPost = DataUtils.copyProperties(request, Post.class);
        newPost.setUserId(commonService.getLoginId());
        postRepository.save(newPost);
        return PostCreateResponse.of(newPost.getId());
    }

    @Override
    public PostSelfResponse self(PostSelfRequest request) {
        var post = getPost(request.getId());
        post.setViewCount(post.getViewCount() + 1);
        postRepository.save(post);

        var account = accountService.self(AccountSelfRequest.of(post.getUserId()));
        String author = account.getFirstName() + " " + account.getLastName();
        var response = DataUtils.copyProperties(post, PostSelfResponse.class);
        response.setAuthor(author);
        response.setPublishedAt(post.getCreatedAt());
        return response;
    }

    @Override
    public PostSelfResponse delete(PostDeleteRequest request) {
        return null;
    }

    @Override
    public PostSearchResponse search(PostSearchRequest request) {
        return null;
    }

    @Override
    public PostUpdateResponse update(PostUpdateRequest request) {
        return null;
    }

    private Post getPost(Long id) {
        return postRepository
                .findById(id)
                .orElseThrow(() -> new ApiRequestException("post with id %s not found".formatted(id)));
    }

}