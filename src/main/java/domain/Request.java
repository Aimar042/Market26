package domain;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Request implements Serializable {

    @XmlID
    @Id
    @XmlJavaTypeAdapter(IntegerAdapter.class)
    @GeneratedValue
    private Integer RequestNumber;

    private String title;
    private String description;

    @ManyToOne
    private User user;

    @XmlIDREF
    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = CascadeType.PERSIST,
        orphanRemoval = true
    )
    private List<Report> reports = new ArrayList<Report>();

    @XmlIDREF
    @OneToMany(
        fetch = FetchType.EAGER,
        cascade = CascadeType.PERSIST,
        orphanRemoval = true
    )
    private List<Reclamation> reclamations = new ArrayList<Reclamation>();

    public Request() {
        super();
    }

    public Request(
        String title,
        String description,

        User user,
        boolean onSale
    ) {
        super();
        this.title = title;
        this.description = description;
        this.user = user;
    }

    /**
     * Get the number of the sale
     *
     * @return the sale number
     */
    public Integer getSaleNumber() {
        return RequestNumber;
    }

    /**
     * Set a number to a sale
     *
     * @param sale Number to be set	 */

    public void setSaleNumber(Integer saleNumber) {
        this.RequestNumber = saleNumber;
    }

    /**
     * Get the title  of the sale
     *
     * @return the title
     */

    public String getTitle() {
        return title;
    }

    /**
     * Set the title of the sale
     *
     * @param title to be set
     */

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Get the description of the sale
     *
     * @return the sale description
     */

    public String getDescription() {
        return description;
    }

    /**
     * Set the description of the sale
     *
     * @param description to be set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    public User getUser() {
        return this.user;
    }

    public String toString() {
        return RequestNumber + ";" + title;
    }

    public List<Report> getRports() {
        return this.reports;
    }

    public void addRport(String header, String description, String userName) {
        reports.add(new Report(header, description, userName, getSaleNumber()));
    }

    public void addReclamation(
        String header,
        String description,
        boolean status,
        String userName
    ) {
        reclamations.add(
            new Reclamation(
                header,
                description,
                status,
                userName,
                getSaleNumber()
            )
        );
    }

    public List<Reclamation> getReclamations() {
        return this.reclamations;
    }
}
