package org.example.cuidadodemascotas.servicemicroservice.apis.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CarerWithServicesRequestDTOServicesInner
 */

@JsonTypeName("CarerWithServicesRequestDTO_services_inner")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-10-23T03:14:05.163097800-03:00[America/Asuncion]")
public class CarerWithServicesRequestDTOServicesInner {

  private Long serviceTypeId;

  private String description;

  private Double price;

  public CarerWithServicesRequestDTOServicesInner() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CarerWithServicesRequestDTOServicesInner(Long serviceTypeId, Double price) {
    this.serviceTypeId = serviceTypeId;
    this.price = price;
  }

  public CarerWithServicesRequestDTOServicesInner serviceTypeId(Long serviceTypeId) {
    this.serviceTypeId = serviceTypeId;
    return this;
  }

  /**
   * ID del tipo de servicio
   * @return serviceTypeId
  */
  @NotNull 
  @Schema(name = "serviceTypeId", description = "ID del tipo de servicio", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("serviceTypeId")
  public Long getServiceTypeId() {
    return serviceTypeId;
  }

  public void setServiceTypeId(Long serviceTypeId) {
    this.serviceTypeId = serviceTypeId;
  }

  public CarerWithServicesRequestDTOServicesInner description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Descripción del servicio
   * @return description
  */
  @Size(max = 500) 
  @Schema(name = "description", description = "Descripción del servicio", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public CarerWithServicesRequestDTOServicesInner price(Double price) {
    this.price = price;
    return this;
  }

  /**
   * Precio del servicio
   * minimum: 0
   * @return price
  */
  @NotNull @DecimalMin("0") 
  @Schema(name = "price", description = "Precio del servicio", requiredMode = Schema.RequiredMode.REQUIRED)
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
    CarerWithServicesRequestDTOServicesInner carerWithServicesRequestDTOServicesInner = (CarerWithServicesRequestDTOServicesInner) o;
    return Objects.equals(this.serviceTypeId, carerWithServicesRequestDTOServicesInner.serviceTypeId) &&
        Objects.equals(this.description, carerWithServicesRequestDTOServicesInner.description) &&
        Objects.equals(this.price, carerWithServicesRequestDTOServicesInner.price);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serviceTypeId, description, price);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CarerWithServicesRequestDTOServicesInner {\n");
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

