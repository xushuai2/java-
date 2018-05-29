package demo.common;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * 系统常量类
 * 
 * @author 
 */
public class ConstantSanLin {

	// ------------------------------------------------关联表标识------------------------------------------------------
	/** 产品类型表 */
	public static final String TABLE_BAO_T_PRODUCT_TYPE_INFO = "BAO_T_PRODUCT_TYPE_INFO";
	/** 分配信息表 */
	public static final String TABLE_BAO_T_ALLOT_INFO = "BAO_T_ALLOT_INFO";
	/** 账户表 */
	public static final String TABLE_BAO_T_ACCOUNT_INFO = "BAO_T_ACCOUNT_INFO";
	/** 审核信息表 */
	public static final String TABLE_BAO_T_AUDIT_INFO = "BAO_T_AUDIT_INFO";
	/** 日志信息表 */
	public static final String TABLE_BAO_T_LOG_INFO = "BAO_T_LOG_INFO";
	/** 系统信息表 */
	public static final String TABLE_BAO_T_SYSTEM_MESSAGE_INFO = "BAO_T_SYSTEM_MESSAGE_INFO";
	/** 赎回信息表 */
	public static final String TABLE_BAO_T_ATONE_INFO = "BAO_T_ATONE_INFO";
	/** 还款记录表 */
	public static final String TABLE_BAO_T_REPAYMENT_RECORD_INFO = "BAO_T_REPAYMENT_RECORD_INFO";
	/** 投资信息表 */
	public static final String TABLE_BAO_T_INVEST_INFO = "BAO_T_INVEST_INFO";
	/** 用户信息表 */
	public static final String TABLE_BAO_T_CUST_INFO = "BAO_T_CUST_INFO";
	/** 用户联系人信息表 */
	public static final String TABLE_BAO_T_CONTACT_INFO = "BAO_T_CONTACT_INFO";
	/** 交易流水表 */
	public static final String TABLE_BAO_T_TRADE_FLOW_INFO = "BAO_T_TRADE_FLOW_INFO";
	/** 银行表 */
	public static final String TABLE_BAO_T_BANK_CARD_INFO = "BAO_T_BANK_CARD_INFO";
	/** BAO转账信息表 */
	public static final String TABLE_BAO_T_TRANS_ACCOUNT_INFO = "BAO_T_TRANS_ACCOUNT_INFO";
	/** 客户活动明细表 */
	public static final String TABLE_BAO_T_CUST_ACTIVITY_DETAIL="BAO_T_CUST_ACTIVITY_DETAIL";
	/** 银行卡解绑申请表 */
	public static final String TABLE_BAO_T_UNBIND_INFO = "BAO_T_UNBIND_INFO";
	/** 扩展表 */
	public static final String TABLE_BAO_T_EXPAND_INFO = "BAO_T_EXPAND_INFO";
	/** 客户申请表 */
	public static final String TABLE_BAO_T_CUST_APPLY_INFO = "BAO_T_CUST_APPLY_INFO";
	
	// ------------------------------------------------关联表字段名------------------------------------------------------
	/** 公共ID列名 */
	public static final String RELATE_COLUMN_NAME_ID = "ID";

	/** 公共客户ID列名 */
	public static final String RELATE_COLUMN_NAME_CUSTID = "CUST_ID";

	// ------------------------------------------------附件类型------------------------------------------------------
	/** 有效 **/
	public final static String VALID_STATUS_VALID = "有效";

	/** 无效 **/
	public final static String VALID_STATUS_INVALID = "无效";

	// -----------------------------------------------注册类型-------------------------------------------------------
	public static final String REG_ENABLE_STATUS = "正常";

	// -----------------------------------------------短信类型-------------------------------------------------------
	public static final String[] SMS_TYPE = { "注册", "手机找回密码", "绑定银行卡", "绑定邮箱", "邮件找回密码", "绑定手机", "提现", "手机找回提现密码", "修改绑定银行卡", "手机找回交易密码" };

	/** 短信类型 --注册 */
	public static final String SMS_TYPE_REGISTER = "注册";

	/** 短信类型 --手机找回密码 */
	public static final String SMS_TYPE_FIND_PASSWORD = "手机找回密码";

	/** 短信类型 --绑定银行卡 */
	public static final String SMS_TYPE_BANKCARD = "绑定银行卡";

