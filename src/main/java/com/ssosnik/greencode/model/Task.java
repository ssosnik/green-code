package com.ssosnik.greencode.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;

import io.swagger.v3.oas.annotations.media.Schema;

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
		FAILURE_RESTART("FAILURE_RESTART"), PRIORITY("PRIORITY"), SIGNAL_LOW("SIGNAL_LOW"), STANDARD("STANDARD");

		private static final Map<String, RequestTypeEnum> STRING_TO_VALUE_MAP = new HashMap<>();

		static {
			for (RequestTypeEnum e : values()) {
				STRING_TO_VALUE_MAP.put(e.value, e);
			}
		}

		private String value;

		RequestTypeEnum(String value) {
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
		public static RequestTypeEnum fromValue(String value) {
			RequestTypeEnum result = STRING_TO_VALUE_MAP.get(value);
			if (result == null) {
				throw new IllegalArgumentException("Unexpected value '" + value + "'");
			}
			return result;
		}
	}

	private RequestTypeEnum requestType;

	private Integer atmId;

	public Task region(Integer region) {
		this.region = region;
		return this;
	}

	/**
	 * Get region minimum: 1 maximum: 9999
	 * 
	 * @return region
	 */
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
	 * 
	 * @return requestType
	 */

	@Schema(name = "requestType", example = "STANDARD", description = "Type of request", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@JsonProperty("requestType")
	public RequestTypeEnum getRequestType() {
		return requestType;
	}

	@JsonIgnore
	public Integer getRequestPriority() {
		return requestType.ordinal();
	}

	public void setRequestType(RequestTypeEnum requestType) {
		this.requestType = requestType;
	}

	public Task atmId(Integer atmId) {
		this.atmId = atmId;
		return this;
	}

	/**
	 * Get atmId minimum: 1 maximum: 9999
	 * 
	 * @return atmId
	 */
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
		return Objects.equals(this.region, task.region) && Objects.equals(this.requestType, task.requestType)
				&& Objects.equals(this.atmId, task.atmId);
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
