package flab.gotable.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Member {
    private long seq;
    private String name;
    private String id;
    private String password;
    private String phone;
}
