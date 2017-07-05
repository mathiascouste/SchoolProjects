package droneexpress.optimizer.statistics;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "REQUEST")
public class StatRequest implements Serializable {

    private static final long serialVersionUID = 8957495791690234333L;

    private Long ident;
    private Long departure;

    private Date dateTime;
    private Date requestDateTime;

    public StatRequest() {
        this.departure = null;
        this.dateTime = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        this.requestDateTime = Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
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

    @Column(name = "DEPARTURE")
    @NotNull
    public Long getDeparture() {
        return departure;
    }

    public void setDeparture(Long departure) {
        this.departure = departure;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((ident == null) ? 0 : ident.hashCode());
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
        if (!(obj instanceof StatRequest)) {
            return false;
        }
        StatRequest other = (StatRequest) obj;
        if (ident == null) {
            if (other.ident != null) {
                return false;
            }
        } else if (!ident.equals(other.ident)) {
            return false;
        }
        return true;
    }

    @Column(name = "dateTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    @Column(name = "requestDateTime")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getRequestDateTime() {
        return requestDateTime;
    }

    public void setRequestDateTime(Date requestDateTime) {
        this.requestDateTime = requestDateTime;
    }

    public void setDateTime_LocalDateTime(LocalDateTime dateTime) {
        this.dateTime = Date.from(dateTime.toInstant(ZoneOffset.UTC));
    }
}
