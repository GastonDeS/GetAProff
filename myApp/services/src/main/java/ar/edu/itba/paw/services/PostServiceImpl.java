package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.PostDao;
import ar.edu.itba.paw.interfaces.services.PostService;
import ar.edu.itba.paw.models.Page;
import ar.edu.itba.paw.models.PageRequest;
import ar.edu.itba.paw.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Transactional
    @Override
    public Optional<Post> post(Long uploaderId, Long classId, String filename, byte[] file, String message, String type) {
        return Optional.ofNullable(postDao.post(uploaderId, classId, filename, file, message, type));
    }

    @Transactional
    @Override
    public Page<Post> retrievePosts(Long classId, Integer page, Integer pageSize) {
        return postDao.retrievePosts(classId, new PageRequest(page, pageSize));
    }

    @Transactional
    @Override
    public Optional<Post> getPost(Long postId) {
        return Optional.ofNullable(postDao.getPostById(postId));
    }
}
