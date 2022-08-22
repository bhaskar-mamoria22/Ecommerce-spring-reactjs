package com.gmail.bhaskar.ecommerce.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;


@Entity
@Table(name = "review")
@Data
@NoArgsConstructor
public class Review {
    /**
     * The unique code of the object.
     * The @Id annotation says that the field is the key for the current object.
     */
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    /**
     * Author of current perfume review
     */
    @NotBlank(message = "Fill in the input field")
    private String author;

    /**
     * Author message for current perfume review
     */
    @NotBlank(message = "Fill in the input field")
    private String message;

    /**
     * Date of perfume review
     */
    private LocalDate date;
}
