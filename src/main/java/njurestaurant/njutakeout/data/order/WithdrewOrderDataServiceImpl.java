package njurestaurant.njutakeout.data.order;

import njurestaurant.njutakeout.data.dao.order.WithdrewOrderDao;
import njurestaurant.njutakeout.dataservice.account.UserDataService;
import njurestaurant.njutakeout.dataservice.order.WithdrewOrderDataService;
import njurestaurant.njutakeout.entity.account.User;
import njurestaurant.njutakeout.entity.order.WithdrewOrder;
import njurestaurant.njutakeout.publicdatas.order.WithdrewState;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrewOrderDataServiceImpl implements WithdrewOrderDataService {
    private final WithdrewOrderDao withdrewOrderDao;
    private final UserDataService userDataService;

    @Autowired
    public WithdrewOrderDataServiceImpl(WithdrewOrderDao withdrewOrderDao, UserDataService userDataService) {
        this.withdrewOrderDao = withdrewOrderDao;
        this.userDataService = userDataService;
    }

    @Override
    public WithdrewOrder saveWithdrewOrder(WithdrewOrder withdrewOrder) {
        return withdrewOrderDao.save(withdrewOrder);
    }

    @Override
    public void deleteById(int id) {
        withdrewOrderDao.deleteById(id);
    }

    @Override
    public WithdrewOrder findWithdrewOrderById(int id) {
        Optional<WithdrewOrder> withdrewOrderOptional = withdrewOrderDao.findById(id);
        return withdrewOrderOptional.isPresent() ? withdrewOrderOptional.get() : null;
    }

    @Override
    public List<WithdrewOrder> findAll() {
        return withdrewOrderDao.findAll();
    }

    @Override
    public Page<WithdrewOrder> findByState(WithdrewState withdrewState, Pageable pageable, WithdrewOrder withdrewOrder) {
        return condition(withdrewState, pageable, withdrewOrder);
    }

    @Override
    public List<WithdrewOrder> findByOperatorId(int id) {
        return withdrewOrderDao.findWithdrewOrdersByOperateId(id);
    }

    @Override
    public List<WithdrewOrder> findByDateRange(Date d1, Date d2) {
        return withdrewOrderDao.findAll(dateBetween(d1, d2));
    }

    private Specification<WithdrewOrder> dateBetween(Date startDate, Date endDate) {
        return new Specification<WithdrewOrder>() {
            @Override
            public Predicate toPredicate(Root<WithdrewOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.between(root.get("applyTime"), startDate, endDate);
            }
        };
    }

    public Page<WithdrewOrder> condition(WithdrewState withdrewState , Pageable pageable, WithdrewOrder withdrewOrder) {
        return withdrewOrderDao.findAll(new Specification<WithdrewOrder>() {
            @Override
            public Predicate toPredicate(Root<WithdrewOrder> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> list = new ArrayList<>();
                if (!StringUtils.isEmpty(withdrewOrder.getNumber())) {
                    list.add(cb.equal(root.get("number").as(String.class), withdrewOrder.getNumber()));
                }
                if (!StringUtils.isEmpty(withdrewOrder.getType())) {
                    list.add(cb.equal(root.get("type").as(String.class), withdrewOrder.getType()));
                }
                if (!StringUtils.isEmpty(withdrewOrder.getCard_out())) {
                    list.add(cb.equal(root.get("card_out").as(String.class), withdrewOrder.getCard_out()));
                }
                if (!StringUtils.isEmpty(withdrewOrder.getCard_in())) {
                    list.add(cb.equal(root.get("card_in").as(String.class), withdrewOrder.getCard_in()));
                }
                if (withdrewOrder.getStartDate() != null) {
                    list.add(cb.greaterThan(root.get("applyTime").as(Date.class), withdrewOrder.getStartDate()));
                }
                if (withdrewOrder.getEndDate() != null) {
                    list.add(cb.lessThan(root.get("applyTime").as(Date.class), withdrewOrder.getEndDate()));
                }
                if (!StringUtils.isEmpty(withdrewOrder.getApplicantUsername())) {
                    User user = userDataService.getUserByUsername(withdrewOrder.getApplicantUsername());
                    if (user == null)
                        return null;
                    else
                        list.add(cb.equal(root.get("applicant_id").as(Integer.class), user.getId()));
                }
                if (withdrewState!=null){
                    System.out.println(withdrewState);
                    list.add(cb.equal(root.get("state").as(WithdrewState.class),  withdrewState));
                }
                query.where(cb.and(list.toArray(new Predicate[list.size()])));
                return query.getRestriction();
            }
        }, pageable);
    }
}
