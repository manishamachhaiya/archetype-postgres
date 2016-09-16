#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.service.impl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.gdn.common.exception.ApplicationRuntimeException;

import ${package}.dao.api.SystemParameterRepository;
import ${package}.model.entity.SystemParameter;

public class SystemParameterServiceImplTest {

	private static final String BLANK = "";

	private static final String DESCRIPTION = "description";

	private static final String VALUE = "value";

	private static final String VALUE_2 = "value_2";

	private static final String VARIABLE = "variable";

	private static final String VARIABLE_NOT_FOUND = "variable_not_found";

	private static final String STORE_ID = "store_id";

	@InjectMocks
	private SystemParameterServiceImpl paramServiceImpl;

	@Mock
	private SystemParameterRepository paramRepo;

	private SystemParameter systemParameter;
	private List<SystemParameter> listsys = new ArrayList<SystemParameter>();

	private SystemParameter emptySysParameter;

	private SystemParameter systemParameterWithNotFoundVariable;

	private SystemParameter systemParameterWithUpdateValue;

	private List<SystemParameter> systemParameters;

	@Test(expected = ApplicationRuntimeException.class)
	public void deleteWithBlankStoreId() {
		this.paramServiceImpl.delete(SystemParameterServiceImplTest.BLANK, SystemParameterServiceImplTest.VARIABLE);
	}

	@Test(expected = ApplicationRuntimeException.class)
	public void deleteWithBlankVariable() {
		this.paramServiceImpl.delete(SystemParameterServiceImplTest.STORE_ID, SystemParameterServiceImplTest.BLANK);
	}

	@Test
	public void deleteWithCorrectParameter() {
		this.paramServiceImpl.delete(SystemParameterServiceImplTest.STORE_ID, SystemParameterServiceImplTest.VARIABLE);
		verify(this.paramRepo).deleteByStoreIdAndVariable(SystemParameterServiceImplTest.STORE_ID,
				SystemParameterServiceImplTest.VARIABLE);
	}

	@Test
	public void deleteWithNotFoundVariable() {
		boolean success = true;
		try {
			this.paramServiceImpl.delete(SystemParameterServiceImplTest.STORE_ID,
					SystemParameterServiceImplTest.VARIABLE_NOT_FOUND);
		} catch (ApplicationRuntimeException e) {
			success = false;
		}
		verify(this.paramRepo).deleteByStoreIdAndVariable(SystemParameterServiceImplTest.STORE_ID,
				SystemParameterServiceImplTest.VARIABLE_NOT_FOUND);
		assertThat(success, equalTo(false));
	}

	@Test(expected = ApplicationRuntimeException.class)
	public void findAllWithBlankStoreId() {
		this.paramServiceImpl.findAll(SystemParameterServiceImplTest.BLANK);
	}

	@Test
	public void findAllWithCorrectStoreId() {
		this.paramServiceImpl.findAll(SystemParameterServiceImplTest.STORE_ID);
		verify(this.paramRepo).findAllByStoreId(SystemParameterServiceImplTest.STORE_ID);
	}

	@Test
	public void findWithCorrectStoreIdAndVariable() {
		this.paramServiceImpl.findValueByStoreIdAndVariable(SystemParameterServiceImplTest.STORE_ID,
				SystemParameterServiceImplTest.VARIABLE);
		verify(this.paramRepo).findByStoreIdAndVariable(SystemParameterServiceImplTest.STORE_ID,
				SystemParameterServiceImplTest.VARIABLE);
	}

	@Test(expected = ApplicationRuntimeException.class)
	public void findWithNotFoundVariable() {
		try {
			this.paramServiceImpl.findValueByStoreIdAndVariable(SystemParameterServiceImplTest.STORE_ID,
					SystemParameterServiceImplTest.VARIABLE_NOT_FOUND);
		} catch (Exception e) {
			verify(this.paramRepo).findByStoreIdAndVariable(SystemParameterServiceImplTest.STORE_ID,
					SystemParameterServiceImplTest.VARIABLE_NOT_FOUND);

			throw e;
		}
	}

