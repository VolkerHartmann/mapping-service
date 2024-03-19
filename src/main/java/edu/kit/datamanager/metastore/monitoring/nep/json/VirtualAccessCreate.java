
package edu.kit.datamanager.metastore.monitoring.nep.json;

import javax.annotation.processing.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Root
 * <p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "virtual_service_id",
    "payload"
})
@Generated("jsonschema2pojo")
public class VirtualAccessCreate {

    /**
     * Virtual Access ID
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("virtual_service_id")
    private String virtualServiceId;
    /**
     * Any Payload
     * <p>
     * 
     * 
     */
    @JsonProperty("payload")
    private Payload payload;

    /**
     * Virtual Access ID
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("virtual_service_id")
    public String getVirtualServiceId() {
        return virtualServiceId;
    }

    /**
     * Virtual Access ID
     * <p>
     * 
     * (Required)
     * 
     */
    @JsonProperty("virtual_service_id")
    public void setVirtualServiceId(String virtualServiceId) {
        this.virtualServiceId = virtualServiceId;
    }

    /**
     * Any Payload
     * <p>
     * 
     * 
     */
    @JsonProperty("payload")
    public Payload getPayload() {
        return payload;
    }

    /**
     * Any Payload
     * <p>
     * 
     * 
     */
    @JsonProperty("payload")
    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(VirtualAccessCreate.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("virtualServiceId");
        sb.append('=');
        sb.append(((this.virtualServiceId == null)?"<null>":this.virtualServiceId));
        sb.append(',');
        sb.append("payload");
        sb.append('=');
        sb.append(((this.payload == null)?"<null>":this.payload));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = ((result* 31)+((this.virtualServiceId == null)? 0 :this.virtualServiceId.hashCode()));
        result = ((result* 31)+((this.payload == null)? 0 :this.payload.hashCode()));
        return result;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if ((other instanceof VirtualAccessCreate) == false) {
            return false;
        }
        VirtualAccessCreate rhs = ((VirtualAccessCreate) other);
        return (((this.virtualServiceId == rhs.virtualServiceId)||((this.virtualServiceId!= null)&&this.virtualServiceId.equals(rhs.virtualServiceId)))&&((this.payload == rhs.payload)||((this.payload!= null)&&this.payload.equals(rhs.payload))));
    }

}
