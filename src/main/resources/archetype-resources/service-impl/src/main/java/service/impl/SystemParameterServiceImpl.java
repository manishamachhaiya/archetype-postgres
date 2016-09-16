#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.impl;

import static com.gdn.common.base.GdnPreconditions.checkArgument;
import static com.gdn.common.base.GdnPreconditions.checkState;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import ${package}.dao.api.SystemParameterRepository;
import ${package}.model.entity.SystemParameter;
import ${package}.service.api.SystemParameterService;

@Service
public class SystemParameterServiceImpl implements SystemParameterService {

	private static final String SYSTEM_PARAMETER_IS_NOT_FOUND = "System parameter '%s' is not found for storeId '%s'";

	private static final String VARIABLE_MUST_NOT_BE_BLANK = "variable must not be blank";

	private static final String STORE_ID_MUST_NOT_BE_BLANK = "storeId must not be blank";

	private static final String VALUE_MUST_NOT_BE_BLANK = "value must not be blank";

	@Autowired
	private SystemParameterRepository systemParameterRepository;

	@CacheEvict(value = { ${package}.enums.CacheNames.SYSTEM_PARAMETER }, allEntries = true)
	@Override
	public void delete(String storeId, String variable) {
		checkArgument(StringUtils.isNotBlank(storeId), STORE_ID_MUST_NOT_BE_BLANK);
		checkArgument(StringUtils.isNotBlank(variable), VARIABLE_MUST_NOT_BE_BLANK);
		List<SystemParameter> deletedParams = systemParameterRepository.deleteByStoreIdAndVariable(storeId, variable);
		checkState(!deletedParams.isEmpty(), String.format(SYSTEM_PARAMETER_IS_NOT_FOUND, variable, storeId));

	}

	@Cacheable(value = { ${package}.enums.CacheNames.SYSTEM_PARAMETER })
	@Override
	public List<SystemParameter> findAll(String storeId) {
		checkArgument(StringUtils.isNotBlank(storeId), STORE_ID_MUST_NOT_BE_BLANK);
		return systemParameterRepository.findAllByStoreId(storeId);
	}

	@Cacheable(value = ${package}.enums.CacheNames.SYSTEM_PARAMETER)
	@Override
	public List<SystemParameter> findValueByStoreIdAndVariable(String storeId, String variable) {
		checkArgument(StringUtils.isNotBlank(storeId), STORE_ID_MUST_NOT_BE_BLANK);
		checkArgument(StringUtils.isNotBlank(variable), VARIABLE_MUST_NOT_BE_BLANK);
		List<SystemParameter> systemParameter = this.systemParameterRepository.findByStoreIdAndVariable(storeId,
				variable);
		checkState(systemParameter != null, String.format(SYSTEM_PARAMETER_IS_NOT_FOUND, variable, storeId));
		return systemParameter;
	}

	@CacheEvict(value = ${package}.enums.CacheNames.SYSTEM_PARAMETER, allEntries = true)
	@Override
	public void update(SystemParameter systemParameter) {
		checkArgument(StringUtils.isNotBlank(systemParameter.getStoreId()), STORE_ID_MUST_NOT_BE_BLANK);
		checkArgument(StringUtils.isNotBlank(systemParameter.getVariable()), VARIABLE_MUST_NOT_BE_BLANK);
		checkArgument(StringUtils.isNotBlank(systemParameter.getValue()), VALUE_MUST_NOT_BE_BLANK);
		checkArgument(StringUtils.isNotBlank(systemParameter.getDescription()), "description must not be blank");
		SystemParameter oldSystemParameter = systemParameterRepository.findAndUpdate(systemParameter);
		checkState(oldSystemParameter != null, String.format(SYSTEM_PARAMETER_IS_NOT_FOUND,
				systemParameter.getStoreId(), systemParameter.getVariable()));

	}

}
