package com.erp.member.domain;

import com.erp.member.controller.dto.MemberLoginDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<MemberLoginDto> findById(String id);

}
