package com.kingdee.model.po;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;

@Table(name = "spring_scheduled_cron")
public class SpringScheduledCron {
    /**
     * 主键id
     */
    @Id
    @Column(name = "cron_id")
    private Integer cronId;

    /**
     * 定时任务完整类名
     */
    @Column(name = "cron_key")
    private String cronKey;

    /**
     * cron表达式
     */
    @Column(name = "cron_expression")
    private String cronExpression;

    /**
     * 任务描述
     */
    @Column(name = "task_explain")
    private String taskExplain;

    /**
     * 状态,1:正常;2:停用
     */
    private Byte status;

    /**
     * 获取主键id
     *
     * @return cron_id - 主键id
     */
    public Integer getCronId() {
        return cronId;
    }

    /**
     * 设置主键id
     *
     * @param cronId 主键id
     */
    public void setCronId(Integer cronId) {
        this.cronId = cronId;
    }

    /**
     * 获取定时任务完整类名
     *
     * @return cron_key - 定时任务完整类名
     */
    public String getCronKey() {
        return cronKey;
    }

    /**
     * 设置定时任务完整类名
     *
     * @param cronKey 定时任务完整类名
     */
    public void setCronKey(String cronKey) {
        this.cronKey = cronKey;
    }

    /**
     * 获取cron表达式
     *
     * @return cron_expression - cron表达式
     */
    public String getCronExpression() {
        return cronExpression;
    }

    /**
     * 设置cron表达式
     *
     * @param cronExpression cron表达式
     */
    public void setCronExpression(String cronExpression) {
        this.cronExpression = cronExpression;
    }

    /**
     * 获取任务描述
     *
     * @return task_explain - 任务描述
     */
    public String getTaskExplain() {
        return taskExplain;
    }

    /**
     * 设置任务描述
     *
     * @param taskExplain 任务描述
     */
    public void setTaskExplain(String taskExplain) {
        this.taskExplain = taskExplain;
    }

    /**
     * 获取状态,1:正常;2:停用
     *
     * @return status - 状态,1:正常;2:停用
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态,1:正常;2:停用
     *
     * @param status 状态,1:正常;2:停用
     */
    public void setStatus(Byte status) {
        this.status = status;
    }
}