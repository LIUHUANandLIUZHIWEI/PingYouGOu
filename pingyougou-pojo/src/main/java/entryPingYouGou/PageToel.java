package entryPingYouGou;

import java.io.Serializable;
import java.util.List;

public class PageToel<T> implements Serializable {
		private long pageTole;
		private List<T> list;
		
		public PageToel(long pageTole, List<T> list) {
			super();
			this.pageTole = pageTole;
			this.list = list;
		}
		public long getPageTole() {
			return pageTole;
		}
		public void setPageTole(long pageTole) {
			this.pageTole = pageTole;
		}
		public List<T> getList() {
			return list;
		}
		public void setList(List<T> list) {
			this.list = list;
		}
		
}
