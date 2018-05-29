package demo.SpringEl;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.common.TemplateParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;


/**
 * 
 * @author dell
 *
 */
public class ExpressionUtils {
	private static ExpressionParser parser = new SpelExpressionParser();
	private static Map<String,Expression> spelExpressionCaches = new ConcurrentHashMap<String,Expression>();
	public static String getCommExpression(String value,Map<String,Object> params){
		StandardEvaluationContext context = new StandardEvaluationContext();
		context = initCommContext(context);
		//对变量赋值
		if(params!=null){
			context.setVariables(params);
		}
		Expression expression = spelExpressionCaches.get(value);
		if(expression==null){
			expression = parser.parseExpression(value,new TemplateParserContext());
			spelExpressionCaches.put(value,expression);
		}
		return expression.getValue(context,String.class);
	}
	static StandardEvaluationContext initCommContext(StandardEvaluationContext context){
		Calendar cal=Calendar.getInstance();//使用日历类
		
		context.setVariable("time",new Date());
		context.setVariable("year",cal.get(Calendar.YEAR));
		context.setVariable("month",cal.get(Calendar.MONTH)+1);
		context.setVariable("day",cal.get(Calendar.DAY_OF_MONTH));
		
		
		try {
			context.registerFunction("getName",demoMethod.class.getMethod("getName"));//xushuai
			context.registerFunction("getDate",DateUtil.class.getMethod("getDate",Integer.class));
			context.registerFunction("getDateString",DateUtil.class.getMethod("getDateString",Integer.class,String.class));
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}
		return context;
	}
	
}