	/** 短信类型 --修改绑定银行卡 */
	public static final String SMS_TYPE_MODIFY_BANKCARD = "修改绑定银行卡";

	/** 短信类型 --绑定邮箱 */
	public static final String SMS_TYPE_BINDING_EMAIL = "绑定邮箱";

	/** 短信类型 --邮件找回密码 */
	public static final String SMS_TYPE_FIND_PASSWORD_EMAIL = "邮件找回密码";

	/** 短信类型 --绑定手机 */
	public static final String SMS_TYPE_BINDING_MOBILE = "绑定手机";

	/** 短信类型 --绑定新手机 **/
	public static final String SMS_TYPE_BINDING_NEW_MOBILE = "绑定新手机";
	
	/** 短信类型 --修改绑定手机 **/
	public static final String SMS_TYPE_UPDATE_MOBILE = "修改绑定手机";

	/** 短信类型 --提现 */
	public static final String SMS_TYPE_WITHDRAWAL = "提现";

	/** 短信类型 --手机找回提现密码 */
	public static final String SMS_TYPE_WITHDRAWAL_BACK = "手机找回提现密码";

	/** 短信类型 --手机找回交易密码 */
	public static final String SMS_TYPE_TRADE_PASSWD = "手机找回交易密码";
	
	/** 短信类型 --提现申请成功 */
	public static final String SMS_TYPE_WITHDRAWAL_APPLY = "提现申请成功";
	
	/** 短信类型 --提现审核通过*/
	public static final String SMS_TYPE_WITHDRAW_SUCCESS = "提现审核通过";
	
	/** 短信类型 --提现审核拒绝*/
	public static final String SMS_TYPE_WITHDRAW_FAIL = "提现审核拒绝";
	
	/** 短信类型 --体验宝赎回*/
	public static final String SMS_TYPE_EXPERIENCE_WITHDRAW = "体验宝赎回";
	
	/** 短信类型 --解绑申请拒绝*/
	public static final String SMS_TYPE_UNBIND_REFUSE = "解绑申请拒绝";
	
	/** 短信类型 --解绑申请通过*/
	public static final String SMS_TYPE_UNBIND_PASS = "解绑申请通过";

	// -----------------------------------------------短信状态-------------------------------------------------------
	/** 短信状态 --已发送 */
	public static final String SMS_SEND_STATUS_SENT = "已发送";

	/** 短信状态 --未发送 */
	public static final String SMS_SEND_STATUS_UNSENT = "未发送";

	// -----------------------------------------------邮箱模板名称-------------------------------------------------------
	/** 邮箱绑定模板 **/
	public static final String BIND_MAIL_TEMPLATE = "hqb_bindEmailTemplate.ftl";

	// -----------------------------------------------客服信息-------------------------------------------------------
	/** 平台客服电话 */
	public static final String PLATFORM_SERVICE_TEL = "4000-858-308";

	/** 平台客服邮箱 */
	public static final String CUSTOMER_EMAIL = "service@shanlinbao.com";

	// ------------------------------------------------------------------------------------------------------
	/** 目标类型(手机,邮箱) */
	public static final String TARGET_TYPE_TEL = "手机";

	public static final String TARGET_TYPE_MAIL = "邮箱";

	// ---------------------------------------------客户状态---------------------------------------------------------
	/** 客户状态--是否启用-正常 **/
	public static final String ENABLE_STATUS_01 = "正常";

	/** 客户状态--是否启用-冻结 **/
	public static final String ENABLE_STATUS_02 = "冻结";

	// ------------------------------------------------客户性别------------------------------------------------------
	/** 客户性别--男 **/
	public static final String SEX_MAN = "男";

	/** 客户性别--女 **/
	public static final String SEX_WOMAN = "女";

	// ----------------------------------------------证件类型--------------------------------------------------------
	/** 证件类型--身份证 */
	public static final String CREDENTIALS_ID_CARD = "身份证";

	// ---------------------------------------------认证状态-----------------------------------------------------------
	/** 认证状态-- 未审核 */
	public static final String AUTH_STATUS_UNAUDIT = "未审核";

	/** 认证状态-- 通过 */
	public static final String AUTH_STATUS_PASS = "通过";

	/** 认证状态-- 驳回 */
	public static final String AUTH_STATUS_REJECT = "驳回";

