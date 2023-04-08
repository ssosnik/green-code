package com.ssosnik.greencode.model;

import java.util.Objects;

import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * Clan
 */

@Generated(value = "com.ssosnik.greencode.codegen.languages.SpringCodegen", date = "2023-04-01T16:14:58.930237600+02:00[Europe/Warsaw]")
public class Clan {

	private Integer numberOfPlayers;

	private Integer points;

	public Clan numberOfPlayers(Integer numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
		return this;
	}

	/**
	 * Get numberOfPlayers minimum: 1 maximum: 1000
	 * 
	 * @return numberOfPlayers
	 */
	@Min(1)
	@Max(1000)
	@Schema(name = "numberOfPlayers", example = "10", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@JsonProperty("numberOfPlayers")
	public Integer getNumberOfPlayers() {
		return numberOfPlayers;
	}

	public void setNumberOfPlayers(Integer numberOfPlayers) {
		this.numberOfPlayers = numberOfPlayers;
	}

	public Clan points(Integer points) {
		this.points = points;
		return this;
	}

	/**
	 * Get points minimum: 1 maximum: 1000000
	 * 
	 * @return points
	 */
	@Min(1)
	@Max(1000000)
	@Schema(name = "points", example = "500", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
	@JsonProperty("points")
	public Integer getPoints() {
		return points;
	}

	public void setPoints(Integer points) {
		this.points = points;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Clan clan = (Clan) o;
		return Objects.equals(this.numberOfPlayers, clan.numberOfPlayers) && Objects.equals(this.points, clan.points);
	}

	@Override
	public int hashCode() {
		return Objects.hash(numberOfPlayers, points);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class Clan {\n");
		sb.append("    numberOfPlayers: ").append(toIndentedString(numberOfPlayers)).append("\n");
		sb.append("    points: ").append(toIndentedString(points)).append("\n");
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
