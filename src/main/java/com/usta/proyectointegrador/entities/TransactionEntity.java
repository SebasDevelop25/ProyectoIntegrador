package com.usta.proyectointegrador.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "transacciones")
public class TransactionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_transaccion")
    private Long idTransaction;


    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0.")
    @Digits(integer = 10, fraction = 2)
    @Column(name = "monto", precision = 10, scale = 2)
    private BigDecimal amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_transaccion")
    private LocalDate transactionDate = LocalDate.now();

    @Column(name = "numero tarjeta", length = 100)
    private String bank;

    @Column(name = "metodo_pago", length = 50)
    private String paymentMethod;

    @Column(name = "nombre_titular", length = 100)
    private String cardholderName;

    @Size(min = 3, max = 4, message = "El CVV debe tener entre 3 y 4 dígitos.")
    @Column(name = "cvv", length = 4)
    private String cvv;

    @Email(message = "Correo inválido.")
    @Column(name = "email", length = 150)
    private String email;

    // Usuario relacionado

    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private UsersEntity usuario;

    // Startup relacionada

    @JoinColumn(name = "id_startup", referencedColumnName = "id_startup")
    @ManyToOne(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private StartupEntity startup;
}