	/** 认证状态-- 失效 */
	public static final String AUTH_STATUS_INVALID = "失效";

	
	// ---------------------------------------------善林宝-产品类型-----------------------------------------------------------
	/** 产品类型-活期宝 */
	public static final String PRODUCT_TYPE_01 = "活期宝";
	/** 产品类型-善林理财 */
	public static final String PRODUCT_TYPE_02 = "善林理财"; 
	/** 产品类型-体验宝 */
	public static final String PRODUCT_TYPE_03 = "体验宝";
	/** 产品类型-定期宝 */
	public static final String PRODUCT_TYPE_04 = "定期宝";

	// ----------------------------------------------分配状态--------------------------------------------------------
	public static final String ALLOT_STATUS_01 = "已分配";
	public static final String ALLOT_STATUS_02 = "已使用";
	public static final String ALLOT_STATUS_03 = "已撤销";

	/** 执行状态-已执行 */
	public static final String EXEC_STATUS = "已执行";
	/** 执行状态-未执行 */
	public static final String EXEC_UN_STATUS = "未执行";

	public static final String PASS_TYPE_LOGIN = "1";

	public static final String PASS_TYPE_TRADE = "2";

	// -----------------------------------------------交易状态-------------------------------------------------------
	/** 交易状态--未处理 */
	public static final String TRADE_STATUS_01 = "未处理";

	/** 交易状态--处理中 */
	public static final String TRADE_STATUS_02 = "处理中";

	/** 交易状态--处理成功 */
	public static final String TRADE_STATUS_03 = "处理成功";

	/** 交易状态--处理失败 */
	public static final String TRADE_STATUS_04 = "处理失败";

	// ---------------------------------------------------提现审核状态---------------------------------------------------
	/** 提现审核状态--审核中 */
	public static final String AUDIT_STATUS_REVIEWD = "审核中";

	/** 提现审核状态--拒绝 */
	public static final String AUDIT_STATUS_REfUSE = "拒绝";

	/** 提现审核状态--通过 */
	public static final String AUDIT_STATUS_PASS = "通过";

	// ---------------------------------------------------审核日志类型---------------------------------------------------
	/** 审核日志类型--审核中 */
	public static final String LOG_TYPE_AUDIT = "审核";

	// ---------------------------------------------网站公告状态-----------------------------------------------------------
	/** 网站公告状态-新建 **/
	public static final String AFFICHE_STATUS_NEW = "新建";

	/** 网站公告状态-已发布 **/
	public static final String AFFICHE_STATUS_PUBLISHED = "已发布";

	/** 网站公告状态-已失效 **/
	public static final String AFFICHE_STATUS_INVALIED = "已失效";

	/** 网站公告状态-已删除 **/
	public static final String AFFICHE_STATUS_DELETED = "已删除";

	// ---------------------------------------------网站公告类型-----------------------------------------------------------
	/** 网站公告类型-网站公告 **/
	public static final String AFFICHE_TYPE_ALL = "网站公告";

	/** 网站公告类型-网站通知 **/
	public static final String AFFICHE_TYPE_NOTICE = "网站通知";

	/** 网站公告类型 -行业动态 **/
	public static final String AFFICHE_TYPE_NEWS = "行业动态";

	/** 网站公告类型-公司动态 **/
	public static final String AFFICHE_TYPE_COMPANY = "公司动态";

	/** 网站公告类型-banner **/
	public static final String AFFICHE_TYPE_BANNER = "banner";
	
	/** 网站公告类型-媒体报道 **/
	public static final String AFFICHE_TYPE_MEDIA = "媒体报道";

	// -----------------------------------------------消息状态-------------------------------------------------------
	/** 消息状态 未读 */
	public static final String SITE_MESSAGE_NOREAD = "未读";

	/** 消息状态 已读 */
	public static final String SITE_MESSAGE_ISREAD = "已读";

	// ----------------------------------------------产品状态--------------------------------------------------------
	/** 产品状态-开放中 */
	public static final String PRODUCT_STATUS_BID_ING = "开放中";
	/** 产品状态-已满标 */
	public static final String PRODUCT_STATUS_BID_FINISH = "已满额";

	// ----------------------------------------------投资方式--------------------------------------------------------
	/** 投资方式-加入 */
	public static final String INVEST_METHOD_JOIN = "加入";

	// ----------------------------------------------投资来源--------------------------------------------------------
	/** 投资来源-PC端 */
	public static final String INVEST_SOURCE_PC = "PC端";
	/** 投资来源-APP端 */
	public static final String INVEST_SOURCE_APP = "APP端";

