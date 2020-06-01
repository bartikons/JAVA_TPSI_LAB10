/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wizut.tpsi.ogloszenia.services;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import wizut.tpsi.ogloszenia.BodyStyle;
import wizut.tpsi.ogloszenia.CarManufacturer;
import wizut.tpsi.ogloszenia.CarModel;
import wizut.tpsi.ogloszenia.FuelType;
import wizut.tpsi.ogloszenia.Useruser;
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

    public Integer mxpage(OfferFilter filter) {
        String jpql = "SELECT count(cm) FROM Offer cm where 1=1";

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
        if (filter.getDescription()!=null) {
            
                jpql = jpql + " AND cm.description like :dest";
        }
        Query query = em.createQuery(jpql);
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
        if (filter.getDescription() != null) {
            query.setParameter("dest", "%" +filter.getDescription()+"%");
        }
        return ((Number) query.getSingleResult()).intValue() / 20;
    }

    public CarManufacturer getCarManufacturer(int id) {
        return em.find(CarManufacturer.class, id);
    }

    public CarModel getCarModel(int id) {
        return em.find(CarModel.class, id);
    }

    public Offer getOffer(int id) {
        return em.find(Offer.class, id);
    }

    public Useruser getUseruser(int id) {
        return em.find(Useruser.class, id);
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

    public List<Offer> getOffersByModel(int modelId) {
        String jpql = "select cm from Offer cm where cm.model.id = :id order by cm.title";
        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        query.setParameter("id", modelId);

        return query.getResultList();
    }

    public List<Offer> getOffersByManufacturer(int manufacturerId) {
        String jpql = "select * from Offer cm where cm.model.manufacturer.id = :id order by cm.title";
        TypedQuery<Offer> query = em.createQuery(jpql, Offer.class);
        query.setParameter("id", manufacturerId);

        return query.getResultList();

    }
    public Useruser getuser(Useruser useruser) {
        String jpql = "select cm from Useruser cm where cm.username= :usernama AND cm.password=hash('SHA256', stringtoutf8(:userpass), 1)";

        Query query = em.createQuery(jpql, Useruser.class);
        query.setParameter("usernama", useruser.getUsername());
        query.setParameter("userpass", useruser.getPassword());
        Useruser userreturn;
        try {
            userreturn=(Useruser) query.getSingleResult();
        } catch (NoResultException e) {
            userreturn=null;
        }
        return userreturn;
    }

    public List<Offer> getOffers(OfferFilter filter) {
        String jpql = "select cm from Offer cm  where 1=1";
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
        if (filter.getDescription()!=null) {
            
                jpql = jpql + " AND cm.description like :dest";
        }
        switch (filter.getSorte()) {
            case "price_up":
                jpql = jpql + " order by cm.price";
                break;
            case "price_down":
                jpql = jpql + " order by cm.price DESC";
                break;
            case "proyear_up":
                jpql = jpql + " order by cm.year";
                break;
            case "proyear_down":
                jpql = jpql + " order by cm.year DESC";
                break;
            case "addyear_up":
                jpql = jpql + " order by cm.yearofadd DESC";
                break;
            case "addyear_down":
                jpql = jpql + " order by cm.yearofadd ";
                break;
            case "default":
                jpql = jpql + " order by cm.id";
                break;
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
        if (filter.getDescription() != null) {
            query.setParameter("dest","%" +filter.getDescription()+"%");
        }
        int max = 20;
        query.setFirstResult(filter.getPage() * max);
        query.setMaxResults(max);
        return query.getResultList();
    }

    public Offer createOffer(Offer offer) {

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDateTime now = LocalDateTime.now();
        offer.setYearofadd(Integer.parseInt(dtf.format(now)));
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
