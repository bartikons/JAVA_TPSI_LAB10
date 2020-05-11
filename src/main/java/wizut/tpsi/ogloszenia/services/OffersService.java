/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizut.tpsi.ogloszenia.services;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import wizut.tpsi.ogloszenia.BodyStyle;
import wizut.tpsi.ogloszenia.CarManufacturer;
import wizut.tpsi.ogloszenia.CarModel;
import wizut.tpsi.ogloszenia.FuelType;
import wizut.tpsi.ogloszenia.jpa.Offer;
import wizut.tpsi.ogloszenia.web.OfferFilter;

/**
 *
 * @author naeri
 */
@Service
@Transactional
public class OffersService {

    @PersistenceContext
    private EntityManager em;

    public CarManufacturer getCarManufacturer(int id) {
        return em.find(CarManufacturer.class, id);
    }

    public CarModel getCarModel(int id) {
        return em.find(CarModel.class, id);
    }

    public Offer getOffer(int id) {
        return em.find(Offer.class, id);
    }

    public List<CarManufacturer> getCarManufacturers() {
        String jpql = "select cm from CarManufacturer cm order by cm.name";
        TypedQuery<CarManufacturer> query = em.createQuery(jpql, CarManufacturer.class);
        List<CarManufacturer> result = query.getResultList();
        return result;
    }

    public List<BodyStyle> getBodyStyles() {
        String jpql = "select cm from BodyStyle cm order by cm.name";
        TypedQuery<BodyStyle> query = em.createQuery(jpql, BodyStyle.class);
        List<BodyStyle> result = query.getResultList();
        return result;
    }

    public List<FuelType> getFuelTypes() {
        String jpql = "select cm from FuelType cm order by cm.name";
        TypedQuery<FuelType> query = em.createQuery(jpql, FuelType.class);
        List<FuelType> result = query.getResultList();
        return result;
    }

    public List<CarModel> getCarModels() {
        String jpql = "select cm from CarModel cm order by cm.name";
        TypedQuery<CarModel> query = em.createQuery(jpql, CarModel.class);
        List<CarModel> result = query.getResultList();
        return result;
    }

    public List<CarModel> getCarModels(int manufacturerId) {
        String jpql = "select cm from CarModel cm where cm.manufacturer.id = :id order by cm.name";
        TypedQuery<CarModel> query = em.createQuery(jpql, CarModel.class);
        query.setParameter("id", manufacturerId);

        return query.getResultList();
    }

    public List<Offer> getOffers() {
        String jpql = "select cm from Offer cm order by cm.id";
        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        List<Offer> result = query.getResultList();
        return result;
    }

    public List<Offer> getOffersByModel(int modelId) {
        String jpql = "select cm from Offer cm where cm.model.id = :id order by cm.title";
        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        query.setParameter("id", modelId);

        return query.getResultList();
    }

    public List<Offer> getOffersByManufacturer(int manufacturerId) {
        String jpql = "select cm from Offer cm where cm.model.manufacturer.id = :id order by cm.title";
        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        query.setParameter("id", manufacturerId);

        return query.getResultList();

    }

    public List<Offer> getOffers(OfferFilter filter) {
        String jpql = "select cm from Offer cm where 1=1";
        if (filter.getManufacturerId() != null) {
            if (filter.getModelId() != null) {
                jpql = jpql + " AND cm.model.id = :idmodel";
            } else {
                jpql = jpql + " AND cm.model.manufacturer.id = :idmanufacturer";
            }
        }
        if (filter.getFuelId() != null) {
            jpql = jpql + " AND cm.fuelType.id = :idFuel";
        }

        if (filter.getToyear() != null && filter.getFromyear() != null) {
            jpql = jpql + " AND cm.year BETWEEN :YEAR1 AND :YEAR2";
        } else {
            if (filter.getFromyear() != null) {
                jpql = jpql + " AND cm.year >= :YEAR1";
            }
            if (filter.getToyear() != null) {
                jpql = jpql + " AND cm.year <= :YEAR2";
            }
        }
        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        if (filter.getManufacturerId() != null) {
            if (filter.getModelId() != null) {
                query.setParameter("idmodel", filter.getModelId());
            } else {
                query.setParameter("idmanufacturer", filter.getManufacturerId());
            }
        }
        if (filter.getFuelId() != null) {
            query.setParameter("idFuel", filter.getFuelId());
        }
        if (filter.getToyear() != null && filter.getFromyear() != null) {
            query.setParameter("YEAR1", filter.getFromyear());
            query.setParameter("YEAR2", filter.getToyear());
        } else {
            if (filter.getFromyear() != null) {
                query.setParameter("YEAR1", filter.getFromyear());
            }
            if (filter.getToyear() != null) {
                query.setParameter("YEAR2", filter.getToyear());
            }
        }
        return query.getResultList();
    }

    public Offer createOffer(Offer offer) {
        em.persist(offer);
        return offer;
    }

    public Offer deleteOffer(Integer id) {
        Offer offer = em.find(Offer.class, id);
        em.remove(offer);
        return offer;
    }

    public Offer saveOffer(Offer offer) {
        return em.merge(offer);
    }
}
