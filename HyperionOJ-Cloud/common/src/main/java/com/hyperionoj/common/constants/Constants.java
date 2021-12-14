package com.hyperionoj.common.constants;

/**
 * @author Hyperion
 * @date 2021/12/1
 */
public interface Constants {
    /**
     * 状态码成功
     */
    int SUCCESS_CODE = 200;

    /**
     * 用户密码加密盐
     */
    String SALT = "HyperionOJ";

    /**
     * 邮箱符号: '@'
     */
    Character AT = '@';

    /**
     * 分隔符英文句号: '.'
     */
    Character PERIOD = '.';

    /**
     * 下划线: '_'
     */
    String UNDERLINE = "_";

    /**
     * redis分隔符英文冒号: ':'
     */
    Character COLON = ':';

    /**
     * redis存token时的首部字符
     */
    String TOKEN = "TOKEN:";

    /**
     * redis中验证码首部字符
     */
    String VER_CODE = "VER_CODE:";

    /**
     * 验证码长度为6
     */
    Integer CODE_LENGTH = 6;

    /**
     * 请求头里面的字段: UNKNOWN
     */
    String UNKNOWN = "UNKNOWN";

    /**
     * 请求头里面的字段: UNDEFINED
     */
    String UNDEFINED = "UNDEFINED";

    /**
     * 表示逗号 ","
     */
    String COMMA = ",";

    /**
     * 非法字符
     */
    String ILLEGAL_CHAR_SYSTEM = "system(";

    /**
     * 表示kafka请求judge服务运行代码主题
     */
    String KAFKA_TOPIC_SUBMIT = "SUBMIT";

    /**
     * 表示kafka从judge服务返回page服务运行结果消息主题
     */
    String KAFKA_TOPIC_SUBMIT_RESULT = "RESULT";

    /**
     * 表示kafka更新用户提交数量主题
     */
    String KAFKA_TOPIC_SUBMIT_PAGE = "SUBMIT_PAGE";

    /**
     * 表示提交状态
     */
    String AC = "AC";

    /**
     * 题目评论类名
     */
    String REDIS_KAY_PROBLEM_CONTROLLER = "ProblemController";

    /**
     * 题目评论方法名
     */
    String REDIS_KEY_METHOD_SUPPORT_COMMENT = "supportComment";

    /**
     * 获取评论列表
     */
    String REDIS_KEY_METHOD_GET_COMMENTS = "getComments";

    /**
     * 评论缓存名
     */
    String REDIS_KEY_CACHE_NAME = "ProblemCommentNumber";

    /**
     * 方法名
     */
    String REDIS_KEY_METHOD_PROBLEM_LIST = "getProblemList";

    /**
     * 问题缓存名
     */
    String REDIS_KEY_CACHE_NAME_PROBLEM = "problemList";

    /**
     * 获取题目方法名
     */
    String REDIS_KRY_CLASS_NAME_PROBLEM = "problemId";

    /**
     * 获取题目方法名
     */
    String REDIS_KRY_METHOD_PROBLEM = "getProblemById";

}
