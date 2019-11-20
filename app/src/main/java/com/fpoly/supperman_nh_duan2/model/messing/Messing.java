
package com.fpoly.supperman_nh_duan2.model.messing;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Messing {

    @SerializedName("multicast_id")
    @Expose
    private Long multicastId;
    @SerializedName("success")
    @Expose
    private Long success;
    @SerializedName("failure")
    @Expose
    private Long failure;
    @SerializedName("canonical_ids")
    @Expose
    private Long canonicalIds;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public Long getMulticastId() {
        return multicastId;
    }

    public void setMulticastId(Long multicastId) {
        this.multicastId = multicastId;
    }

    public Long getSuccess() {
        return success;
    }

    public void setSuccess(Long success) {
        this.success = success;
    }

    public Long getFailure() {
        return failure;
    }

    public void setFailure(Long failure) {
        this.failure = failure;
    }

    public Long getCanonicalIds() {
        return canonicalIds;
    }

    public void setCanonicalIds(Long canonicalIds) {
        this.canonicalIds = canonicalIds;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }
}
