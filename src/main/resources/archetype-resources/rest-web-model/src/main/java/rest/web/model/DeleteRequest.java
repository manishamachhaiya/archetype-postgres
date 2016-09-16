#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.rest.web.model;

public class DeleteRequest {
  private String id;

  public String getId() {
    return this.id;
  }

  public void setId(String id) {
    this.id = id;
  }

  @Override
  public String toString() {
    return String.format("DeleteRequest [id=%s, toString()=%s]", this.id, super.toString());
  }
}
