package droneexpress.optimizer.statistics;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "RESPONSE")
public class StatResponse implements Serializable {

    public static final int UNSET = 0;
    public static final int UNFINDABLE = 1;
    public static final int LOCALLY = 2;
    public static final int EMPTY = 3;
    public static final int DISPLACED = 4;

    private static final long serialVersionUID = 8957495791690234333L;

    private Long ident;
    private int type;
    private double travelledDistance;
    private Date responseDateTime;
    private long delay;

    public StatResponse() {
        this.type = UNSET;
        this.travelledDistance = UNSET;
        this.responseDateTime = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.getIdent() == null) ? 0 : this.getIdent().hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof StatResponse)) {
            return false;
        }
        StatResponse other = (StatResponse) obj;
        if (this.getIdent() == null) {
            if (other.getIdent() != null) {
                return false;
            }
        } else if (!this.getIdent().equals(other.getIdent())) {
            return false;
        }
        return true;
    }

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getIdent() {
        return ident;
    }

    public void setIdent(Long ident) {
        this.ident = ident;
    }

    @Column(name = "TYPE")
    @NotNull
    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Column(name = "TRAVELLED")
    @NotNull
    public double getTravelledDistance() {
        return travelledDistance;
    }

    public void setTravelledDistance(double travelledDistance) {
        this.travelledDistance = travelledDistance;
    }

    @Column(name = "responseDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getResponseDateTime() {
        return responseDateTime;
    }

    public void setResponseDateTime(Date responseDateTime) {
        this.responseDateTime = responseDateTime;
    }

    @Column(name = "delay")
    public long getDelay() {
        return delay;
    }

    public void setDelay(long delay) {
        this.delay = delay;
    }
}
