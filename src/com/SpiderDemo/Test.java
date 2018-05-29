package com.SpiderDemo;

import java.util.List;

public class Test {
	 @org.junit.Test
	 public void getDatasByClass()
	 {
          Rule rule = new Rule(
          "http://www1.sxcredit.gov.cn/public/infocomquery.do?method=publicIndexQuery",
          new String[] { "query.enterprisename","query.registationnumber" }, 
          new String[] { "兴网","" },
          "cont_right",
          Rule.CLASS,
          Rule.POST);
          List<LinkTypeData> extracts = ExtractService.extract(rule);
          printf(extracts);
      }
  
      @org.junit.Test
      public void getDatasByCssQuery()
      {
          Rule rule = new Rule("http://www.11315.com/search",
                  new String[] { "name" }, new String[] { "兴网" },
                  "div.g-mn div.con-model", Rule.SELECTION, Rule.GET);
          List<LinkTypeData> extracts = ExtractService.extract(rule);
          printf(extracts);
      }
      
      
      /**
      * 使用百度新闻，只设置url和关键字与返回类型
      * 我们只设置了链接、关键字、和请求类型，不设置具体的筛选条件。
      * 结果：有一定的垃圾数据是肯定的，但是需要的数据肯定也抓取出来了。我们可以设置Rule.SECTION,以及筛选条件进一步的限制。
      */
      @org.junit.Test
      public void getDatasByCssQueryUserBaidu()
      {
          Rule rule = new Rule("http://news.baidu.com/ns",
                   new String[] { "word" }, new String[] { "楼市" },
                   null, -1, Rule.GET);
          List<LinkTypeData> extracts = ExtractService.extract(rule);
          printf(extracts);
      }
  
      public void printf(List<LinkTypeData> datas)
      {
          for (LinkTypeData data : datas)
          {
              System.out.println(data.getLinkText());
              System.out.println(data.getLinkHref());
              System.out.println("***********************************");
          }
  
      }
}