	// ----------------------------------------------赎回方式--------------------------------------------------------
	/** 赎回方式-快速赎回 */
	public static final String ATONE_METHOD_IMMEDIATE = "快速赎回";
	/** 赎回方式-普通赎回 */
	public static final String ATONE_METHOD_NORMAL = "普通赎回";
	/** 赎回方式-提前赎回 */
	public static final String ATONE_METHOD_ADVANCE = "提前赎回";
	/** 赎回方式-到期赎回 */
	public static final String ATONE_METHOD_EXPIRE = "到期赎回";

	// ----------------------------------------------账户类型--------------------------------------------------------
	public static final String ACCOUNT_TYPE_BASIC = "01";

	// ----------------------------------------------资金方向--------------------------------------------------------
	/** 资金方向--转入 */
	public static final String BANKROLL_FLOW_DIRECTION_IN = "收入";

	/** 资金方向--转出 */
	public static final String BANKROLL_FLOW_DIRECTION_OUT = "支出";

	/** 资金方向--无 */
	public static final String BANKROLL_FLOW_DIRECTION_NONE = "";

	// ----------------------------------------------还款状态--------------------------------------------------------
	/** 还款状态-未还款 */
	public static final String REPAYMENT_STATUS_WAIT = "未还款";
	/** 还款状态-已还款 */
	public static final String REPAYMENT_STATUS_CLEAN = "已还款";
	
	// ----------------------------------------------债权状态--------------------------------------------------------
	public static final String CREDIT_RIGHT_STATUS_CLEAN = "结清";

	// ----------------------------------------------账户类型--------------------------------------------------------
	/** 账户类型-总帐 */
	public static final String ACCOUNT_TYPE_MAIN = "总账";
	/** 账户类型-分账 */
	public static final String ACCOUNT_TYPE_SUB = "分账";

	// -----------------------------------------------客户类型-------------------------------------------------------
	/**
	 * 公司收益账户
	 */
	public static final String ACCOUNT_TYPE_COMPANY = "公司收益账户";
	/**
	 * 客户账户
	 */
	public static final String ACCOUNT_TYPE_CUSTOMER = "客户账户";
	/**
	 * 居间人账户
	 */
	public static final String ACCOUNT_TYPE_MIDDLE = "居间人账户";
	/**
	 * 定期宝账户
	 */
	public static final String ACCOUNT_TYPE_SLBAO = "定期宝账户";	

	
	/** 初始推广等级 */
	public static final String SPREAD_LEVEL_ROOT = "0";
	/** 推广来源ID */
	public static final String INVITE_ORIGIN_ID_ROOT = "0";

	// ----------------------------------------------系统初始化用户ID--------------------------------------------------------
	/** 系统管理员用户主键ID */
	public static final String CUST_ADMIN_ID = "SYSTEM_ADMIN";

	/** 客服用户主键ID */
	public static final String CUST_KF_ID = "KF0001";
	
	/** 居间人主键ID*/
	public static final String CUST_ID_CENTER = "C00001";
	/** 收益人主键ID*/
	public static final String CUST_ID_ERAN = "C00002";

	// ----------------------------------------------系统用户--------------------------------------------------------
	/** 系统用户 */
	public static final String SYSTEM_USER_BACK = "root";
	// ----------------------------------------------公司主账户ID--------------------------------------------------------
	/** 公司主账户-居间人账户 ID*/
	public static final String ACCOUNT_ID_CENTER = "A00001";
	/** 公司主账户-收益账户 ID*/
	public static final String ACCOUNT_ID_ERAN = "A00002";
	/** 公司主账户-风险金账户 ID*/
	public static final String ACCOUNT_ID_RISK = "A00003";
	
	// ----------------------------------------------公司分账户ID--------------------------------------------------------
	/** 公司分账户-居间人账户ID */
	public static final String SUB_ACCOUNT_ID_CENTER = "SA0001";
	/** 公司分账户-收益账户 ID*/
	public static final String SUB_ACCOUNT_ID_ERAN = "SA0002";
	
	/** 定期宝公司分账户-居间人账户ID */
	public static final String SUB_ACCOUNT_ID_CENTER_11 = "SA0011";
	/** 定期宝公司分账户-收益账户 ID*/
	public static final String SUB_ACCOUNT_ID_ERAN_12 = "SA0012";
	/** 定期宝公司分账户-风险金账户 ID*/
	public static final String SUB_ACCOUNT_ID_ERAN_13 = "SA0013";
	
