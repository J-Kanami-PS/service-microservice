package org.example.cuidadodemascotas.servicemicroservice.apis.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ServiceResponseDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-10-23T01:23:03.345706300-03:00[America/Asuncion]")
public class ServiceResponseDTO {

  private Long id;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime updatedAt;

  private Boolean active;

  private Long carerId;

  private Long serviceTypeId;

  private String description;

  private Double price;

  public ServiceResponseDTO id(Long id) {
    this.id = id;
    return this;
  }

  /**
   * Get id
   * @return id
  */
  
  @Schema(name = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ServiceResponseDTO createdAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
    return this;
  }

  /**
   * Get createdAt
   * @return createdAt
  */
  @Valid 
  @Schema(name = "createdAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("createdAt")
  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public ServiceResponseDTO updatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
    return this;
  }

  /**
   * Get updatedAt
   * @return updatedAt
  */
  @Valid 
  @Schema(name = "updatedAt", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("updatedAt")
  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public ServiceResponseDTO active(Boolean active) {
    this.active = active;
    return this;
  }

  /**
   * Get active
   * @return active
  */
  
  @Schema(name = "active", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("active")
  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }

  public ServiceResponseDTO carerId(Long carerId) {
    this.carerId = carerId;
    return this;
  }

  /**
   * Get carerId
   * @return carerId
  */
  
  @Schema(name = "carerId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("carerId")
  public Long getCarerId() {
    return carerId;
  }

  public void setCarerId(Long carerId) {
    this.carerId = carerId;
  }

  public ServiceResponseDTO serviceTypeId(Long serviceTypeId) {
    this.serviceTypeId = serviceTypeId;
    return this;
  }

  /**
   * Get serviceTypeId
   * @return serviceTypeId
  */
  
  @Schema(name = "serviceTypeId", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("serviceTypeId")
  public Long getServiceTypeId() {
    return serviceTypeId;
  }

  public void setServiceTypeId(Long serviceTypeId) {
    this.serviceTypeId = serviceTypeId;
  }

  public ServiceResponseDTO description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Get description
   * @return description
  */
  
  @Schema(name = "description", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public ServiceResponseDTO price(Double price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * @return price
  */
  
  @Schema(name = "price", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("price")
  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ServiceResponseDTO serviceResponseDTO = (ServiceResponseDTO) o;
    return Objects.equals(this.id, serviceResponseDTO.id) &&
        Objects.equals(this.createdAt, serviceResponseDTO.createdAt) &&
        Objects.equals(this.updatedAt, serviceResponseDTO.updatedAt) &&
        Objects.equals(this.active, serviceResponseDTO.active) &&
        Objects.equals(this.carerId, serviceResponseDTO.carerId) &&
        Objects.equals(this.serviceTypeId, serviceResponseDTO.serviceTypeId) &&
        Objects.equals(this.description, serviceResponseDTO.description) &&
        Objects.equals(this.price, serviceResponseDTO.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdAt, updatedAt, active, carerId, serviceTypeId, description, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceResponseDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
    sb.append("    carerId: ").append(toIndentedString(carerId)).append("\n");
    sb.append("    serviceTypeId: ").append(toIndentedString(serviceTypeId)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    price: ").append(toIndentedString(price)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

