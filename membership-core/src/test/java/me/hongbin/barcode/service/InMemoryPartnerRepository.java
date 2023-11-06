package me.hongbin.barcode.service;

import me.hongbin.partner.domain.Partner;
import me.hongbin.partner.infra.PartnerRepository;

public class InMemoryPartnerRepository extends FakeRepository<Partner, Long> implements PartnerRepository {

}
