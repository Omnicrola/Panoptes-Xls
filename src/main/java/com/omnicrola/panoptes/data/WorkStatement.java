package com.omnicrola.panoptes.data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

public class WorkStatement {

    public static final WorkStatement EMPTY = new WorkStatement("", "", "", "", 0);

    @Required
    private String projectName;
    @Required
    private String client;
    @Required
    private String projectCode;
    @Required
    private String sowCode;
    @Required
    private float billableRate;

    public WorkStatement() {
    }

    public WorkStatement(String projectName, String client, String projectCode, String sowCode, float billableRate) {
        this.projectName = projectName;
        this.client = client;
        this.projectCode = projectCode;
        this.sowCode = sowCode;
        this.billableRate = billableRate;
    }

    public float getBillableRate() {
        return this.billableRate;
    }

    public String getClient() {
        return this.client;
    }

    public String getProjectCode() {
        return this.projectCode;
    }

    public String getProjectName() {
        return this.projectName;
    }

    public String getSowCode() {
        return this.sowCode;
    }


    @Override
    public String toString() {
        return this.sowCode + " : " + this.projectName + " " + this.projectCode + " " + this.client;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + Float.floatToIntBits(this.billableRate);
        result = prime * result + ((this.client == null) ? 0 : this.client.hashCode());
        result = prime * result + ((this.projectCode == null) ? 0 : this.projectCode.hashCode());
        result = prime * result + ((this.projectName == null) ? 0 : this.projectName.hashCode());
        result = prime * result + ((this.sowCode == null) ? 0 : this.sowCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        WorkStatement other = (WorkStatement) obj;
        if (Float.floatToIntBits(this.billableRate) != Float.floatToIntBits(other.billableRate)) {
            return false;
        }
        if (this.client == null) {
            if (other.client != null) {
                return false;
            }
        } else if (!this.client.equals(other.client)) {
            return false;
        }
        if (this.projectCode == null) {
            if (other.projectCode != null) {
                return false;
            }
        } else if (!this.projectCode.equals(other.projectCode)) {
            return false;
        }
        if (this.projectName == null) {
            if (other.projectName != null) {
                return false;
            }
        } else if (!this.projectName.equals(other.projectName)) {
            return false;
        }
        if (this.sowCode == null) {
            if (other.sowCode != null) {
                return false;
            }
        } else if (!this.sowCode.equals(other.sowCode)) {
            return false;
        }
        return true;
    }

}