	@Before
	public void setUp() throws Exception {
		initMocks(this);

		SystemParameter e = new SystemParameter(SystemParameterServiceImplTest.STORE_ID,
				SystemParameterServiceImplTest.VARIABLE, SystemParameterServiceImplTest.VALUE,
				SystemParameterServiceImplTest.DESCRIPTION);
		listsys.add(e);
		this.systemParameters = listsys;

		this.systemParameterWithNotFoundVariable = new SystemParameter(SystemParameterServiceImplTest.STORE_ID,
				SystemParameterServiceImplTest.VARIABLE_NOT_FOUND, SystemParameterServiceImplTest.VALUE,
				SystemParameterServiceImplTest.DESCRIPTION);
		this.systemParameterWithUpdateValue = new SystemParameter(SystemParameterServiceImplTest.STORE_ID,
				SystemParameterServiceImplTest.VARIABLE, SystemParameterServiceImplTest.VALUE_2,
				SystemParameterServiceImplTest.DESCRIPTION);
		ArrayList<SystemParameter> emptySystemParameter = new ArrayList<SystemParameter>();
		this.emptySysParameter = new SystemParameter();
		this.systemParameters = new ArrayList<SystemParameter>();
		this.systemParameters.add(this.systemParameter);

		when(this.paramRepo.findByStoreIdAndVariable(SystemParameterServiceImplTest.STORE_ID,
				SystemParameterServiceImplTest.VARIABLE)).thenReturn(this.listsys);
		when(this.paramRepo.findByStoreIdAndVariable(SystemParameterServiceImplTest.STORE_ID,
				SystemParameterServiceImplTest.VARIABLE_NOT_FOUND)).thenReturn(null);
		when(this.paramRepo.deleteByStoreIdAndVariable(SystemParameterServiceImplTest.STORE_ID,
				SystemParameterServiceImplTest.VARIABLE_NOT_FOUND)).thenReturn(emptySystemParameter);
		when(this.paramRepo.deleteByStoreIdAndVariable(SystemParameterServiceImplTest.STORE_ID,
				SystemParameterServiceImplTest.VARIABLE)).thenReturn(this.systemParameters);
		when(this.paramRepo.findAndUpdate(this.systemParameterWithNotFoundVariable)).thenReturn(null);
		when(this.paramRepo.findAndUpdate(this.systemParameterWithUpdateValue)).thenReturn(this.systemParameter);
	}

	@After
	public void tearDown() throws Exception {
		verifyNoMoreInteractions(this.paramRepo);
	}

	@Test(expected = ApplicationRuntimeException.class)
	public void updateWithBlankDescription() {
		SystemParameter parameter = new SystemParameter(SystemParameterServiceImplTest.STORE_ID,
				SystemParameterServiceImplTest.VARIABLE, SystemParameterServiceImplTest.VALUE,
				SystemParameterServiceImplTest.BLANK);
		this.paramServiceImpl.update(parameter);
	}

	@Test(expected = ApplicationRuntimeException.class)
	public void updateWithBlankStoreId() {
		SystemParameter parameter = new SystemParameter(SystemParameterServiceImplTest.BLANK,
				SystemParameterServiceImplTest.VARIABLE, SystemParameterServiceImplTest.VALUE,
				SystemParameterServiceImplTest.DESCRIPTION);
		this.paramServiceImpl.update(parameter);
	}

	@Test(expected = ApplicationRuntimeException.class)
	public void updateWithBlankValue() {
		SystemParameter parameter = new SystemParameter(SystemParameterServiceImplTest.STORE_ID,
				SystemParameterServiceImplTest.VARIABLE, SystemParameterServiceImplTest.BLANK,
				SystemParameterServiceImplTest.DESCRIPTION);
		this.paramServiceImpl.update(parameter);
	}

	@Test(expected = ApplicationRuntimeException.class)
	public void updateWithBlankVariable() {
		SystemParameter parameter = new SystemParameter(SystemParameterServiceImplTest.STORE_ID,
				SystemParameterServiceImplTest.BLANK, SystemParameterServiceImplTest.VALUE,
				SystemParameterServiceImplTest.DESCRIPTION);
		this.paramServiceImpl.update(parameter);
	}

	@Test
	public void updateWithCorrectParameter() {
		this.paramServiceImpl.update(this.systemParameterWithUpdateValue);
		verify(this.paramRepo).findAndUpdate(this.systemParameterWithUpdateValue);
	}

	@Test
	public void updateWithNotFoundVariable() {
		boolean success = true;
		try {
			this.paramServiceImpl.update(this.systemParameterWithNotFoundVariable);
		} catch (ApplicationRuntimeException e) {
			success = false;
		}
		assertThat(success, equalTo(false));
		verify(this.paramRepo).findAndUpdate(this.systemParameterWithNotFoundVariable);
	}

}