package io.member;

import java.io.IOException;
import java.util.List;

public interface MemberRepository {

    void add(Member member);

    List<Member> findAll();

}
