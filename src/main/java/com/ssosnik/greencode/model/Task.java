package com.ssosnik.greencode.model;

import java.util.Objects;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * Task
 */

@Generated(value = "com.ssosnik.greencode.codegen.languages.SpringCodegen", date = "2023-04-01T15:51:15.919232900+02:00[Europe/Warsaw]")
public class Task {

  private Integer region;

  /**
   * Type of request
   */
  public enum RequestTypeEnum {
    FAILURE_RESTART("FAILURE_RESTART", 10),
    PRIORITY("PRIORITY", 20),
    SIGNAL_LOW("SIGNAL_LOW", 30),
    STANDARD("STANDARD", 40);

    private String value;
    private Integer priority;

    RequestTypeEnum(String value, Integer priority) {
      this.value = value;
      this.priority = priority;
    }

    public Integer getPriority() {
      return priority;
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
    public static RequestTypeEnum fromValue(String value) {
    	// TODO create HashMap
      for (RequestTypeEnum b : RequestTypeEnum.values()) {
        if (b.value.equals(value)) {
          return b;
        }
      }
      throw new IllegalArgumentException("Unexpected value '" + value + "'");
    }
  }

  private RequestTypeEnum requestType;

  private Integer atmId;

  public Task region(Integer region) {
    this.region = region;
    return this;
  }

  /**
   * Get region
   * minimum: 1
   * maximum: 9999
   * @return region
  */
  @Min(1) @Max(9999) 
  @Schema(name = "region", example = "10", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("region")
  public Integer getRegion() {
    return region;
  }

  public void setRegion(Integer region) {
    this.region = region;
  }

  public Task requestType(RequestTypeEnum requestType) {
    this.requestType = requestType;
    return this;
  }

  /**
   * Type of request
   * @return requestType
  */
  
  @Schema(name = "requestType", example = "STANDARD", description = "Type of request", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("requestType")
  public RequestTypeEnum getRequestType() {
    return requestType;
  }
  	
    @JsonIgnore
	public Integer getRequestPriority() {
		return requestType.getPriority();
	}

	
  public void setRequestType(RequestTypeEnum requestType) {
    this.requestType = requestType;
  }

  public Task atmId(Integer atmId) {
    this.atmId = atmId;
    return this;
  }

  /**
   * Get atmId
   * minimum: 1
   * maximum: 9999
   * @return atmId
  */
  @Min(1) @Max(9999) 
  @Schema(name = "atmId", example = "500", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("atmId")
  public Integer getAtmId() {
    return atmId;
  }

  public void setAtmId(Integer atmId) {
    this.atmId = atmId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Task task = (Task) o;
    return Objects.equals(this.region, task.region) &&
        Objects.equals(this.requestType, task.requestType) &&
        Objects.equals(this.atmId, task.atmId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(region, requestType, atmId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Task {\n");
    sb.append("    region: ").append(toIndentedString(region)).append("\n");
    sb.append("    requestType: ").append(toIndentedString(requestType)).append("\n");
    sb.append("    atmId: ").append(toIndentedString(atmId)).append("\n");
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

