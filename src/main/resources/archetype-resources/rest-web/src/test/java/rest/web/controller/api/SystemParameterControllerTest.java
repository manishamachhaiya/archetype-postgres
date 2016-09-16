#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest.web.controller.api;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import ${package}.model.entity.SystemParameter;
import ${package}.rest.web.model.ApiPath;
import ${package}.rest.web.model.DeleteRequest;
import ${package}.rest.web.model.SystemParameterRequest;
import ${package}.service.api.SystemParameterService;

public class SystemParameterControllerTest {

	private static final String STORE_ID = "store-id";

	private static final String STORE_ID_NOT_FOUND = "store-id-not-found";

	private static final String CLIENT_ID = "client-id";

	private static final String REQUEST_ID = "request-id";

	private static final String CHANNEL_ID = "channel-id";

	private static final String VALUE = "value";

	private static final String VARIABLE = "variable";

	private static final String DESCRIPTION = "description";

	@InjectMocks
	private SystemParameterController controller;

	@Mock
	private SystemParameterService service;

	private MockMvc mockMvc;
	private List<SystemParameter> parameterInsert;
	private List<DeleteRequest> deleteRequest;
	private List<SystemParameter> systemParameters;
	private List<SystemParameter> findRequest;
	private SystemParameter systemParameter;
	private String sampleJsonReqInsert;
	private String sampleJsonReqDelete;
	private SystemParameterRequest parameterCreateRequest;

	@Test
	public void deleteSystemParameter() throws Exception {
		this.mockMvc
				.perform(delete(ApiPath.SYSTEM_PARAMETER_DELETE).accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON).content(this.sampleJsonReqDelete)
						.param("storeId", SystemParameterControllerTest.STORE_ID)
						.param("requestId", SystemParameterControllerTest.REQUEST_ID)
						.param("channelId", SystemParameterControllerTest.CHANNEL_ID)
						.param("clientId", SystemParameterControllerTest.CLIENT_ID)
						.param("variable", SystemParameterControllerTest.VARIABLE))
				.andExpect(status().isOk()).andExpect(jsonPath(".success", equalTo(true)));

		verify(this.service).delete(SystemParameterControllerTest.STORE_ID, SystemParameterControllerTest.VARIABLE);
	}

	@Test
	public void findAll() throws Exception {
		this.mockMvc
				.perform(get(ApiPath.SYSTEM_PARAMETER_FIND_ALL).accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.param("storeId", SystemParameterControllerTest.STORE_ID)
						.param("requestId", SystemParameterControllerTest.REQUEST_ID)
						.param("channelId", SystemParameterControllerTest.CHANNEL_ID)
						.param("clientId", SystemParameterControllerTest.CLIENT_ID))
				.andExpect(status().isOk()).andExpect(jsonPath(".success", equalTo(true)))
				.andExpect(jsonPath(".requestId", equalTo(SystemParameterControllerTest.REQUEST_ID)));

		verify(this.service).findAll(SystemParameterControllerTest.STORE_ID);
	}

	@Test
	public void findAllStoreIdNotFound() throws Exception {
		this.mockMvc
				.perform(get(ApiPath.SYSTEM_PARAMETER_FIND_ALL).accept(MediaType.APPLICATION_JSON)
						.contentType(MediaType.APPLICATION_JSON)
						.param("storeId", SystemParameterControllerTest.STORE_ID_NOT_FOUND)
						.param("requestId", SystemParameterControllerTest.REQUEST_ID)
						.param("channelId", SystemParameterControllerTest.CHANNEL_ID)
						.param("clientId", SystemParameterControllerTest.CLIENT_ID))
				.andExpect(status().isOk()).andExpect(jsonPath(".success", equalTo(true)))
				.andExpect(jsonPath(".requestId", equalTo(SystemParameterControllerTest.REQUEST_ID)));

		verify(this.service).findAll(SystemParameterControllerTest.STORE_ID_NOT_FOUND);
	}

	@Before
	public void setUp() throws Exception {
		initMocks(this);

		ObjectMapper mapper = new ObjectMapper();

		this.mockMvc = standaloneSetup(this.controller).build();

		/*
		 * this.parameterInsert = new
		 * SystemParameter(SystemParameterControllerTest.STORE_ID,
		 * SystemParameterControllerTest.VARIABLE,
		 * SystemParameterControllerTest.VALUE,
		 * SystemParameterControllerTest.DESCRIPTION);
		 */

		this.sampleJsonReqInsert = FileUtils.readFileToString(new File("src/test/resources/insertRequest.json"));

		this.sampleJsonReqDelete = FileUtils.readFileToString(new File("src/test/resources/deleteRequest.json"));

		this.deleteRequest = mapper.readValue(this.sampleJsonReqDelete,
				mapper.getTypeFactory().constructCollectionType(List.class, DeleteRequest.class));
		assertThat(this.deleteRequest, notNullValue());

		this.systemParameters = new ArrayList<>();
		this.findRequest = new ArrayList<SystemParameter>();

		this.systemParameter = new SystemParameter(SystemParameterControllerTest.STORE_ID,
				SystemParameterControllerTest.VARIABLE, SystemParameterControllerTest.VALUE,
				SystemParameterControllerTest.DESCRIPTION);

		this.findRequest.add(this.systemParameter);

		this.parameterCreateRequest = new SystemParameterRequest();
		this.parameterCreateRequest = mapper.readValue(this.sampleJsonReqInsert,
				mapper.getTypeFactory().constructType(SystemParameterRequest.class));
		assertThat(this.parameterCreateRequest, notNullValue());

		when(this.service.findValueByStoreIdAndVariable(SystemParameterControllerTest.STORE_ID,
				SystemParameterControllerTest.VARIABLE)).thenReturn(this.parameterInsert);

		when(this.service.findAll(SystemParameterControllerTest.STORE_ID_NOT_FOUND)).thenReturn(this.systemParameters);

		when(this.service.findAll(SystemParameterControllerTest.STORE_ID)).thenReturn(this.findRequest);
	}

	@After
	public void tearDown() throws Exception {
		verifyNoMoreInteractions(this.service);
	}

	@Test
	public void updateSystemParamter() throws Exception {
		this.mockMvc.perform(post(ApiPath.SYSTEM_PARAMETER_UPDATE).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).content(this.sampleJsonReqInsert)
				.param("storeId", SystemParameterControllerTest.STORE_ID)
				.param("requestId", SystemParameterControllerTest.REQUEST_ID)
				.param("channelId", SystemParameterControllerTest.CHANNEL_ID)
				.param("clientId", SystemParameterControllerTest.CLIENT_ID)).andExpect(status().isOk());
		verify(this.service).update(this.systemParameter);
	}
}
