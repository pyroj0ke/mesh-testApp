package org.app.mesh.domains;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Account {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private BigDecimal balance = new BigDecimal(0);

	@OneToOne  (fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Transient
	private  ReadWriteLock accountLock = new ReentrantReadWriteLock();
}