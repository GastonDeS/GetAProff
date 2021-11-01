package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Post;

import java.util.List;

public interface PostDao {

    Post post(Long uploaderId, Long classId, String filename, byte[] file, String message);

    List<Post> retrievePosts(Long classId);

    Post getPostById(Long postId);
}
