package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Post;

import java.util.List;
import java.util.Optional;

public interface PostService {

    Optional<Post> post(Long uploaderId, Long classId, String filename, byte[] file, String message, String type);

    List<Post> retrievePosts(Long classId);

    Post getFileData(Long postId);
}
