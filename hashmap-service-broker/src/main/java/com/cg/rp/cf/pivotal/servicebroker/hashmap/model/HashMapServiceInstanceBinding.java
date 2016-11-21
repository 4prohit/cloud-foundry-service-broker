package com.cg.rp.cf.pivotal.servicebroker.hashmap.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author rohitpat
 *
 */
@Entity
@Table(name = "service_bindings")
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class HashMapServiceInstanceBinding {
	// model to store service binding and it's metadata
	
	@JsonSerialize
    @JsonProperty("service_binding_id")
    @Id
    private String serviceBindingId;

    
    @JsonSerialize
    @JsonProperty("service_instance_id")
    @Column(nullable = false)
    private String serviceInstanceId;

    @JsonSerialize
    @JsonProperty("app_guid")
    @Column(nullable = false)
    private String appGuid;

    @OneToOne(orphanRemoval = true, cascade = CascadeType.ALL)
    @JoinColumn(name = "service_binding_id")
    private Credentials credentials;
    
    @SuppressWarnings("unused")
    public HashMapServiceInstanceBinding() {}
    
    public HashMapServiceInstanceBinding(String serviceBindingId,
			  String serviceInstanceId,
			  Credentials credentials,
			  String syslogDrainUrl, String appGuid) {
		this.serviceBindingId = serviceBindingId;
		this.serviceInstanceId = serviceInstanceId;
		setCredentials(credentials);
		this.appGuid = appGuid;
    }

    public String getServiceBindingId() {
		return serviceBindingId;
	}

	public void setServiceBindingId(String serviceBindingId) {
		this.serviceBindingId = serviceBindingId;
	}

	public String getServiceInstanceId() {
		return serviceInstanceId;
	}

	public void setServiceInstanceId(String serviceInstanceId) {
		this.serviceInstanceId = serviceInstanceId;
	}

	public String getAppGuid() {
        return appGuid;
    }

    public void setAppGuid(String appGuid) {
        this.appGuid = appGuid;
    }

	public Credentials getCredentials() {
		return credentials;
	}

	public void setCredentials(Credentials credentials) {
		this.credentials = credentials;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HashMapServiceInstanceBinding that = (HashMapServiceInstanceBinding) o;

        if (!appGuid.equals(that.appGuid)) return false;
        if (!serviceBindingId.equals(that.serviceBindingId)) return false;
        if (!serviceInstanceId.equals(that.serviceInstanceId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = serviceBindingId.hashCode();
        result = 31 * result + serviceInstanceId.hashCode();
        result = 31 * result + appGuid.hashCode();
        return result;
    }
}
