package com.xinyunlian.jinfu.dealer.dealer.dto.resp;

import com.xinyunlian.jinfu.prod.dto.ProductDto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by menglei on 2016年08月31日.
 */
public class AuthProdDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String dealerId;

    private String areaId;

    private String prodId;

    private String provinceId;

    private String cityId;

    private String districtId;

    private String streetId;

    private String province;

    private String city;

    private String area;

    private String street;

    private ProductDto productDto;

    private List<ProductDto> ProductList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDealerId() {
        return dealerId;
    }

    public void setDealerId(String dealerId) {
        this.dealerId = dealerId;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getProdId() {
        return prodId;
    }

    public void setProdId(String prodId) {
        this.prodId = prodId;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public ProductDto getProductDto() {
        return productDto;
    }

    public void setProductDto(ProductDto productDto) {
        this.productDto = productDto;
    }

    public List<ProductDto> getProductList() {
        return ProductList;
    }

    public void setProductList(List<ProductDto> productList) {
        ProductList = productList;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getDistrictId() {
        return districtId;
    }

    public void setDistrictId(String districtId) {
        this.districtId = districtId;
    }

    public String getStreetId() {
        return streetId;
    }

    public void setStreetId(String streetId) {
        this.streetId = streetId;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