	// ----------------------------------------------公司主账户编号--------------------------------------------------------
	/** 公司主账户-居间人账户 编号*/
	public static final String ACCOUNT_NO_CENTER = "ACCT000001";
	/** 公司主账户-收益账户 编号*/
	public static final String ACCOUNT_NO_ERAN = "ACCT000002";
	/** 公司主账户-风险金账户 编号*/
	public static final String ACCOUNT_NO_RISK = "ACCT000003";
	
	// ----------------------------------------------公司分账户编号--------------------------------------------------------
	/** 公司分账户-居间人账户编号 */
	public static final String SUB_ACCOUNT_NO_CENTER = "SACCT00001";
	/** 公司分账户-收益账户 编号*/
	public static final String SUB_ACCOUNT_NO_ERAN = "SACCT00002";
	
	/** 公司分账户-居间人账户编号 */
	public static final String SUB_ACCOUNT_NO_CENTER_11 = "SACCT00011";
	/** 公司分账户-收益账户 编号*/
	public static final String SUB_ACCOUNT_NO_ERAN_12 = "SACCT00012";	
	/** 公司分账户-风险金账户 编号*/
	public static final String SUB_ACCOUNT_NO_RISK_13 = "SACCT00013";

	// -----------------------------------------------------job名称-------------------------------------------------
	/** 定时任务--债权价值计算 */
	public static final String JOB_NAME_LOANVAULECALCULATEJOB = "债权价值计算";
	/** 定时任务--定时发标 */
	public static final String JOB_NAME_RELEASEJOB = "定时发标";
	/** 定时任务--定时关标 */
	public static final String JOB_NAME_CLOSEJOB = "定时关标";
	/** 定时任务--每日结息 */
	public static final String JOB_NAME_DAILYACCRUALJOB = "每日结息";
	/** 定时任务--可开放价值计算 */
	public static final String JOB_NAME_OPENVALUEJOB = "可开放价值计算";
	/** 定时任务--还款计算 */
	public static final String JOB_NAME_REPAYMENTJOB = "还款计算";
	/** 定时任务--赎回详情 */
	public static final String JOB_NAME_ATONEDETAIL = "赎回详情";	
	/** 定时任务--体验宝每日结息 */
	public static final String JOB_NAME_TYBDAILYACCRUALJOB = "体验宝每日结息";	
	/** 定时任务--体验宝到期赎回 */
	public static final String JOB_NAME_TYBWITHDRAWJOB = "体验宝到期赎回";
	/** 定时任务--推荐奖励 */
	public static final String JOB_NAME_RECOMMENDEDAWARDS = "推荐奖励记录生成";
	/** 定时任务--对外定时通知 */
	public static final String JOB_NAME_OPENSERVICE_NOTIFY = "对外定时通知";
	/** 定时任务--体验宝到期赎回 短信通知用户任务*/
	public static final String JOB_NAME_TYBWITHDRAWSENDSMSJOB = "体验宝到期赎回短信通知用户";
	/** 定时任务--补银行卡 */
	public static final String JOB_NAME_MENDBANK = "补银行卡信息";	
	/** 定时任务--每日还款数据发送邮件 */
	public static final String JOB_REPAYMENT_EMAIL = "每日还款数据发送邮件";	
	/** 定时任务--公司回购 */
	public static final String JOB_NAME_TERM_ATONE_BUY = "公司回购";	
	/** 定时任务--定期宝赎回到帐 */
	public static final String JOB_NAME_TERM_ATONE_SETTLEMENT = "定期宝赎回到帐";	
	/** 定时任务--到期赎回 */
	public static final String JOB_NAME_TERM_ATONE_WITHDRAW = "到期赎回";	
	/** 定时任务--定期宝每日结息 */
	public static final String JOB_NAME_TERM_DALIY_SETTLEMENT = "定期宝每日结息";
	/** 定时任务--金牌推荐人每日结息 */
	public static final String JOB_NAME_GOLD_DALIY_SETTLEMENT = "金牌推荐人每日结息";
	/** 定时任务--金牌推荐人到期处理 */
	public static final String JOB_NAME_GOLD_WITHDRAW = "金牌推荐人到期处理";
	
