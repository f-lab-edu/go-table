package flab.gotable.mapper;

import flab.gotable.member.dto.request.MemberSignUpRequestDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {
    public void saveMember(MemberSignUpRequestDto memberSignUpRequestDto);
}
