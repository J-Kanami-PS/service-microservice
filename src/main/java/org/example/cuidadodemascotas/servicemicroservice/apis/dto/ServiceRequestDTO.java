package org.example.cuidadodemascotas.servicemicroservice.apis.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * ServiceRequestDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-10-18T23:30:00.353076500-03:00[America/Asuncion]")
public class ServiceRequestDTO {

  private Long carerId;

  private Long serviceTypeId;

  private String description;

  private Double price;

  public ServiceRequestDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public ServiceRequestDTO(Long carerId, Long serviceTypeId, Double price) {
    this.carerId = carerId;
    this.serviceTypeId = serviceTypeId;
    this.price = price;
  }

  public ServiceRequestDTO carerId(Long carerId) {
    this.carerId = carerId;
    return this;
  }

  /**
   * Get carerId
   * @return carerId
  */
  @NotNull 
  @Schema(name = "carerId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("carerId")
  public Long getCarerId() {
    return carerId;
  }

  public void setCarerId(Long carerId) {
    this.carerId = carerId;
  }

  public ServiceRequestDTO serviceTypeId(Long serviceTypeId) {
    this.serviceTypeId = serviceTypeId;
    return this;
  }

  /**
   * Get serviceTypeId
   * @return serviceTypeId
  */
  @NotNull 
  @Schema(name = "serviceTypeId", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("serviceTypeId")
  public Long getServiceTypeId() {
    return serviceTypeId;
  }

  public void setServiceTypeId(Long serviceTypeId) {
    this.serviceTypeId = serviceTypeId;
  }

  public ServiceRequestDTO description(String description) {
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

  public ServiceRequestDTO price(Double price) {
    this.price = price;
    return this;
  }

  /**
   * Get price
   * minimum: 0
   * @return price
  */
  @NotNull @DecimalMin("0") 
  @Schema(name = "price", requiredMode = Schema.RequiredMode.REQUIRED)
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
    ServiceRequestDTO serviceRequestDTO = (ServiceRequestDTO) o;
    return Objects.equals(this.carerId, serviceRequestDTO.carerId) &&
        Objects.equals(this.serviceTypeId, serviceRequestDTO.serviceTypeId) &&
        Objects.equals(this.description, serviceRequestDTO.description) &&
        Objects.equals(this.price, serviceRequestDTO.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(carerId, serviceTypeId, description, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ServiceRequestDTO {\n");
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

