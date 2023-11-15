package cl.evaluation.exercise.models;

import java.util.Date;
import java.util.Set;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = {"email"})})
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String email;

  private String name;

  @Column(nullable = false)
  private String password;

  @Column(nullable = false, updatable = false)
  @CreationTimestamp
  private Date created;

  @Column(nullable = false)
  @CreationTimestamp
  private Date modified;

  @Column(nullable = false)
  @CreationTimestamp
  private Date last_login;

  private String token;

  private Boolean active;

  @PrePersist
  public void onPrePersist() {
    this.active = this.active == null ? true : this.active;
  }

  @OneToMany(mappedBy = "userEntity")
  private Set<PhoneEntity> phones;

  @ManyToMany(fetch = FetchType.EAGER, targetEntity = RoleEntity.class,
      cascade = CascadeType.PERSIST)
  @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "role_id"))
  private Set<RoleEntity> roles;

}
