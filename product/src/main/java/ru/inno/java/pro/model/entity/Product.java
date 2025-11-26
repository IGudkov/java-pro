package ru.inno.java.pro.model.entity;

import jakarta.persistence.*;
import ru.inno.java.pro.model.enums.ProductType;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "account_number", nullable = false)
  private String accountNumber;

  @Column(name = "balance", nullable = false)
  private BigDecimal balance;

  @Column(name = "product_type", nullable = false)
  @Enumerated(EnumType.STRING)
  private ProductType productType;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  public Long getId() {
    return id;
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  public ProductType getProductType() {
    return productType;
  }

  public User getUser() {
    return user;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setAccountNumber(String accountNumber) {
    this.accountNumber = accountNumber;
  }

  public void setBalance(BigDecimal balance) {
    this.balance = balance;
  }

  public void setProductType(ProductType productType) {
    this.productType = productType;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
