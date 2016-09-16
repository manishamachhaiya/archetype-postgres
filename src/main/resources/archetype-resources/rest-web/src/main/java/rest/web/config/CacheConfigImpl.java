#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest.web.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

import com.gdn.common.base.cache.CacheConfig;

@Configuration
@EnableCaching(order = 0)
public class CacheConfigImpl extends CacheConfig {
  // do nothing
}
