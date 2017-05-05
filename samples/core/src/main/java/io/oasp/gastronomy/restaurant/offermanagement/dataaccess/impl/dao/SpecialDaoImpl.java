package io.oasp.gastronomy.restaurant.offermanagement.dataaccess.impl.dao;

import javax.inject.Named;

import com.mysema.query.alias.Alias;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.types.path.EntityPathBase;

import io.oasp.gastronomy.restaurant.general.common.api.datatype.Money;
import io.oasp.gastronomy.restaurant.general.dataaccess.base.dao.ApplicationDaoImpl;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.SpecialEntity;
import io.oasp.gastronomy.restaurant.offermanagement.dataaccess.api.dao.SpecialDao;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.SpecialSearchCriteriaTo;
import io.oasp.gastronomy.restaurant.offermanagement.logic.api.to.WeeklyPeriodSearchCriteriaTo;
import io.oasp.module.jpa.common.api.to.PaginatedListTo;

/**
 * This is the implementation of {@link SpecialDao}.
 */
@Named
public class SpecialDaoImpl extends ApplicationDaoImpl<SpecialEntity> implements SpecialDao {

  /**
   * The constructor.
   */
  public SpecialDaoImpl() {

    super();
  }

  @Override
  public Class<SpecialEntity> getEntityClass() {

    return SpecialEntity.class;
  }

  @Override
  public PaginatedListTo<SpecialEntity> findSpecials(SpecialSearchCriteriaTo criteria) {

    SpecialEntity special = Alias.alias(SpecialEntity.class);
    EntityPathBase<SpecialEntity> alias = Alias.$(special);
    JPAQuery query = new JPAQuery(getEntityManager()).from(alias);

    String name = criteria.getName();
    if (name != null) {
      query.where(Alias.$(special.getName()).eq(name));
    }
    WeeklyPeriodSearchCriteriaTo activePeriod = criteria.getActivePeriod();
    if (activePeriod != null) {
      // was: query.where(Alias.$(special.getActivePeriod()).eq(activePeriod));
      query.where(Alias.$(special.getActivePeriod().getStartingDay()).eq(activePeriod.getStartingDay()));
      query.where(Alias.$(special.getActivePeriod().getStartingHour()).eq(activePeriod.getStartingHour()));
      query.where(Alias.$(special.getActivePeriod().getEndingDay()).eq(activePeriod.getEndingDay()));
      query.where(Alias.$(special.getActivePeriod().getEndingHour()).eq(activePeriod.getEndingHour()));
    }
    Money specialPrice = criteria.getSpecialPrice();
    if (specialPrice != null) {
      query.where(Alias.$(special.getSpecialPrice()).eq(specialPrice));
    }
    return findPaginated(criteria, query, alias);
  }

}