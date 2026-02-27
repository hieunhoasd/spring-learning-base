package HotWhellShop_Spring_react.domain;

import org.springframework.web.bind.annotation.PathVariable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotBlank(message = "ten nguoi dung khong duoc de trong")
    private String name;

    @Column(unique = true)
    @NotBlank(message = "email khong duoc de trong ")
    private String email;

    @Size(min = 1, max = 8)
    private String password;

}
