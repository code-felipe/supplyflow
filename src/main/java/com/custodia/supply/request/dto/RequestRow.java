package com.custodia.supply.request.dto;

import java.util.Date;

import com.custodia.supply.request.entity.Request;




public class RequestRow {
	
	private Long id;
    private String description;
    private String additionalItems;
    private Date createAt;       
    
    private String siteAddress;     
    private String siteCode;
    
    private Long itemsCount;
    private Long totalQuantity;   // <-- NUEVO
    

    public RequestRow() {
	
	}

	public RequestRow(Long id, String description, String additionalItems,
                      Date createAt, String siteAddress, String siteCode, Long itemsCount,
                      Long totalQuantity) {
        this.id = id;
        this.description = description;
        this.additionalItems = additionalItems;
        this.createAt = createAt;
        this.siteAddress = siteAddress;
        this.siteCode = siteCode;
        this.itemsCount = itemsCount;
        this.totalQuantity = totalQuantity;
        

    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAdditionalItems() {
		return additionalItems;
	}

	public void setAdditionalItems(String additionalItems) {
		this.additionalItems = additionalItems;
	}

	public Date getCreateAt() {
		return createAt;
	}

	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}

	public String getSiteAddress() {
		return siteAddress;
	}

	public void setSiteAddress(String siteAddress) {
		this.siteAddress = siteAddress;
	}

	public String getSiteCode() {
		return siteCode;
	}

	public void setSiteCode(String siteCode) {
		this.siteCode = siteCode;
	}

	public Long getItemsCount() {
		return itemsCount;
	}
	
	public void setItemsCount(Long items) {
		this.itemsCount = items;
	}

	public Long getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(Long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}
	
	public static RequestRow of(Request re) {
		RequestRow r = new RequestRow();
		r.setAdditionalItems(re.getAdditionalItems());
		r.setCreateAt(re.getCreateAt());
		r.setDescription(r.getDescription());
		
		return r;
		
	};
	
}
/*
 * private Long id;

	private String description;

	@Column(name = "additional_items")
	private String additionaItems;

	@ManyToOne(fetch = FetchType.LAZY)
	private User user;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "site_id")
	private CustomerSite shipTo; // ← 
 */
