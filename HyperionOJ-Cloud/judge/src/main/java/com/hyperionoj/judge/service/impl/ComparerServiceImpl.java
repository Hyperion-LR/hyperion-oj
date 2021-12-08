package com.hyperionoj.judge.service.impl;

import com.hyperionoj.judge.service.ComparerService;
import org.springframework.stereotype.Service;

/**
 * @author Hyperion
 * @date 2021/12/8
 */
@Service
public class ComparerServiceImpl implements ComparerService {
    /**
     * 比较运行结果是否正确
     *
     * @param codeRes   代码运行的结果
     * @param problemId 题目id
     * @return 代码运行是否正确
     */
    @Override
    public Boolean compare(String codeRes, String problemId) {
        return null;
    }
}
