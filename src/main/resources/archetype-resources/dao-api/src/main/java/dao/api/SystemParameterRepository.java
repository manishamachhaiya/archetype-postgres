#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.api;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ${package}.model.entity.SystemParameter;

public interface SystemParameterRepository extends JpaRepository<SystemParameter, String> {

	/**
	 *
	 * @param storeId
	 * @param variable
	 * @return empty list if object not found
	 */
	public List<SystemParameter> deleteByStoreIdAndVariable(String storeId, String variable);

	/**
	 *
	 * @param storeId
	 * @return
	 */
	public List<SystemParameter> findAllByStoreId(String storeId);

	/**
	 *
	 * @param variable
	 * @param storeId
	 * @return null if not found
	 */
	public List<SystemParameter> findByStoreIdAndVariable(String storeId, String variable);

	/**
	 *
	 * @param SystemParamater
	 */

	SystemParameter findAndUpdate(SystemParameter systemParameter);

}
