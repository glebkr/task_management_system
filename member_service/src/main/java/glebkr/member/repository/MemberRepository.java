package glebkr.member.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import glebkr.member.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, UUID> {

}
