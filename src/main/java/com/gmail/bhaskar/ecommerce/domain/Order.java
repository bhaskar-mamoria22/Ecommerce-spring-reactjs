package com.gmail.bhaskar.ecommerce.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id", "user", "perfumeList"})
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Double totalPrice;

    /**
     * Date when the order was made.
     */
    private LocalDate date;


    @NotBlank(message = "Fill in the input field")
    private String firstName;


    @NotBlank(message = "Fill in the input field")
    private String lastName;

    /**
     * City of delivery of the order.
     * The @NotBlank annotation says the field should not be empty.
     */
    @NotBlank(message = "Fill in the input field")
    private String city;

    /**
     * Delivery address of the order.
     * The @NotBlank annotation says the field should not be empty.
     */
    @NotBlank(message = "Fill in the input field")
    private String address;

    /**
     * Customer email.
     * The @Email annotation says the string has to be a well-formed email address.
     * The @NotBlank annotation says the field should not be empty.
     */
    @Email(message = "Incorrect email")
    @NotBlank(message = "Email cannot be empty")
    private String email;

    /**
     * Customer phone number.
     * The @NotBlank annotation says the field should not be empty.
     */
    @NotBlank(message = "Phone number cannot be empty")
    private String phoneNumber;

    /**
     * Customer post index.
     * The @NotBlank annotation says the field should not be empty.
     */
    @NotNull(message = "Post index cannot be empty")
    @Min(value = 5, message = "Post index must contain 5 digits")
    private Integer postIndex;

    /**
     * List of products in the order.
     * Between the {@link Order} and {@link Perfume} objects, there is a many-to-many relationship, that is,
     * every record in one table is directly related to every record in another table.
     * Sampling on first access to the current object.
     */
    @OrderColumn
    @ManyToMany(fetch = FetchType.EAGER)
    private List<Perfume> perfumeList;


    @ManyToOne
    private User user;

    public Order(User user) {
        this.date = LocalDate.now();
        this.user = user;
        this.perfumeList = new ArrayList<>();
    }
}
