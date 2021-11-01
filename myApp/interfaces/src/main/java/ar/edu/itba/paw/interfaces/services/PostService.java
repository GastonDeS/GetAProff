package ar.edu.itba.paw.interfaces.services;

import ar.edu.itba.paw.models.Post;

import java.util.List;

public interface PostService {

    Post post(Long uploaderId, Long classId, String filename, byte[] file, String message);

    List<Post> retrievePosts(Long classId);

    Post getFileData(Long postId);
}
