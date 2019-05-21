package com.w2g.emun;

/**
 * 定义全局错误码枚举类 ----分模块进行相应的错误码统一管理 此处需要开发人员进行共同维护，希望共同维护好整个平台的错误码机制 错误码命名规范：
 * 总位数：5位 前两位定义模块，如用户模块定义为01 后三位为真实的错误码，如01001表示用户模块的第一个错误码，具体错误含义由开发人员定义
 * Created by W2G 2018/4/13.
 */
public enum ErrorCode {

	// 全局前端参数传递异常
	GLOBLE_PARAM_ERROR_CODE("10002", "前端[%s]参数传递异常！"),

	// 全局前端参数传递异常
	ALL_PARAM_ERROR_CODE("10002", "[%s]！"),

	// 全局未知错误码
	GLOBLE_UNKOWN_ERROR("10001", "系统未知错误！"),

	// 全局查询框架抛出的异常
	GLOBLE_COMMON_SELECT_EXCEPTION("10003", "数据库操作异常！"),

	// token生成过程异常
	GLOBLE_GEN_TOKEN_EXCEPTION("10004", "token生成过程异常！"),

	GLOBLE_UNAUTHORIZED_EXCEPTION("10401", "您未授权token，无法进行操作！"),

	/** 10403 您无权限操作该资源！ */
	GLOBLE_ACCESS_DENIED_EXCEPTION("10403", "您无权限操作该资源！"),

	GLOBLE_404_EXCEPTION("404", "404异常！"),

	// 全局成功码
	GLOBLE_SUCCESS("200", "success"),

	// 全局错误
	GLOBLE_ERROR("200", "error"),

	/** 全局 失败 %s失败，请稍后重试。 */
	GLOBLE_FAIL_MSG_FORMAT ("10008", "%s失败，请稍后重试。"),
	GLOBLE_WECHAT_OPENID_NULL ("10009", "微信用户openId获取失败！ [%s]"),
	/**************************************** 用户模块 模块代号;11 **************************************************/

	USER_ID_EXCEPTION("11001", "userId参数异常!"),
	USER_LOGIN_PARAM_EXCEPTION("11002","用户登录参数异常!"),
	USER_LOGIN_VERCODE_EXCEPTION("11003", "用户登录参数手机号与验证码匹配异常!"),
	USER_NO_EXIST("11004", "用户不存在！"),
	USER_NO_PERMITION("11005", "用户没有权限！"),
	USER_NO_START_HOST_AUTH("11006", "主办方用户还没有进行主办方认证！"),
	USER_LOGIN_PWD_FAIL("11007", "账号或密码错误！"),
	USER_PHONE_CODE_FAIL("11008", "手机号验证码校验失败！"),
	USER_QUALIFICATIONIMGS_FAIL("11009", "资质证书不合法！"),
	SYS_ROLE_TABLE_ERROR("11110", "用户角色表数据异常！"),
    USER_TOKEN_FAIL("11010", "token校验错误"),
	USER_STATUS_FAIL("11011", "用户账号不可用"),
    USER_APP_ADD_ERROR("11012", "APP注册用户失败"),
	USER_TOKEN_INVALID("11013", "token已失效"),
    USER_REPEAT_ACTIVATION("11014", "用户已激活，不可以重读激活身份。"),
    USER_NOT_ACTIVATION_ERROR("11015", "用户未激活,不可使用本功能。"),
	USER_REGISTER_REPEAT_ERROR("11016", "该手机已注册，请直接登录"),
    USER_EDIT_PASSWORD_FIRST_ERROR("11017", "账户已设定密码，无需重复设定"),
    USER_ADD_ERROR ("11018", "注册用户失败，请稍后重试"),

    /*************** 用户钱包模块 模块代号：12 ******************/

    /** 12001 您已设定过钱包密码，无需重复设定 */
    WALLET_PASSWORD_REPEAT_POST("12001", "您已设定过钱包密码，无需重复设定"),
    /** 12002 您尚未设定过钱包密码，请先设定后，继续操作 */
    WALLET_PASSWORD_NULL("12002", "您尚未设定过钱包密码，请先设定后，继续操作"),
    /** 12003 密码不正确，请重新输入 */
    WALLET_PASSWORD_NOT_EQUAL("12003", "密码不正确，请重新输入"),
    /** 12004 余额不足 */
    WALLET_BALANCE_LESS_ZERO("12004", "余额不足"),
	/** 12005 最少提现100元 */
	WALLET_BALANCE_LESS_LESS("12005", "最少提现100元"),

    /***************************************** 会议模块  模块代号；22 *************************************************/

