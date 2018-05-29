package demo.common;


import java.util.List;

public class PageData<T> {
	// 分页结果集
	private List<T> dataList = null;
	// 记录总数
	private int totalcount = 0;
	// 每页显示记录数
	private int pageSize = 0;
	// 当前页数
	private int currentPage = 1;
	/*初始化分页组件*/
	public PageData(int totalcount, int pageSize, int currentPage,List<T> dataList) {
		setTotalcount(totalcount);
		setPageSize(pageSize);
		setCurrentPage(currentPage);
		setDataList(dataList);
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

	public int getTotalcount() {
		return totalcount;
	}

	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPageCount() {
		int p=0;
		if (totalcount % pageSize == 0) {
			p=(int) (totalcount / pageSize);
		} else {
			p=(int) (totalcount / pageSize + 1);
		}
		return p==0?1:p;
	}
}
