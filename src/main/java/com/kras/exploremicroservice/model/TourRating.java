package com.kras.exploremicroservice.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Document
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class TourRating {
    @Id
    private String id;
    private String tourId;

    @NotNull
    private Integer customerId;

    @Min(0)
    @Max(5)
    private Long score;

    @Size(max = 255)
    private String comment;

    public TourRating() {
    }

    public TourRating(String tourId, Integer customerId, Long score, String comment) {
        this.tourId = tourId;
        this.customerId = customerId;
        this.score = score;
        this.comment = comment;
    }
}
