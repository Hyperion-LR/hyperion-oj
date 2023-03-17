package com.hyperionoj.web.presentation.controller;

import com.hyperionoj.web.application.api.AlarmService;
import com.hyperionoj.web.application.api.FlinkTaskService;
import com.hyperionoj.web.application.api.JobOperationService;
import com.hyperionoj.web.infrastructure.constants.ErrorCode;
import com.hyperionoj.web.infrastructure.constants.JobActionCodeEnum;
import com.hyperionoj.web.infrastructure.exception.JobResourceNotEnoughException;
import com.hyperionoj.web.presentation.dto.JobActionDTO;
import com.hyperionoj.web.presentation.dto.JobBaseDTO;
import com.hyperionoj.web.presentation.dto.param.JobListPageParams;
import com.hyperionoj.web.presentation.vo.JobBaseVO;
import com.hyperionoj.web.presentation.vo.Result;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import java.util.Arrays;
import java.util.Set;

import static com.hyperionoj.web.infrastructure.constants.Constants.START;
import static com.hyperionoj.web.infrastructure.constants.Constants.STOP;

/**
 * @author Hyperion
 * @date 2023/3/11
 */
@RestController
@RequestMapping("/job")
public class JobOperationController {

    @Resource
    private AlarmService alarmService;

    @Resource
    private JobOperationService jobOperationService;

    @Resource
    private FlinkTaskService flinkTaskService;

    /**
     * 获取用户所有的的作业列表
     *
     * @return 作业列表
     */
    @GetMapping("/list")
    public Result jobList(@RequestBody JobListPageParams pageParams) {
        return Result.success(jobOperationService.list(pageParams));
    }

    /**
     * 获取作业详情
     *
     * @param jobId 作品ID
     * @return 作业详情情况
     */
    @GetMapping("/{jobId}")
    public Result findById(@PathVariable("jobId") Long jobId) {
        return Result.success(jobOperationService.findById(jobId));
    }

    /**
     * 创建作业
     *
     * @param jobBaseDTO 新加参数
     * @return 作业视图
     */
    @PostMapping("/create")
    public Result createJob(@RequestBody JobBaseDTO jobBaseDTO) {
        JobBaseVO jobBaseVO = null;
        try {
            jobBaseVO = jobOperationService.add(jobBaseDTO);
        } catch (JobResourceNotEnoughException e) {
            return Result.fail(ErrorCode.JOB_USER_RESOURCE_FULL);
        }
        if (jobBaseVO == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return Result.success(jobBaseVO);
    }

    /**
     * 更新作业
     *
     * @param jobBaseDTO 新加参数
     * @return 作业视图
     */
    @PostMapping("/update")
    public Result updateJob(@RequestBody JobBaseDTO jobBaseDTO) {
        JobBaseVO jobBaseVO = null;
        try {
            jobBaseVO = jobOperationService.update(jobBaseDTO);
        } catch (JobResourceNotEnoughException e) {
            return Result.fail(ErrorCode.JOB_USER_RESOURCE_FULL);
        }
        if (jobBaseVO == null) {
            return Result.fail(ErrorCode.PARAMS_ERROR);
        }
        return Result.success(jobBaseVO);
    }

    /**
     * 删除作业
     *
     * @param jobId 作业相关信息
     * @return 是否删除成功
     */
    @DeleteMapping("/delete/{jobId}")
    public Result deleteJob(@PathVariable("jobId") Long jobId) {
        return Result.success(jobOperationService.delete(jobId));
    }

    /**
     * 上传资源
     *
     * @param multipartFileList 资源列表
     * @return 上传是否成功
     */
    @PostMapping("/resource/{jobId}")
    public Result updateResource(@PathVariable("jobId") Long jobId, @RequestParam("resourceList") MultipartFile[] multipartFileList) {
        return Result.success(jobOperationService.updateResource(jobId, multipartFileList));
    }

    /**
     * 启动/暂停作业
     *
     * @param jobActionDTO action参数
     * @return action结果
     */
    @PostMapping("/action")
    public Result action(@RequestBody JobActionDTO jobActionDTO) {
        if (START.equals(jobActionDTO.getAction())) {
            JobActionCodeEnum code = flinkTaskService.startJob(jobActionDTO);
            if (JobActionCodeEnum.START_PROCESS_SUCCESS.equals(code)) {
                return Result.success(code);
            }
            return Result.fail(code);
        }

        if (STOP.equals(jobActionDTO.getAction())) {
            JobActionCodeEnum code = flinkTaskService.stopJob(jobActionDTO);
            if (JobActionCodeEnum.STOP_PROCESS_SUCCESS.equals(code)) {
                return Result.success(code);
            }
            return Result.fail(code);
        }

        return Result.fail(JobActionCodeEnum.ACTION_PARAMS_ERROR);
    }
}
