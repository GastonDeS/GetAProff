package ar.edu.itba.paw.interfaces.daos;

import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.PageRequest;
import ar.edu.itba.paw.models.Post;

public interface PostDao {

    Post post(Long uploaderId, Long classId, String filename, byte[] file, String message, String type);

    Page<Post> retrievePosts(Long classId, PageRequest pageRequest);

    Post getPostById(Long postId);
}
