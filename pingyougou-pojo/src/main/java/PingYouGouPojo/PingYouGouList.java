package PingYouGouPojo;

import java.io.Serializable;
import java.util.List;
import BeanPingYouGou.PingYouGouPoJo;

public class PingYouGouList implements Serializable{
		private List<PingYouGouPoJo> bran;
		private List<PingYouGouPoJo> Specification;
		
		
		public PingYouGouList() {
			super();
		}
		public PingYouGouList(List<PingYouGouPoJo> bran, List<PingYouGouPoJo> specification) {
			super();
			this.bran = bran;
			Specification = specification;
		}
		public List<PingYouGouPoJo> getBran() {
			return bran;
		}
		public void setBran(List<PingYouGouPoJo> bran) {
			this.bran = bran;
		}
		public List<PingYouGouPoJo> getSpecification() {
			return Specification;
		}
		public void setSpecification(List<PingYouGouPoJo> specification) {
			Specification = specification;
		}
		
		
}
