package com.gmail.bhaskar.ecommerce.controller;

import com.gmail.bhaskar.ecommerce.domain.Order;
import com.gmail.bhaskar.ecommerce.domain.Perfume;
import com.gmail.bhaskar.ecommerce.domain.User;
import com.gmail.bhaskar.ecommerce.service.OrderService;
import com.gmail.bhaskar.ecommerce.service.PerfumeService;
import com.gmail.bhaskar.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/rest")
@PreAuthorize("hasAuthority('ADMIN')")
public class AdminRestController {
    /**
     * Upload path for images.
     */
    @Value("${upload.path}")
    private String uploadPath;

    /**
     * Service object for working with users.
     */
    private final UserService userService;

    /**
     * Service object for working with products.
     */
    private final PerfumeService perfumeService;

    /**
     * Service object for working with orders.
     */
    private final OrderService orderService;


    @Autowired
    public AdminRestController(UserService userService, PerfumeService perfumeService, OrderService orderService) {
        this.userService = userService;
        this.perfumeService = perfumeService;
        this.orderService = orderService;
    }


    @PostMapping("/admin/add")
    public ResponseEntity<?> addPerfume(
            @Valid Perfume perfume,
            BindingResult bindingResult,
            @RequestPart(name = "file", required = false) MultipartFile file
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        } else {
            saveFile(perfume, file);

            Perfume savedPerfume = perfumeService.save(perfume);

            return new ResponseEntity<>(savedPerfume, HttpStatus.CREATED);
        }
    }


    @PutMapping("/admin/edit")
    public ResponseEntity<?> updatePerfume(
            @Valid Perfume perfume,
            BindingResult bindingResult,
            @RequestPart(name = "file", required = false) MultipartFile file
    ) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);

            return new ResponseEntity<>(errorsMap, HttpStatus.BAD_REQUEST);
        } else {
            saveFile(perfume, file);

            perfumeService.saveProductInfoById(perfume.getPerfumeTitle(), perfume.getPerfumer(), perfume.getYear(),
                    perfume.getCountry(), perfume.getPerfumeGender(), perfume.getFragranceTopNotes(),
                    perfume.getFragranceMiddleNotes(), perfume.getFragranceBaseNotes(), perfume.getDescription(),
                    perfume.getFilename(), perfume.getPrice(), perfume.getVolume(), perfume.getType(), perfume.getId());

            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    @GetMapping("/admin/orders")
    public ResponseEntity<?> getAllOrders() {
        List<Order> orders = orderService.findAll();

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }


    @GetMapping("/admin/user/{id}")
    public ResponseEntity<?> getUser(@PathVariable("id") Long userId) {
        User user = userService.getOne(userId);

        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @GetMapping("/admin/user/all")
    public ResponseEntity<?> getAllUsers() {
        List<User> users = userService.findAll();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     * Update and save user by an administrator.
     * URL request {"/admin/user/edit"}, method POST.
     *
     * @param username user name.
     * @param form     user roles.
     * @param user     user id.
     * @return ResponseEntity with HTTP response: status code, headers, and body.
     */
    @PutMapping("/admin/user/edit")
    public ResponseEntity<?> updateUser(
            @RequestParam String username,
            @RequestParam Map<String, String> form,
            @RequestParam("userId") User user
    ) {
        userService.userSave(username, form, user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method for saving file in upload directory.
     *
     * @param perfume current product.
     * @param file    file image.
     */
    private void saveFile(Perfume perfume, @RequestParam("file") MultipartFile file) throws IOException {
        if (file == null) {
            perfume.setFilename("empty.jpg");
        } else {
            File uploadDir = new File(uploadPath);

            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }
            String uuidFile = UUID.randomUUID().toString();
            String resultFilename = uuidFile + "." + file.getOriginalFilename();

            file.transferTo(new File(uploadPath + "/" + resultFilename));
            perfume.setFilename(resultFilename);
        }
    }
}
