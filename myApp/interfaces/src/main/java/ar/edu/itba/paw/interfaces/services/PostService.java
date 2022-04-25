package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.Post;

import java.util.Optional;

public interface PostService {

    Optional<Post> post(Long uploaderId, Long classId, String filename, byte[] file, String message, String type);

    Page<Post> retrievePosts(Long classId, Integer page, Integer pageSize);

    Post getFileData(Long postId);
}
