package org.example.cuidadodemascotas.servicemicroservice.apis.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.ServiceResponseDTO;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.UserResponseDTO;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CarerWithServicesResponseDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-10-23T03:14:05.163097800-03:00[America/Asuncion]")
public class CarerWithServicesResponseDTO {

  private Long id;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime createdAt;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime updatedAt;

  private Boolean active;

  private UserResponseDTO user;

  /**
   * Estado de disponibilidad
   */
  public enum AvailabilityStateEnum {
    AVAILABLE("AVAILABLE"),
    
    NOT_AVAILABLE("NOT_AVAILABLE"),
    
    BUSY("BUSY");

    private String value;

    AvailabilityStateEnum(String value) {
      this.value = value;
    }

    @JsonValue
    public String getValue() {
      return value;
    }

    @Override
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static AvailabilityStateEnum fromValue(String value) {
      for (AvailabilityStateEnum b : AvailabilityStateEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private AvailabilityStateEnum availabilityState;

  private Integer amountPet;

  @Valid
  private List<@Valid ServiceResponseDTO> services;

  public CarerWithServicesResponseDTO id(Long id) {
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

  public CarerWithServicesResponseDTO createdAt(OffsetDateTime createdAt) {
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

  public CarerWithServicesResponseDTO updatedAt(OffsetDateTime updatedAt) {
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

  public CarerWithServicesResponseDTO active(Boolean active) {
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

  public CarerWithServicesResponseDTO user(UserResponseDTO user) {
    this.user = user;
    return this;
  }

  /**
   * Get user
   * @return user
  */
  @Valid 
  @Schema(name = "user", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("user")
  public UserResponseDTO getUser() {
    return user;
  }

  public void setUser(UserResponseDTO user) {
    this.user = user;
  }

  public CarerWithServicesResponseDTO availabilityState(AvailabilityStateEnum availabilityState) {
    this.availabilityState = availabilityState;
    return this;
  }

  /**
   * Estado de disponibilidad
   * @return availabilityState
  */
  
  @Schema(name = "availabilityState", description = "Estado de disponibilidad", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("availabilityState")
  public AvailabilityStateEnum getAvailabilityState() {
    return availabilityState;
  }

  public void setAvailabilityState(AvailabilityStateEnum availabilityState) {
    this.availabilityState = availabilityState;
  }

  public CarerWithServicesResponseDTO amountPet(Integer amountPet) {
    this.amountPet = amountPet;
    return this;
  }

  /**
   * Cantidad de mascotas que puede cuidar
   * @return amountPet
  */
  
  @Schema(name = "amountPet", description = "Cantidad de mascotas que puede cuidar", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("amountPet")
  public Integer getAmountPet() {
    return amountPet;
  }

  public void setAmountPet(Integer amountPet) {
    this.amountPet = amountPet;
  }

  public CarerWithServicesResponseDTO services(List<@Valid ServiceResponseDTO> services) {
    this.services = services;
    return this;
  }

  public CarerWithServicesResponseDTO addServicesItem(ServiceResponseDTO servicesItem) {
    if (this.services == null) {
      this.services = new ArrayList<>();
    }
    this.services.add(servicesItem);
    return this;
  }

  /**
   * Lista de servicios ofrecidos
   * @return services
  */
  @Valid 
  @Schema(name = "services", description = "Lista de servicios ofrecidos", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("services")
  public List<@Valid ServiceResponseDTO> getServices() {
    return services;
  }

  public void setServices(List<@Valid ServiceResponseDTO> services) {
    this.services = services;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CarerWithServicesResponseDTO carerWithServicesResponseDTO = (CarerWithServicesResponseDTO) o;
    return Objects.equals(this.id, carerWithServicesResponseDTO.id) &&
        Objects.equals(this.createdAt, carerWithServicesResponseDTO.createdAt) &&
        Objects.equals(this.updatedAt, carerWithServicesResponseDTO.updatedAt) &&
        Objects.equals(this.active, carerWithServicesResponseDTO.active) &&
        Objects.equals(this.user, carerWithServicesResponseDTO.user) &&
        Objects.equals(this.availabilityState, carerWithServicesResponseDTO.availabilityState) &&
        Objects.equals(this.amountPet, carerWithServicesResponseDTO.amountPet) &&
        Objects.equals(this.services, carerWithServicesResponseDTO.services);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, createdAt, updatedAt, active, user, availabilityState, amountPet, services);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CarerWithServicesResponseDTO {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    createdAt: ").append(toIndentedString(createdAt)).append("\n");
    sb.append("    updatedAt: ").append(toIndentedString(updatedAt)).append("\n");
    sb.append("    active: ").append(toIndentedString(active)).append("\n");
    sb.append("    user: ").append(toIndentedString(user)).append("\n");
    sb.append("    availabilityState: ").append(toIndentedString(availabilityState)).append("\n");
    sb.append("    amountPet: ").append(toIndentedString(amountPet)).append("\n");
    sb.append("    services: ").append(toIndentedString(services)).append("\n");
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

