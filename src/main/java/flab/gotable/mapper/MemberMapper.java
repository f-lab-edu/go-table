package flab.gotable.mapper;

import flab.gotable.domain.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface MemberMapper {
    @Transactional
    public void saveMember(Member member);
}
