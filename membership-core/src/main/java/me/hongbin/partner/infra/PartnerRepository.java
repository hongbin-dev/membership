package me.hongbin.partner.infra;

import org.springframework.data.jpa.repository.JpaRepository;

import me.hongbin.partner.domain.Partner;

public interface PartnerRepository extends JpaRepository<Partner, Long> {
}
