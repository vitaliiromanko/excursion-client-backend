package com.lpnu.excursionclient.model.client;

import com.lpnu.excursionclient.model.order.Order;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Formula;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Table(name = "client")
@Getter
@Setter
@ToString
public class Client implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "patronymic")
    private String patronymic;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "birthday")
    private LocalDate birthday;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "client_photo_data_id")
    private ClientPhotoData clientPhotoData;

    @OneToMany(targetEntity = Order.class,
            mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<Order> orders = new LinkedHashSet<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client_status_id")
    private ClientStatus clientStatus;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "update_date")
    private LocalDateTime updateDate;

    @OneToMany(targetEntity = ClientRefreshToken.class,
            mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<ClientRefreshToken> clientRefreshTokens = new HashSet<>();

    @OneToMany(targetEntity = ClientActivationToken.class,
            mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<ClientActivationToken> clientActivationTokens = new HashSet<>();

    @OneToMany(targetEntity = ClientResetPasswordToken.class,
            mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    private Set<ClientResetPasswordToken> clientResetPasswordTokens = new HashSet<>();

    @Formula("IFNULL(patronymic, \"\")")
    private String notNullPatronymic;

    @Formula("CONCAT(last_name, \" \", first_name, IF(patronymic IS NULL , \"\", CONCAT(\" \", patronymic)))")
    private String fullName;

    @PrePersist
    protected void prePersist() {
        creationDate = LocalDateTime.now();
        updateDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void preUpdate() {
        updateDate = LocalDateTime.now();
    }

    protected Client() {
    }

    public static Client of(String firstName, String lastName, String password, String email,
                            ClientStatus clientStatus) {
        return new Client(
                null, firstName, lastName, null, password, email, null, null,
                null, Collections.emptySet(), clientStatus, Collections.emptySet(),
                new HashSet<>(), Collections.emptySet()
        );
    }

    public Client(UUID id, String firstName, String lastName, String patronymic, String password, String email,
                  String phoneNumber, LocalDate birthday, ClientPhotoData clientPhotoData, Set<Order> orders,
                  ClientStatus clientStatus, Set<ClientRefreshToken> clientRefreshTokens,
                  Set<ClientActivationToken> clientActivationTokens, Set<ClientResetPasswordToken> clientResetPasswordTokens) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.patronymic = patronymic;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.birthday = birthday;
        this.clientPhotoData = clientPhotoData;
        this.orders = orders;
        this.clientStatus = clientStatus;
        this.clientRefreshTokens = clientRefreshTokens;
        this.clientActivationTokens = clientActivationTokens;
        this.clientResetPasswordTokens = clientResetPasswordTokens;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !clientStatus.getName().equals("ЗАБЛОКОВАНИЙ");
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return clientStatus.getName().equals("АКТИВНИЙ");
    }
}