	// ------------------------------------------------日期格式-----------------------------------------------------------------
	public static final String DATE_FORMAT_STYLE_YYYYMMDD = "yyyyMMdd";

	// ------------------------------------------------操作类型-----------------------------------------------------------------
	/** 操作类型--客户冻结 */
	public static final String OPERATION_TYPE_01 = "客户冻结";
	/** 操作类型--客户解冻 */
	public static final String OPERATION_TYPE_02 = "客户解冻";
	/** 操作类型--实名认证 */
	public static final String OPERATION_TYPE_03 = "实名认证";
	/** 操作类型--登录 */
	public static final String OPERATION_TYPE_04 = "登录";
	/** 操作类型--充值 */
	public static final String OPERATION_TYPE_05 = "充值";
	/** 操作类型--提现 */
	public static final String OPERATION_TYPE_06 = "提现";
	/** 操作类型--绑定银行卡 */
	public static final String OPERATION_TYPE_07 = "绑定银行卡";
	/** 操作类型--购买活期宝 */
	public static final String OPERATION_TYPE_08 = "购买活期宝";
	/** 操作类型--赎回活期宝 */
	public static final String OPERATION_TYPE_09 = "赎回活期宝";
	/** 操作类型--提现审核 */
	public static final String OPERATION_TYPE_10 = "提现审核";
	/** 操作类型--赎回审核 */
	public static final String OPERATION_TYPE_12 = "赎回审核";
	/** 操作类型--注册送体验金 */
	public static final String OPERATION_TYPE_13 = "注册送体验金";
	/** 操作类型--购买体验宝 */
	public static final String OPERATION_TYPE_14 = "购买体验宝";
	/** 操作类型--赎回体验宝 */
	public static final String OPERATION_TYPE_15 = "赎回体验宝";
	/** 操作类型--注册 */
	public static final String OPERATION_TYPE_16 = "注册";
	/** 操作类型--客户反馈审核 */
	public static final String OPERATION_TYPE_17 = "客户反馈审核";
	/** 操作类型--解绑银行卡 */
	public static final String OPERATION_TYPE_18 = "解绑银行卡";
	/** 操作类型--用户信息修改 */
	public static final String OPERATION_TYPE_19 = "用户信息修改";
	/** 操作类型--联系人信息修改 */
	public static final String OPERATION_TYPE_20 = "联系人信息修改";
	/** 操作类型--购买定期宝 */
	public static final String OPERATION_TYPE_21 = "购买定期宝";
	/** 操作类型--赎回定期宝 */
	public static final String OPERATION_TYPE_22 = "赎回定期宝";
	/** 操作类型--金牌推荐人申请 */
	public static final String OPERATION_TYPE_23 = "金牌推荐人申请";

	// ------------------------------------------------保障方式-----------------------------------------------------------------
	/** 保障方式--01 */
	public static final String ENSURE_METHOD_01 = "风险保障金";
	
	// -------------------------------------------注册------------------------------
	public static final String CUST_SOURCE_WAP = "wap";
	public static final String CUST_TYPE = "理财客户";
	
	// --------------------------------------头像上传----------------------------------
	public static final List<String> portraitExtList = Arrays.asList("jpg", "png");
	
	// ----------------------------------------------活动ID--------------------------------------------------------
	/** 活动-注册送体验金 ID*/
	public static final String ACTIVITY_ID_REGIST_01 = "1";
	/** 活动-推荐有奖 ID*/
	public static final String ACTIVITY_ID_REGIST_02 = "2";
	/** 活动-推荐送体验金 ID*/
	public static final String ACTIVITY_ID_REGIST_03 = "3";
	/** 活动-购买定期宝送体验金 ID*/
	public static final String ACTIVITY_ID_REGIST_04 = "4";

	// ------------------------------------------密码等级------------------------------------
	public static final String PWD_LEVEL_LOW = "低";
	public static final String PWD_LEVEL_MIDDLE = "中";
	public static final String PWD_LEVEL_HIGH = "高";

	
	/** 体验宝体验天数：15天 */
	//public static final String EXPIRE_DAYS="15";
	
	//---------------------------------------------意见类型---------------------------------------------
	public static final String SUGGESTION_TYPE_FEEDBACK = "意见反馈";

