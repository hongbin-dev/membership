package me.hongbin.partner.service;

import org.springframework.stereotype.Service;

import me.hongbin.partner.domain.Category;
import me.hongbin.partner.domain.Partner;
import me.hongbin.partner.dto.PartnerCreateResponse;
import me.hongbin.partner.infra.PartnerRepository;

@Service
public class PartnerService {

    private final PartnerRepository partnerRepository;

    public PartnerService(PartnerRepository partnerRepository) {
        this.partnerRepository = partnerRepository;
    }

    public PartnerCreateResponse create(String name, Category category) {
        var partner = new Partner(category, name);
        var persistedPartner = partnerRepository.save(partner);

        return new PartnerCreateResponse(
            persistedPartner.getId(),
            persistedPartner.getName(),
            persistedPartner.getCategory()
        );
    }
}
