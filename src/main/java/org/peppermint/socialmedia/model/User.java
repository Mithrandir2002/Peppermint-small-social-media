package org.peppermint.socialmedia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Reels> reels;

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany
    @JoinTable(
            name = "user_follow",
            joinColumns = @JoinColumn(name = "follower"),
            inverseJoinColumns = @JoinColumn(name = "following")
    )
    private List<User> followers = new ArrayList<>();

    @JsonIgnore
    @ToString.Exclude
    @ManyToMany(mappedBy = "followers")
    private List<User> followings = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Comment> comments = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Post> userPost = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Story> stories = new ArrayList<>();

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "savedPost",
            joinColumns = @JoinColumn(name = "user"),
            inverseJoinColumns = @JoinColumn(name = "post")
    )
    private List<Post> savedPost = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "users")
    private List<Chat> chats = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Message> messages = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "liked")
    private List<Comment> likedComments = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "liked")
    private List<Post> likedPosts = new ArrayList<>();
}
