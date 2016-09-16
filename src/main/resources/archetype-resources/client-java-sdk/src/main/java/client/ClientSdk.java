#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.client;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.gdn.common.client.GdnRestClientConfiguration;
import com.gdn.common.util.GdnUUIDHelper;
import com.gdn.common.web.client.GdnBaseRestCrudClient;
import com.gdn.common.web.wrapper.response.GdnRestSingleResponse;
import ${package}.rest.web.model.ApiPath;
import ${package}.rest.web.model.SystemParameterResponse;

public class ClientSdk extends GdnBaseRestCrudClient {

  private static final String JSON_TYPE = "application/json";

  public ClientSdk(GdnRestClientConfiguration clientConfig, String contextPath) {
    super(clientConfig);
    this.setContextPath(contextPath);
  }

  public ClientSdk(String username, String password, String host, Integer port, String storeId,
      String clientId, String channelId, String contextPath) {
    super(username, password, host, port, clientId, channelId, storeId, contextPath);
  }

  public GdnRestSingleResponse<SystemParameterResponse> findOne(String requestId, String variable)
      throws Exception {
    URI uri =
        this.generateURI(ApiPath.SYSTEM_PARAMETER_FIND_ONE, requestId,
            new HashMap<String, String>());
    return this.invokeGetSingle(uri, SystemParameterResponse.class, ClientSdk.JSON_TYPE);
  }

  private URI generateURI(String path, String requestId, Map<String, String> additionalParameterMap)
      throws Exception {
    String location = this.getContextPath() + path;
    return this.getHttpClientHelper().getURI(this.getClientConfig().getHost(),
        this.getClientConfig().getPort(), location,
        this.getMandatoryParameter(this.getDefaultRequestIdValue(requestId)),
        additionalParameterMap);
  }

  private String getDefaultRequestIdValue(String requestId) {
    if ((requestId == null) || (requestId.trim().length() == 0)) {
      return GdnUUIDHelper.generateUUID();
    }
    return requestId;
  }
}