    EVENT_NO_PERMITION_YOU_TO_GET("22001","您无权操作该会议，该会议并不您创办的!"),
    EVENT_NO_EXIST("22002","会议不存在"),
    EVENT_NO_VERIFY("22003","会议未审核"),
    EVENT_VERIFY_FAIL("22004","会议审核失败"),
    EVENT_LIST_SELECT_PARAMES_ERROR("22005","查询会议列表状态参数异常！"),
    EVENT_ID_PARAMES_ERROR("22006","会议id参数异常！"),
    EVENT_NO_PERMITION_YOU_TO_EDIT("22007","您无权修改该会议，该会议并不您创办的!"),
    EVENT_HASENT_THIS_GUST("22008","该会议下并没有该参会嘉宾！"),
	EVENT_GUEST_DUPLICATE("22011", "会议嘉宾重复，同一场会议中不能存在相同姓名|手机号的参会嘉宾！"),
	EVENT_NAV_NOT_BELONG_YOU("22012","导航异常，或不属于该会议！"),
	EVENT_CHILDNAV_NOT_BELONG_FATHERNAV("22013","子栏目导航异常，或不属于该父栏目！"),
	EVENT_SENDMSG_NOT_ALLOW_DELETE("22014","该短信模板属于系统类，不允许删除！"),
    EVENT_REGISTERSET_NO_EXIST("22100","尚未设置参会注册系统是否开放"),
    EVENT_REGISTERTYPE_NO_EXIST("22101","查询有误"),
	EVENT_REGISTERTYPES_NO_EXIST("22102","尚未创建注册参会类型或注册类型不合法！"),
	EVENT_FILECONFIG_NO_EXIST("22103","没有可用报名字段，会议报名表单设置有误"),
	EVENT_RETY_NO_EXIST("22104","请勿重复提交"),
	EVENT_RETYLIST_NO_EXIST("22105","注册类型不能为空"),
	EVENT_RETYENT_NO_EXIST("22106","不存在该条数据"),
	EVENT_DATAERROR("22107","数据有误"),

	EVENT_EVENTMENT_EXIST("22108","该用户已注册该会议，请勿重复提交"),

	EVENT_MANAGEPAYMENT_EXIST("22108","该订单已存在"),

	EVENT_MANAGEPAYMENT_EXCEPTION("22109","订单数量异常"),

	EVENT_RETYLIST_NO_RESON("32105","审核备注不能为空"),
	/** 22110 该用户尚未注册会议。 */
	EVENT_EVENTMENT_NO_EXIST ("22110", "该用户尚未注册会议。"),

	/** 22111 查询会议注册人数据异常。 */
	EVENTMENBER_LIST_SELECT_PARAMES_ERROR("22111","查询会议注册人数据异常（>1）,请联系主办方！"),

	/** 22112 该选项值已经存在。 */
	EVENT_FIEIF_IS_EXIST ("22112", "该选项值已经存在。"),

	/** 22113 用户手机号已存在。 */
	USER_VERCODE_EXCEPTION("22113", "用户手机号已存在!"),

	/** 22114 用户名密码错误。 */
	USER_ADMIN_EXCEPTION("22114", "用户名密码错误,请联系管理员!"),

	/** 22115 账号异常。 */
	USER_ADMIN_LOGIN_EXCEPTION("22115", "账号异常,请联系管理员!"),

	/** 22116 登录账号已存在。 */
	USER_LOGINNAME_EXCEPTION("22116", "登录账号已存在!"),

	/** 22117 请至少选择一个删除的目标。 */
	USER_NO_GOAL_EXCEPTION("22117", "请至少选择一个删除的目标!"),

	/** 22118 原始密码输入错误。 */
	USER_WRONG_PASSWORD_EXCEPTION("22118", "原始密码输入错误!"),

	/** 22119 密码输入不同。 */
	USER_NO_PASSWORD_EXCEPTION("22119", "密码输入不同!"),

	/** 22120 密码长度必须大于6位。 */
	USER_LENGTH_PASSWORD_EXCEPTION("22120", "密码长度必须大于6位!"),

	/** 22121 该会议已经存在banner会议banner推广位中。 */
	ADMIN_EVENT_BANNER_EXCEPTION("22121", "该会议已经存在推广位中!"),

	/** 22122 banner会议banner推广位已满。 */
	ADMIN_EVENT_BANNER_OVER("22122", "banner推广位已满!"),

	/** 22123 账号权限未开通 */
	ADMIN_ROLE_STATUS("22123", "账号权限未开通!"),

	/** 22124 无数据导出 */
	ADMIN_NO_DATA_TO_EXPLORE("22124", "至少选择一条数据!"),

	/** 22125 无法插入更多数据 */
	ADMIN_NO_DATA_TO_INSERT("22125", "无法插入更多数据!"),

