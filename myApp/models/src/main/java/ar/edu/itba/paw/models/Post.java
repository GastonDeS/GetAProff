package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "posts_postid_seq")
    @SequenceGenerator(name = "posts_postid_seq", sequenceName = "posts_postid_seq", allocationSize = 1)
    private Long postId;

    @JoinColumn(name = "classid", referencedColumnName = "classid")
    @ManyToOne(optional = false)
    private Class associatedClass;

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

    Post() {
        //Just for Hibernate
    }

    public Post(Long postId, Class associatedClass, User uploader, String message, String filename, byte[] file, String type) {
        this.postId = postId;
        this.associatedClass = associatedClass;
        this.uploader = uploader;
        this.message = message;
        this.filename = filename;
        this.file = file;
        this.type = type;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }

    public Class getAssociatedClass() {
        return associatedClass;
    }

    public void setAssociatedClass(Class associatedClass) {
        this.associatedClass = associatedClass;
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
