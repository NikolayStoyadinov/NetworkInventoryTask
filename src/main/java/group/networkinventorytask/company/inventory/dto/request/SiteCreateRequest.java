package group.networkinventorytask.company.inventory.dto.request;

import java.math.BigDecimal;

public class SiteCreateRequest {

    private Long id;

    private String siteCode;

    private String name;

    private String address;

    private String city;

    private String countryCode;

    private BigDecimal latitude;

    private BigDecimal longitude;

    private String status;

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {this.siteCode = siteCode;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}
}