	/** 22126 无效查询 */
	ADMIN_NO_INFO_MESSAGE("22126", "无效查询!"),

	/** 22127 只能注销审核通过且未用户 */
	ADMIN_EXTRACT_STATUS("22127", "只能注销审核通过且未用户!"),

	/** 22301 房型剩余数量不足。 */
	EVENT_ROOM_TYPE_SURPLUS_NUM_INSUFFICIENT ("22301", "房型剩余数量不足。"),

	/** 22302 订单已失效或被删除。 */
	EVENT_PAYMENT_STATUS_NOT_OK ("22302", "订单已失效或被删除。"),

	/** 22303 注册用户已完成注册缴费，不可删除 */
	EVENT_REGISTER_FOR_ROOM_DELETE_REGISTER_PAY_OK ("22303", "用户已完成缴费，不可删除"),

	/** 22304 注册用户已完成签到，不可删除 */
	EVENT_REGISTER_FOR_ROOM_DELETE_SIGEND_IN ("22304", "注册用户已完成签到，不可删除"),

	/** 22305 注册用户已完成住宿缴费，不可删除 */
	EVENT_REGISTER_FOR_ROOM_DELETE_PAY_OK ("22305", "注册用户已完成住宿缴费，不可删除"),

	/** 22306 用户已经预定酒店*/
	EVENT_REGISTER_FOR_ROOM_ORDER_OK ("22306", "用户已经预定酒店，不可重复预定,请前往个人中心查看"),

	/** 22307 用户已经预定酒店*/
	EVENT_REGISTER_FOR_ROOM_HOTEL_SET_NOT_OK ("22307", "主办方暂未设置酒店预定相关参数，不可预定！"),

	/** 22308 用户预定的房型类型与真实房型不匹配*/
	EVENT_REGISTER_FOR_ROOM_TYPE_NOT_MATCH ("22308", "用户预定的房型类型与真实房型不匹配！"),

	/** 22309 本次下单提交的已保存注册信息有误*/
	EVENT_REGISTER_ID_NOT_MATCH ("22309", "本次下单提交的已保存注册信息有误！"),

	/** 22310 导出数据为空，不可导出！*/
	EVENT_REGISTER_DATA_EMPTY ("22310", "导出数据为空，不可导出！"),

	/** 22311 短信模板不存在*/
	EVENT_SEND_MESSAGE_ERROR ("22311", "短信模板不存在！"),

	/** 22312 短信发送接收人为空*/
	EVENT_SEND_MESSAGE_RECEIVE_NULL ("22312", "短信发送接收人为空！"),

	/** 22313 主办方不提供发票*/
	EVENT_NOT_SUPPORT_INVOICE ("22313", "主办方不提供发票！"),

	/** 22314 导航不存在*/
	EVENT_NAV_NOT_EXIST ("22314", "导航不存在！"),

	/** 22315 重复签到*/
	EVENT_SIGN_REPEAT ("22315", "重复签到！"),

	/** 22316 主办方暂未进行签到设置*/
	EVENT_NO_SIGN_SETTING ("22316", "主办方暂未进行签到设置！"),

	/** 22317 签到失败*/
	EVENT_SIGN_ERROR ("22317", "签到失败,[%s]"),

	/** 22314 导航内容为空*/
	EVENT_NAV_CONTENT_NOT_EXIST ("22318", "导航内容为空！"),

	/** 22319 按钮不可关闭，已经有人注册会议了！*/
	EVENT_REGISTER_BUTTON_NOT_CLOSE ("22319", "按钮不可关闭，已经有人注册会议了！"),

	/** 22320 该序号已被使用，请输入其他排列序号*/
	EVENT_APP_STATUS ("22320", "该序号已被占用，请输入其他排列序号！"),

	/** 22321 电子发票详情尚未开出，请稍后查看详情 */
	EVENT_USER_INVOICE_PDF_NULL ("22321", "电子发票详情尚未开出，请稍后查看详情"),

	/** 22322 会议结束时间超过两个月，不可申请电子发票 */
	EVENT_USER_INVOICE_EVENT_TIME_OUT ("22322", "会议结束时间超过两个月，不可申请电子发票"),

	/** 23001 电子发票暂不对外开放。 */
	INVOICE_EMAIL_FLAG_ERROR ("23001", "电子发票暂不对外开放。"),

	/** 23002 电子发票的开票金额不得小于1元。 */
	INVOICE_EMAIL_AMOUNT_MIN ("23002", "电子发票的开票金额不得小于1元。"),

	/** 23003 电子发票不存在，请确认 */
	INVOICE_NULL_ERROR ("23003", "发票不存在，请确认"),

	/** 23004 发票尚未开出 */
	INVOICE_STATUS_NOT_DONE ("23004", "发票尚未开出"),

