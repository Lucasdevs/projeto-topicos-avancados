package com.unifacisa.rede.controller;

import com.unifacisa.rede.config.BasicAuthValidator;
import com.unifacisa.rede.dto.PostCreateDTO;
import com.unifacisa.rede.dto.PostDTO;
import com.unifacisa.rede.entity.PostEntity;
import com.unifacisa.rede.entity.UserEntity;
import com.unifacisa.rede.login.LoggedUserCache;
import com.unifacisa.rede.mapper.PostMapper;
import com.unifacisa.rede.repository.PostRepository;
import com.unifacisa.rede.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    @Autowired
    BasicAuthValidator basicAuthValidator;

    @Autowired
    LoggedUserCache loggedUserCache;

    @Autowired
    PostMapper postMapper;

    public PostController(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    @GetMapping
    public List<PostDTO> getAllPosts() {
        return toDtoList(postRepository.findAll());
    }

    @GetMapping("/{id}")
    public List<PostDTO> getByUserId(@PathVariable Long id) {
        return toDtoList(postRepository.findByUserId(id));
    }

    @PostMapping
    public PostDTO createPost(
            @RequestBody PostCreateDTO dto,
            HttpServletRequest request
    ) {

        basicAuthValidator.validateBasicAuth(request);

        UserEntity user = loggedUserCache
                .getLoggedUser();

        PostEntity post = new PostEntity();
        post.setUser(user);
        post.setMessage(dto.getMessage());
        post.setCreationDate(LocalDateTime.now());

        postRepository.save(post);

        return PostMapper.INSTANCE.toDto(post);
    }

    @PutMapping("/{id}")
    public PostDTO updatePost(@PathVariable Long id, @RequestBody PostDTO postDTO) {
        PostEntity post = postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post não encontrado"));

        post.setMessage(postDTO.getMessage());

        return toDto(postRepository.save(post));
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        postRepository.deleteById(id);
        return "Post deletado com sucesso!";
    }

    @GetMapping("/feed")
    @Cacheable("feed")
    public List<PostDTO> feed(Authentication auth) {
        UserEntity user = userRepository.findByUsername(auth.getName()).orElseThrow();

        Set<Long> ids = user.getFriends()
                .stream().map(UserEntity::getId).collect(Collectors.toSet());
        ids.add(user.getId());

        List<PostEntity> posts = postRepository.findAll();
        List<PostEntity> postsFiltered =posts.stream().filter(p -> ids.contains(p.getUser().getId())).collect(Collectors.toList());

        return PostMapper.INSTANCE.toDTOList(postsFiltered);
    }

    private PostDTO toDto(PostEntity postEntity) {
        return postMapper.toDto(postEntity);
    }

    private List<PostDTO> toDtoList(List<PostEntity> posts) {
        List<PostDTO> postDTOList = new ArrayList<>();
        for (PostEntity postEntity : posts) {
            postDTOList.add(toDto(postEntity));
        }
        return postDTOList;
    }

}
