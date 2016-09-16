#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.client;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.util.ReflectionTestUtils;

import com.gdn.common.base.shade.org.apache.http.client.methods.HttpGet;
import com.gdn.common.base.shade.org.apache.http.impl.client.CloseableHttpClient;
import com.gdn.common.client.GdnRestClientConfiguration;
import com.gdn.common.util.GdnHttpClientHelper;
import com.gdn.common.web.param.MandatoryRequestParam;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import ${package}.rest.web.model.ApiPath;
import ${package}.rest.web.model.SystemParameterResponse;

public class ClientSdkTest {
  private static final String CLIENT_ID = "client-id";
  private static final String CHANNEL_ID = "channel-id";
  private static final String STORE_ID = "store-id";
  private static final String REQUEST_ID = "request-id";
  private static final String VARIABLE = "variable";
  private static final String USERNAME = "username";
  private static final String PASSWORD = "password";
  private static final String JSON_UTF_8 = "application/json; charset=utf-8";
  private static final int PORT = 80;
  private static final String HOST = "host";
  private static final int CONNECTION_TIMEOUT_IN_MS = 1;
  private static final String CONTEXT_PATH = "cart";
  private final ClientSdk cartClient = new ClientSdk("username", "password", "http://localhost",
      8080, ClientSdkTest.STORE_ID, ClientSdkTest.CLIENT_ID, ClientSdkTest.CHANNEL_ID,
      "${package}-cart-rest-web");

  @InjectMocks
  private ClientSdk client;

  @Mock
  private GdnHttpClientHelper clientHelper;

  @Mock
  private GdnRestSingleResponse<SystemParameterResponse> systemParameterResponse;

  @Captor
  private ArgumentCaptor<MandatoryRequestParam> captorMandatoryRequestParam;

  private URI uri;
  private Map<String, String> additionalParameterMapFindValueByStoreIdAndVariable;

  @Test
  public void cartClient() throws Exception {
    assertThat(this.client.getClientConfig(), notNullValue());
    assertThat(this.client.getContextPath(), notNullValue());
  }

  @Ignore
  @Test
  public void findOne() throws Exception {
    GdnRestSingleResponse<SystemParameterResponse> systemParameterResponse =
        this.cartClient.findOne(ClientSdkTest.REQUEST_ID, ClientSdkTest.VARIABLE);
    assertThat(systemParameterResponse.getValue().getVariable(), equalTo(ClientSdkTest.VARIABLE));
  }


  @Test
  public void findValueByStoreIdAndVariable() throws Exception {
    GdnRestSingleResponse<SystemParameterResponse> response =
        this.client.findOne(ClientSdkTest.REQUEST_ID, ClientSdkTest.VARIABLE);

    verify(this.clientHelper).getURI(eq(ClientSdkTest.HOST), eq(ClientSdkTest.PORT),
        eq("/cart" + ApiPath.SYSTEM_PARAMETER_FIND_ONE),
        this.captorMandatoryRequestParam.capture(),
        eq(this.additionalParameterMapFindValueByStoreIdAndVariable));

    verify(this.clientHelper).invokeGetSingle(any(HttpGet.class),
        eq(SystemParameterResponse.class), eq("application/json"), any(CloseableHttpClient.class));

    verify(this.clientHelper).getRequestConfig(ClientSdkTest.CONNECTION_TIMEOUT_IN_MS);

    MandatoryRequestParam requestParam = this.captorMandatoryRequestParam.getValue();
    assertThat(requestParam, notNullValue());
    assertThat(requestParam.getStoreId(), equalTo(ClientSdkTest.STORE_ID));
    assertThat(requestParam.getChannelId(), equalTo(ClientSdkTest.CHANNEL_ID));
    assertThat(requestParam.getClientId(), equalTo(ClientSdkTest.CLIENT_ID));
    assertThat(requestParam.getRequestId(), not(isEmptyOrNullString()));
    assertThat(requestParam.getRequestId().length(), not(0));
    assertThat(response, notNullValue());
    assertThat(response, equalTo(this.systemParameterResponse));
  }