	// ----------------------------------------------体验金交易状态--------------------------------------------------------
	/** 交易状态-已领取*/
	public static final String USER_ACTIVITY_TRADE_STATUS_01 = "已领取";
	/** 交易状态-部分使用*/
	public static final String USER_ACTIVITY_TRADE_STATUS_02 = "部分使用";
	/** 交易状态-全部使用*/
	public static final String USER_ACTIVITY_TRADE_STATUS_03 = "全部使用";
	/** 交易状态-已过期*/
	public static final String USER_ACTIVITY_TRADE_STATUS_04 = "已过期";
	
	// ----------------------------------------------推荐有奖交易状态--------------------------------------------------------
	/** 交易状态-已结算*/
	public static final String USER_ACTIVITY_TRADE_STATUS_05 = "已结算";
	/** 交易状态-未结算*/
	public static final String USER_ACTIVITY_TRADE_STATUS_06 = "未结算";
	
	// ----------------------------------------------支付方式--------------------------------------------------------
	/** 支付方式-认证支付*/
	public static final String PAY_TYPE_01 = "AUTH_PAY";
	/** 支付方式-网银支付*/
	public static final String PAY_TYPE_02 = "ONLINE_BANK_PAY";
	
	// ----------------------------------------------推荐有奖奖励层级--------------------------------------------------------
	/** 推荐有奖奖励层级*/
	public static final int AWARD_SPREAD_LEVEL = 8;
	
	// ----------------------------------------------奖励形式--------------------------------------------------------
	/** 奖励形式-体验金*/
	public static final String REAWARD_SPREAD_01 = "体验金";
	/** 奖励形式-现金*/
	public static final String REAWARD_SPREAD_02 = "现金";
	/** 奖励形式-优惠劵*/
	public static final String REAWARD_SPREAD_03 = "优惠劵";
	
	// ----------------------------------------------活动来源--------------------------------------------------------
	/** 活动来源-注册*/
	public static final String ACTIVITY_SOURCE_01 = "注册";
	/** 活动来源-投资*/
	public static final String ACTIVITY_SOURCE_02 = "投资";
	/** 活动来源-推荐送体验金*/
	public static final String ACTIVITY_SOURCE_03 = "推荐送体验金";
	/** 活动来源-定期宝送体验金*/
	public static final String ACTIVITY_SOURCE_04 = "定期宝送体验金";
	
	//-----------------------------------------------还款方式---------------------------------------------------------
	/** 还款方式-等额本息*/
	public static final String REPAYMENT_METHOD_01="等额本息";
	/** 还款方式-每期还息到期付本*/
	public static final String REPAYMENT_METHOD_02="先息后本";
	/** 还款方式-到期一次性还本付息*/
	public static final String REPAYMENT_METHOD_03="到期还本付息";
	
	//-----------------------------------------------还款方式---------------------------------------------------------
	/**银行卡是否默认**/
	public static final String BANKCARD_DEFAULT="1";
	//-----------------------------------------------调帐类型---------------------------------------------------------

	public static final String TANS_ACCOUNT_TYPE_03="调账";
	public static final String TANS_ACCOUNT_TYPE_01="投资奖励";
	public static final String TANS_ACCOUNT_TYPE_02="推荐奖励";
	public static final String TANS_ACCOUNT_TYPE_04="推广佣金";
	public static final String TANS_ACCOUNT_TYPE_05="推广奖励";

	//-----------------------------------------------通道类型---------------------------------------------------------
	public static final String CHANNEL_NO_DIANZAN = "2015070100000001";
	
	//-----------------------------------------------通道类型---------------------------------------------------------
	public static final String THIRD_PARTY_TYPE_DIANZAN = "点赞网";
	public static final String THIRD_PARTY_TYPE_SHANLIN = "善林宝";		
	