	/** 23320 标题不能为空*/
	EVENT_REGISTER_NO_EXIST("23320","标题不能为空"),

	/** 23320 标题不能为空*/
	EVENT_REGISTER_NO_EXISTS("23321","标题重复"),

	/** 23322 标题不能为空*/
	EVENT_C_EXISTS1("23322","请勿重复提交申请"),

	/** 23322 标题不能为空*/
	EVENT_A_EXISTS1("23323","该用户已激活"),

	/** 23324 支付方式不可切换，您已经选择了银行转账*/
	EVENT_ORDER_PAY_TYPE_FORBID("23324","支付方式不可切换，您已经选择了银行转账"),

	/*************************************** 公用操作数据库结果模块 ****************************************************/

	/** 10010 查询数据不存在 */
	QUERY_DATE_NOT_EXIST ("10010", "查询数据不存在"),

	/** 10011 查询[%s]不存在 */
	QUERY_DATE_NOT_EXIST_FORMAT ("10011", "查询[%s]不存在"),

	/** 10012 更新失败 */
	UPDATE_FAIL ("10012", "更新失败"),

	/** 10013 新增失败 */
	INSERT_FAIL ("10013", "新增失败"),

	/** 10014 删除失败 */
	DELETE_FAIL ("10014", "删除失败"),

	/** 10015 更新失败，有别人更新了这条数据 */
	UPDATE_FAIL_CHANGE ("10015", "更新失败，有别人更新了这条数据"),

	/****************************************** 短信发送结果模块 *************************************************/

	MESSAGE_SEND_FAIL("31001", "短信发送失败"),
	/** 31002 短信验证码不正确或已过期，请重试 */
	VER_CODE_CHECK_FAIL("31002", "短信验证码不正确或已过期，请重试"),

	/****************************************** 支付模块 *************************************************/

	/** 40001 创建微信订单失败，请稍后重试。 */
	PAY_WXPAY_UNIFIED_ORDER_ERROR ("40001", "创建微信订单失败，请稍后重试。"),

	/** 41001 下单失败，请稍后重试。 */
	CREATE_PAYMENT_FAIL ("41001", "下单失败，请稍后重试。"),

	/** 41008 下单失败，请稍后重试。 */
	CREATE_QUERY_ORDER_FAIL ("41009", "查询对账记录失败。"),

	/** 41010 删除交易流水失败 */
	PAYMENT_RECORD_DEL_ERROR_NULL ("41010", "删除交易流水失败，不存在可删除的数据。"),

	/** 41011 已存在有效订单，重复下单 */
	ORDER_EXIST_VALID_ERROR ("41011", "已存在有效订单，无需重复下单。"),

	/** 41012 单个开票失败 */
	APPLY_INVOICE_ONE_ERROR ("41012", "单个开票失败，请检查订单状态！"),

	/** 41013 已有签到数据，不可删除扫码项目 */
	ERROR_TO_DELETE_SIGN_SESSION ("41013", "已有签到数据，不可操作扫码项目！"),

	/****************************************** 上传图片模块 *************************************************/
	FILE_SIZE_PASS_LIMIT("42001","文件尺寸超过限制"),
	FILE_TYPE_NOT_PLEASED("42002","文件类型不符合要求"),

	/****************************************** 微信相关模块 *************************************************/
	FAIL_GET_OPENID("51001","获取openId失败，参数异常！"),
	FAIL_GET_JSAPI_TICKET("51002","获取JSAPI_TICKET失败！"),


	/****************************************** 结算相关模块 *************************************************/
	SETTLEMENT_TYPE_ERROR("61001","结算类型错误"),
	PLATFORM_RATE_ERROR("61002","平台费率设置异常"),
	SETTLEMENT_MONEY_ZERO("61003","结算金额不可为0"),
	SETTLEMENT_USERID_ERROR("61004","结算用户id异常"),
	SETTLEMENT_SERIVICEID_ERROR("61005","结算业务id异常"),
	SETTLEMENT_MONEY_ERROR("61006","结算金额异常"),
	WALLET_MONEY_ERROR("61106","提现尚未处理完成，无法进行下次提现"),
	WALLET_NO_MONEY_ERROR("61107","没有相关数据无法导出"),

	/****************************   直播视频   ****************************************/
	LIVE_PROGRAM_SETTLEMENT_ERROR_NOT_END("70001", "直播节目尚未结束，不可结算。"),

	;

	ErrorCode(String errorCode, String msg) {
		this.errorCode = errorCode;
		this.msg = msg;
	}

	private String errorCode;

	private String msg;

	public String getErrorCode() {
		return errorCode;
	}

	public String getMsg() {
		return msg;
	}
}

