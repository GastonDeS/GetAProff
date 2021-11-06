package ar.edu.itba.paw.models;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posts_postid_seq")
    @SequenceGenerator(name = "posts_postid_seq", sequenceName = "posts_postid_seq", allocationSize = 1)
    private Long postId;

    @JoinColumn(name = "classid", referencedColumnName = "classid")
    @ManyToOne(optional = false)
    private Lecture associatedLecture;

    @JoinColumn(name = "userid", referencedColumnName = "userid")
    @ManyToOne(optional = false)
    private User uploader;

    @Column
    private String message;

    @Column
    private String filename;

    @Column
    private byte[] file;

    @Column
    private String type;

    @Column
    private Timestamp time;

    Post() {
        //Just for Hibernate
    }

    public Post(Long postId, Lecture associatedLecture, User uploader, String message, String filename, byte[] file, String type, Timestamp time) {
        this.postId = postId;
        this.associatedLecture = associatedLecture;
        this.uploader = uploader;
        this.message = message;
        this.filename = filename;
        this.file = file;
        this.type = type;
        this.time = time;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Lecture getAssociatedLecture() {
        return associatedLecture;
    }

    public void setAssociatedLecture(Lecture associatedLecture) {
        this.associatedLecture = associatedLecture;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
