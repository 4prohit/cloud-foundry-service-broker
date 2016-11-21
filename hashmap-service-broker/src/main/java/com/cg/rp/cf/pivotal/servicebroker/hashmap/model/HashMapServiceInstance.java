package com.cg.rp.cf.pivotal.servicebroker.hashmap.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.cloud.servicebroker.model.CreateServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.DeleteServiceInstanceRequest;
import org.springframework.cloud.servicebroker.model.UpdateServiceInstanceRequest;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

/**
 * @author rohitpat
 *
 */
@Entity
@Table(name="service_instances")
@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
public class HashMapServiceInstance {
	// model to store service instance and it's metadata
	
    @Id
    @JsonSerialize
	@JsonProperty("service_instance_id")
	private String serviceInstanceId;

    @JsonSerialize
    @JsonProperty("service_id")
    @Column(nullable = false)
    private String serviceDefinitionId;

    @JsonSerialize
    @JsonProperty("plan_id")
    @Column(nullable = false)
    private String planId;

    @JsonSerialize
    @JsonProperty("organization_guid")
    @Column(nullable = false)
    private String organizationGuid;

    @JsonSerialize
    @JsonProperty("space_guid")
    @Column(nullable = false)
    private String spaceGuid;
    
    @SuppressWarnings("unused")
    public HashMapServiceInstance() {}

	public HashMapServiceInstance(String serviceInstanceId, String serviceDefinitionId, String planId,
						   String organizationGuid, String spaceGuid) {
		this.serviceInstanceId = serviceInstanceId;
		this.serviceDefinitionId = serviceDefinitionId;
		this.planId = planId;
		this.organizationGuid = organizationGuid;
		this.spaceGuid = spaceGuid;
	}
    
    public HashMapServiceInstance(CreateServiceInstanceRequest request) {
		this.serviceDefinitionId = request.getServiceDefinitionId();
		this.planId = request.getPlanId();
		this.organizationGuid = request.getOrganizationGuid();
		this.spaceGuid = request.getSpaceGuid();
		this.serviceInstanceId = request.getServiceInstanceId();
	}
    
    public HashMapServiceInstance(DeleteServiceInstanceRequest request) {
		this.serviceInstanceId = request.getServiceInstanceId();
		this.planId = request.getPlanId();
		this.serviceDefinitionId = request.getServiceDefinitionId();
	}
    
    public HashMapServiceInstance(UpdateServiceInstanceRequest request) {
		this.serviceInstanceId = request.getServiceInstanceId();
		this.planId = request.getPlanId();
	}

    public String getServiceInstanceId() {
		return serviceInstanceId;
	}

	public void setServiceInstanceId(String serviceInstanceId) {
		this.serviceInstanceId = serviceInstanceId;
	}

	public String getServiceDefinitionId() {
		return serviceDefinitionId;
	}

	public void setServiceDefinitionId(String serviceDefinitionId) {
		this.serviceDefinitionId = serviceDefinitionId;
	}

	public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getOrganizationGuid() {
        return organizationGuid;
    }

    public void setOrganizationGuid(String organizationGuid) {
        this.organizationGuid = organizationGuid;
    }

    public String getSpaceGuid() {
        return spaceGuid;
    }

    public void setSpaceGuid(String spaceGuid) {
        this.spaceGuid = spaceGuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HashMapServiceInstance that = (HashMapServiceInstance) o;

        if (!serviceInstanceId.equals(that.serviceInstanceId)) return false;
        if (!organizationGuid.equals(that.organizationGuid)) return false;
        if (!planId.equals(that.planId)) return false;
        if (!serviceDefinitionId.equals(that.serviceDefinitionId)) return false;
        if (!spaceGuid.equals(that.spaceGuid)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = serviceInstanceId.hashCode();
        result = 31 * result + serviceDefinitionId.hashCode();
        result = 31 * result + planId.hashCode();
        result = 31 * result + organizationGuid.hashCode();
        result = 31 * result + spaceGuid.hashCode();
        return result;
    }
}
