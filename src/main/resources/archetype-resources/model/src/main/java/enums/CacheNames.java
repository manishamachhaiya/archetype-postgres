#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.enums;

public interface CacheNames {

  String PREFIX = "com.gdn.x:${rootArtifactId}:";

  String SYSTEM_PARAMETER = CacheNames.PREFIX + "xparameter";

}
