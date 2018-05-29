package demo.Entity;

import java.util.Date;

public class AppStatistics extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6894684995683587029L;

	private Integer id;

    private Integer appid;

    private String appname;

    private Long request;

    private Long click;

    private Long imp;

    private Double revenue;

    private Date statisticaldate;

    private Date wtime;

    private Integer conversion;

    private Double ecpm;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAppid() {
        return appid;
    }

    public void setAppid(Integer appid) {
        this.appid = appid;
    }

    public String getAppname() {
        return appname;
    }

    public void setAppname(String appname) {
        this.appname = appname == null ? null : appname.trim();
    }

    public Long getRequest() {
        return request;
    }

    public void setRequest(Long request) {
        this.request = request;
    }

    public Long getClick() {
        return click;
    }

    public void setClick(Long click) {
        this.click = click;
    }

    public Long getImp() {
        return imp;
    }

    public void setImp(Long imp) {
        this.imp = imp;
    }

    public Double getRevenue() {
        return revenue;
    }

    public void setRevenue(Double revenue) {
        this.revenue = revenue;
    }

    public Date getStatisticaldate() {
        return statisticaldate;
    }

    public void setStatisticaldate(Date statisticaldate) {
        this.statisticaldate = statisticaldate;
    }

    public Date getWtime() {
        return wtime;
    }

    public void setWtime(Date wtime) {
        this.wtime = wtime;
    }

    public Integer getConversion() {
        return conversion;
    }

    public void setConversion(Integer conversion) {
        this.conversion = conversion;
    }

    public Double getEcpm() {
        return ecpm;
    }

    public void setEcpm(Double ecpm) {
        this.ecpm = ecpm;
    }
}