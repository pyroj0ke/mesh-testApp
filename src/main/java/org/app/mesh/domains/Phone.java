package org.app.mesh.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Pattern;

@Entity
@Table(name  = "phone_data")
@Setter
@Getter
@NoArgsConstructor
public class Phone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne  (fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	private User user;

	@Pattern(regexp = "7\\d{13}",
	message = "номер телефона не соответствует ожидаемому формату")
	@Column (nullable = false)
	private  String phone;
}