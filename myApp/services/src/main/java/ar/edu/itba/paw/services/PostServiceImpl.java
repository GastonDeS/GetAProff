package ar.edu.itba.paw.services;

import ar.edu.itba.paw.interfaces.daos.PostDao;
import ar.edu.itba.paw.interfaces.services.PostService;
import ar.edu.itba.paw.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostDao postDao;

    @Transactional
    @Override
    public Post post(Long uploaderId, Long classId, String filename, byte[] file, String message, String type) {
        return postDao.post(uploaderId, classId, filename, file, message, type);
    }

    @Transactional
    @Override
    public List<Post> retrievePosts(Long classId) {
        List<Post> postList = postDao.retrievePosts(classId);
        postList.sort((o1, o2) -> o2.getPostId().compareTo(o1.getPostId()));
        return postList;
    }

    @Override
    public Post getFileData(Long postId) {
        return postDao.getPostById(postId);
    }
}
