package io.oasp.gastronomy.restaurant.offermanagement.common.api;

import io.oasp.gastronomy.restaurant.general.common.api.ApplicationEntity;
import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.WeeklyPeriodEmbeddable;

public interface Special extends ApplicationEntity {

  public String getName();

  public void setName(String name);

  public WeeklyPeriodEmbeddable getActivePeriod();

  public void setActivePeriod(WeeklyPeriodEmbeddable activePeriod);

  public Money getSpecialPrice();

  public void setSpecialPrice(Money specialPrice);

}
