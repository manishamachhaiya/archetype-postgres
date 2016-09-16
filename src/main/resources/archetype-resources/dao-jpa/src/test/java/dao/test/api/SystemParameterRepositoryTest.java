#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.dao.api;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;

import ${package}.dao.config.TestConfig;
import ${package}.model.entity.SystemParameter;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { TestConfig.class })
@TestExecutionListeners(listeners = { DependencyInjectionTestExecutionListener.class,
		DirtiesContextTestExecutionListener.class, TransactionalTestExecutionListener.class })
public class SystemParameterRepositoryTest {

	private static final String STORE_ID = "123";

	private static final String STORE_ID_2 = "1234";

	private static final String DESCRIPTION = "description";

	private static final String VALUE = "value";

	private static final String VALUE_2 = "value_2";

	private static final String VARIABLE = "variable";

	private static final String VARIABLE_2 = "variable2";

	@Autowired
	private SystemParameterRepository sysParameterRepo;

	private SystemParameter sysParameter;

	private SystemParameter sysParameter2;

	@Test
	public void deleteByStoreIdAndVariableObjectNotFound() {
		List<SystemParameter> sysParamDeleted = sysParameterRepo.deleteByStoreIdAndVariable(STORE_ID_2, VARIABLE);

		assertThat(sysParameterRepo.findByStoreIdAndVariable(STORE_ID, VARIABLE), notNullValue());
		assertThat(sysParamDeleted.size(), equalTo(0));
		assertThat(sysParamDeleted, notNullValue());
	}

	@Test
	public void deleteByStoreIdAndVariableReturnDeletedObject() {
		List<SystemParameter> sysParamDeleted = sysParameterRepo.deleteByStoreIdAndVariable(STORE_ID, VARIABLE);

		assertThat(sysParameterRepo.findByStoreIdAndVariable(STORE_ID, VARIABLE), nullValue());
		assertThat(sysParamDeleted.size(), equalTo(1));
		assertThat(sysParamDeleted.get(0).getVariable(), equalTo(VARIABLE));
	}

	@Test
	public void findAllByStoreId() {
		sysParameter2 = new SystemParameter(STORE_ID, VARIABLE_2, VALUE, DESCRIPTION);
		sysParameterRepo.save(sysParameter2);
		List<SystemParameter> systemParameters = sysParameterRepo.findAllByStoreId(STORE_ID);
		assertThat(systemParameters.size(), equalTo(2));
	}

	@Test
	public void findAndUpdate() {
		sysParameter.setValue(VALUE_2);
		SystemParameter oldValue = sysParameterRepo.findAndUpdate(sysParameter);
		assertThat(oldValue.getValue(), equalTo(VALUE));

		System.out.println(oldValue.toString());
		System.out.println(sysParameterRepo.findByStoreIdAndVariable(STORE_ID, VARIABLE).toString());
	}

	@Test
	public void findAndUpdateFailed() {
		sysParameter.setValue(VALUE_2);
		sysParameter.setVariable(VARIABLE_2);
		SystemParameter oldValue = sysParameterRepo.findAndUpdate(sysParameter);
		assertThat(oldValue, nullValue());
	}

	@Test(expected = org.springframework.dao.DuplicateKeyException.class)
	public void saveDuplicateStoreIdAndVariableThrowsException() {
		sysParameter2 = new SystemParameter(STORE_ID, VARIABLE, VALUE, DESCRIPTION);
		sysParameterRepo.save(sysParameter2);
	}

	@Test
	public void saveDuplicateVariableDifferentStoreIdSuccess() {
		sysParameter2 = new SystemParameter(STORE_ID_2, VARIABLE, VALUE, DESCRIPTION);
		sysParameterRepo.save(sysParameter2);
	}

	@Before
	public void setUp() throws Exception {
		sysParameter = new SystemParameter(STORE_ID, VARIABLE, VALUE, DESCRIPTION);
		sysParameterRepo.save(sysParameter);
	}

	@After
	public void tearDown() throws Exception {
		sysParameterRepo.deleteAll();
	}
