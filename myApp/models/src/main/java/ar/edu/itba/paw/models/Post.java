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

    @JoinColumn(name = "classid", referencedColumnName = "classid", foreignKey = @ForeignKey(name = "posts_classid_fkey"))
    @ManyToOne(optional = false)
    private Lecture associatedLecture;

    @JoinColumn(name = "userid", referencedColumnName = "userid", foreignKey = @ForeignKey(name = "posts_userid_fkey"))
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

    @Column(nullable = false)
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

    private Post(Builder builder) {
        this.postId = builder.postId;
        this.associatedLecture = builder.associatedLecture;
        this.uploader = builder.uploader;
        this.message = builder.message;
        this.filename = builder.filename;
        this.file = builder.file;
        this.type = builder.type;
        this.time = builder.time;
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

    public static class Builder
    {
        private User uploader;
        private Lecture associatedLecture;
        private Long postId;
        private Timestamp time;
        private String message, filename, type;
        private byte[] file;

        public Builder() {
        }
        public Builder uploader(User uploader) {
            this.uploader = uploader;
            return this;
        }
        public Builder associatedLecture(Lecture associatedLecture) {
            this.associatedLecture = associatedLecture;
            return this;
        }
        public Builder postId(Long postId) {
            this.postId = postId;
            return this;
        }
        public Builder message(String message) {
            this.message = message;
            return this;
        }
        public Builder filename(String filename) {
            this.filename = filename;
            return this;
        }
        public Builder type(String type) {
            this.type = type;
            return this;
        }
        public Builder file(byte[] file) {
            this.file = file;
            return this;
        }
        public Builder time(Timestamp time) {
            this.time = time;
            return this;
        }
        public Post build() {
            return new Post(this);
        }
    }
}
