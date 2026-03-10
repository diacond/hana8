package com.hana8.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dept extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dept_id", columnDefinition = "int unsigned")
	private Long id;

	@Column(name = "dept_name", nullable = false, length = 50)
	private String name;

	@ManyToOne
	@JoinColumn(name = "captain_id", referencedColumnName = "id",
		columnDefinition = "int unsigned",
		foreignKey = @ForeignKey(name = "fk_Dept_captain"))
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private Member captain;

	@ManyToMany(mappedBy = "depts")
	@Builder.Default
	@ToString.Exclude
	@EqualsAndHashCode.Exclude
	private List<Member> deptMembers = new ArrayList<>();
}
