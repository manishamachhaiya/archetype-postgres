#set( $symbol_pound = '#' )
#set( $symbol_dollar = '$' )
#set( $symbol_escape = '\' )
package ${package}.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.gdn.common.base.GdnObjects;

import com.gdn.common.base.entity.GdnBaseEntity;

@Entity
@Table(name = SystemParameter.TABLE_NAME, uniqueConstraints = { @UniqueConstraint(columnNames = { "storeid" }) })
public class SystemParameter extends GdnBaseEntity {
	public static final String TABLE_NAME = "SystemParameter";
	private static final long serialVersionUID = 1L;

	@Column(name = "storeid")
	private String storeid;

	@Column(name = "variable")
	private String variable;

	@Column(name = "value")
	private String value;

	@Column(name = "description")
	private String description;

	public SystemParameter() {
		// do nothing
	}

	/**
	 * @param storeId
	 * @param variable
	 * @param value
	 * @param description
	 */
	public SystemParameter(String storeId, String variable, String value, String description) {
		super.setStoreId(storeId);
		this.variable = variable;
		this.value = value;
		this.description = description;
	}

	@Override
	public boolean equals(Object obj) {
		return GdnObjects.equals(this, obj);
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @return the variable
	 */
	public String getVariable() {
		return variable;
	}

	@Override
	public int hashCode() {
		return GdnObjects.hashCode(this);
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @param variable
	 *            the variable to set
	 */
	public void setVariable(String variable) {
		this.variable = variable;
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("SystemParameter [variable=%s, value=%s, description=%s, toString()=%s]", variable, value,
				description, super.toString());
	}

}
