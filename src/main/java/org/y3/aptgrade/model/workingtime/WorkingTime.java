package org.y3.aptgrade.model.workingtime;

import java.util.Date;
import org.neo4j.graphdb.Node;
import org.y3.aptgrade.model.Model;

/**
 *
 * @author christian.rybotycky
 */
public class WorkingTime extends Model{
    
    public final static String RESOURCE_KEY_EMPLOYEE = "EMPLOYEE";
    public final static String RESOURCE_KEY_BEGIN_DATETIME_STAMP = "BEGIN_DATETIME_STAMP";
    public final static String RESOURCE_KEY_END_DATETIME_STAMP = "END_DATETIME_STAMP";
    public final static String RESOURCE_KEY_DEDUCTED_BREAK_DURATION = "DEDUCTED_BREAK_DURATION";
    public final static String RESOURCE_KEY_MANDATORY_WORK_TIME = "MANDATORY_WORK_TIME";
    public final static String RESOURCE_KEY_CALCULATED_GROSS_WORKING_TIME = "CALCULATED_GROSS_WORKING_TIME";
    public final static String RESOURCE_KEY_CALCULATED_NET_WORKING_TIME = "CALCULATED_NET_WORKING_TIME";
    public final static String RESOURCE_KEY_CALCULATED_WORK_OVERTIME = "CALCULATED_WORK_OVERTIME";

    public WorkingTime(Node modelNode) {
        super(modelNode);
    }
    
    public void setBeginWorkDatetimeStamp(Date beginWork) {
        setProperty(RESOURCE_KEY_BEGIN_DATETIME_STAMP, beginWork);
    }
    
    public Date getBeginWorkDatetimeStamp() {
        return getPropertyAsDate(RESOURCE_KEY_BEGIN_DATETIME_STAMP);
    }
    
    public void setEndWorkDatetimeStamp(Date endWork) {
        setProperty(RESOURCE_KEY_END_DATETIME_STAMP, endWork);
    }
    
    public Date getEndWorkDatetimeStamp() {
        return getPropertyAsDate(RESOURCE_KEY_END_DATETIME_STAMP);
    }
    
    public void setDeductedBreakDuration(int breakTime) {
        setProperty(RESOURCE_KEY_DEDUCTED_BREAK_DURATION, breakTime);
    }
    
    public int getDeductedBreakDuration() {
        return getPropertyAsInt(RESOURCE_KEY_DEDUCTED_BREAK_DURATION);
    }
    
    public void setMandatoryWorkTime(int mandatoryWorkTime) {
        setProperty(RESOURCE_KEY_MANDATORY_WORK_TIME, mandatoryWorkTime);
    }
    
    public int getMandatoryWorkTime() {
        return getPropertyAsInt(RESOURCE_KEY_MANDATORY_WORK_TIME);
    }
    
    public void setEmployee(String employee) {
        setProperty(RESOURCE_KEY_EMPLOYEE, employee);
    }
    
    public String getEmployee() {
        return getPropertyAsString(RESOURCE_KEY_EMPLOYEE);
    }
    
    public int calculateGrossWorkingTime() {
        int calculatedGrossWorkingTime = 0;
        Date endDate = getEndWorkDatetimeStamp();
        Date beginDate = getBeginWorkDatetimeStamp();
        if (endDate != null && beginDate != null) {
            long result = ((endDate.getTime()/60000) - (beginDate.getTime()/60000));
            calculatedGrossWorkingTime = (int) result;
        }
        return calculatedGrossWorkingTime;
    }
    
    public int calculateNetWorkingTime() {
        return calculateGrossWorkingTime() - getDeductedBreakDuration();
    }
    
    public boolean isMandatoryWorkingTimeFulfilled() {
        return (calculateNetWorkingTime() >= getMandatoryWorkTime());
    }
    
    public int calculateWorkOverTime() {
        return calculateNetWorkingTime() - getMandatoryWorkTime();
    }
    
    @Override
    public String toString() {
        String toString = getEmployee();
        Date workDate = getBeginWorkDatetimeStamp();
        if (workDate == null) {
            workDate = getEndWorkDatetimeStamp();
            if (workDate == null) {
                return toString;
            }
        }
        toString += " [" + getFormattedDate(workDate) + "]";
        return toString;
    }
    
}
