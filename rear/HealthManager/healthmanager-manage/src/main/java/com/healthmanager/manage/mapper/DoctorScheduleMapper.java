package com.healthmanager.manage.mapper;

import java.util.Date;
import java.util.List;
import com.healthmanager.manage.domain.DoctorSchedule;
import org.apache.ibatis.annotations.Select;

/**
 * 医生排班Mapper接口
 * 
 * @author ccc212
 * @date 2024-12-05
 */
public interface DoctorScheduleMapper 
{
    /**
     * 查询医生排班
     * 
     * @param scheduleId 医生排班主键
     * @return 医生排班
     */
    public DoctorSchedule selectDoctorScheduleByScheduleId(Long scheduleId);

    /**
     * 查询医生排班列表
     * 
     * @param doctorSchedule 医生排班
     * @return 医生排班集合
     */
    public List<DoctorSchedule> selectDoctorScheduleList(DoctorSchedule doctorSchedule);

    /**
     * 新增医生排班
     * 
     * @param doctorSchedule 医生排班
     * @return 结果
     */
    public int insertDoctorSchedule(DoctorSchedule doctorSchedule);

    /**
     * 修改医生排班
     * 
     * @param doctorSchedule 医生排班
     * @return 结果
     */
    public int updateDoctorSchedule(DoctorSchedule doctorSchedule);

    /**
     * 删除医生排班
     * 
     * @param scheduleId 医生排班主键
     * @return 结果
     */
    public int deleteDoctorScheduleByScheduleId(Long scheduleId);

    /**
     * 批量删除医生排班
     * 
     * @param scheduleIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDoctorScheduleByScheduleIds(Long[] scheduleIds);

    @Select("select * from health_doctor_schedule where doctor_id = #{doctorId} and date = #{date}")
    DoctorSchedule getSchedule(Long doctorId, Date date);
}
