package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.dataaccess.api.ApplicationPersistenceEntity;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.Offer;
import io.oasp.gastronomy.restaurant.offermanagement.common.api.Special;

/**
 * The {@link ApplicationPersistenceEntity persistent entity} for a special.
 *
 * @author mbrunnli
 */
@Entity(name = "Special")
@Table(name = "Special")
public class SpecialEntity extends ApplicationPersistenceEntity implements Special {

  private String name;

  @Embedded
  private WeeklyPeriodEmbeddable activePeriod;

  private Money specialPrice;

  private static final long serialVersionUID = 1L;

  /**
   * Returns the name of this special.
   *
   * @return name the name of this special.
   */
  @Column(unique = true)
  public String getName() {

    return this.name;
  }

  /**
   * Sets the name of this special.
   *
   * @param name the name of this special.
   */
  public void setName(String name) {

    this.name = name;
  }

  /**
   * Returns the {@link WeeklyPeriodEmbeddable active period} this special applies for.
   *
   * @return activePeriod the {@link WeeklyPeriodEmbeddable active period} this special applies for.
   */
  public WeeklyPeriodEmbeddable getActivePeriod() {

    return this.activePeriod;
  }

  /**
   * Sets the {@link WeeklyPeriodEmbeddable active period} this special applies for.
   *
   * @param activePeriod the {@link WeeklyPeriodEmbeddable active period} this special applies for.
   */
  public void setActivePeriod(WeeklyPeriodEmbeddable activePeriod) {

    this.activePeriod = activePeriod;
  }

  /**
   * Returns the new {@link Money special price} for the {@link Offer}.
   *
   * @return specialPrice the new {@link Money special price} for the {@link Offer}.
   */
  public Money getSpecialPrice() {

    return this.specialPrice;
  }

  /**
   * Sets the new {@link Money special price} for the {@link Offer}.
   *
   * @param specialPrice the new {@link Money special price} for the {@link Offer}.
   */
  public void setSpecialPrice(Money specialPrice) {

    this.specialPrice = specialPrice;
  }

}
