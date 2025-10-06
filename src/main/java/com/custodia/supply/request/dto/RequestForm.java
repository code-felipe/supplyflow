package com.custodia.supply.request.dto;

import java.util.Date;
import java.util.List;

import com.custodia.supply.customer.entity.CustomerSite;
import com.custodia.supply.request.entity.Request;
import com.custodia.supply.requestitem.entity.RequestItem;
import com.custodia.supply.user.entity.User;
import com.custodia.supply.util.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotEmpty;

import java.util.Date;


public class RequestForm {

    private Long id;

    @NotEmpty
    private String description;

    private String additionalItems;
    private Date createAt; // keep Date to match your entity

    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    /** ⚠ Consider removing password from a DTO used in views/forms/logs */
    private String password;
    private Date createAtUser;

    private Long assignedSiteId;   // from Request.shipTo.id
    private String siteAddress;       // from Request.shipTo.name
    private String siteCode;       // from Request.shipTo.externalCode
    private String customerName;   // from Request.shipTo.customer.name
    private String customerCode;   // from Request.shipTo.customer.externalCode

    public RequestForm() {}

    public RequestForm(
            Long id,
            @NotEmpty String description,
            String additionalItems,
            Date createAt,
            Long userId,
            String firstName,
            String lastName,
            String email,
            String phone,
            String password,
            Date createAtUser,
            Long assignedSiteId,
            String siteAddress,
            String siteCode,
            String customerName,
            String customerCode
    ) {
        this.id = id;
        this.description = description;
        this.additionalItems = additionalItems;
        this.createAt = createAt;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.createAtUser = createAtUser;
        this.assignedSiteId = assignedSiteId;
        this.siteAddress = siteAddress;
        this.siteCode = siteCode;
        this.customerName = customerName;
        this.customerCode = customerCode;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getAdditionalItems() { return additionalItems; }
    public void setAdditionalItems(String additionalItems) { this.additionalItems = additionalItems; }

    public Date getCreateAt() { return createAt; }
    public void setCreateAt(Date createAt) { this.createAt = createAt; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public Date getCreateAtUser() { return createAtUser; }
    public void setCreateAtUser(Date createAtUser) { this.createAtUser = createAtUser; }

    public Long getAssignedSiteId() { return assignedSiteId; }
    public void setAssignedSiteId(Long assignedSiteId) { this.assignedSiteId = assignedSiteId; }

    public String getSiteAddress() { return siteAddress; }
    public void setSiteAddress(String siteAddress) { this.siteAddress = siteAddress; }

    public String getSiteCode() { return siteCode; }
    public void setSiteCode(String siteCode) { this.siteCode = siteCode; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerCode() { return customerCode; }
    public void setCustomerCode(String customerCode) { this.customerCode = customerCode; }

    // Factory: map from entity -> DTO
    public static RequestForm of(Request re) {
        if (re == null) return null;

        RequestForm r = new RequestForm();

        r.setId(re.getId());
        r.setDescription(re.getDescription());
        // Your entity currently exposes getAdditionaItems() (typo). Use it to match:
        r.setAdditionalItems(re.getAdditionalItems());
        r.setCreateAt(re.getCreateAt());

        // user
        if (re.getUser() != null) {
            r.setUserId(re.getUser().getId());
            r.setFirstName(re.getUser().getFirstName());
            r.setLastName(re.getUser().getLastName());
            r.setEmail(re.getUser().getEmail());
            r.setPhone(re.getUser().getPhone());
            r.setPassword(re.getUser().getPassword()); // ⚠ consider not exposing this
            r.setCreateAtUser(re.getUser().getCreateAt());
        }

        // shipTo (destination site) — this is what your Request entity models
        if (re.getShipTo() != null) {
            CustomerSite site = re.getShipTo();
            r.setAssignedSiteId(site.getId());
            r.setSiteAddress(site.getAddress());
            r.setSiteCode(site.getExternalCode());

            if (site.getCustomer() != null) {
                r.setCustomerName(site.getCustomer().getName());
                r.setCustomerCode(site.getCustomer().getExternalCode());
            }
        }

        return r;
    }
    
 // en el DTO
    public static RequestForm fromUser(User u) {
        RequestForm f = new RequestForm();
        f.setUserId(u.getId());
        f.setFirstName(u.getFirstName());
        f.setLastName(u.getLastName());
        f.setEmail(u.getEmail());
        f.setPhone(u.getPhone());
        
        if (u.getAssignedSite() != null) f.assignedSiteId = u.getAssignedSite().getId();
        return f;
    }

}
