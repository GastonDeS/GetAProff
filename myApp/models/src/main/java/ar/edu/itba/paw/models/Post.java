package ar.edu.itba.paw.models;

import javax.persistence.*;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "classfiles_postid_seq")
    @SequenceGenerator(name = "classfiles_postid_seq", sequenceName = "classfiles_postid_seq", allocationSize = 1)
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

    Post() {
        //Just for Hibernate
    }

    public Post(Long postId, Class associatedClass, User uploader, String message, String filename, byte[] file) {
        this.postId = postId;
        this.associatedClass = associatedClass;
        this.uploader = uploader;
        this.message = message;
        this.filename = filename;
        this.file = file;
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
}
