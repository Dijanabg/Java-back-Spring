package com.iktpreobuka.project.scheduler;



import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.services.BillService;

@Component
public class OfferScheduler {

	@Autowired
    private OfferRepository offerRepository;

    @Autowired
    private BillService billService; // Pretpostavljam da imate servis koji upravlja računima

    // Ova metoda se automatski pokreće periodično, npr. jednom dnevno u ponoć
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkAndCancelExpiredOffers() {
    	List<OfferEntity> offers = StreamSupport.stream(offerRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        LocalDateTime now = LocalDateTime.now();
        for (OfferEntity offer : offers) {
            if (offer.getOfferExpires().isBefore(now)) {
                // Za svaku isteklu ponudu, otkazujemo povezane račune
                billService.cancelBillsForExpiredOffer(offer.getId());
            }
        }
    }
}
