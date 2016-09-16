package ${package}.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;

import ${package}.model.entity.SystemParameter;
import org.springframework.orm.jpa.JpaTemplate;

public class SystemParameterRepositoryImpl {

	@Autowired
	JpaTemplate jpatemplate;

	public SystemParameter findAndUpdate(SystemParameter systemParameter) {

		return jpatemplate.merge(systemParameter);

	}
}