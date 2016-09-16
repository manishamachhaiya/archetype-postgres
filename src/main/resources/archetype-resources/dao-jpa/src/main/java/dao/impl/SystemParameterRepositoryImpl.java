#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import ${package}.dao.api.SystemParameterRepositoryCustom;
import ${package}.enums.SystemParameterNames;
import ${package}.model.entity.SystemParameter;

public class SystemParameterRepositoryImpl implements
SystemParameterRepositoryCustom {

  @Autowired
  MongoTemplate mongoTemplate;

  @Override
  public SystemParameter findAndUpdate(SystemParameter systemParameter) {
    Query query = new Query();
    query.addCriteria(Criteria.where(SystemParameterNames.VARIABLE)
        .is(systemParameter.getVariable()).and(SystemParameterNames.STORE_ID)
        .is(systemParameter.getStoreId()));
    Update update =
        new Update().set(SystemParameterNames.VALUE, systemParameter.getValue()).set(
            SystemParameterNames.DESCRIPTION, systemParameter.getDescription());

    return mongoTemplate.findAndModify(query, update, SystemParameter.class);
  }
}
