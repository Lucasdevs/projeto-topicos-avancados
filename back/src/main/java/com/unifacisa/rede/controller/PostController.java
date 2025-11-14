package com.unifacisa.rede.controller;

import com.unifacisa.rede.dto.PostCreateDTO;
import com.unifacisa.rede.dto.PostDTO;
import com.unifacisa.rede.entity.PostEntity;
import com.unifacisa.rede.entity.UserEntity;
import com.unifacisa.rede.mapper.PostMapper;
import com.unifacisa.rede.repository.PostRepository;
import com.unifacisa.rede.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:5173")
public class PostController {

    private final UserRepository userRepository;

    private final PostRepository postRepository;

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
    public PostDTO createPost(@RequestBody PostCreateDTO postCreateDTO) {
        UserEntity user = userRepository.findById(postCreateDTO.getUserID()).orElse(null);

        PostDTO postDTO = new PostDTO();
        postDTO.setMessage(postCreateDTO.getMessage());

        PostEntity post = toEntity(postDTO);
        post.setUser(user);

        PostEntity p = postRepository.save(post);

        return toDto(p);
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

    private PostDTO toDto(PostEntity postEntity) {
        return postMapper.toDto(postEntity);
    }

    private PostEntity toEntity(PostDTO postDTO) {
        return postMapper.toEntity(postDTO);
    }

    private List<PostDTO> toDtoList(List<PostEntity> posts) {
        List<PostDTO> postDTOList = new ArrayList<>();
        for (PostEntity postEntity : posts) {
            postDTOList.add(toDto(postEntity));
        }
        return postDTOList;
    }
}
