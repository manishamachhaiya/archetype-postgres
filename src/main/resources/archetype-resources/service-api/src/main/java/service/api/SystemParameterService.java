#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.api;
import java.util.List;

import ${package}.model.entity.SystemParameter;

public interface SystemParameterService {

	/**
	 *
	 * @param storeId
	 * @param variable
	 */
	void delete(String storeId, String variable);

	/**
	 *
	 * @param storeId
	 * @return
	 */
	List<SystemParameter> findAll(String storeId);

	/**
	 *
	 * @param storeId
	 * @param variable
	 * @return
	 */
	List<SystemParameter> findValueByStoreIdAndVariable(String storeId, String variable);

	/**
	 *
	 * @param systemParameter
	 */
	void update(SystemParameter systemParameter);

}
