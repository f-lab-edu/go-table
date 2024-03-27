package flab.gotable.Repository;

import flab.gotable.dto.request.MemberSignUpRequestDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface MemberRepository {
    @Transactional
    public void saveMember(MemberSignUpRequestDto memberSignUpRequestDto);
}
