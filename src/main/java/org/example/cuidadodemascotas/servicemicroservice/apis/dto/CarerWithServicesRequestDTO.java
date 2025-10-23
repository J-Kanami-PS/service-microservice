package org.example.cuidadodemascotas.servicemicroservice.apis.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.example.cuidadodemascotas.servicemicroservice.apis.dto.CarerWithServicesRequestDTOServicesInner;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * CarerWithServicesRequestDTO
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-10-23T03:14:05.163097800-03:00[America/Asuncion]")
public class CarerWithServicesRequestDTO {

  private Long userId;

  /**
   * Estado de disponibilidad del cuidador
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
  private List<@Valid CarerWithServicesRequestDTOServicesInner> services;

  public CarerWithServicesRequestDTO() {
    super();
  }

  /**
   * Constructor with only required parameters
   */
  public CarerWithServicesRequestDTO(Long userId, AvailabilityStateEnum availabilityState, Integer amountPet) {
    this.userId = userId;
    this.availabilityState = availabilityState;
    this.amountPet = amountPet;
  }

  public CarerWithServicesRequestDTO userId(Long userId) {
    this.userId = userId;
    return this;
  }

  /**
   * ID del usuario asociado al cuidador
   * @return userId
  */
  @NotNull 
  @Schema(name = "userId", description = "ID del usuario asociado al cuidador", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("userId")
  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public CarerWithServicesRequestDTO availabilityState(AvailabilityStateEnum availabilityState) {
    this.availabilityState = availabilityState;
    return this;
  }

  /**
   * Estado de disponibilidad del cuidador
   * @return availabilityState
  */
  @NotNull 
  @Schema(name = "availabilityState", description = "Estado de disponibilidad del cuidador", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("availabilityState")
  public AvailabilityStateEnum getAvailabilityState() {
    return availabilityState;
  }

  public void setAvailabilityState(AvailabilityStateEnum availabilityState) {
    this.availabilityState = availabilityState;
  }

  public CarerWithServicesRequestDTO amountPet(Integer amountPet) {
    this.amountPet = amountPet;
    return this;
  }

  /**
   * Cantidad de mascotas que puede cuidar
   * minimum: 0
   * @return amountPet
  */
  @NotNull @Min(0) 
  @Schema(name = "amountPet", description = "Cantidad de mascotas que puede cuidar", requiredMode = Schema.RequiredMode.REQUIRED)
  @JsonProperty("amountPet")
  public Integer getAmountPet() {
    return amountPet;
  }

  public void setAmountPet(Integer amountPet) {
    this.amountPet = amountPet;
  }

  public CarerWithServicesRequestDTO services(List<@Valid CarerWithServicesRequestDTOServicesInner> services) {
    this.services = services;
    return this;
  }

  public CarerWithServicesRequestDTO addServicesItem(CarerWithServicesRequestDTOServicesInner servicesItem) {
    if (this.services == null) {
      this.services = new ArrayList<>();
    }
    this.services.add(servicesItem);
    return this;
  }

  /**
   * Lista de servicios que ofrece el cuidador
   * @return services
  */
  @Valid 
  @Schema(name = "services", description = "Lista de servicios que ofrece el cuidador", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("services")
  public List<@Valid CarerWithServicesRequestDTOServicesInner> getServices() {
    return services;
  }

  public void setServices(List<@Valid CarerWithServicesRequestDTOServicesInner> services) {
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
    CarerWithServicesRequestDTO carerWithServicesRequestDTO = (CarerWithServicesRequestDTO) o;
    return Objects.equals(this.userId, carerWithServicesRequestDTO.userId) &&
        Objects.equals(this.availabilityState, carerWithServicesRequestDTO.availabilityState) &&
        Objects.equals(this.amountPet, carerWithServicesRequestDTO.amountPet) &&
        Objects.equals(this.services, carerWithServicesRequestDTO.services);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId, availabilityState, amountPet, services);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CarerWithServicesRequestDTO {\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
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