  @Test
  public void findValueByStoreIdAndVariableWithEmptyRequestId() throws Exception {
    GdnRestSingleResponse<SystemParameterResponse> response =
        this.client.findOne(" ", ClientSdkTest.VARIABLE);

    verify(this.clientHelper).getURI(eq(ClientSdkTest.HOST), eq(ClientSdkTest.PORT),
        eq("/cart" + ApiPath.SYSTEM_PARAMETER_FIND_ONE),
        this.captorMandatoryRequestParam.capture(),
        eq(this.additionalParameterMapFindValueByStoreIdAndVariable));

    verify(this.clientHelper).invokeGetSingle(any(HttpGet.class),
        eq(SystemParameterResponse.class), eq("application/json"), any(CloseableHttpClient.class));

    verify(this.clientHelper).getRequestConfig(ClientSdkTest.CONNECTION_TIMEOUT_IN_MS);

    MandatoryRequestParam requestParam = this.captorMandatoryRequestParam.getValue();
    assertThat(requestParam, notNullValue());
    assertThat(requestParam.getStoreId(), equalTo(ClientSdkTest.STORE_ID));
    assertThat(requestParam.getChannelId(), equalTo(ClientSdkTest.CHANNEL_ID));
    assertThat(requestParam.getClientId(), equalTo(ClientSdkTest.CLIENT_ID));
    assertThat(requestParam.getRequestId(), not(isEmptyOrNullString()));
    assertThat(response, notNullValue());
    assertThat(response, equalTo(this.systemParameterResponse));
  }

  @Test
  public void findValueByStoreIdAndVariableWithNullRequestId() throws Exception {
    GdnRestSingleResponse<SystemParameterResponse> response =
        this.client.findOne(null, ClientSdkTest.VARIABLE);

    verify(this.clientHelper).getURI(eq(ClientSdkTest.HOST), eq(ClientSdkTest.PORT),
        eq("/cart" + ApiPath.SYSTEM_PARAMETER_FIND_ONE),
        this.captorMandatoryRequestParam.capture(),
        eq(this.additionalParameterMapFindValueByStoreIdAndVariable));

    verify(this.clientHelper).invokeGetSingle(any(HttpGet.class),
        eq(SystemParameterResponse.class), eq("application/json"), any(CloseableHttpClient.class));

    verify(this.clientHelper).getRequestConfig(ClientSdkTest.CONNECTION_TIMEOUT_IN_MS);

    MandatoryRequestParam requestParam = this.captorMandatoryRequestParam.getValue();
    assertThat(requestParam, notNullValue());
    assertThat(requestParam.getStoreId(), equalTo(ClientSdkTest.STORE_ID));
    assertThat(requestParam.getChannelId(), equalTo(ClientSdkTest.CHANNEL_ID));
    assertThat(requestParam.getClientId(), equalTo(ClientSdkTest.CLIENT_ID));
    assertThat(requestParam.getRequestId(), not(isEmptyOrNullString()));
    assertThat(response, notNullValue());
    assertThat(response, equalTo(this.systemParameterResponse));
  }

  @Before
  public void setUp() throws Exception {
    initMocks(this);

    this.uri = new URI("/test-uri");

    GdnRestClientConfiguration clientConfiguration =
        new GdnRestClientConfiguration(ClientSdkTest.USERNAME, ClientSdkTest.PASSWORD,
        		ClientSdkTest.HOST, ClientSdkTest.PORT, ClientSdkTest.CLIENT_ID,
        		ClientSdkTest.CHANNEL_ID, ClientSdkTest.STORE_ID);
    clientConfiguration.setConnectionTimeoutInMs(ClientSdkTest.CONNECTION_TIMEOUT_IN_MS);
    this.client = new ClientSdk(clientConfiguration, ClientSdkTest.CONTEXT_PATH);

    ReflectionTestUtils.setField(this.client, "httpClientHelper", this.clientHelper,
        GdnHttpClientHelper.class);

    this.additionalParameterMapFindValueByStoreIdAndVariable = new HashMap<String, String>();

    when(
        this.clientHelper.invokeGetSingle(any(HttpGet.class), eq(SystemParameterResponse.class),
            eq("application/json"), any(CloseableHttpClient.class))).thenReturn(
        this.systemParameterResponse);

    when(
        this.clientHelper.getURI(eq(ClientSdkTest.HOST), eq(ClientSdkTest.PORT),
            eq(ClientSdkTest.CONTEXT_PATH + ApiPath.SYSTEM_PARAMETER_FIND_ONE),
            any(MandatoryRequestParam.class),
            eq(this.additionalParameterMapFindValueByStoreIdAndVariable))).thenReturn(this.uri);
  }

  @After
  public void tearDown() {
    verifyNoMoreInteractions(this.clientHelper);
  }
}
