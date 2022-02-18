package com.cjrequena.sample.db.entity.eventstore;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 *
 * <p></p>
 * <p></p>
 * @author cjrequena
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@Entity
@Table(name = "aggregate")
public class AggregateEntity {

  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name = "name")
  private String name;

  @Column(name = "version")
  private int version;

  @Builder
  public AggregateEntity(
    @NotNull @NotBlank UUID id,
    @NotNull @NotBlank String name,
    @NotNull @NotBlank int version
  ) {
    this.setId(id);
    this.setName(name);
    this.setVersion(version);
  }

}
