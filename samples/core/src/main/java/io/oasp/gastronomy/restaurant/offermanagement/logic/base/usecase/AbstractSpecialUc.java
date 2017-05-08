package io.oasp.gastronomy.restaurant.offermanagement.logic.base.usecase;

import javax.inject.Inject;

import io.oasp.gastronomy.restaurant.general.logic.base.AbstractUc;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.SpecialDao;

/**
 * Abstract use case for Specials, which provides access to the commonly necessary data access objects.
 */
public class AbstractSpecialUc extends AbstractUc {

  /** @see #getSpecialDao() */
  @Inject
  private SpecialDao specialDao;

  /**
   * Returns the field 'specialDao'.
   * 
   * @return the {@link SpecialDao} instance.
   */
  public SpecialDao getSpecialDao() {

    return this.specialDao;
  }

}