	//-----------------------------------------------银行卡解约类型---------------------------------------------------------
	/**银行卡解约类型-已丢失**/
	public static final String UNBIND_CARD_TYPE_LOST = "已丢失";
	/**银行卡解约类型-未丢失**/
	public static final String UNBIND_CARD_TYPE_UNLOST = "未丢失";
	/**银行卡解约附件类型-手持银行卡照片**/
	public static final String UNBIND_CARD_ATTACHMENT_TYPE_BANK_CARD = "手持银行卡照片";
	/**银行卡解约附件类型-银行挂失证明**/
	public static final String UNBIND_CARD_ATTACHMENT_TYPE_BANK_LOST = "银行挂失证明";
	/**银行卡解约附件类型-手持身份证正面**/
	public static final String UNBIND_CARD_ATTACHMENT_TYPE_PAPER_BEFORE = "手持身份证正面";
	/**银行卡解约附件类型-手持身份证反面**/
	public static final String UNBIND_CARD_ATTACHMENT_TYPE_PAPER_BEHIND = "手持身份证反面";
	/**银行卡解约附件文件类型-WORD**/
	public static final String UNBIND_CARD_ATTACHMENT_DOC_TYPE_WORD = "WORD";
	/**银行卡解约附件文件类型-EXCEL**/
	public static final String UNBIND_CARD_ATTACHMENT_DOC_TYPE_EXCEL = "EXCEL";
	/**银行卡解约附件文件类型-PIG**/
	public static final String UNBIND_CARD_ATTACHMENT_DOC_TYPE_PIG = "PIG";
	
	/**债权最小年化利率**/
	public static final String LOAN_MIN_YEAR_RATE="0.07";
	/**债权最大年化利率**/
	public static final String LOAN_MAX_YEAR_RATE="0.2";
	
	//-----------------------------------------------邮件服务器参数定义---------------------------------------------------------
	/**邮件发送平台名称**/
	public static final String FORM_PLAT_NAME = "善林宝";
	/**邮件发送平台服务邮件地址**/
	public static final String PLAT_EMAIL_ADDRESS = "service@shanlinbao.com";
	
	// ----------------------------------------------定期宝类型---------------------------------------------------
	public final static String BAO_FIXEDINVESTMENT_TYPE_CY = "持有中";
	public final static String BAO_FIXEDINVESTMENT_TYPE_SH = "赎回中";
	public final static String BAO_FIXEDINVESTMENT_TYPE_TC = "已退出";
	
	// ----------------------------------------------定期宝投资状态---------------------------------------------------
	/**定期宝投资状态--收益中**/
	public final static String TERM_INVEST_STATUS_EARN = "收益中";
	/**定期宝投资状态--到期处理中**/
	public final static String TERM_INVEST_STATUS_WAIT = "到期处理中";
	/**定期宝投资状态--已到期**/
	public final static String TERM_INVEST_STATUS_FINISH = "已到期";
	/**定期宝投资状态--提前赎回中**/
	public final static String TERM_INVEST_STATUS_ADVANCE = "提前赎回中";
	/**定期宝投资状态--提前赎回**/
	public final static String TERM_INVEST_STATUS_ADVANCE_FINISH = "提前赎回";
	
		
	public final static String PRODUCT_ID_GOLD = "金牌推荐人佣金与奖励";

	//-----------------金牌推荐人申请条件常量-----------------------------------------------------------------------------------------------------------------------------------------------
	/**定期宝在投金额最小金额**/
	public final static BigDecimal INEST_AMOUNT_MIN = new BigDecimal("1000");
	/**推荐人数**/
	public final static int RECOMMEND_COUNT = 5;
	
	//-----------------金牌推荐人申请状态-----------------------------------------------------------------------------------------------------------------------------------------------
	/**申请状态-没有申请**/
	public final static String UNAPPLY_RECOMMEND = "没有申请";
	/**申请状态-申请审核中**/    
	public final static String APPLYING_RECOMMEND = "申请审核中";
	/**申请状态-已申请**/
	public final static String APPLYED_RECOMMEND = "已申请";
	/**申请状态-申请拒绝**/
	public final static String APPLYED_RECOMMEND_REFUSE = "申请拒绝";
	
	// ---------------金牌推荐人申请申核状态------------------------------------------------------------------------------------------------------------------------------------------
	/** 金牌推荐人申请申核状态--审核中 */
	public static final String AUDIT_STATUS_REVIEWD_RECOMMEND = "审核中";
	/** 金牌推荐人申请申核状态--拒绝 */
	public static final String AUDIT_STATUS_REfUSE_RECOMMEND = "拒绝";
	/** 金牌推荐人申请申核状态--通过 */
	public static final String AUDIT_STATUS_PASS_RECOMMEND = "通过";
	
	// ---------------金牌推荐人状态------------------------------------------------------------------------------------------------------------------------------------------
	/** 是否金牌推荐人状态--是 */
	public static final String IS_RECOMMEND_YES = "是";
	/** 是否金牌推荐人状态--否 */
	public static final String IS_RECOMMEND_NO = "否";

}



