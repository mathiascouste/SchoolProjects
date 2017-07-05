package droneexpress.optimizer.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;

import droneexpress.optimizer.statistics.StatRequest;
import droneexpress.optimizer.statistics.StatResponse;
import droneexpress.tools.Converter;


@Local
@Stateless
public class StatisticsCrud {

    @PersistenceContext(unitName = "optimizer-pu")
    EntityManager entityManager;

    public void saveRequest(StatRequest request) {
        entityManager.persist(request);
    }

    public void saveResponse(StatResponse response) {
        entityManager.persist(response);
    }

    public long getRequestsCount() {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(StatResponse.class)));
        return entityManager.createQuery(cq).getSingleResult();
    }

    public double getAverageDelay() {
        CriteriaBuilder qb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Double> cq = qb.createQuery(Double.class);

        Root<StatResponse> employee = cq.from(StatResponse.class);
        cq.select(qb.avg(employee.<Number> get("delay")));

        return entityManager.createQuery(cq).getSingleResult();
    }

    public List<Integer> getRequestsByTime(String unit, int length) {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime next;
        List<Integer> res = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            next = minus(now, unit, 1);
            res.add(this.getRequestsBetween(next, now));
            now = next;
        }
        return res;
    }

    private int getRequestsBetween(LocalDateTime from, LocalDateTime to) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> cq = cb.createQuery(Long.class);
        CriteriaQuery<StatRequest> cq_sr = cb.createQuery(StatRequest.class);

        Root<StatRequest> requests = cq_sr.from(StatRequest.class);
        Predicate pred1 = cb.lessThan(requests.get("requestDateTime"),
                Converter.LocalDateTimeTODate(to));
        Predicate pred2 = cb.greaterThan(requests.get("requestDateTime"),
                Converter.LocalDateTimeTODate(from));
        Predicate pred = cb.and(pred1, pred2);

        cq.select(cb.count(cq.from(StatRequest.class)));
        cq.where(pred);

        return entityManager.createQuery(cq).getSingleResult().intValue();
    }

    public LocalDateTime minus(LocalDateTime ldt, String unit, int n) {
        if (unit.toLowerCase().equals("second")) {
            return ldt.minusSeconds(n);
        } else if (unit.toLowerCase().equals("minute")) {
            return ldt.minusMinutes(n);
        } else if (unit.toLowerCase().equals("hour")) {
            return ldt.minusHours(n);
        } else if (unit.toLowerCase().equals("day")) {
            return ldt.minusDays(n);
        } else if (unit.toLowerCase().equals("week")) {
            return ldt.minusWeeks(n);
        } else if (unit.toLowerCase().equals("month")) {
            return ldt.minusMonths(n);
        } else {
            return ldt.minusYears(n);
        }
    }
}
