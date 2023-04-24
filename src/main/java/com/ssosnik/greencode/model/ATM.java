package com.ssosnik.greencode.model;

import java.util.Objects;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * ATMs details
 */

@Schema(name = "ATM", description = "ATMs details")
@Generated(value = "com.ssosnik.greencode.codegen.languages.SpringCodegen", date = "2023-04-01T15:51:15.919232900+02:00[Europe/Warsaw]")
public class ATM {

	private Integer region;

	private Integer atmId;

	public ATM() {
		this.region = null;
		this.atmId = null;
	}

	public ATM(Integer region, Integer atmId) {
		this.region = region;
		this.atmId = atmId;
	}

	public ATM region(Integer region) {
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

	public ATM atmId(Integer atmId) {
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
		ATM ATM = (ATM) o;
		return Objects.equals(this.region, ATM.region) && Objects.equals(this.atmId, ATM.atmId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(region, atmId);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class ATM {\n");
		sb.append("    region: ").append(toIndentedString(region)).append("\n");
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
