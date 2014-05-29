package org.y3.aptgrade.model.workingtime;

import javax.swing.JComponent;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JTextField;
import org.y3.aptgrade.model.FurtherModelContentPanel;
import org.y3.aptgrade.view.ApplicationFrame;
import org.y3.aptgrade.view.theme.ModelForm;

/**
 *
 * @author christian.rybotycky
 */
public class WorkingTimeForm extends ModelForm {
    
    private JTextField jtf_employee;
    private JFormattedTextField jftf_beginDateTimeStamp, jftf_endDateTimeStamp, jftf_deductedBreakDuration, jftf_mandatoryWorkTime;
    private JLabel jl_grossWorkingTime, jl_netWorkingTime, jl_workOverTime;
    
    public WorkingTimeForm(ApplicationFrame _applicationFrame) {
        super(_applicationFrame);
    }

    @Override
    protected void initForm() {
        jtf_employee = new JTextField();
        addRow(new JLabel(MESSAGES.getString(WorkingTime.RESOURCE_KEY_EMPLOYEE)), jtf_employee);
        jftf_beginDateTimeStamp = new JFormattedTextField(getDateTimeFormatter());
        addRow(new JLabel(MESSAGES.getString(WorkingTime.RESOURCE_KEY_BEGIN_DATETIME_STAMP)), jftf_beginDateTimeStamp);
        jftf_endDateTimeStamp = new JFormattedTextField(getDateTimeFormatter());
        addRow(new JLabel(MESSAGES.getString(WorkingTime.RESOURCE_KEY_END_DATETIME_STAMP)), jftf_endDateTimeStamp);
        jl_grossWorkingTime = new JLabel();
        addRow(new JLabel(MESSAGES.getString(WorkingTime.RESOURCE_KEY_CALCULATED_GROSS_WORKING_TIME)), jl_grossWorkingTime, new JLabel("min"));
        jftf_deductedBreakDuration = new JFormattedTextField(getIntegerFormatter(2));
        addRow(new JLabel(MESSAGES.getString(WorkingTime.RESOURCE_KEY_DEDUCTED_BREAK_DURATION)), jftf_deductedBreakDuration, new JLabel("min"));
        jftf_mandatoryWorkTime = new JFormattedTextField(getIntegerFormatter(3));
        addRow(new JLabel(MESSAGES.getString(WorkingTime.RESOURCE_KEY_MANDATORY_WORK_TIME)), jftf_mandatoryWorkTime, new JLabel("min"));
        jl_netWorkingTime = new JLabel();
        addRow(new JLabel(MESSAGES.getString(WorkingTime.RESOURCE_KEY_CALCULATED_NET_WORKING_TIME)), jl_netWorkingTime, new JLabel("min"));
        jl_workOverTime = new JLabel();
        addRow(new JLabel(MESSAGES.getString(WorkingTime.RESOURCE_KEY_CALCULATED_WORK_OVERTIME)), jl_workOverTime, new JLabel("min"));
        setEditable(false);
    }

    @Override
    public JComponent getFirstFocusableEditorComponent() {
        return jtf_employee;
    }

    @Override
    public Object[] getAllComponentsToManageEditableMode() {
        return new Object[]{jftf_beginDateTimeStamp,jftf_deductedBreakDuration,jftf_endDateTimeStamp,
        jftf_mandatoryWorkTime, jtf_employee};
    }

    @Override
    public FurtherModelContentPanel[] getFurtherContentTabs() {
        return null;
    }

    @Override
    public void bindUI() {
        WorkingTime workingTime = getWorkingTime();
        if (workingTime != null) {
            jtf_employee.setText(workingTime.getEmployee());
            jftf_beginDateTimeStamp.setText(formatDateTimeToString(workingTime.getBeginWorkDatetimeStamp()));
            jftf_endDateTimeStamp.setText(formatDateTimeToString(workingTime.getEndWorkDatetimeStamp()));
            jftf_deductedBreakDuration.setText(Integer.toString(workingTime.getDeductedBreakDuration()));
            jftf_mandatoryWorkTime.setText(Integer.toString(workingTime.getMandatoryWorkTime()));
            jl_grossWorkingTime.setText(Integer.toString(workingTime.calculateGrossWorkingTime()));
            jl_netWorkingTime.setText(Integer.toString(workingTime.calculateNetWorkingTime()));
            jl_workOverTime.setText(Integer.toString(workingTime.calculateWorkOverTime()));
            if (workingTime.isMandatoryWorkingTimeFulfilled()) {
                jl_netWorkingTime.setForeground(getPositiveColor());
                jl_workOverTime.setForeground(getPositiveColor());
            } else {
                jl_netWorkingTime.setForeground(getNegativeColor());
                jl_workOverTime.setForeground(getNegativeColor());
            }
        } else {
            jtf_employee.setText(NV);
            jftf_beginDateTimeStamp.setText(NV);
            jftf_endDateTimeStamp.setText(NV);
            jftf_deductedBreakDuration.setText(NV);
            jftf_mandatoryWorkTime.setText(NV);
            jl_grossWorkingTime.setText(NV);
            jl_netWorkingTime.setText(NV);
            jl_workOverTime.setText(NV);
        }
    }

    @Override
    public void fillModelFromForm() {
        WorkingTime workingTime = getWorkingTime();
        if (workingTime != null) {
            workingTime.setEmployee(jtf_employee.getText());
            workingTime.setBeginWorkDatetimeStamp(parseDateTimeFromString(jftf_beginDateTimeStamp.getText()));
            workingTime.setEndWorkDatetimeStamp(parseDateTimeFromString(jftf_endDateTimeStamp.getText()));
            workingTime.setDeductedBreakDuration(parseIntFromString(jftf_deductedBreakDuration.getText()));
            workingTime.setMandatoryWorkTime(parseIntFromString(jftf_mandatoryWorkTime.getText()));
        }
    }
    
    /**
     * Get the current WorkingTime model
     * @return the WorkingTime, if model is instance of WorkingTime, else null
     */
    public WorkingTime getWorkingTime() {
        if (model instanceof WorkingTime) {
            return (WorkingTime) model;
        } else {
            return null;
        }
    }
    
}
