package com.god.thymeleaf.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private  String username;
    private String password;
    private Boolean enabled;

    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles =new ArrayList<>();



//    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)  //user 삭제시 연관된 board에서도 삭제

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY) //사용하는 순간에 조회함.
//    @JsonIgnore
    private  List<Board> boards =new ArrayList<>();
}
